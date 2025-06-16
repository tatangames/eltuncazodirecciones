package com.tatanstudios.eltuncazodirecciones.model.listado

import com.google.gson.annotations.SerializedName

data class ModeloDirecciones(
    @SerializedName("success") val success: Int,
    @SerializedName("listado") val listado: List<ModeloDireccionesArray>
)

data class ModeloDireccionesArray(
    @SerializedName("id") val id: Int,
    @SerializedName("id_cliente_direc") val id_cliente_direc: Int?,
    @SerializedName("nombre") val nombre: String?,
    @SerializedName("direccion") val direccion: String?,
    @SerializedName("referencia") val referencia: String?,
    @SerializedName("telefono") val telefono: String?,
    @SerializedName("latitud") val latitud: String?,
    @SerializedName("longitud") val longitud: String?,
    @SerializedName("bloque") val bloque: Int,
)


data class ModeloDeboActualizar(
    @SerializedName("success") val success: Int,
    @SerializedName("actualizar") val actualizar: Int
)


data class ModeloBasico(
    @SerializedName("success") val success: Int,
)