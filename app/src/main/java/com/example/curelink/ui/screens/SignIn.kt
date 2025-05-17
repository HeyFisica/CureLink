package com.example.curelink.ui.screens

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.curelink.R
import com.example.curelink.ui.screens.navigation.Routes
import com.example.curelink.viewModel.MyViewModel

@Composable
fun SignInScreen(viewModel: MyViewModel, navController: NavHostController) {

    val state = viewModel.loginUserState.collectAsState()

    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }


    when {
        state.value.isLoading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }

        }

        state.value.error != null -> {
            Text(text = state.value.error.toString())
            Log.d("TAG", "OnError : ${state.value.error}")
        }

        state.value.success != null -> {
//            Log.d("TAG", state.value.success!!.message)
            if (state.value.success!!.status == 200) {

                LaunchedEffect(state.value.success) {
                    navController.navigate(Routes.WaitingRoutes(state.value.success!!.message))
                }

            }


        }
    }


    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(R.drawable.back2),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier.fillMaxSize(),

            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {


            Image(
                painter = painterResource(R.drawable.logo),
                contentDescription = null,
                modifier = Modifier.size(150.dp),
                contentScale = ContentScale.Crop
            )

            Text(
                text = "Cure Link", color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp
            )

            TextField(
                value = email.value,
                onValueChange = { email.value = it },
                label = { Text(text = "Email") },
                modifier = Modifier
                    .padding(10.dp)


                    .padding(10.dp)

                    .background(Color.Transparent)

                    .border(BorderStroke(2.dp, Color.White), shape = RoundedCornerShape(10.dp)),
                shape = RoundedCornerShape(10.dp),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent

                )
            )



            TextField(
                value = password.value,
                onValueChange = { password.value = it },
                label = { Text(text = "Password") },
                modifier = Modifier
                    .padding(10.dp)


                    .background(Color.Transparent)

                    .border(BorderStroke(2.dp, Color.White), shape = RoundedCornerShape(10.dp)),
                shape = RoundedCornerShape(10.dp),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent

                )
            )


            Button(
                onClick = {
                    viewModel.loginUser(
                        email.value, password.value,


                        )

                },

                modifier = Modifier
                    .padding(start = 40.dp, end = 40.dp, top = 40.dp)
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(Color.White)

            ) {

                Text(
                    text = "Sign In", color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(7.dp)

                )

            }


            Row(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "Create New Account",

                    modifier = Modifier.align(Alignment.CenterVertically),
                    color = Color.Black
                )
                Button(
                    onClick = {
                        navController.navigate(Routes.SignUpRoutes.route)
                    },
                    modifier = Modifier,

                    colors = ButtonDefaults.buttonColors(Color.Transparent)

                ) {
                    Text(
                        text = "Sign Up", color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }


        }
    }

}