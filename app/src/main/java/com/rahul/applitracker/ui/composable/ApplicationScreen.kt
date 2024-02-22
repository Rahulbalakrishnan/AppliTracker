package com.rahul.applitracker.ui.composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.rahul.applitracker.R
import com.rahul.applitracker.database.room.Application
import com.rahul.applitracker.database.room.ApplicationEvent
import com.rahul.applitracker.database.room.ApplicationState
import com.rahul.applitracker.navigation.AddScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApplicationScreen(
    state: ApplicationState,
    onEvent: (ApplicationEvent) -> Unit,
    navController: NavHostController
) {

    var selectedApplication by remember { mutableStateOf<Application?>(null) }
    Box(
        modifier = Modifier
            .fillMaxSize()

    ) {
        Scaffold(
            containerColor = Color.White,
            topBar = {
                Text(
                    text = "My Applications",
                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 32.sp,
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .padding(top = 20.dp)
                )
            },
            content = {
                Column(
                    modifier = Modifier
                        .padding(10.dp)
                        .padding(vertical = 10.dp),
                ) {
                    LazyColumn(
                        contentPadding = it,
                        modifier = Modifier
                            .background(color = Color.Transparent)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        items(state.applications) { application ->
                            Card(
                                onClick = { selectedApplication = application },
                                modifier = Modifier
                                    .padding(vertical = 8.dp)
                                    .height(100.dp)
                                    .fillMaxWidth(),
                                colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                                border = BorderStroke(
                                    Dp.Hairline,
                                    MaterialTheme.colorScheme.primary
                                )
                            ) {
                                Row(
                                    modifier = Modifier
                                        .padding(10.dp).padding(horizontal = 15.dp)
                                        .fillMaxWidth().weight(1f),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween

                                ) {
                                    Column(
                                        modifier = Modifier
                                            .padding(vertical = 5.dp)


                                    ) {
                                        Text(
                                            text = application.companyName,
                                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                            fontSize = 18.sp,
                                            color = MaterialTheme.colorScheme.primary,
                                            fontWeight = FontWeight.ExtraBold
                                        )
                                        Text(
                                            text = application.jobPosition,
                                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                            fontSize = 13.sp,
                                            color = MaterialTheme.colorScheme.primary,
                                            fontWeight = FontWeight.Light
                                        )
                                        Text(
                                            text = "Applied",
                                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                            fontSize = 12.sp,
                                            color = Color.Green,
                                            fontWeight = FontWeight.Light
                                        )
                                    }

                                    Column(
                                        modifier = Modifier.padding(vertical = 5.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {


                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_trash),
                                            contentDescription = "Trash Icon",
                                            tint = Color.Red,
                                            modifier = Modifier.clickable {
                                                onEvent(
                                                    ApplicationEvent.DeleteApplication(
                                                        application
                                                    )
                                                )
                                            }.size(25.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            },
            bottomBar = {

                Button(
                    onClick = {
                        navController.navigate(AddScreen.route)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {
                    Text(
                        text = "Add Application",
                        color = Color.White,
                        fontWeight = FontWeight.ExtraBold,
                        fontFamily = FontFamily(Font(R.font.poppins_regular)),
                        fontSize = 16.sp
                    )
                }
            }
        )
        if (selectedApplication != null) {
            ApplicationDetailsScreen(
                application = selectedApplication!!,
                onClose = { selectedApplication = null }
            )
        }
    }

}

@Composable
fun ApplicationDetailsScreen(
    application: Application,
    onClose: () -> Unit
) {
    AlertDialog(
        containerColor = MaterialTheme.colorScheme.primary,
        onDismissRequest = onClose,
        title = {
            Text(
                text = "Application Details",
                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                fontWeight = FontWeight.ExtraBold,
                fontSize = 13.sp,
            )
        },
        text = {

            Column {
                Text(
                    text = "ID : ${application.applicationId}",
                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 13.sp,
                    color = Color.White,
                    modifier = Modifier.padding(2.dp)
                )
                Text(
                    text = "Applied on : ${application.date}",
                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 13.sp,
                    color = Color.White,
                    modifier = Modifier.padding(2.dp)
                )
                Text(
                    text = "Status : Applied",
                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 13.sp,
                    color = Color.Green,
                    modifier = Modifier.padding(2.dp)
                )
                Text(
                    text = "Company : ${application.companyName}",
                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 13.sp,
                    color = Color.White,
                    modifier = Modifier.padding(2.dp)
                )
                Text(
                    text = "Position : ${application.jobPosition}",
                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 13.sp,
                    color = Color.White,
                    modifier = Modifier.padding(2.dp)
                )
                Text(
                    text = "URL : ${application.jobUrl}",
                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 13.sp,
                    color = Color.White,
                    modifier = Modifier.padding(2.dp)
                )
                Text(
                    text = "Notes : ${application.notes}",
                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 13.sp,
                    color = Color.White,
                    modifier = Modifier.padding(2.dp)
                )
            }

        },
        confirmButton = {
            // Close button in the dialog
            Button(
                colors = ButtonDefaults.buttonColors(Color.White),
                onClick = onClose,
                modifier = Modifier.padding(8.dp)
            ) {
                Text(
                    text = "Close", color = MaterialTheme.colorScheme.primary,
                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 13.sp,
                )
            }
        }
    )
}
