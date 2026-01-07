package com.example.frontend_alp_vp

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Membuat ekstensi properti context.dataStore
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_prefs")

class UserPreferences(private val context: Context) {

    companion object {
        val USER_TOKEN = stringPreferencesKey("user_token")
    }

    // Fungsi untuk mengambil token (berupa Flow agar reaktif)
    fun getToken(): Flow<String?> {
        return context.dataStore.data.map { preferences ->
            preferences[USER_TOKEN]
        }
    }

    // Fungsi untuk menyimpan token (akan dipakai nanti saat Login selesai)
    suspend fun saveToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_TOKEN] = token
        }
    }
}