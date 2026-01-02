package com.example.frontend_alp_vp.ui.view.login

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.frontend_alp_vp.model.LoginRequest
import com.example.frontend_alp_vp.model.LoginResponse
import com.example.frontend_alp_vp.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel : ViewModel() {
    // State variables
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var isLoading by mutableStateOf(false)

    // The Login Function
    fun login(context: Context, onSuccess: () -> Unit) {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            isLoading = true

            val request = LoginRequest(email, password)

            RetrofitClient.instance.login(request).enqueue(object : Callback<LoginResponse> {
                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                    isLoading = false
                    if (response.isSuccessful) {
                        Toast.makeText(context, "Login Berhasil!", Toast.LENGTH_SHORT).show()
                        // Save token here if you had DataStore set up, for now just navigate
                        onSuccess()
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