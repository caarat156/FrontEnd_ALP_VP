package com.example.frontend_alp_vp.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// This creates the storage file "user_prefs"
private val Context.dataStore by preferencesDataStore("user_prefs")

class AuthenticationManager(private val context: Context) {
    companion object {
        private val TOKEN_KEY = stringPreferencesKey("auth_token")
    }

    // Read the token (Flow updates automatically)
    val authToken: Flow<String?>
        get() = context.dataStore.data.map { preferences ->
            preferences[TOKEN_KEY]
        }

    // Save the token
    suspend fun saveToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[TOKEN_KEY] = token
        }
    }

    // Delete token (Logout)
    suspend fun clearToken() {
        context.dataStore.edit { preferences ->
            preferences.remove(TOKEN_KEY)
        }
    }
}