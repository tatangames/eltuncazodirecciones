package com.tatanstudios.eltuncazodirecciones.vistas.principal

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.navOptions
import com.tatanstudios.eltuncazodirecciones.R
import com.tatanstudios.eltuncazodirecciones.extras.TokenManager
import com.tatanstudios.eltuncazodirecciones.extras.itemsMenu
import com.tatanstudios.eltuncazodirecciones.model.rutas.Routes
import kotlinx.coroutines.launch
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.first
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextButton
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.room.Room
import com.tatanstudios.eltuncazodirecciones.componentes.BarraToolbarColorMenuPrincipal
import com.tatanstudios.eltuncazodirecciones.componentes.CustomModalCerrarSesion
import com.tatanstudios.eltuncazodirecciones.componentes.CustomToasty
import com.tatanstudios.eltuncazodirecciones.componentes.DrawerBody
import com.tatanstudios.eltuncazodirecciones.componentes.DrawerHeader
import com.tatanstudios.eltuncazodirecciones.componentes.LoadingModal
import com.tatanstudios.eltuncazodirecciones.componentes.ToastType
import com.tatanstudios.eltuncazodirecciones.viewmodel.ActualizarGPSViewModel
import com.tatanstudios.eltuncazodirecciones.viewmodel.VerificarDeboActualizarViewModel
import com.tatanstudios.eltuncazodirecciones.vistas.opciones.actualizar.AppDatabase
import com.tatanstudios.eltuncazodirecciones.vistas.opciones.actualizar.DireccionEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class,
    ExperimentalFoundationApi::class
)
@Composable
fun PrincipalScreen(
    navController: NavHostController,
    viewModel: VerificarDeboActualizarViewModel = viewModel(),
    viewModelActualizar: ActualizarGPSViewModel = viewModel(),
    ) {
    val ctx = LocalContext.current
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    var showModalCerrarSesion by remember { mutableStateOf(false) }
    val resultado by viewModel.resultado.observeAsState()
    val tokenManager = remember { TokenManager(ctx) }
    val scope = rememberCoroutineScope() // Crea el alcance de coroutine
    val keyboardController = LocalSoftwareKeyboardController.current
    var _idusuario by remember { mutableStateOf("") }

    var listaDirecciones by remember { mutableStateOf<List<DireccionEntity>>(emptyList()) }
    var searchQuery by remember { mutableStateOf("") }

    val focusManager = LocalFocusManager.current
    var isLoading by remember { mutableStateOf(true) }

    var showDialog by remember { mutableStateOf(false) }
    var nuevaLatitud by remember { mutableStateOf("") }
    var nuevaLongitud by remember { mutableStateOf("") }

    val resultadoActualizar by viewModelActualizar.resultado.observeAsState()
    val isLoadingActualizar by viewModelActualizar.isLoading.observeAsState(false)

    var idCliente by remember { mutableStateOf(0) }
    var idBloque by remember { mutableStateOf(0) }

    val db = remember {
        Room.databaseBuilder(
            ctx,
            AppDatabase::class.java,
            "mi_db"
        ).build()
    }
    val dao = remember { db.direccionDao() }


    LaunchedEffect(Unit) {
        scope.launch {
            _idusuario = tokenManager.idUsuario.first()
            viewModel.verificarDeboActualizarRetrofit(id = _idusuario)

            val direcciones = withContext(Dispatchers.IO) {
                dao.obtenerDirecciones()
            }

            listaDirecciones = direcciones
            isLoading = false
        }
    }

    val listaFiltrada = remember(listaDirecciones, searchQuery) {
        if (searchQuery.isBlank()) {
            listaDirecciones
        } else {
            val query = searchQuery.trim().lowercase()
            listaDirecciones.filter { direccion ->
                (direccion.nombre?.lowercase()?.contains(query) == true) ||
                        (direccion.telefono?.lowercase()?.contains(query) == true)
            }
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                DrawerHeader()
                DrawerBody(items = itemsMenu) { item ->
                    when (item.id) {
                        1 -> {
                            // cerrar sesion
                            showModalCerrarSesion = true
                        }
                    }

                    scope.launch {
                        drawerState.close()
                    }
                }

                // Spacer para empujar el contenido hacia arriba
                Spacer(modifier = Modifier.weight(1f))

                // Texto de la versión
                Text(
                    text = stringResource(R.string.version) + " " + getVersionName(ctx),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    ) {

        Scaffold(
            topBar = {
                BarraToolbarColorMenuPrincipal(
                    stringResource(R.string.menu),
                    colorResource(R.color.colorMoradoApp),
                    onIconClick = {
                        // MANDARLO A OTRA PANTALLA DONDE SE ACTUALIZARA LOS DATOS
                        navController.navigate(Routes.VistaActualizar.route) {
                            navOptions {
                                launchSingleTop = true
                            }
                        }

                    }
                )
            }
        ) { innerPadding ->

            if (isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }else{

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .pointerInput(Unit) {
                            detectTapGestures(onTap = {
                                focusManager.clearFocus()        // Quita el foco del TextField
                                keyboardController?.hide()       // Oculta teclado
                            })
                        }
                ) {


                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {

                        OutlinedTextField(
                            value = searchQuery,
                            onValueChange = { searchQuery = it },
                            label = { Text("Buscar por nombre o teléfono") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            singleLine = true,
                            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Buscar") },
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    keyboardController?.hide()
                                }
                            )
                        )

                        val listState = rememberLazyListState()

                        LazyColumn(
                            state = listState,
                            contentPadding = PaddingValues(16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                            modifier = Modifier
                                .fillMaxSize()
                                .nestedScroll(remember {
                                    object : NestedScrollConnection {
                                        override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                                            keyboardController?.hide()
                                            focusManager.clearFocus()
                                            return Offset.Zero
                                        }
                                    }
                                })
                        ) {
                            items(listaFiltrada) { direccion ->
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(12.dp))
                                        .padding(4.dp)
                                        .combinedClickable(
                                            onClick = { /* aquí va click normal si quieres */ },
                                            onLongClick = {
                                                nuevaLatitud = ""
                                                nuevaLongitud = ""
                                                idCliente = direccion.idcliente
                                                idBloque = direccion.bloque ?: 0 // siempre vendra que bloque es
                                                showDialog = true
                                            }
                                        ),
                                    elevation = CardDefaults.cardElevation(6.dp)
                                ) {
                                    Column(modifier = Modifier.padding(8.dp)) {
                                        Text(
                                            text = direccion.nombre ?: "",
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 22.sp
                                        )
                                        Spacer(modifier = Modifier.height(8.dp))
                                        Text(
                                            text = "Dir: ${direccion.referencia}",
                                            fontSize = 18.sp
                                        )

                                        if (!direccion.referencia.isNullOrBlank()) {
                                            Spacer(modifier = Modifier.height(8.dp))
                                            Text(
                                                text = "Ref: ${direccion.referencia}",
                                                fontSize = 16.sp
                                            )
                                        }

                                        if (!direccion.telefono.isNullOrBlank()) {
                                            Spacer(modifier = Modifier.height(8.dp))
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.SpaceBetween,
                                                modifier = Modifier.fillMaxWidth()
                                            ) {
                                                Text(
                                                    text = "Tel: ${direccion.telefono}",
                                                    fontSize = 17.sp
                                                )

                                                Button(
                                                    onClick = {
                                                        val intent = Intent(Intent.ACTION_DIAL).apply {
                                                            data = Uri.parse("tel:${direccion.telefono}")
                                                        }
                                                        ctx.startActivity(intent)
                                                    },
                                                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp), // Menor padding
                                                    modifier = Modifier.height(36.dp) // Altura más pequeña
                                                ) {
                                                    Icon(
                                                        Icons.Default.Phone,
                                                        contentDescription = "Llamar",
                                                        modifier = Modifier.size(18.dp) // Ícono más pequeño
                                                    )
                                                    Spacer(modifier = Modifier.width(4.dp))
                                                    Text(
                                                        "Llamar",
                                                        fontSize = 14.sp // Texto más pequeño
                                                    )
                                                }
                                            }
                                        }

                                        if (!direccion.latitud.isNullOrBlank() && !direccion.longitud.isNullOrBlank()) {
                                            Spacer(modifier = Modifier.height(12.dp))
                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.Center
                                            ) {
                                                Button(
                                                    onClick = {
                                                        val uri = Uri.parse("geo:${direccion.latitud},${direccion.longitud}?q=${direccion.latitud},${direccion.longitud}")
                                                        val intent = Intent(Intent.ACTION_VIEW, uri)
                                                        ctx.startActivity(intent)
                                                    },
                                                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)), // Verde
                                                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp), // Menor padding
                                                    modifier = Modifier.height(36.dp) // Altura más pequeña
                                                ) {
                                                    Icon(
                                                        Icons.Default.LocationOn,
                                                        contentDescription = "Ver mapa",
                                                        modifier = Modifier.size(18.dp) // Ícono más pequeño
                                                    )
                                                    Spacer(modifier = Modifier.width(4.dp))
                                                    Text(
                                                        "Mapa",
                                                        fontSize = 14.sp // Texto más pequeño
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }

                    }
                }




            } // end-else



            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { /* No hacer nada para evitar cierre al tocar afuera */ },
                    title = { Text("Actualizar GPS") },
                    text = {
                        Column {
                            OutlinedTextField(
                                value = nuevaLatitud,
                                onValueChange = {
                                    if (it.length <= 100) nuevaLatitud = it
                                },
                                label = { Text("Latitud") },
                                singleLine = true,
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            OutlinedTextField(
                                value = nuevaLongitud,
                                onValueChange = {
                                    if (it.length <= 100) nuevaLongitud = it
                                },
                                label = { Text("Longitud") },
                                singleLine = true,
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                            )
                        }
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                // Aquí tu acción de actualizar


                                keyboardController?.hide()

                                when {
                                    nuevaLatitud.isBlank() -> {
                                        CustomToasty(
                                            ctx,
                                            "Latitud es requerido",
                                            ToastType.INFO
                                        )
                                    }
                                    nuevaLongitud.isBlank() -> {
                                        CustomToasty(
                                            ctx,
                                            "Longitud es requerido",
                                            ToastType.INFO
                                        )
                                    }

                                    else -> {
                                        showDialog = false
                                        viewModelActualizar.actualizarGpsRetrofit(idCliente, idBloque, nuevaLatitud, nuevaLongitud)
                                    }
                                }

                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)) // verde
                        ) {
                            Text("Actualizar", color = Color.White)
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showDialog = false }) {
                            Text("Cancelar")
                        }
                    }
                )
            }


            if (showModalCerrarSesion) {
                CustomModalCerrarSesion(showModalCerrarSesion,
                    stringResource(R.string.cerrar_sesion),
                    onDismiss = { showModalCerrarSesion = false },
                    onAccept = {
                        scope.launch {
                            // Llamamos a deletePreferences de manera segura dentro de una coroutine
                            tokenManager.deletePreferences()

                            // cerrar modal
                            showModalCerrarSesion = false

                            navigateToLogin(navController)
                        }
                    })
            }


            if (isLoadingActualizar) {
                LoadingModal(isLoading = isLoadingActualizar)
            }


            // SOLO NECESITO MOSTRAR EL MENSAJE SI DEBE ACTUALIZAR
            resultado?.getContentIfNotHandled()?.let { result ->
                when (result.success) {
                    1 -> {
                        if(result.actualizar == 1){
                            CustomToasty(
                                ctx,
                                stringResource(id = R.string.actualizar_direcciones),
                                ToastType.INFO
                            )
                        }
                    }
                }
            }

            resultadoActualizar?.getContentIfNotHandled()?.let { result ->
                when (result.success) {
                    1 -> {
                        CustomToasty(
                            ctx,
                            stringResource(id = R.string.actualizado),
                            ToastType.SUCCESS
                        )
                    }
                    else -> {
                        // Error, mostrar Toast
                        CustomToasty(
                            ctx,
                            stringResource(id = R.string.error_reintentar_de_nuevo),
                            ToastType.ERROR
                        )
                    }
                }
            }


        } //end-scalfold
    }
}


fun getVersionName(context: Context): String {
    return try {
        val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
        packageInfo.versionName ?: "N/A"
    } catch (e: PackageManager.NameNotFoundException) {
        "N/A"
    }
}


// redireccionar a vista login
private fun navigateToLogin(navController: NavHostController) {
    navController.navigate(Routes.VistaLogin.route) {
        popUpTo(Routes.VistaPrincipal.route) {
            inclusive = true // Elimina VistaPrincipal de la pila
        }
        launchSingleTop = true // Asegura que no se creen múltiples instancias de VistaLogin
    }
}

