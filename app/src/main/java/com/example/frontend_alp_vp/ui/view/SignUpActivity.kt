package com.example.frontend_alp_vp.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.frontend_alp_vp.model.RegisterRequest // [NEW] Import Request Model
import com.example.frontend_alp_vp.model.RegisterResponse // [NEW] Import Response Model
import com.example.frontend_alp_vp.network.RetrofitClient // [NEW] Import Network Client
import com.example.frontend_alp_vp.ui.theme.FrontEnd_ALP_VPTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FrontEnd_ALP_VPTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SignUpScreen(
                        onLoginClick = {
                            // Navigate back to Login Screen
                            finish()
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun SignUpScreen(onLoginClick: () -> Unit) {
    val context = LocalContext.current // [NEW] Context for Toasts

    // State for all the fields
    var namaLengkap by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var kataSandi by remember { mutableStateOf("") }
    var nomorTelepon by remember { mutableStateOf("") }

    // [NEW] Loading State
    var isLoading by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(scrollState), // Make it scrollable so it fits on small screens
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Title
        Text(
            text = "Daftar",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(32.dp))

        // The 5 Fields using our custom component
        SignupTextField(label = "Nama Lengkap", value = namaLengkap, onValueChange = { namaLengkap = it })
        Spacer(modifier = Modifier.height(16.dp))

        SignupTextField(label = "Username", value = username, onValueChange = { username = it })
        Spacer(modifier = Modifier.height(16.dp))

        SignupTextField(label = "Email", value = email, onValueChange = { email = it })
        Spacer(modifier = Modifier.height(16.dp))

        SignupTextField(label = "Kata Sandi", value = kataSandi, onValueChange = { kataSandi = it }, isPassword = true)
        Spacer(modifier = Modifier.height(16.dp))

        // For phone number, we want a number keyboard
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(text = "Nomor Telepon", fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 8.dp))
            OutlinedTextField(
                value = nomorTelepon,
                onValueChange = { nomorTelepon = it },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.tertiary,
                    unfocusedContainerColor = MaterialTheme.colorScheme.tertiary,
                    focusedBorderColor = MaterialTheme.colorScheme.secondary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.secondary
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                singleLine = true
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // "Daftar" Button
        Button(
            onClick = {
                // [NEW] 1. Validation
                if (namaLengkap.isNotEmpty() && username.isNotEmpty() && email.isNotEmpty() && kataSandi.isNotEmpty()) {
                    isLoading = true

                    // [NEW] 2. Create Request Object
                    // Note: We are not sending 'nomorTelepon' because the backend model doesn't support it yet.
                    val request = RegisterRequest(
                        name = namaLengkap,
                        username = username,
                        email = email,
                        password = kataSandi
                    )

                    // [NEW] 3. Network Call
                    RetrofitClient.instance.register(request).enqueue(object : Callback<RegisterResponse> {
                        override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                            isLoading = false
                            if (response.isSuccessful) {
                                // SUCCESS
                                Toast.makeText(context, "Registrasi Berhasil! Silakan Login.", Toast.LENGTH_LONG).show()
                                // Navigate back to Login
                                onLoginClick()
                            } else {
                                // ERROR (e.g., Email already taken)
                                Toast.makeText(context, "Registrasi Gagal: Cek data anda.", Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                            isLoading = false
                            // NETWORK ERROR
                            Toast.makeText(context, "Connection Error: ${t.message}", Toast.LENGTH_SHORT).show()
                        }
                    })
                } else {
                    Toast.makeText(context, "Mohon isi semua data wajib", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary), // Brown button
            shape = RoundedCornerShape(12.dp),
            enabled = !isLoading // Disable button while loading
        ) {
            if (isLoading) {
                CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
            } else {
                Text(text = "Daftar", fontSize = 18.sp ,fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Footer Link
        Row {
            Text(text = "Jika sudah memiliki akun klik ")
            Text(
                text = "Masuk",
                color = MaterialTheme.colorScheme.secondary, // Brown text
                textDecoration = TextDecoration.Underline,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable { onLoginClick() }
            )
        }
    }
}

// Put this at the bottom of the file, outside the class
@Composable
fun SignupTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    isPassword: Boolean = false
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        // The Bold Label
        Text(
            text = label,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        // The Input Box
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp), // Rounded corners
            visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
            colors = OutlinedTextFieldDefaults.colors(
                // Background is your Beige (tersier)
                focusedContainerColor = MaterialTheme.colorScheme.tertiary,
                unfocusedContainerColor = MaterialTheme.colorScheme.tertiary,
                // Border is your Brown (sekunder)
                focusedBorderColor = MaterialTheme.colorScheme.secondary,
                unfocusedBorderColor = MaterialTheme.colorScheme.secondary
            ),
            singleLine = true
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SignUpScreenPreview() {
    FrontEnd_ALP_VPTheme {
        // Pass empty function {} for preview
        SignUpScreen(onLoginClick = {})
    }
}