package com.example.curelink.network

import com.example.curelink.utils.BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiProvider {
    //this function is used for provide api service and in this we define our base url

    companion object {

    fun providerApiService() =
        Retrofit.Builder().baseUrl(BASE_URL).client(OkHttpClient.Builder().build())
            .addConverterFactory(
                GsonConverterFactory.create()
            ).build().create(ApiServices::class.java)
}



}


