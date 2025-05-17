package com.example.curelink.ui.screens.navigation

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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.curelink.R
import com.example.curelink.response.GetAllProductsResponseItem
import com.example.curelink.viewModel.MyViewModel

@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
@Composable
fun AllProductsScreen(viewModel: MyViewModel, navController: NavHostController) {


    val state = viewModel.getAllProductsState.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.getAllProducts()
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


            if (state.value.success != null) {
                val proId = state.value.success!!.first.products_id

                LazyColumn(modifier = Modifier.fillMaxSize()) {


                    Log.d("TAG", "User list: ${state.value.success}")

                    items(state.value.success ?: emptyList()) { user ->


                        if (proId != null) {
                            AllProducts(product = user, navController)
                        }
                    }
                }

            }
        }


    }
}


@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
@Composable
fun AllProducts(
    product: GetAllProductsResponseItem,

    navController: NavController
) {
    val myBlueColor = Color(0xFF90ADC6)
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(R.drawable.back2),
            contentDescription = null,
//
            contentScale = ContentScale.Crop
        )



        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 25.dp)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)

                    .height(100.dp),
                horizontalArrangement = Arrangement.spacedBy(40.dp),
                verticalAlignment = Alignment.CenterVertically

            ) {

                Card(
                    modifier = Modifier,

                    elevation = CardDefaults.cardElevation(3.dp),
                    colors = CardDefaults.cardColors(Color.White),
                    shape = RoundedCornerShape(10.dp),
                ) {
                    Image(
                        painter = painterResource(R.drawable.logo),
                        contentDescription = "medicine",
                        contentScale = ContentScale.Crop,

                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .size(100.dp)
                            .clip(shape = RoundedCornerShape(10.dp))
                    )

                }

                VerticalDivider(
                    modifier = Modifier
//                        .fillMaxHeight()
                        .width(1.dp), color = myBlueColor
                )

                Column(
                    modifier = Modifier,

                    ) {


                    product.name?.let {


                        Text(
                            text = it,
                            modifier = Modifier
                                .padding(0.dp)
                                .align(Alignment.CenterHorizontally),

                            fontSize = 20.sp
                        )
                    }

                    product.price?.let {
                        Text(
                            text = " ₹ $it",
                            modifier = Modifier
                                .padding(0.dp)
                                .align(Alignment.CenterHorizontally),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold

                        )
                    }



                    Button(
                        modifier = Modifier.padding(0.dp), onClick = {
                            if(product.products_id!=null)
                            navController.navigate(Routes.SpecificProductRoutes(product.products_id))
                        },
                        colors = ButtonDefaults.buttonColors(Color.White)
                    ) {

                        Text(
                            text = "Get Product", color = myBlueColor,
                            fontWeight = FontWeight.Bold
                        )

                    }

                }


            }
        }
    }
}


