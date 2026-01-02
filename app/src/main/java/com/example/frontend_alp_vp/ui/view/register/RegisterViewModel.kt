package com.example.frontend_alp_vp.ui.view.register

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.frontend_alp_vp.model.RegisterRequest
import com.example.frontend_alp_vp.model.RegisterResponse
import com.example.frontend_alp_vp.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel : ViewModel() {
    // State variables
    var name by mutableStateOf("")
    var username by mutableStateOf("")
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var phoneNumber by mutableStateOf("")
    var isLoading by mutableStateOf(false)

    fun register(context: Context, onSuccess: () -> Unit) {
        if (name.isNotEmpty() && username.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && phoneNumber.isNotEmpty()) {
            isLoading = true

            val request = RegisterRequest(name, username, email, password, phoneNumber)

            RetrofitClient.instance.register(request).enqueue(object : Callback<RegisterResponse> {
                override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                    isLoading = false
                    if (response.isSuccessful) {
                        Toast.makeText(context, "Registrasi Berhasil! Silahkan Masuk", Toast.LENGTH_SHORT).show()
                        onSuccess()
                    } else {
                        Toast.makeText(context, "Gagal: ${response.message()}", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                    isLoading = false
                    Toast.makeText(context, "Error Jaringan: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        } else {
            Toast.makeText(context, "Mohon isi semua data", Toast.LENGTH_SHORT).show()
        }
    }
}