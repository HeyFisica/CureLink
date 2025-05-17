package com.example.curelink.ui.auth

import androidx.compose.runtime.Composable
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.curelink.R
import com.example.curelink.ui.screens.navigation.Routes
import com.example.curelink.viewModel.MyViewModel


@Composable
fun OtpVerificationScreen(viewModel: MyViewModel, navController: NavController) {
    var otp by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }

    Column(Modifier.padding(16.dp).fillMaxSize()) {
        Text("Enter OTP")
        OutlinedTextField(
            value = otp,
            onValueChange = { otp = it },
            placeholder = { Text("6-digit code") }
        )
        Card(modifier = Modifier
            .padding(20.dp),
            elevation = CardDefaults.cardElevation(5.dp)) {

            Image(
                painter = painterResource(R.drawable.otpverify1),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.align(Alignment.CenterHorizontally)
                    .clip(RoundedCornerShape(10.dp))
                    .size(300.dp)

            )
        }

        Spacer(Modifier.height(16.dp))
        Button(onClick = {
            viewModel.verifyOtp(
                otp,
                navController,
                onError = { error = it }
            )
//            navController.navigate(Routes.AllProductsRoutes)
        },
            modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text("Verify")
        }
        if (error.isNotEmpty()) {
            Text(error, color = Color.Red)
        }
    }
}
