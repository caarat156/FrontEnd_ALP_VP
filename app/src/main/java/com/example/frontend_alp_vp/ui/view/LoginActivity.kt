package com.example.frontend_alp_vp.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions // [ADDED] For keyboard types
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext // [ADDED] To show Toasts
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.frontend_alp_vp.MainActivity
import com.example.frontend_alp_vp.model.LoginRequest // [NEW] Your data model
import com.example.frontend_alp_vp.model.LoginResponse // [NEW] Your data model
import com.example.frontend_alp_vp.network.RetrofitClient // [NEW] Your network client
import com.example.frontend_alp_vp.ui.theme.FrontEnd_ALP_VPTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FrontEnd_ALP_VPTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LoginScreen(
                        onSignUpClick = {
                            // [NOTE] Ensure SignUpActivity exists, or comment this line out for now
                            startActivity(Intent(this, SignUpActivity::class.java))
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun LoginScreen(onSignUpClick: () -> Unit) {
    // [NEW] Logic: Get Context for Toast messages
    val context = LocalContext.current

    // [CHANGED] Logic: Renamed 'username' to 'email' to match your Backend Requirement
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // [NEW] Logic: State to show loading spinner
    var isLoading by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Masuk", fontSize = 32.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(32.dp))

        // [CHANGED] Logic: Passed 'email' instead of 'username'
        LoginTextField(
            label = "Email",
            value = email,
            onValueChange = { email = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        LoginTextField(
            label = "Kata Sandi",
            value = password,
            onValueChange = { password = it },
            isPassword = true
        )

        Spacer(modifier = Modifier.height(32.dp))

        // [CHANGED] Logic: Wrapped Button content to handle Loading State
        Button(
            onClick = {
                // [NEW] Network Call: The "Brain" of the operation
                if (email.isNotEmpty() && password.isNotEmpty()) {
                    isLoading = true // Start loading

                    val request = LoginRequest(email, password)

                    RetrofitClient.instance.login(request).enqueue(object : Callback<LoginResponse> {
                        override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                            isLoading = false // Stop loading

                            if (response.isSuccessful) {
                                Toast.makeText(context, "Login Berhasil!", Toast.LENGTH_SHORT).show()

                                // Navigate to MainActivity
                                val intent = Intent(context, MainActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                context.startActivity(intent)
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
            },
            modifier = Modifier.fillMaxWidth().height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
            shape = RoundedCornerShape(12.dp),
            enabled = !isLoading // [NEW] Disable button while loading
        ) {
            if (isLoading) {
                // [NEW] Show spinner if loading
                CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
            } else {
                Text(text = "Masuk", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row {
            Text(text = "Belum memiliki akun? klik ")
            Text(
                text = "Daftar",
                color = MaterialTheme.colorScheme.secondary,
                textDecoration = TextDecoration.Underline,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable { onSignUpClick() }
            )
        }
    }
}

// [ADDED] The Custom Component you were missing
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    isPassword: Boolean = false
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        singleLine = true,
        // Logic to toggle password visibility dots
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = if (isPassword) {
            KeyboardOptions(keyboardType = KeyboardType.Password)
        } else {
            KeyboardOptions(keyboardType = KeyboardType.Email)
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.secondary,
            unfocusedBorderColor = Color.Gray
        )
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreenPreview() {
    FrontEnd_ALP_VPTheme {
        // We pass an empty function {} because the preview doesn't need to actually navigate
        LoginScreen(onSignUpClick = {})
    }
}