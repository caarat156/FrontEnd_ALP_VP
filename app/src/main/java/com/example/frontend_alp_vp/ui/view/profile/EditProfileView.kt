package com.example.frontend_alp_vp.ui.view.profile

import android.app.Application
import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewModelScope
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.frontend_alp_vp.datastore.AuthenticationManager
import com.example.frontend_alp_vp.network.RetrofitClient
import com.example.frontend_alp_vp.model.UserResponse
import com.example.frontend_alp_vp.ui.view.login.LoginView
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// --- VIEWMODEL ---
class EditProfileViewModel(application: Application) : AndroidViewModel(application) {

    var nama by mutableStateOf("")
    var username by mutableStateOf("")
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var nomorTelepon by mutableStateOf("")
    var imageUri by mutableStateOf<Uri?>(null)
    var isLoading by mutableStateOf(false)

    init {
        loadUserProfile()
    }

    private fun loadUserProfile() {
        viewModelScope.launch {
            isLoading = true
            try {
                val context = getApplication<Application>().applicationContext
                val authManager = AuthenticationManager(context)
                val savedToken = authManager.authToken.first()

                if (savedToken != null) {
                    val token = "Bearer $savedToken"
                    // Assume standard call - adjust if your API differs
                    // val response = RetrofitClient.instance.getProfile(token)
                }
            } catch (e: Exception) {
                println("Error loading profile: ${e.message}")
            } finally {
                isLoading = false
            }
        }
    }

    fun updateUserProfile(context: Context) {
        viewModelScope.launch {
            isLoading = true
            try {
                val appCtx = getApplication<Application>().applicationContext
                val authManager = AuthenticationManager(appCtx)
                val savedToken = authManager.authToken.first()

                if (savedToken != null) {
                    val token = "Bearer $savedToken"
                    val updateData = hashMapOf(
                        "name" to nama,
                        "username" to username,
                        "email" to email,
                        "phoneNumber" to nomorTelepon
                    )
                    if (password.isNotEmpty()) {
                        updateData["password"] = password
                    }

                    RetrofitClient.instance.updateProfile(token, updateData).enqueue(object : Callback<UserResponse> {
                        override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                            isLoading = false
                            if (response.isSuccessful) {
                                Toast.makeText(context, "Profil Berhasil Diupdate!", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(context, "Gagal Update: ${response.message()}", Toast.LENGTH_SHORT).show()
                            }
                        }
                        override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                            isLoading = false
                            Toast.makeText(context, "Error Jaringan: ${t.message}", Toast.LENGTH_SHORT).show()
                        }
                    })
                }
            } catch (e: Exception) {
                isLoading = false
                Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

// --- SCREEN ---
@Composable
fun EditProfileView(
    viewModel: EditProfileViewModel = viewModel(),
    onNavigateBack: () -> Unit = {},
    onLogout: () -> Unit // Navigation Callback
) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        viewModel.imageUri = uri
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Edit Profil",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

            // Logout Button
            Button(
                onClick = { onLogout() },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE53935)),
                shape = RoundedCornerShape(8.dp),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 0.dp),
                modifier = Modifier.height(32.dp)
            ) {
                Text(text = "Keluar", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.White)
            }
        }

        // Profile Image
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(Color.LightGray)
                .border(2.dp, MaterialTheme.colorScheme.tertiary, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            if (viewModel.imageUri != null) {
                AsyncImage(
                    model = ImageRequest.Builder(context).data(viewModel.imageUri).crossfade(true).build(),
                    contentDescription = "Profile Picture",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                // Placeholder icon if you have one, or simple text
                Text("Foto")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        ThemeButton(text = "Ubah Gambar", onClick = { launcher.launch("image/*") })
        Spacer(modifier = Modifier.height(32.dp))

        // Form Fields
        if (viewModel.isLoading) {
            CircularProgressIndicator(modifier = Modifier.padding(16.dp))
        } else {
            ThemeTextField(label = "Nama", value = viewModel.nama, onValueChange = { viewModel.nama = it })
            ThemeTextField(label = "Username", value = viewModel.username, onValueChange = { viewModel.username = it })
            ThemeTextField(label = "Email", value = viewModel.email, onValueChange = { viewModel.email = it }, keyboardType = KeyboardType.Email)
            ThemeTextField(label = "Password", value = viewModel.password, onValueChange = { viewModel.password = it }, isPassword = true)
            ThemeTextField(label = "Nomor Telpon", value = viewModel.nomorTelepon, onValueChange = { viewModel.nomorTelepon = it }, keyboardType = KeyboardType.Phone)
        }

        Spacer(modifier = Modifier.height(40.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            ThemeButton(text = "Kembali", onClick = onNavigateBack, modifier = Modifier.weight(1f).padding(end = 8.dp))
            ThemeButton(text = "Simpan", onClick = { viewModel.updateUserProfile(context) }, modifier = Modifier.weight(1f).padding(start = 8.dp))
        }
    }
}

// Reuseable Components internal to this file
@Composable
fun ThemeButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
        shape = RoundedCornerShape(12.dp),
        modifier = modifier.height(50.dp)
    ) {
        Text(text = text, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.Black)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThemeTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    isPassword: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    Column(modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)) {
        Text(text = label, fontSize = 16.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 8.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            singleLine = true
        )
    }
}