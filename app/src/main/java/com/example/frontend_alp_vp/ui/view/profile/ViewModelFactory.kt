package com.example.frontend_alp_vp.ui.view.profile



import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.frontend_alp_vp.datastore.AuthenticationManager
import com.example.frontend_alp_vp.ui.view.profile.ProfileViewModel

class ProfileViewModelFactory(private val authManager: AuthenticationManager) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProfileViewModel(authManager) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}