package com.example.frontend_alp_vp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.frontend_alp_vp.ui.navigation.AppNavigation
import com.example.frontend_alp_vp.ui.theme.FrontEnd_ALP_VPTheme
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.Coil

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Setup Coil untuk SVG (Penting untuk gambar dari placeholder/web tertentu)
        val imageLoader = ImageLoader.Builder(this)
            .components { add(SvgDecoder.Factory()) }
            .build()
        Coil.setImageLoader(imageLoader)

        enableEdgeToEdge()
        setContent {
            FrontEnd_ALP_VPTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    AppNavigation() // Panggil fungsi navigasi utama
                }
            }
        }
    }
}