package com.example.curelink.response

data class NewResponseItem(
    val category: String,
    val id: Int,
    val name: String,
    val price: Double,
    val products_id: String,
    val stock: Int
)