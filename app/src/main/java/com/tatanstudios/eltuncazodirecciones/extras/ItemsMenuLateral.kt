package com.tatanstudios.eltuncazodirecciones.extras

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.ui.graphics.vector.ImageVector
import com.tatanstudios.eltuncazodirecciones.R

sealed class ItemsMenuLateral(
    val icon: ImageVector,
    val idString: Int,
    val id: Int
) {


    object ItemMenu1 : ItemsMenuLateral(
        Icons.AutoMirrored.Filled.Logout,
        R.string.cerrar_sesion,
        1
    )
}

// Lista de items del menú lateral
val itemsMenu = listOf(ItemsMenuLateral.ItemMenu1,

)
