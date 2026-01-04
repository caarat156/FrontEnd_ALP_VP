package com.example.frontend_alp_vp.ui.view.login

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.frontend_alp_vp.datastore.AuthenticationManager
import com.example.frontend_alp_vp.model.LoginRequest
import com.example.frontend_alp_vp.network.RetrofitClient
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    // State variables
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var isLoading by mutableStateOf(false)

    // Initialize AuthenticationManager
    private val authenticationManager = AuthenticationManager(application.applicationContext)

    fun login(context: Context, onSuccess: () -> Unit) {
        if (email.isNotEmpty() && password.isNotEmpty()) {

            // 1. Start Coroutine (Required for suspend functions)
            viewModelScope.launch {
                isLoading = true
                try {
                    val request = LoginRequest(email, password)

                    // 2. Call API directly (No .enqueue needed anymore!)
                    // Because ApiService now returns LoginResponse directly
                    val loginResponse = RetrofitClient.instance.login(request)

                    // 3. Check Logic
                    if (!loginResponse.token.isNullOrEmpty()) {
                        // Save Token
                        authenticationManager.saveToken(loginResponse.token)

                        Toast.makeText(context, "Login Berhasil!", Toast.LENGTH_SHORT).show()
                        onSuccess()
                    } else {
                        val msg = loginResponse.message ?: "Token tidak ditemukan"
                        Toast.makeText(context, "Login Gagal: $msg", Toast.LENGTH_SHORT).show()
                    }

                } catch (e: Exception) {
                    // 4. Handle Network Errors
                    Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                } finally {
                    isLoading = false
                }
            }
        } else {
            Toast.makeText(context, "Mohon isi semua data", Toast.LENGTH_SHORT).show()
        }
    }
}