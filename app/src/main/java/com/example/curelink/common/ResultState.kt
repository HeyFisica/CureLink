package com.example.curelink.common

//We make sealed class For same type of subclass defined at one place
sealed class ResultState<out T> {

    data class Success< out T>(val data:T):
        ResultState<T>() //DataType is type of T because it is used for make generic


    data class Error< T>(val exception:Exception): ResultState<T>()
    data object  Loading:ResultState<Nothing>()

}
