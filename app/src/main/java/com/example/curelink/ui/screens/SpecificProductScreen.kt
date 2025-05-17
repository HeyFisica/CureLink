package com.example.curelink.ui.screens


import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.curelink.R
import com.example.curelink.response.GetSpecificProductResponseItem
import com.example.curelink.ui.screens.navigation.Routes
import com.example.curelink.viewModel.MyViewModel


@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
@Composable
fun SpecificProductScreen(viewModel: MyViewModel, productId: String, navController: NavController) {


    val state = viewModel.getSpecificState.collectAsState()

    LaunchedEffect(Unit) {
        Log.d("USER_DETAILS", "Fetching user with ID: $productId")

        viewModel.getSpecificProduct(productId)


    }


    when {
        state.value.isLoading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }


        }

        state.value.error != null -> {
            Text("Error: ${state.value.error}")

        }

        state.value.success != null -> {
            val product = state.value.success!!.firstOrNull()
            Log.d("PRODUCT", "Fetching user with ID: $product")


            if (product != null) {

                Column(modifier = Modifier.fillMaxSize()) {
                    ProductData(product, viewModel, navController)


                }

            }
        }


    }

}


@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
@Composable
fun ProductData(
    product: GetSpecificProductResponseItem,
    viewModel: MyViewModel,
    navController: NavController
) {

    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(R.drawable.back2), contentDescription = "background",
            contentScale = ContentScale.Crop,

            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()

                .padding(top = 25.dp)

        ) {

            Image(
                painter = painterResource(R.drawable.logo), contentDescription = "medicine",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .size(150.dp)
            )
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),

                elevation = CardDefaults.cardElevation(10.dp),
                colors = CardDefaults.cardColors(Color.White),
                shape = RoundedCornerShape(10.dp),

                ) {


                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Text(text = "Name", modifier = Modifier.padding(start = 16.dp, top = 8.dp))
                    product.name?.let {
                        Text(
                            text = it,
                            modifier = Modifier
                                .padding(end = 16.dp, top = 8.dp)
                                .align(Alignment.CenterVertically),
                            fontSize = 20.sp
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Text(text = "Price", modifier = Modifier.padding(start = 16.dp, top = 8.dp))
                    product.price.toString().let {

                        Text(
                            text = it,
                            modifier = Modifier
                                .padding(end = 16.dp, top = 8.dp)
                                .align(Alignment.CenterVertically),
                            fontSize = 20.sp
                        )
                    }
                }


                Row(
                    modifier = Modifier
                        .fillMaxWidth()

                        .padding(start = 8.dp, end = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Text(text = "Category", modifier = Modifier.padding(start = 16.dp, top = 8.dp))
                    product.category?.let {
                        Text(
                            text = it,
                            modifier = Modifier
                                .padding(end = 16.dp, top = 8.dp)
                                .align(Alignment.CenterVertically),
                            fontSize = 20.sp
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Text(
                        text = "Product Id",
                        modifier = Modifier.padding(start = 16.dp, top = 8.dp)
                    )
                    product.stock.toString().let {
                        Text(
                            text = it,
                            modifier = Modifier
                                .padding(end = 16.dp, top = 8.dp)
                                .align(Alignment.CenterVertically),
                            fontSize = 20.sp
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "ProductId   ",
                        modifier = Modifier.padding(start = 16.dp, top = 8.dp)
                    )

                    product.products_id?.let {
                        Text(
                            text = it,
                            modifier = Modifier
                                .padding(end = 16.dp, top = 8.dp)
                                .align(Alignment.CenterVertically),
                            fontSize = 11.sp
                        )
                    }

                }


            }


            Button(modifier = Modifier.align(Alignment.CenterHorizontally), onClick = {
                navController.navigate(Routes.CreateOrderRoutes)


            })
            {
                Text(text = "Buy Product", modifier = Modifier.align(Alignment.CenterVertically))
            }

        }
    }


}






