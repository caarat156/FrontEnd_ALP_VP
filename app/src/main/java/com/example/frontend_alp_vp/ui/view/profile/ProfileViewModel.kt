package com.example.frontend_alp_vp.ui.view.profile

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
                // Get token from DataStore safely
                val token = authManager.authToken.first()

                if (!token.isNullOrEmpty()) {
                    val authHeader = "Bearer $token"

                    // 1. Fetch User Profile
                    val userResponse = RetrofitClient.instance.getProfile(authHeader)
                    // Ensure we are assigning the data correctly
                    if (userResponse.data != null) {
                        _userData.value = userResponse.data
                    }

                    // 2. Fetch Reels
                    val reelsResponse = RetrofitClient.instance.getMyReels() // Add token if API requires
                    if (reelsResponse.isSuccessful) {
                        _userReels.value = reelsResponse.body()?.data ?: emptyList()
                    }
                }
            } catch (e: Exception) {
                // Log error for debugging
                println("PROFILE_ERROR: ${e.message}")
                e.printStackTrace()
            }
        }
    }
}