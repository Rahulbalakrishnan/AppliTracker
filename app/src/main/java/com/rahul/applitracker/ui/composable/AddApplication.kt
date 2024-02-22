package com.rahul.applitracker.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.rahul.applitracker.R
import com.rahul.applitracker.database.room.ApplicationEvent
import com.rahul.applitracker.database.room.ApplicationState
import com.rahul.applitracker.navigation.Home
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun AddApplication(
    state: ApplicationState,
    onEvent: (ApplicationEvent) -> Unit,
    navController: NavHostController
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
        ) {

            Text(
                text = "Add Application",
                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 32.sp,
                modifier = Modifier.padding(top = 20.dp, start = 20.dp)

            )

            Column(modifier = Modifier.padding(20.dp)) {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    value = state.applicationId,
                    onValueChange = { onEvent(ApplicationEvent.SetApplicationId(it)) },
                    label = {
                        Text(
                            text = "Application Number",
                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                            color = MaterialTheme.colorScheme.primary
                        )
                    },
                )

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    value = state.companyName,
                    onValueChange = { onEvent(ApplicationEvent.SetCompanyName(it)) },
                    label = {
                        Text(
                            text = "Company",
                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                            color = MaterialTheme.colorScheme.primary
                        )
                    })
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    value = state.jobPosition,
                    onValueChange = { onEvent(ApplicationEvent.SetJobPosition(it)) },
                    label = {
                        Text(
                            text = "Position",
                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                            color = MaterialTheme.colorScheme.primary
                        )
                    })
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    value = state.jobUrl,
                    onValueChange = { onEvent(ApplicationEvent.SetJobUrl(it)) },
                    label = {
                        Text(
                            text = "Job Url",
                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                            color = MaterialTheme.colorScheme.primary
                        )
                    })
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth(),

                    value = state.notes,
                    onValueChange = { onEvent(ApplicationEvent.SetNotes(it)) },
                    label = {
                        Text(
                            text = "Additional Notes",
                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                            color = MaterialTheme.colorScheme.primary
                        )
                    },
                    maxLines = 5,
                )
            }


            Row(
                modifier = Modifier
                    .padding(20.dp),

                ) {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        navController.navigate(Home.route) {
                            // To remove everything from stack till "Home"
                            popUpTo(Home.route)
                            // To prevent adding the same screen more than once
                            launchSingleTop = true
                        }
                        val formattedDate =
                            SimpleDateFormat("EEE MMM dd, yyyy", Locale.getDefault()).format(
                                Calendar.getInstance().time
                            )

                        onEvent(ApplicationEvent.SetDate(formattedDate))
                        onEvent(ApplicationEvent.SaveApplication)

                    }) {
                    Text(
                        text = "Save Application ",
                        color = Color.Green,
                        fontWeight = FontWeight.ExtraBold,
                        fontFamily = FontFamily(Font(R.font.poppins_regular)),
                        fontSize = 18.sp
                    )

                }
            }
        }
    }
}

