package com.example.frontend_alp_vp.ui.view.register

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frontend_alp_vp.model.RegisterRequest
import com.example.frontend_alp_vp.network.RetrofitClient
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {
    // State variables
    var name by mutableStateOf("")
    var username by mutableStateOf("")
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var phone_number by mutableStateOf("")
    var isLoading by mutableStateOf(false)

    fun register(context: Context, onSuccess: () -> Unit) {
        if (name.isNotEmpty() && username.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && phone_number.isNotEmpty()) {

            // 1. Start Coroutine
            viewModelScope.launch {
                isLoading = true
                try {
                    val request = RegisterRequest(name, username, email, password, phone_number)

                    val response = RetrofitClient.instance.register(request)

                    // 3. Success!
                    Toast.makeText(context, "Registrasi Berhasil! Silahkan Masuk", Toast.LENGTH_SHORT).show()
                    onSuccess()

                } catch (e: Exception) {
                    // 4. Handle Errors (Network fail, or Server returned 400/500)
                    Toast.makeText(context, "Gagal: ${e.message}", Toast.LENGTH_SHORT).show()
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