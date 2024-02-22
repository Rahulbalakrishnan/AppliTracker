package com.rahul.applitracker


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.rahul.applitracker.database.room.ApplicationDatabase
import com.rahul.applitracker.database.room.ApplicationViewModel
import com.rahul.applitracker.navigation.AddScreen
import com.rahul.applitracker.navigation.Home
import com.rahul.applitracker.ui.composable.AddApplication
import com.rahul.applitracker.ui.composable.ApplicationScreen
import com.rahul.applitracker.ui.theme.AppliTrackerTheme

class MainActivity : ComponentActivity() {

    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            ApplicationDatabase::class.java,
            "Applications.db"
        ).build()
    }
    private val viewModel by viewModels<ApplicationViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return ApplicationViewModel(db.dao) as T
                }
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppliTrackerTheme {

                val state by viewModel.state.collectAsState()
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Home.route
                ) {
                    composable(Home.route) {
                        ApplicationScreen(state = state, onEvent = viewModel::onEvent, navController)
                    }
                    composable(AddScreen.route) {
                        AddApplication(state = state, onEvent = viewModel::onEvent, navController)
                    }

                }


            }


        }
    }
}


