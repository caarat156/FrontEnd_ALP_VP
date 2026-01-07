package com.example.frontend_alp_vp.ui.view.profile

import android.content.Context
import android.net.Uri
import android.webkit.MimeTypeMap
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frontend_alp_vp.datastore.AuthenticationManager
import com.example.frontend_alp_vp.model.Reel
import com.example.frontend_alp_vp.model.UserData
import com.example.frontend_alp_vp.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.FileOutputStream

class ProfileViewModel(private val authManager: AuthenticationManager) : ViewModel() {

    private val _userData = MutableStateFlow<UserData?>(null)
    val userData: StateFlow<UserData?> = _userData

    private val _userReels = MutableStateFlow<List<Reel>>(emptyList())
    val userReels: StateFlow<List<Reel>> = _userReels

    init {
        loadProfileData()
    }

    fun loadProfileData() {
        viewModelScope.launch {
            try {
                val token = authManager.authToken.first()
                if (!token.isNullOrEmpty()) {
                    val authHeader = "Bearer $token"

                    // 1. Fetch Profile
                    val userResponse = RetrofitClient.instance.getProfile(authHeader)
                    if (userResponse.data != null) {
                        _userData.value = userResponse.data
                    }

                    // 2. Fetch Reels
                    val reelsResponse = RetrofitClient.instance.getMyReels(authHeader)
                    if (reelsResponse.isSuccessful) {
                        _userReels.value = reelsResponse.body()?.data ?: emptyList()
                    }
                }
            } catch (e: Exception) {
                Log.e("PROFILE_VM", "Error loading data", e)
            }
        }
    }

    fun uploadReel(context: Context, uri: Uri, caption: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                val token = authManager.authToken.first() ?: ""

                if (token.isNotEmpty()) {
                    Log.d("UPLOAD_DEBUG", "Starting upload...")

                    // 1. Prepare File with CORRECT MimeType and Extension
                    val file = uriToFile(context, uri)

                    // Determine MIME type (e.g. image/jpeg or video/mp4)
                    val mimeType = context.contentResolver.getType(uri) ?: "image/jpeg"
                    Log.d("UPLOAD_DEBUG", "File: ${file.name}, Mime: $mimeType")

                    val requestFile = file.asRequestBody(mimeType.toMediaTypeOrNull())
                    val body = MultipartBody.Part.createFormData("file", file.name, requestFile)

                    // 2. Prepare Caption
                    val captionPart = caption.toRequestBody("text/plain".toMediaTypeOrNull())

                    // 3. Upload
                    val response = RetrofitClient.instance.uploadReel("Bearer $token", body, captionPart)

                    if (response.isSuccessful) {
                        Log.d("UPLOAD_DEBUG", "Upload Success: ${response.code()}")
                        onSuccess()
                        loadProfileData() // Refresh grid
                    } else {
                        // Log the server error message
                        val errorBody = response.errorBody()?.string()
                        Log.e("UPLOAD_ERROR", "Failed: ${response.code()} - $errorBody")
                    }
                } else {
                    Log.e("UPLOAD_ERROR", "Token is empty")
                }
            } catch (e: Exception) {
                Log.e("UPLOAD_EXCEPTION", "Exception during upload", e)
                e.printStackTrace()
            }
        }
    }

    // --- HELPER TO GET FILE WITH CORRECT EXTENSION ---
    private fun uriToFile(context: Context, uri: Uri): File {
        val contentResolver = context.contentResolver

        // Try to get extension from MimeType (e.g. .jpg, .mp4)
        val mimeType = contentResolver.getType(uri)
        val extension = MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType) ?: "tmp"

        val inputStream = contentResolver.openInputStream(uri)
        // Create temp file with the REAL extension
        val tempFile = File.createTempFile("upload_", ".$extension", context.cacheDir)

        val outputStream = FileOutputStream(tempFile)
        inputStream?.copyTo(outputStream)

        inputStream?.close()
        outputStream.close()

        return tempFile
    }

    fun deleteReel(contentId: Int) {
        viewModelScope.launch {
            try {
                val token = authManager.authToken.first() ?: ""
                if (token.isNotEmpty()) {
                    val response = RetrofitClient.instance.deleteReel("Bearer $token", contentId)

                    if (response.isSuccessful) {
                        // Reload data to remove the deleted item from the screen
                        loadProfileData()
                    } else {
                        Log.e("DELETE_REEL", "Failed: ${response.code()}")
                    }
                }
            } catch (e: Exception) {
                Log.e("DELETE_REEL", "Error", e)
            }
        }
    }
}