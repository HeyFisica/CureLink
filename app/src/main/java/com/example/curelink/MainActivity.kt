package com.example.curelink

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.curelink.prf.PreferencesDataStore
import com.example.curelink.ui.screens.navigation.SignNavigation
import com.example.curelink.ui.theme.CureLinkTheme
import com.example.curelink.viewModel.MyViewModel

class MainActivity() : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val context = LocalContext.current
            val preferencesDataStore = PreferencesDataStore(context)
            val viewModel = MyViewModel(preferencesDataStore)

            CureLinkTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(modifier = Modifier.padding(innerPadding)) {

                        SignNavigation(viewModel, preferencesDataStore)

                    }


                }
            }
        }
    }
}




