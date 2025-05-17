package com.example.curelink.response

data class GetSpecificProductResponseItem(
    val category: String? = null,
    val id: Int? = null,
    val name: String? = null,
    val price: Double ?= null,
    val products_id: String?= null,
    val stock: Int? = null
)