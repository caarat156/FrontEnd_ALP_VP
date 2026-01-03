package com.example.frontend_alp_vp.ui.view.login

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel // Change to AndroidViewModel
import androidx.lifecycle.viewModelScope // Needed for Coroutines
import com.example.frontend_alp_vp.datastore.AuthenticationManager
import com.example.frontend_alp_vp.model.LoginRequest
import com.example.frontend_alp_vp.model.LoginResponse
import com.example.frontend_alp_vp.network.RetrofitClient
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// 1. Change to AndroidViewModel to get Application Context
class LoginViewModel(application: Application) : AndroidViewModel(application) {

    // State variables
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var isLoading by mutableStateOf(false)

    // 2. Initialize AuthenticationManager
    private val authenticationManager = AuthenticationManager(application.applicationContext)

    fun login(context: Context, onSuccess: () -> Unit) {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            isLoading = true

            val request = LoginRequest(email, password)

            RetrofitClient.instance.login(request).enqueue(object : Callback<LoginResponse> {
                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                    isLoading = false
                    if (response.isSuccessful) {
                        val loginResponse = response.body()

                        // 3. CHECK IF TOKEN EXISTS AND SAVE IT
                        if (loginResponse != null && !loginResponse.token.isNullOrEmpty()) {

                            // Launch a coroutine to save into DataStore
                            viewModelScope.launch {
                                authenticationManager.saveToken(loginResponse.token)

                                // Show success and navigate
                                Toast.makeText(context, "Login Berhasil!", Toast.LENGTH_SHORT).show()
                                onSuccess()
                            }
                        } else {
                            Toast.makeText(context, "Login Gagal: Token tidak ditemukan", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(context, "Gagal: Cek Email/Password", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    isLoading = false
                    Toast.makeText(context, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        } else {
            Toast.makeText(context, "Mohon isi semua data", Toast.LENGTH_SHORT).show()
        }
    }
}