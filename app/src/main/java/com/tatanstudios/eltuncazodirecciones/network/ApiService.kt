package com.tatanstudios.eltuncazodirecciones.network


import com.tatanstudios.eltuncazodirecciones.model.listado.ModeloBasico
import com.tatanstudios.eltuncazodirecciones.model.listado.ModeloDeboActualizar
import com.tatanstudios.eltuncazodirecciones.model.listado.ModeloDirecciones
import com.tatanstudios.eltuncazodirecciones.model.login.ModeloLogin
import io.reactivex.rxjava3.core.Single
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    // VERIFICACION DE USUARIO PARA MOTORISTA
    @POST("motorista/login")
    @FormUrlEncoded
    fun verificarUsuarioPassword(@Field("usuario") usuario: String,
                          @Field("password") password: String,
                          ): Single<ModeloLogin>


    @POST("motorista/direcciones")
    @FormUrlEncoded
    fun listadoDirecciones(@Field("id") id: String
    ): Single<ModeloDirecciones>


    @POST("motorista/verificar/deboactualizar")
    @FormUrlEncoded
    fun verificarDeboActualizar(@Field("id") id: String,
    ): Single<ModeloDeboActualizar>


    @POST("motorista/actualizar/gps")
    @FormUrlEncoded
    fun actualizarGps(@Field("id") id: Int,
                      @Field("bloque") bloque: Int,
                      @Field("latitud") latitud: String,
                      @Field("longitud") longitud: String,
    ): Single<ModeloBasico>

}


