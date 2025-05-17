package com.example.curelink.ui.auth

import android.app.Activity
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.curelink.R
import com.example.curelink.ui.screens.navigation.Routes
import com.example.curelink.viewModel.MyViewModel

@Composable
fun PhoneAuthScreen(viewModel: MyViewModel, navController: NavController) {

    val context = LocalContext.current
    val activity = context as? Activity
    var phoneNumber by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }





    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
Card(modifier = Modifier
    .padding(20.dp),
    elevation = CardDefaults.cardElevation(5.dp)) {
    Image(
        painter = painterResource(R.drawable.phoneauth),
        contentDescription = null,
        contentScale = ContentScale.Crop,

        modifier = Modifier.align(Alignment.CenterHorizontally)

            .clip(RoundedCornerShape(10.dp))
            .size(300.dp)
    )
}

        Text(
            "Enter Phone Number", modifier = Modifier
                .padding(20.dp)
                .align(Alignment.CenterHorizontally),
            fontSize = 20.sp
        )
        OutlinedTextField(
            value = phoneNumber,
            onValueChange = { phoneNumber = it },
            placeholder = { Text("+91xxxxxxxxxx") },
            modifier = Modifier
                .align(
                    Alignment.CenterHorizontally
                )
                .fillMaxWidth()
                .padding(20.dp)
        )
        Spacer(Modifier.height(16.dp))
        Button(onClick = {
            activity?.let {
                viewModel.sendVerificationCode(
                    phoneNumber,
                    it,
//                    onCodeSent = { navController.navigate(Routes.OtpVerificationRoute) },
                    onCodeSent = {
                        Log.d("PhoneAuth", "OTP sent successfully")
                        navController.navigate(Routes.OtpVerificationRoute)
                    },
                    onError = { error = it }
                )
            } ?: run {
                error = "Activity not available"
            }
        },
            modifier = Modifier.align(Alignment.CenterHorizontally)
                .fillMaxWidth()
                .padding(20.dp)) {
            Text("Send OTP")
        }
    }
}

/// 46u

//    val context = LocalContext.current
//    val auth = FirebaseAuth.getInstance()
//
//    var phoneNumber by remember { mutableStateOf("") }
//    var otpCode by remember { mutableStateOf("") }
//    var verificationId by remember { mutableStateOf<String?>(null) }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp)
//    ) {
//        Text("Firebase Phone Auth (Test Number)", fontSize = 20.sp, fontWeight = FontWeight.Bold)
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        OutlinedTextField(
//            value = phoneNumber,
//            onValueChange = { phoneNumber = it },
//            label = { Text("Enter Test Phone Number") },
//            placeholder = { Text("+11234567890") },
//            singleLine = true
//        )
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        Button(onClick = {
//            // Simulate sending OTP (for test number, verificationId is mocked)
//            val options = PhoneAuthOptions.newBuilder(auth)
//                .setPhoneNumber(phoneNumber)
//                .setTimeout(60L, TimeUnit.SECONDS)
//                .setActivity(context as Activity) // Only for real devices
//                .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//                    override fun onVerificationCompleted(credential: PhoneAuthCredential) {
//                        Toast.makeText(context, "Verification completed", Toast.LENGTH_SHORT).show()
//                    }
//
//                    override fun onVerificationFailed(e: FirebaseException) {
//                        Toast.makeText(
//                            context,
//                            "Verification failed: ${e.message}",
//                            Toast.LENGTH_LONG
//                        ).show()
//                    }
//
//                    override fun onCodeSent(
//                        vid: String,
//                        token: PhoneAuthProvider.ForceResendingToken
//                    ) {
//                        verificationId = vid
//                        Toast.makeText(context, "Code sent!", Toast.LENGTH_SHORT).show()
//                    }
//                })
//                .build()
//
//            PhoneAuthProvider.verifyPhoneNumber(options)
//        }) {
//            Text("Send OTP")
//        }
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        OutlinedTextField(
//            value = otpCode,
//            onValueChange = { otpCode = it },
//            label = { Text("Enter OTP") },
//            placeholder = { Text("123456") },
//            singleLine = true
//        )
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        Button(onClick = {
//            verificationId?.let {
//                val credential = PhoneAuthProvider.getCredential(it, otpCode)
//                auth.signInWithCredential(credential)
//                    .addOnCompleteListener { task ->
//                        if (task.isSuccessful) {
//                            Toast.makeText(context, "Signed in successfully!", Toast.LENGTH_SHORT)
//                                .show()
//                            // Navigate to Home or next screen
//
//                            navController.navigate(Routes.HomeScreenRoutes)
//                        } else {
//                            Toast.makeText(context, "Sign-in failed", Toast.LENGTH_SHORT).show()
//                        }
//                    }
//            }
//        }) {
//            Text("Verify OTP")
//        }
//    }
//}
