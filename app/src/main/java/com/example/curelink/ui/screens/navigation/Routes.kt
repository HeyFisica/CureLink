package com.example.curelink.ui.screens.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Routes(val route: String) {
    @Serializable
    object SignUpRoutes : Routes("singn_up")

    @Serializable
    object SignInRoutes : Routes("sign_in")

    @Serializable
    data object HomeScreenRoutes


    @Serializable
    data object AllProductsRoutes


    @Serializable
    data class SpecificProductRoutes(val productId: String)


    @Serializable
    data object CreateOrderRoutes




    @Serializable
    data class WaitingRoutes(val userID: String)

    @Serializable
    object PhoneAuthRoute

    @Serializable
    object OtpVerificationRoute

    @Serializable
    object MapRoute


}

