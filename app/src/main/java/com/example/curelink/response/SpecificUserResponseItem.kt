package com.example.curelink.response

data class SpecificUserResponseItem(
    val address: String? =null,
    val block: Int? = null,
    val date_of_account_creation: String? = null,
    val email: String? = null,
    val id: Int? = null,
    val isApproved: Int? = null,
    val name: String? = null,
    val password: String? = null,
    val phone_number: String? = null,
    val pin_code : String? = null,
    val user_id: String? = null
)