package com.example.curelink.repo

import android.util.Log
import com.example.curelink.common.ResultState
import com.example.curelink.network.ApiProvider
import com.example.curelink.response.CreateOrderResponse
import com.example.curelink.response.CreateUserResponse
import com.example.curelink.response.GetAllProductsResponse
import com.example.curelink.response.GetSpecificProductResponse
import com.example.curelink.response.LoginResponse
import com.example.curelink.response.SpecificUserResponse
import com.example.curelink.viewModel.MyViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

//We make suspend function in this class
class Repo {

    suspend fun createUser(
        name: String,
        password: String,
        email: String,
        phoneNumber: String,
        address: String,
        pinCode: String,


        ): Flow<ResultState<CreateUserResponse>> = flow {

        emit(ResultState.Loading)

        try {
            val response = ApiProvider.providerApiService()
                .createUser(name, password, email, phoneNumber, address, pinCode)
            if (response.isSuccessful && response.body() != null) {
                emit(ResultState.Success(response.body()!!))


            }

        } catch (e: Exception) {

            emit(ResultState.Error(e))


        }

    }


    //For Login

    suspend fun authUser(
        email: String,
        password: String,
    ): Flow<ResultState<LoginResponse>> = flow {

        emit(ResultState.Loading)

        try {
            val response = ApiProvider.providerApiService().authUser(email, password)
            if (response.isSuccessful && response.body() != null) {
                emit(ResultState.Success(response.body()!!))


            }

        } catch (e: Exception) {

            emit(ResultState.Error(e))


        }
    }


    suspend fun getSpecificUserData(
        userId: String
    ): Flow<ResultState<SpecificUserResponse>> = flow {
        emit(ResultState.Loading)


        try {
            val response = ApiProvider.providerApiService().getSpecificUser(userId)
            if (response.isSuccessful && response.body() != null) {
                emit(ResultState.Success(response.body()!!))
            }
        } catch (e: Exception) {

            emit(ResultState.Error(e))


        }

    }

    suspend fun getAllProducts(

    ): Flow<ResultState<GetAllProductsResponse>> = flow {
        emit(ResultState.Loading)

        try {
            val response = ApiProvider.providerApiService().getAllProducts()

            if (response.isSuccessful && response.body() != null) {
                emit(ResultState.Success(response.body()!!))


            } else {

                emit(ResultState.Error(Exception(response.message())))
            }


        } catch (e: Exception) {
            emit(ResultState.Error(e))
        }


    }

    suspend fun getSpecificProduct(
        productId: String
    ): Flow<ResultState<GetSpecificProductResponse>> = flow {
        emit(ResultState.Loading)


        try {
            val response = ApiProvider.providerApiService().getSpecificProduct(productId)
            if (response.isSuccessful && response.body() != null) {
                emit(ResultState.Success(response.body()!!))
            }
        } catch (e: Exception) {

            emit(ResultState.Error(e))


        }

    }

    suspend fun createOrder(
        userId: String,
        productId: String,
        quantity: Int,
        price: Float,
        totalAmount: Float,
        productName: String,
        userName: String,
        message: String,
        category: String

    ): Flow<ResultState<CreateOrderResponse>> = flow {
        emit(ResultState.Loading)


        try {
            val response = ApiProvider.providerApiService().createOrder(
                userId,
                productId,
                quantity,
                price,
                totalAmount,
                productName,
                userName,
                message,
                category
            )
            if (response.isSuccessful && response.body() != null) {
                Log.d("API_RESPONSE", response.body().toString())
                emit(ResultState.Success(response.body()!!))
            }
        } catch (e: Exception) {

            emit(ResultState.Error(e))


        }


    }

}


