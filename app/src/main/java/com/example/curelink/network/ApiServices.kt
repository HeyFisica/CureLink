package com.example.curelink.network

import com.example.curelink.response.CreateOrderResponse
import com.example.curelink.response.CreateUserResponse
import com.example.curelink.response.GetAllProductsResponse
import com.example.curelink.response.GetSpecificProductResponse
import com.example.curelink.response.LoginResponse
import com.example.curelink.response.SpecificUserResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.POST

interface ApiServices {
    @FormUrlEncoded // For encrypt data

    @POST("createUser")
    suspend fun createUser(
        @Field("name") name:String,
        @Field("password")password:String,
        @Field("email")email:String,
        @Field("phoneNumber") phoneNumber:String,
        @Field("address") address: String,
        @Field("pinCode")pinCode:String,
    ): Response<CreateUserResponse>

    @FormUrlEncoded
    @POST("login")
    suspend fun authUser(
        @Field("email")email:String,
        @Field("password")password: String,
        ): Response<LoginResponse>

    @FormUrlEncoded
    @POST("getSpecificUser")
    suspend fun getSpecificUser(
        @Field("user_id")userId:String,
    ): Response<SpecificUserResponse>

    @FormUrlEncoded
    @POST("getSpecificProduct")
    suspend fun getSpecificProduct(
        @Field("product_id")productId:String,
    ): Response<GetSpecificProductResponse>

//    @FormUrlEncoded
    @GET("getAllProducts")
    suspend fun getAllProducts(

    ): Response<GetAllProductsResponse>

    @FormUrlEncoded
    @POST("addOrders")
    suspend fun createOrder(
        @Field("user_id") userId: String,
        @Field("product_id") productId:String,
        @Field("quantity")quantity:Int,
        @Field("price")price:Float,
        @Field("total_amount")totalAmount:Float,
        @Field("product_name")productName: String,
        @Field("user_name")userName:String,
        @Field("message")message:String,
        @Field("category")category:String,


        ): Response<CreateOrderResponse>


//
//    @FormUrlEncoded
//    @HTTP(method = "DELETE", path = "deleteUserDetails", hasBody = true)
//    suspend fun deleteProduct(
//        @Field("user_id") userID: String,
//
//        ): Response<DeleteUserResponse>

}