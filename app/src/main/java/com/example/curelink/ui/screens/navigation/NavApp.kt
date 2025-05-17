package com.example.curelink.ui.screens.navigation

import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalActivity
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Store
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.outlined.Store
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.curelink.prf.PreferencesDataStore
import com.example.curelink.ui.auth.OtpVerificationScreen
import com.example.curelink.ui.auth.PhoneAuthScreen
import com.example.curelink.ui.screens.OrderScreen
import com.example.curelink.ui.screens.ProfileScreen
import com.example.curelink.ui.screens.SpecificProductScreen
import com.example.curelink.ui.screens.map.ManifestUtils
import com.example.curelink.ui.screens.map.MapScreen
import com.example.curelink.viewModel.MapViewModel
import com.example.curelink.viewModel.MyViewModel
import com.google.android.libraries.places.api.Places

@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
@Composable
fun NavApp(
    viewModel: MyViewModel, preferencesDataStore: PreferencesDataStore,
) {

    val mapViewModel = MapViewModel()
    val context = LocalContext.current

    val homeTab = TabBarItem(
        title = "Order",
        selectedIcon = Icons.Filled.ShoppingCart,
        unselected = Icons.Outlined.ShoppingCart
    )
    val availableStockTab = TabBarItem(
        title = "Available stock",
        selectedIcon = Icons.Filled.Store,
        unselected = Icons.Outlined.Store,
        badgeCount = 5
    )
    val productHistory = TabBarItem(
        title = "History",
        selectedIcon = Icons.Filled.History,
        unselected = Icons.Outlined.History
    )
    val profileTab = TabBarItem(
        title = "Profile",
        selectedIcon = Icons.Filled.Person,
        unselected = Icons.Outlined.Person
    )


    val tabBarItem = listOf(homeTab, availableStockTab, productHistory, profileTab)
    val navController = rememberNavController()


    // Retrieve the API key from the manifest file
    val apiKey = ManifestUtils.getApiKeyFromManifest(context)
    // Initialize the Places API with the retrieved API key
    if (!Places.isInitialized() && apiKey != null) {
        Places.initialize(context, apiKey)
    }

    Scaffold(bottomBar = {
        BottomNavigation(tabBarItem = tabBarItem, navController = navController)
    }) { innerPadding ->


        NavHost(
            navController = navController,
            startDestination = homeTab.title,
            modifier = Modifier.padding(innerPadding)
        )
        {


            composable(homeTab.title) {

                val activity = LocalActivity.current

                AllProductsScreen(viewModel, navController)
                BackHandler(enabled = true) {
                    // Exit the app
                    activity?.finish()
                }


            }

            composable(availableStockTab.title) {


            }
            composable(productHistory.title) {


            }


            composable(profileTab.title) {
                ProfileScreen(viewModel, preferencesDataStore, navController)

            }
            composable<Routes.SpecificProductRoutes> {
                val productId = it.toRoute<Routes.SpecificProductRoutes>()
                SpecificProductScreen(viewModel, productId.productId, navController)

            }


            composable<Routes.CreateOrderRoutes> {
                OrderScreen(viewModel, navController)
            }
            composable<Routes.AllProductsRoutes>() {
                AllProductsScreen(viewModel, navController)


            }

            composable<Routes.PhoneAuthRoute> {
                PhoneAuthScreen(viewModel, navController)
            }
            composable<Routes.OtpVerificationRoute> {
                OtpVerificationScreen(viewModel, navController)
            }

            composable<Routes.MapRoute> {
                MapScreen(mapViewModel)
            }


        }


    }
}


data class TabBarItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselected: ImageVector,
    val badgeCount: Int? = null
)


@Composable

fun BottomNavigation(
    tabBarItem: List<TabBarItem>,
    navController: NavController
) {


    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar {
        tabBarItem.forEach { tabBarItem ->
            val itemIsSelected = currentDestination?.hierarchy?.any {
                it.route == tabBarItem.title
            } == true

            NavigationBarItem(
                selected = itemIsSelected,
                onClick = {
                    navController.navigate(tabBarItem.title) {
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    TabBarIconView(
                        isSelected = itemIsSelected,
                        item = tabBarItem

                    )

                },
                label = {
                    Text(text = tabBarItem.title)
                }

            )


        }
    }
}

@Composable
fun TabBarIconView(
    isSelected: Boolean,
    item: TabBarItem
) {
    BadgedBox(badge = { TabBarBadgeView(item.badgeCount) }) {
        Icon(
            imageVector = if (isSelected) item.selectedIcon else item.unselected,
            contentDescription = item.title
        )
    }


}

@Composable
fun TabBarBadgeView(count: Int? = null) {
    if (count != null) {
        Badge {
            Text(text = count.toString())
        }
    }
}








