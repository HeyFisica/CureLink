package com.example.curelink.viewModel

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.curelink.common.ResultState
import com.example.curelink.prf.PreferencesDataStore
import com.example.curelink.repo.Repo
import com.example.curelink.response.CreateOrderResponse
import com.example.curelink.response.CreateUserResponse
import com.example.curelink.response.GetAllProductsResponse
import com.example.curelink.response.GetSpecificProductResponse
import com.example.curelink.response.LoginResponse
import com.example.curelink.response.SpecificUserResponse
import com.example.curelink.ui.screens.navigation.Routes
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.concurrent.TimeUnit

class MyViewModel(
    private val preferencesDataStore: PreferencesDataStore
) : ViewModel() {
    //Here we are getting instance of Repo class and creating and state for handle user data
// variable for verify user
    var storedVerificationId: String? = null
        private set
    var resendToken: PhoneAuthProvider.ForceResendingToken? = null
        private set

    // variables for handle states
    private val repo = Repo()
    private val _createUserState = MutableStateFlow(CreateUserState())
    val createUserState = _createUserState.asStateFlow()

    private val _getAllProductsState = MutableStateFlow(GetAllProductsState())
    val getAllProductsState = _getAllProductsState.asStateFlow()

    private val _getSpecificState = MutableStateFlow(GetSpecificProductState())
    val getSpecificState = _getSpecificState.asStateFlow()

    private val _createOrderState = MutableStateFlow(CreateOrderState())
    val createOrderState = _createOrderState.asStateFlow()


    private val _getuseridstate = MutableStateFlow(GetUserId())
    val getuseridstate = _getuseridstate.asStateFlow()

    init {
        getUserId()
    }

    fun getUserId() {
        viewModelScope.launch(Dispatchers.IO) {
            preferencesDataStore.PreferenceUserId.collect { userId ->
                Timber.tag("MainViewModel").d("Collected userId: $userId")

                _getuseridstate.value = GetUserId(userId = userId.toString(), isLoading = false)
                Timber.tag("MainViewModel").d("Updated userIdState: ${_getuseridstate.value}")
            }
        }
    }


    fun createUser(
        name: String,
        password: String,
        email: String,
        phoneNumber: String,
        address: String,
        pinCode: String,
    ) {
        viewModelScope.launch(Dispatchers.IO) {


            repo.createUser(name, password, email, phoneNumber, address, pinCode).collect {


                when (it) {
                    is ResultState.Loading -> {

                        _createUserState.value = CreateUserState(isLoading = true)

                    }

                    is ResultState.Error -> {
                        _createUserState.value =
                            CreateUserState(error = it.exception.message, isLoading = false)

                    }

                    is ResultState.Success -> {
//                        it.data.message?.let { it1 -> preferencesDataStore.saveUserID(userId = it1) }
                        viewModelScope.launch {
                            it.data.message?.let { it1 -> preferencesDataStore.saveUserID(it1) }
                        }

                        _createUserState.value =
                            CreateUserState(success = it.data, isLoading = false)


                    }
                }

            }

        }
    }


    private val _loginUserState = MutableStateFlow(LoginUserState())
    val loginUserState = _loginUserState.asStateFlow()

    fun loginUser(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.authUser(email, password).collect {

                when (it) {
                    is ResultState.Loading -> {

                        _loginUserState.value = LoginUserState(isLoading = true)

                    }

                    is ResultState.Error -> {
                        _loginUserState.value =
                            LoginUserState(error = it.exception.message, isLoading = false)

                    }

                    is ResultState.Success -> {
                        it.data.message.let { it1 -> preferencesDataStore.saveUserID(userId = it1) }



                        _loginUserState.value = LoginUserState(success = it.data, isLoading = false)


                    }
                }

            }


        }
    }


    private val _getSpecificUser = MutableStateFlow(GetSpecificUserState())
    val getSpecificUser = _getSpecificUser.asStateFlow()

    fun getSpecificUser(userId: String) {

        viewModelScope.launch(Dispatchers.IO) {
            repo.getSpecificUserData(userId).collect {


                when (it) {
                    is ResultState.Loading -> {

                        _getSpecificUser.value = GetSpecificUserState(isLoading = true)

                    }

                    is ResultState.Error -> {
                        Log.d("TAG4", "message ${it.exception}")

                        _getSpecificUser.value =
                            GetSpecificUserState(error = it.exception.message, isLoading = false)

                    }

                    is ResultState.Success -> {
                        Log.d("TAG1", "message ${it.data}")

                        _getSpecificUser.value =
                            GetSpecificUserState(success = it.data, isLoading = false)

                    }
                }


            }
        }


    }

    fun getAllProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getAllProducts().collect {

                when (it) {

                    is ResultState.Loading -> {
                        _getAllProductsState.value = GetAllProductsState(isLoading = true)
                    }

                    is ResultState.Success -> {
                        Log.d("TAG1", "message ${it.data}")
                        _getAllProductsState.value =
                            GetAllProductsState(success = it.data, isLoading = false)

                    }

                    is ResultState.Error -> {
                        Log.d("TAG1", "message ${it.exception}")

                        _getAllProductsState.value =
                            GetAllProductsState(error = it.exception.message, isLoading = false)
                    }


                }


            }


        }

    }


    fun getSpecificProduct(
        productId: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getSpecificProduct(productId).collect {

                when (it) {

                    is ResultState.Loading -> {
                        _getSpecificState.value = GetSpecificProductState(isLoading = false)
                    }

                    is ResultState.Success -> {
                        Log.d("TAGP", "message ${it.data}")
                        _getSpecificState.value =
                            GetSpecificProductState(success = it.data, isLoading = false)

                    }

                    is ResultState.Error -> {
                        Log.d("TAG1", "message ${it.exception}")

                        _getSpecificState.value =
                            GetSpecificProductState(error = it.exception.message, isLoading = false)
                    }


                }


            }


        }
    }

    fun createOrder(
        userId: String,
        productId: String,
        quantity: Int,
        price: Float,
        totalAmount: Float,
        productName: String,
        userName: String,
        message: String,
        category: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.createOrder(
                userId,
                productId,
                quantity,
                price,
                totalAmount,
                productName,
                userName,
                message,
                category
            ).collect {

                when (it) {

                    is ResultState.Loading -> {
                        _createOrderState.value = CreateOrderState(isLoading = true)
                    }

                    is ResultState.Success -> {
                        Log.d("tag23", "message23 ${it.data}")
                        _createOrderState.value =
                            CreateOrderState(success = it.data, isLoading = false)

                    }

                    is ResultState.Error -> {
                        Log.d("TAG1", "message ${it.exception}")

                        _createOrderState.value =
                            CreateOrderState(error = it.exception.message, isLoading = false)
                    }


                }


            }


        }
    }

// For send Verification Code

    fun sendVerificationCode(
        phone: String,
        activity: Activity,
        onCodeSent: () -> Unit,
        onError: (String) -> Unit
    ) {
        val options = PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
            .setPhoneNumber(phone)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(activity)
            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(credential: PhoneAuthCredential) {

                }

                override fun onVerificationFailed(e: FirebaseException) {
                    onError(e.localizedMessage ?: "Verification failed")
                }

                override fun onCodeSent(
                    verificationId: String,
                    token: PhoneAuthProvider.ForceResendingToken
                ) {
                    storedVerificationId = verificationId
                    resendToken = token
                    onCodeSent()
                }
            }).build()

        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    //Function for Verify OTP
    fun verifyOtp(
        code: String,
        navController: NavController,
        onError: (String) -> Unit
    ) {
        val verificationId = storedVerificationId
        if (verificationId == null) {
            onError("Verification ID missing.")
            return
        }

        val credential = PhoneAuthProvider.getCredential(verificationId, code)
        FirebaseAuth.getInstance().signInWithCredential(credential)
            .addOnSuccessListener {
                navController.navigate(Routes.HomeScreenRoutes) {
                    popUpTo(0)
                }
            }
            .addOnFailureListener {
                onError(it.localizedMessage ?: "Invalid OTP")
            }
    }

    private val _imageUri = mutableStateOf<Uri?>(null)
    val imageUri: State<Uri?> = _imageUri

    fun loadSavedImageUri(context: Context) {
        viewModelScope.launch {
            val savedUri = preferencesDataStore.getImageUri()
            _imageUri.value = savedUri?.let { Uri.parse(it.toString()) }
        }
    }

    fun updateImageUri(uri: Uri) {
        _imageUri.value = uri
    }

    fun saveImageUri() {
        viewModelScope.launch {
            _imageUri.value?.let {
                preferencesDataStore.saveImageUri(it.toString())
            }
        }
    }
}


data class LoginUserState(
    val isLoading: Boolean = false,
    val success: LoginResponse? = null,
    val error: String? = null
)

data class CreateUserState(
    val isLoading: Boolean = false,
    val success: CreateUserResponse? = null,
    val error: String? = null
)

data class GetSpecificUserState(
    val isLoading: Boolean = false,
    val success: SpecificUserResponse? = null,
    val error: String? = null
)

data class GetAllProductsState(
    val isLoading: Boolean = false,
    val success: GetAllProductsResponse? = null,
    val error: String? = null
)

data class GetSpecificProductState(
    val isLoading: Boolean = false,
    val success: GetSpecificProductResponse? = null,
    val error: String? = null
)

data class CreateOrderState(
    val isLoading: Boolean = false,
    val success: CreateOrderResponse? = null,
    val error: String? = null
)

data class GetUserId(
    val isLoading: Boolean = true,
    val userId: String? = ""
)
