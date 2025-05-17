package com.example.curelink.ui.screens

import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.curelink.R
import com.example.curelink.prf.PreferencesDataStore
import com.example.curelink.response.SpecificUserResponseItem
import com.example.curelink.ui.screens.navigation.Routes
import com.example.curelink.viewModel.MyViewModel
import kotlinx.coroutines.flow.first
import timber.log.Timber


@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
@Composable


fun ProfileScreen(
    viewModel: MyViewModel, preferencesDataStore: PreferencesDataStore, navController: NavController
) {


    val state = viewModel.getSpecificUser.collectAsState()
    val userId = state.value.success?.firstOrNull()



    LaunchedEffect(Unit) {
        val userId = preferencesDataStore.PreferenceUserId.first()
        if (!userId.isNullOrEmpty()) {
            viewModel.getSpecificUser(userId)
        }
    }



    when {
        state.value.isLoading -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        state.value.error != null -> {
            Text("Error: ${state.value.error}")
        }


        state.value.success != null -> {


            Column(
                modifier = Modifier

                    .padding(top = 20.dp)
            ) {


                val user = state.value.success!!.firstOrNull()
                Timber.tag("USER").d("Fetching user with ID: $user")



                if (userId != null) {

                    if (user != null) {
                        UserData(user, viewModel, navController)
                    }
                }


            }

        }

    }


}


@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
@Composable
fun UserData(
    user: SpecificUserResponseItem,
    viewModel: MyViewModel,
    navController: NavController,
) {


    val context = LocalContext.current


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
    ) {


        ProfileScreenPicker(viewModel)
        IconButton(
            onClick = {
                navController.navigate(Routes.MapRoute)
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Icon(imageVector = Icons.Rounded.LocationOn, contentDescription = "Location")
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            elevation = CardDefaults.cardElevation(6.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                ProfileRow("Name", user.name)
                ProfileRow("Email", user.email)
                ProfileRow("Address", user.address)
                ProfileRow("Phone Number", user.phone_number)
                ProfileRow("Pin Code", user.pin_code)
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            val approve =
                viewModel.getSpecificUser.collectAsState().value.success?.first?.isApproved
            if (approve == 1) {
                Button(
                    onClick = {
                        Toast.makeText(context, "You are already approved", Toast.LENGTH_LONG)
                            .show()
                    },
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(Color.LightGray),
                    elevation = ButtonDefaults.elevatedButtonElevation(4.dp)
                ) {
                    Image(
                        painter = painterResource(R.drawable.approve),
                        contentDescription = "Approved",
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Approved", color = Color.Black)
                }
            } else {
                IconButton(
                    onClick = {
                        Toast.makeText(context, "You are not approved by admin", Toast.LENGTH_LONG)
                            .show()
                    }
                ) {
                    Image(
                        painter = painterResource(R.drawable.disapprove),
                        contentDescription = "Disapproved",
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }


        Button(
            onClick = {

                navController.navigate(Routes.PhoneAuthRoute)

            },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(5.dp),
            colors = ButtonDefaults.buttonColors(Color.LightGray)
        ) {
            Text(
                text = "Verify User", color = Color.Blue,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                modifier = Modifier
                    .align(Alignment.CenterVertically)

            )

        }

    }
}


@Composable
fun ProfileRow(label: String, value: String?) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, fontSize = 16.sp, color = Color.DarkGray)
        Text(text = value ?: "N/A", fontSize = 16.sp, color = Color.Black)
    }
}


@Composable
fun ProfileScreenPicker(viewModel: MyViewModel) {
    val context = LocalContext.current
    val imageUri by viewModel.imageUri

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        uri?.let {
            viewModel.updateImageUri(it)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.loadSavedImageUri(context)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()

            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = rememberAsyncImagePainter(
                model = imageUri,
                placeholder = painterResource(id = R.drawable.ic_launcher_background),
                error = painterResource(id = R.drawable.ic_launcher_background),
                fallback = painterResource(id = R.drawable.ic_launcher_background)
            ),
            contentDescription = "Profile Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .align(Alignment.CenterHorizontally)
                .clickable {
                    imagePickerLauncher.launch(
                        PickVisualMediaRequest(
                            ActivityResultContracts.PickVisualMedia.ImageOnly
                        )
                    )
                }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                viewModel.saveImageUri()
                Toast.makeText(context, "Image Saved!", Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Save Profile")
        }
    }
}
