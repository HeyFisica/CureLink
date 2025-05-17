package com.example.curelink.ui.screens

import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.curelink.R
import com.example.curelink.response.GetAllProductsResponseItem
import com.example.curelink.viewModel.MyViewModel


@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
@Composable


fun OrderScreen(viewModel: MyViewModel, navController: NavController) {

    val state = viewModel.createOrderState.collectAsState()
    val allProductState = viewModel.getAllProductsState.collectAsState().value
    val userid = viewModel.getuseridstate.collectAsState().value
    val getuserdeatilstate = viewModel.getSpecificUser.collectAsState().value



    LaunchedEffect(Unit) {
        viewModel.getAllProducts()
        viewModel.getSpecificUser(userid.userId.toString())
    }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = null,
                        modifier = Modifier
                            .clickable {
                                navController.popBackStack()
                            }
                            .size(30.dp)
                            .padding(start = 5.dp),
                        tint = Color(0xFFCEA819)
                    )
                },
                title = {
                    Text(
                        text = "Place Order ",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold,
                        fontStyle = FontStyle.Italic,
                        modifier = Modifier.padding(start = 10.dp),
                        color = Color(
                            0xFFD9B514
                        )
                    )
                },
                actions = {
                    Icon(
                        Icons.Default.Refresh,
                        contentDescription = null,
                        modifier = Modifier
                            .clickable { viewModel.getAllProducts() }
                            .size(30.dp),
                        tint = Color(0xFFCEA819)
                    )
                }
            )
        },


        ) { innerPadding ->
        Column(
            modifier = Modifier

                .padding(top = 10.dp, start = 6.dp, end = 6.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "All Products",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.weight(1f)
                )

                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = "Refresh",
                    tint = Color.White,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable {
                            // Trigger your refresh logic here
                            viewModel.getAllProducts()
                        }
                )
            }
        }


        Box(modifier = Modifier.fillMaxSize()) {

            when {
                state.value.isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                state.value.success != null -> {
                    if (state.value.success != null) {


                        var selectedProduct by remember {
                            mutableStateOf<GetAllProductsResponseItem?>(
                                null
                            )
                        }

                        LaunchedEffect(Unit) {
                            val product = allProductState.success?.firstOrNull()
                            selectedProduct = product
                        }

                        selectedProduct?.let { product ->
                            DropdownWithTextField(
                                product = product,
                                viewModel = viewModel,
                                navController = navController,
                                username = getuserdeatilstate.success.toString(),
                                user_id = userid.toString()
                            )

                        }


                    } else {
                        Log.d("debug4", "Current order state: $state")
                    }
                }


                state.value.error != null -> {
                    Text(
                        text = state.value.error ?: "Unknown error",
                        color = Color.Red,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                else -> {


                    var selectedProduct by remember {
                        mutableStateOf<GetAllProductsResponseItem?>(
                            null
                        )
                    }

                    LaunchedEffect(Unit) {
                        val product = allProductState.success?.firstOrNull()
                        selectedProduct = product
                    }

                    selectedProduct?.let { product ->
                        DropdownWithTextField(
                            product = product,
                            viewModel = viewModel,
                            navController = navController,
                            username = getuserdeatilstate.success.toString(),
                            user_id = userid.toString()
                        )
                    }


                }

            }
        }


    }
}


@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
@Composable
fun DropdownWithTextField(
    product: GetAllProductsResponseItem,
    viewModel: MyViewModel,
    navController: NavController,
    username: String?,
    user_id: String?
) {
    val option = viewModel.getAllProductsState.collectAsState()
    val productNames = option.value.success?.mapNotNull { it.name }
    var productQuantity by remember { mutableIntStateOf(0) }
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf("") }
    val showImage by remember { mutableStateOf(false) }
    var createOrder by remember { mutableStateOf(false) }


    val context = LocalContext.current

    val product_price = product.price?.toString() ?: "0"
    val product_id = product.products_id ?: "N/A"
    var product_name = product.name?.toString() ?: "N/A"
    val product_category = product.category?.toString() ?: "N/A"
    val product_quantity_available = product.stock?.toString() ?: "0"

    val message = remember { mutableStateOf("") }
    val user_name = remember { mutableStateOf("") }
    val totalPrice = product_price.toDoubleOrNull()?.times(productQuantity) ?: 0.0




    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(R.drawable.back2),
            contentDescription = null,
            contentScale = ContentScale.Crop,

            modifier = Modifier.fillMaxSize(),
        )


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)


        ) {


            LaunchedEffect(Unit) {
                viewModel.getAllProducts()
                Log.d("DEBUG", "API Call Triggered")
            }


            if (productNames.isNullOrEmpty()) {
                Text(
                    text = "Loading products or no data available...",
                    modifier = Modifier.padding(16.dp),
                    color = Color.LightGray
                )
            } else {
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded },
                ) {
                    TextField(
                        value = selectedOptionText,
                        onValueChange = { selectedOptionText = it },
                        label = {
                            Text(
                                text = "Select medicine",
                                color = Color.Black.copy(alpha = 0.8f)
                            )
                        },
                        readOnly = true,
                        shape = RoundedCornerShape(16.dp),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.White.copy(alpha = 0.1f),
                            unfocusedContainerColor = Color.White.copy(alpha = 0.05f),
                            disabledContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                            focusedLabelColor = Color.White.copy(alpha = 0.8f),
                            unfocusedLabelColor = Color.White.copy(alpha = 0.5f),
                            cursorColor = Color.White
                        ),
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                            .border(
                                width = 1.dp,
                                color = Color.White.copy(alpha = 0.3f),
                                shape = RoundedCornerShape(16.dp)
                            )
                            .background(Color.Transparent)
                    )

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier.background(Color.Black.copy(alpha = 0.85f))
                    ) {
                        productNames.forEach { name ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = name,
                                        color = Color.White
                                    )
                                },
                                onClick = {
                                    selectedOptionText = name
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }




            Card(

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.Transparent
                ),
                elevation = CardDefaults.cardElevation(0.dp),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, Color.LightGray)
            ) {
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = "Quantity",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(onClick = {
                        if (productQuantity < 50) productQuantity++ else Toast.makeText(
                            context,
                            "You reached out of limit",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }, modifier = Modifier.background(Color.Transparent)) {
                        Text(
                            text = "+",
                            fontSize = 25.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White
                        )

                    }

                    Text(text = "$productQuantity")



                    Button(
                        onClick = {
                            if (productQuantity < 1) Toast.makeText(
                                context,
                                "Select At least 1 item",
                                Toast.LENGTH_SHORT
                            )
                                .show() else productQuantity--
                        },
                        modifier = Modifier,

                        ) {

                        Text(
                            text = "-",
                            fontSize = 25.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White
                        )
                    }
                }
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.Transparent
                ),
                elevation = CardDefaults.cardElevation(0.dp),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, Color.LightGray)
            ) {
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Category",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Text(
                        text = product_category,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black
                    )
                }
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.Transparent
                ),
                elevation = CardDefaults.cardElevation(0.dp),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, Color.LightGray)
            ) {
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Price",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Text(
                        text = "₹${product_price}",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black
                    )
                }

            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.Transparent
                ),
                elevation = CardDefaults.cardElevation(0.dp),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, Color.LightGray)
            ) {
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Total Amount",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Text(
                        text = " ₹ $totalPrice",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black
                    )
                }

            }

            Button(
                onClick = {
                    if (selectedOptionText.isEmpty()) {
                        Toast.makeText(context, "Please select a product", Toast.LENGTH_SHORT)
                            .show()
                        return@Button
                    }
                    if (productQuantity <= 0) {
                        Toast.makeText(
                            context,
                            "Please select at least 1 quantity",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@Button
                    }

                    val selectedProduct =
                        option.value.success?.find { it.name == selectedOptionText }
                    selectedProduct?.let {
                        viewModel.getSpecificProduct(it.products_id ?: "")
                    }


                    product_name = selectedOptionText
                    viewModel.createOrder(
                        user_id.toString(),
                        product_id,
                        productQuantity,
                        product_price.toFloat(),
                        totalPrice.toFloat(),
                        product_name,
                        user_name.value,
                        message.value,
                        product_category
                    )
                    // Reset form after submission
                    productQuantity = 0
                    user_name.value = ""
                    message.value = ""
                    createOrder = true
                },
                modifier = Modifier.align(Alignment.CenterHorizontally),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White)
            ) {


                Text(
                    text = "Create Order", color = Color.Black, modifier = Modifier


                )


            }

            if (createOrder) {
                Image(
                    painter = painterResource(R.drawable.ordersuccess),
                    contentDescription = "image",
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .size(100.dp) // You had 20.dp (very small)
                )

                OutlinedTextField(
                    value = message.value,
                    onValueChange = { message.value = it },
                    label = { Text("Feedback") },
                    placeholder = { Text("Write your feedback here...") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 120.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = Color.Gray
                    ),
                    singleLine = false,
                    maxLines = 5
                )
            }


        }

    }

}


