package com.example.p7meeting9.ui.navigation

interface AlamatNavigasi {
    val route : String

    object DestinasiHome : AlamatNavigasi{
        override val route = "home"
    }

    object DestinasiDetail : AlamatNavigasi{
        override val route = "detail"
        const val NIM = "nim"
        val routesWithArg = "$route/{$NIM}"
    }

}