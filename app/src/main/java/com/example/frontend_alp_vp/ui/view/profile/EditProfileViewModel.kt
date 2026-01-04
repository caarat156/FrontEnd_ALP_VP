package com.example.frontend_alp_vp.ui.view.profile

import android.app.Application
import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.frontend_alp_vp.datastore.AuthenticationManager
import com.example.frontend_alp_vp.network.RetrofitClient
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.FileOutputStream

class EditProfileViewModel(application: Application) : AndroidViewModel(application) {

    // Form Data
    var nama by mutableStateOf("")
    var username by mutableStateOf("")
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var nomorTelepon by mutableStateOf("")

    // Image Logic
    var currentProfileUrl by mutableStateOf<String?>(null)
    var newImageUri by mutableStateOf<Uri?>(null)
    var isLoading by mutableStateOf(false)

    init {
        loadUserProfile()
    }

    private fun loadUserProfile() {
        viewModelScope.launch {
            isLoading = true
            val context = getApplication<Application>().applicationContext

            try {
                val authManager = AuthenticationManager(context)
                // 1. Get raw token
                var savedToken = authManager.authToken.first()

                if (savedToken.isNullOrEmpty()) {
                    Log.e("API_ERROR", "Token is empty")
                    return@launch
                }

                // 2. CLEAN THE TOKEN (Crucial Fix)
                // Sometimes DataStore saves strings with extra quotes like "eyJ..."
                // We must remove them.
                savedToken = savedToken.replace("\"", "").trim()

                val token = "Bearer $savedToken"

                Log.d("API_DEBUG", "Sending Token: $token") // Check Logcat for this!

// Inside loadUserProfile()

                val response = RetrofitClient.instance.getProfile(token)

// Safety Check: If data is missing, stop here
                if (response.data == null) {
                    Toast.makeText(context, "Error: Data kosong", Toast.LENGTH_SHORT).show()
                    return@launch
                }

                val userData = response.data // Now it is safe to use
                nama = userData.name
// ... rest of your code

                nama = userData.name
                username = userData.username
                email = userData.email
                nomorTelepon = userData.phoneNumber ?: ""

                if (userData.profilePhoto != null) {
                    val rawPath = userData.profilePhoto

                    // 1. Fix Windows slashes just in case
                    val cleanPath = rawPath.replace("\\", "/")

                    // 2. Check if it's already a full URL
                    if (cleanPath.startsWith("http")) {
                        currentProfileUrl = cleanPath
                    } else {
                        // 3. If it's a relative path (e.g., "uploads/image.jpg")
                        // Ensure we don't double the slash
                        val serverUrl = "http://10.0.2.2:3000/public/"

                        // Handle case where path might already start with "public/"
                        // If path is "public/uploads/img.jpg", we want "http.../public/uploads/img.jpg"
                        // If path is "uploads/img.jpg", we want "http.../public/uploads/img.jpg"

                        if (cleanPath.startsWith("public/")) {
                            // Base url without 'public/'
                            currentProfileUrl = "http://10.0.2.2:3000/$cleanPath"
                        } else {
                            currentProfileUrl = "$serverUrl$cleanPath"
                        }
                    }

                    Log.d("IMAGE_DEBUG", "Final Image URL: $currentProfileUrl")
                }

            } catch (e: retrofit2.HttpException) {
                // This catches 401, 404, 500 errors specifically
                Log.e("API_ERROR", "HTTP Error: ${e.code()} - ${e.message()}")
                Toast.makeText(context, "Error: ${e.code()} (Unauthorized or Server Error)", Toast.LENGTH_LONG).show()
            } catch (e: Exception) {
                Log.e("API_ERROR", "General Error: ${e.message}")
                Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_LONG).show()
            } finally {
                isLoading = false
            }
        }
    }

    fun updateUserProfile(context: Context) {
        viewModelScope.launch {
            isLoading = true
            try {
                val authManager = AuthenticationManager(context)
                var savedToken = authManager.authToken.first()

                if (!savedToken.isNullOrEmpty()) {
                    // Clean token here too
                    savedToken = savedToken.replace("\"", "").trim()
                    val token = "Bearer $savedToken"

                    val map = HashMap<String, RequestBody>()

                    // Helper to create plain text parts
                    fun createPart(value: String): RequestBody {
                        return value.toRequestBody("text/plain".toMediaTypeOrNull())
                    }

                    map["name"] = createPart(nama)
                    map["username"] = createPart(username)
                    map["email"] = createPart(email)
                    map["phoneNumber"] = createPart(nomorTelepon)

                    if (password.isNotEmpty()) {
                        map["password"] = createPart(password)
                    }

                    var imagePart: MultipartBody.Part? = null

                    if (newImageUri != null) {
                        val file = uriToFile(newImageUri!!, context)
                        val requestFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())

                        // NOTE: Ensure "profile_photo" matches your backend (upload.single('profile_photo'))
                        imagePart = MultipartBody.Part.createFormData(
                            "profile_photo",
                            file.name,
                            requestFile
                        )
                    }

                    // Call the API
                    RetrofitClient.instance.updateProfile(token, map, imagePart)

                    Toast.makeText(context, "Update Berhasil!", Toast.LENGTH_SHORT).show()
                    newImageUri = null
                    loadUserProfile() // Refresh data
                }
            } catch (e: retrofit2.HttpException) {
                Log.e("API_ERROR", "Update Failed: ${e.code()} - ${e.response()?.errorBody()?.string()}")
                Toast.makeText(context, "Gagal: ${e.code()}", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Log.e("API_ERROR", "Update Error: ${e.message}")
                Toast.makeText(context, "Error System", Toast.LENGTH_SHORT).show()
            } finally {
                isLoading = false
            }
        }
    }

    // ... Keep your uriToFile and other helpers as they were ...
    private fun uriToFile(selectedImg: Uri, context: Context): File {
        val contentResolver = context.contentResolver
        val myFile = File.createTempFile("temp_image", ".jpg", context.cacheDir)
        val inputStream = contentResolver.openInputStream(selectedImg) as java.io.InputStream
        val outputStream = FileOutputStream(myFile)
        inputStream.copyTo(outputStream)
        inputStream.close()
        outputStream.close()
        return myFile
    }

    private fun File.asRequestBody(contentType: okhttp3.MediaType?): RequestBody {
        return RequestBody.create(contentType, this)
    }
}