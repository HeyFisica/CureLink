package com.example.curelink.ui.screens.navigation

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.curelink.prf.PreferencesDataStore
import com.example.curelink.ui.auth.OtpVerificationScreen
import com.example.curelink.ui.auth.PhoneAuthScreen
import com.example.curelink.ui.screens.SignInScreen
import com.example.curelink.ui.screens.SignUp
import com.example.curelink.ui.screens.WaitingScreen
import com.example.curelink.ui.screens.map.ManifestUtils
import com.example.curelink.ui.screens.map.MapScreen
import com.example.curelink.viewModel.MapViewModel
import com.example.curelink.viewModel.MyViewModel
import com.google.android.libraries.places.api.Places
import kotlinx.coroutines.delay
import kotlinx.serialization.descriptors.StructureKind

@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
@Composable
fun SignNavigation(viewModel: MyViewModel, preferencesDataStore: PreferencesDataStore) {
    val navController = rememberNavController()

    val userIdState by viewModel.getuseridstate.collectAsState()
    val specificUser = viewModel.getSpecificUser.collectAsState().value.success?.first?.isApproved




    LaunchedEffect(Unit) {
        delay(1500)
        viewModel.getUserId()


    }

    val startScreen = remember(userIdState.userId, specificUser) {
        when {
            userIdState.userId.isNullOrEmpty() || userIdState.userId == "null" || userIdState.userId == "" -> {
                Log.d("AppNavigation", "startScreen: Login (userId=${userIdState.userId})")
                Routes.SignInRoutes.route
            }

            else -> {
                Log.d("AppNavigation", "startScreen: WaitingScreen (Not Approved)")
                Routes.WaitingRoutes(userIdState.userId ?: "")
            }
        }
    }


    NavHost(
        navController = navController,
        startDestination = startScreen,
        enterTransition = {
            slideInVertically(
                initialOffsetY = { it / 4 },
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioLowBouncy,
                    stiffness = Spring.StiffnessLow
                )
            ) + fadeIn(animationSpec = spring())
        },
        exitTransition = {
            slideOutHorizontally(
                targetOffsetX = { -it },
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioLowBouncy,
                    stiffness = Spring.StiffnessLow
                )
            ) + fadeOut(animationSpec = spring())
        }
    ) {

        composable(Routes.SignInRoutes.route) {
            SignInScreen(viewModel, navController)
        }

        composable(Routes.SignUpRoutes.route) {
            SignUp(viewModel, navController)
        }

        composable<Routes.WaitingRoutes> {
            val userID = it.toRoute<Routes.WaitingRoutes>()
            WaitingScreen(userID.userID, viewModel, navController)
        }



        composable<Routes.HomeScreenRoutes> {}


        composable<Routes.AllProductsRoutes> {}

        composable<Routes.SpecificProductRoutes> {}


        composable<Routes.PhoneAuthRoute> {
            PhoneAuthScreen(viewModel, navController)
        }
        composable<Routes.OtpVerificationRoute> {
            OtpVerificationScreen(viewModel, navController)
        }


    }


    NavApp(viewModel, preferencesDataStore)
}


