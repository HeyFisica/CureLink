package com.example.curelink.prf

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(("user_preferences"))


class PreferencesDataStore(private val context: Context) {



    val IMAGE_URI = stringPreferencesKey("image_uri")
    private val USER_ID_KEY = stringPreferencesKey("user_id_key")

suspend fun saveImageUri(uri: String) {
    context.dataStore.edit { preferences ->
        preferences[IMAGE_URI] = uri
    }
}

    fun getImageUri(): Flow<String?> {
        return context.dataStore.data.map { preferences ->
            preferences[IMAGE_URI]
        }
    }


     suspend fun saveUserID(userId: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_ID_KEY] = userId
        }
    }

    val PreferenceUserId:Flow<String?> = context.dataStore.data.map {
        it[USER_ID_KEY]
    }



    suspend fun  clearUserId(){
        context.dataStore.edit {
            it.remove(USER_ID_KEY)
        }
    }
}