package com.example.frontend_alp_vp.ui.navigation

sealed class Route(val route: String) {
    // --- Auth & Splash ---
    object Splash : Route("splash")
    object Login : Route("login")
    object Register : Route("register")

    // --- Bottom Bar Tabs ---
    object Home : Route("home")
    object Calendar : Route("calendar")
    object Reels : Route("reels")
    object History : Route("history")
    object Profile : Route("profile")

    // --- Fitur Pensi ---
    object PensiList : Route("pensi_list") // Halaman list event pensi
    object PensiDetail : Route("pensi_detail/{pensiId}") {
        fun createRoute(pensiId: Int) = "pensi_detail/$pensiId"
    }
    object Payment : Route("payment/{pensiId}/{scheduleId}") {
        fun createRoute(pensiId: Int, scheduleId: Int) = "payment/$pensiId/$scheduleId"
    }
    object HistoryDetail : Route("history_detail/{bookingId}") {
        fun createRoute(bookingId: Int) = "history_detail/$bookingId"
    }

    // --- Fitur Explore (Wisata/Kuliner/Souvenir) ---
    object Wisata : Route("wisata")
    object WisataDetail : Route("wisata_detail/{placeId}") {
        fun createRoute(placeId: Int) = "wisata_detail/$placeId"
    }

    object Kuliner : Route("kuliner")
    object KulinerDetail : Route("kuliner_detail/{placeId}") {
        fun createRoute(placeId: Int) = "kuliner_detail/$placeId"
    }

    object Souvenir : Route("souvenir")
    object SouvenirDetail : Route("souvenir_detail/{placeId}") {
        fun createRoute(placeId: Int) = "souvenir_detail/$placeId"
    }

    // --- Fitur Lain ---
    object AddReview : Route("add_review/{placeId}") {
        fun createRoute(placeId: Int) = "add_review/$placeId"
    }
    object EditProfile : Route("edit_profile")
}