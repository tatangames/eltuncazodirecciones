package com.tatanstudios.eltuncazodirecciones.vistas.opciones.actualizar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.tatanstudios.eltuncazodirecciones.extras.TokenManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.Transaction
import com.tatanstudios.eltuncazodirecciones.R
import com.tatanstudios.eltuncazodirecciones.componentes.BarraToolbarColor
import com.tatanstudios.eltuncazodirecciones.componentes.CustomToasty
import com.tatanstudios.eltuncazodirecciones.componentes.LoadingModal
import com.tatanstudios.eltuncazodirecciones.componentes.ToastType
import com.tatanstudios.eltuncazodirecciones.model.listado.ModeloDireccionesArray
import com.tatanstudios.eltuncazodirecciones.viewmodel.ListadoDireccionesViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ActualizarScreen(navController: NavHostController,
                                  viewModel: ListadoDireccionesViewModel = viewModel(),
) {

    val ctx = LocalContext.current
    val isLoading by viewModel.isLoading.observeAsState(true)
    val tokenManager = remember { TokenManager(ctx) }
    val resultado by viewModel.resultado.observeAsState()
    val scope = rememberCoroutineScope() // Crea el alcance de coroutine
    val keyboardController = LocalSoftwareKeyboardController.current
    var _idusuario by remember { mutableStateOf("") }


    val db = Room.databaseBuilder(
        ctx,
        AppDatabase::class.java,
        "mi_db"
    ).build()

    val dao = db.direccionDao()

    LaunchedEffect(Unit) {
        scope.launch {
            _idusuario = tokenManager.idUsuario.first()
        }
    }

    // ocultar teclado
    keyboardController?.hide()


    Scaffold(
        topBar = {
            BarraToolbarColor(
                navController,
                stringResource(R.string.actualizar),
                colorResource(R.color.colorMoradoApp),
            )
        }
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

                    Button(
                        onClick = {
                           viewModel.listadoDireccionesRetrofit(_idusuario)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(R.color.colorMoradoApp),
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(text = stringResource(R.string.actualizar_direcciones), fontSize = 18.sp)
                    }
                }
            }


        }

        if (isLoading) {
            LoadingModal(isLoading = true)
        }

        resultado?.getContentIfNotHandled()?.let { result ->
            when (result.success) {
                1 -> {
                    val (direcciones) = mapToEntity(result.listado)
                    scope.launch(Dispatchers.IO) {
                        dao.actualizarDirecciones(direcciones)

                        withContext(Dispatchers.Main) {
                            CustomToasty(
                                ctx,
                                "Actualizado",
                                ToastType.SUCCESS
                            )
                        }
                    }
                }
                else -> {
                    CustomToasty(
                        ctx,
                        stringResource(id = R.string.error_reintentar_de_nuevo),
                        ToastType.ERROR
                    )

                }
            }
        }
    } // end-scalfold




}

@Entity(tableName = "direcciones")
data class DireccionEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val idcliente: Int,
    val id_cliente_direc: Int?,
    val nombre: String?,
    val direccion: String?,
    val referencia: String?,
    val telefono: String?,
    val latitud: String?,
    val longitud: String?,
    val bloque: Int?
)


@Dao
interface DireccionDao {
    @Transaction
    suspend fun actualizarDirecciones(
        direcciones: List<DireccionEntity>,
    ) {
        clearAll()
        insertDirecciones(direcciones)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDirecciones(direcciones: List<DireccionEntity>)

    @Query("DELETE FROM direcciones")
    suspend fun clearAllDirecciones()

    suspend fun clearAll() {
        clearAllDirecciones()
    }

    @Query("SELECT * FROM direcciones")
    suspend fun obtenerDirecciones(): List<DireccionEntity>
}


@Database(entities = [DireccionEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun direccionDao(): DireccionDao
}

private fun mapToEntity(data: List<ModeloDireccionesArray>): Pair<List<DireccionEntity>, Int> {
    val direcciones = data.map { item ->
        DireccionEntity(

            idcliente = item.id,
            id_cliente_direc = item.id_cliente_direc,
            nombre = item.nombre,
            direccion = item.direccion,
            referencia = item.referencia,
            telefono = item.telefono,
            latitud = item.latitud,
            longitud = item.longitud,
            bloque = item.bloque
        )
    }
    return Pair(direcciones, direcciones.size) // ejemplo: el tamaño
}



