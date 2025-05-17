package com.example.curelink.ui.screens

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.curelink.ui.screens.navigation.Routes
import com.example.curelink.viewModel.MyViewModel

@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
@Composable
fun WaitingScreen(userId: String, viewModel: MyViewModel, navController: NavController) {

    val state = viewModel.getSpecificUser.collectAsState()

    LaunchedEffect(key1 = Unit) {
        viewModel.getSpecificUser(userId)
    }



    when {
        state.value.isLoading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()


            }
        }

        state.value.success != null -> {


            val user = state.value.success!!.firstOrNull()
            Log.d("Response", "get user $user")

            if (user!!.isApproved == 1) {

                LaunchedEffect (state.value.success){
                    navController.navigate(Routes.HomeScreenRoutes)
                }
            } else {

                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

                    Text(text = "User is not approved")


                    Button(onClick = {
                        viewModel.getSpecificUser(userId)

                    }) {
                        Text("Refresh")
                    }
                }

            }

        }

        state.value.error != null -> {

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Text(text = "${state.value.error}")
            }


        }

    }


}

