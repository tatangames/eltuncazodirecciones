package com.tatanstudios.eltuncazodirecciones.vistas.login


import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.tatanstudios.eltuncazodirecciones.R
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.imePadding
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tatanstudios.eltuncazodirecciones.componentes.BloqueTextFieldLogin
import com.tatanstudios.eltuncazodirecciones.componentes.BloqueTextFieldPassword
import com.tatanstudios.eltuncazodirecciones.componentes.CustomModal1Boton
import com.tatanstudios.eltuncazodirecciones.componentes.CustomToasty
import com.tatanstudios.eltuncazodirecciones.componentes.LoadingModal
import com.tatanstudios.eltuncazodirecciones.componentes.ToastType
import com.tatanstudios.eltuncazodirecciones.extras.TokenManager
import com.tatanstudios.eltuncazodirecciones.model.rutas.Routes
import com.tatanstudios.eltuncazodirecciones.viewmodel.LoginViewModel

import kotlinx.coroutines.launch

@Composable
fun LoginScreen(navController: NavHostController, viewModel: LoginViewModel = viewModel()) {

    val ctx = LocalContext.current
    val usuario by viewModel.usuario.observeAsState("")
    val password by viewModel.password.observeAsState("")
    val resultado by viewModel.resultado.observeAsState()
    val isLoading by viewModel.isLoading.observeAsState(false)

    var isPasswordVisible by remember { mutableStateOf(false) } // Control de visibilidad de la contraseña
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val keyboardController = LocalSoftwareKeyboardController.current

    val tokenManager = remember { TokenManager(ctx) }

    // Definir el color del fondo al presionar
    val loginButtonColor = if (isPressed) {
        colorResource(id = R.color.colorMoradoApp).copy(alpha = 0.8f) // más oscuro al presionar
    } else {
        colorResource(id = R.color.colorMoradoApp)
    }
    // Animación de sombra
    val elevation by animateDpAsState(if (isPressed) 12.dp else 6.dp)
    val scope = rememberCoroutineScope() // Crea el alcance de coroutine

    // MODAL 1 BOTON
    var showModal1Boton by remember { mutableStateOf(false) }
    var modalMensajeString by remember { mutableStateOf("") }



    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.colorMoradoApp))
            .imePadding() // Acomoda el padding inferior cuando aparece el teclado
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxHeight()
        ) {

            Spacer(modifier = Modifier.height(48.dp))

            // Fondo blanco arriba
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .background(colorResource(R.color.colorMoradoApp))
            ) {
                // Logo centrado en el fondo blanco
                Image(
                    painter = painterResource(id = R.drawable.logotuncazoredondo),
                    contentDescription = stringResource(id = R.string.logotipo),
                    modifier = Modifier
                        .size(130.dp)
                        .align(Alignment.Center)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Título
            Text(
                text = stringResource(id = R.string.app_name_completo),
                fontFamily = FontFamily(Font(R.font.montserratmedium)),
                color = Color.White,
                fontSize = 24.sp,
                modifier = Modifier
                    .offset(y = (-20).dp)
                    .fillMaxWidth(),

                textAlign = TextAlign.Center
            )

            // Card de inicio de sesión
            Card(
                modifier = Modifier
                    .padding(horizontal = 12.dp, vertical = 12.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(25.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
            ) {
                Column(
                    modifier = Modifier
                        .background(Color.White)
                        .padding(10.dp)
                ) {

                    BloqueTextFieldLogin(text = usuario,
                        onTextChanged = { newText -> viewModel.setUsuario(newText) },
                        maxLength = 20
                    )

                    // Bloque para la contraseña
                    BloqueTextFieldPassword(
                        text = password,
                        onTextChanged = { newText -> viewModel.setPassword(newText) },
                        isPasswordVisible = isPasswordVisible,
                        onPasswordVisibilityChanged = { isPasswordVisible = it },
                        maxLength = 16
                    )

                    Button(
                        onClick = {
                            // Acción de login

                            keyboardController?.hide()

                            when {
                                usuario.isBlank() -> {
                                    modalMensajeString = ctx.getString(R.string.usuario_es_requerido)
                                    showModal1Boton = true
                                }

                                password.isBlank() -> {
                                    modalMensajeString = ctx.getString(R.string.password_es_requerido)
                                    showModal1Boton = true
                                }
                                else -> {
                                    viewModel.verificarUsuarioPasssword()
                                }
                            }

                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 32.dp, start = 24.dp, end = 24.dp)
                            .shadow(
                                elevation = elevation, // Cambia la sombra cuando se presiona
                                shape = RoundedCornerShape(25.dp)
                            ),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = loginButtonColor,  // Cambia color al presionar
                            contentColor = colorResource(R.color.colorBlanco),
                        ),
                        interactionSource = interactionSource // Para detectar la interacción
                    ) {
                        Text(
                            text = stringResource(id = R.string.iniciar_sesion),
                            fontSize = 18.sp,
                            style = TextStyle(
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Medium,
                            )
                        )
                    }
                }

                if(showModal1Boton){
                    CustomModal1Boton(showModal1Boton, modalMensajeString, onDismiss = {showModal1Boton = false})
                }

                if (isLoading) {
                    LoadingModal(isLoading = isLoading)
                }

                resultado?.getContentIfNotHandled()?.let { result ->
                    when (result.success) {

                        1 -> {
                            // USUARIO BLOQUEADO
                            val _id = (result.id).toString()

                            scope.launch {
                                tokenManager.saveID(_id)

                                navController.navigate(Routes.VistaPrincipal.route) {
                                    popUpTo(0) { // Esto elimina todas las vistas de la pila de navegación
                                        inclusive = true // Asegura que ninguna pantalla anterior quede en la pila
                                    }
                                    launchSingleTop = true // Evita múltiples instancias de la misma vista
                                }
                            }
                        }
                        2 -> {
                            // DATOS INCORRECTOS
                            CustomToasty(
                                ctx,
                                stringResource(id = R.string.datos_incorrectos),
                                ToastType.INFO
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
            }
        }
    }




}



/*@Preview(showBackground = true)
@Composable
fun PreviewLoginScreen() {
    val navController = rememberNavController()
    LoginScreen(navController = navController)
}*/