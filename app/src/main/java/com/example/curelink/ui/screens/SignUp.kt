package com.example.curelink.ui.screens

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.curelink.R
import com.example.curelink.ui.screens.navigation.Routes
import com.example.curelink.viewModel.MyViewModel

@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
@Composable
fun SignUp(viewModel: MyViewModel, navController: NavHostController) {

    val name = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val phoneNumber = remember { mutableStateOf("") }
    val address = remember { mutableStateOf("") }
    val pinCode = remember { mutableStateOf("") }
    val myBlueColor = Color(0xFF90ADC6)

    val state = viewModel.createUserState.collectAsState()


    val context = LocalContext.current



    when {
        state.value.isLoading -> {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()


            }
        }

        state.value.success != null -> {

            LaunchedEffect(state.value.success) {


                navController.navigate(Routes.HomeScreenRoutes)
            }

        }

        state.value.error != null -> {

            Text(text = "${state.value.error}")


        }

    }

//UI PART
    Box(
        modifier = Modifier.fillMaxSize(),

        ) {
        Image(
            painter = painterResource(R.drawable.back2),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 20.dp),

            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center


        ) {

            Text(
                text = "Create New Account ", modifier = Modifier

                    .padding(8.dp), fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black

            )

            TextField(
                value = name.value,
                onValueChange = { name.value = it },
                label = { Text(text = "Name") },
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
            TextField(
                value = email.value,
                onValueChange = { email.value = it },
                label = { Text(text = "Email") },
                modifier = Modifier
                    .background(Color.Transparent)
                    .padding(10.dp)
                    .border(BorderStroke(2.dp, Color.White), shape = RoundedCornerShape(10.dp)),
                shape = RoundedCornerShape(10.dp),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),

                )
            TextField(
                value = phoneNumber.value,
                onValueChange = { phoneNumber.value = it },
                label = { Text(text = "PhoneNumber") },
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
            TextField(
                value = address.value,
                onValueChange = { address.value = it },
                label = { Text(text = "Address") },
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
            TextField(
                value = pinCode.value,
                onValueChange = { pinCode.value = it },
                label = { Text(text = "Pin Code") },
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
                    if (name.value.isEmpty() && password.value.isEmpty() && email.value.isEmpty() && phoneNumber.value.isEmpty() &&
                        address.value.isEmpty() && pinCode.value.isEmpty()
                    ) {

                        Toast.makeText(context, "Please fill above details", Toast.LENGTH_LONG)
                            .show()

                    } else {


                        viewModel.createUser(
                            name.value,
                            password.value,
                            email.value,
                            phoneNumber.value,
                            address.value,
                            pinCode.value
                        )


                    }


                },
                modifier = Modifier
                    .padding(start = 25.dp, end = 25.dp, top = 50.dp)
                    .fillMaxWidth(),

                colors = ButtonDefaults.buttonColors(Color.White)
            ) {
                Text(
                    text = "Sign Up", color = Color.Black,
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
                    text = "Already have an account",

                    modifier = Modifier.align(Alignment.CenterVertically),
                    color = Color.Black
                )
                Button(
                    onClick = {
                        navController.navigate(Routes.SignInRoutes.route)
                    },
                    modifier = Modifier,

                    colors = ButtonDefaults.buttonColors(Color.Transparent)

                ) {
                    Text(
                        text = "Sign In", color = Color.White,
                        fontWeight = FontWeight.Bold
                    )

                }
            }
        }


    }
}