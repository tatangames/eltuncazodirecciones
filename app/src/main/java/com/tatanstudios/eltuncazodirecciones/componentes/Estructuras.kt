package com.tatanstudios.eltuncazodirecciones.componentes

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Update
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.tatanstudios.eltuncazodirecciones.R
import com.tatanstudios.eltuncazodirecciones.extras.ItemsMenuLateral
import es.dmoral.toasty.Toasty


@Composable
fun BloqueTextFieldLogin(text: String, onTextChanged: (String) -> Unit, maxLength: Int) {
    // Color común para el texto del placeholder y la línea
    val commonColor = Color.Gray

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White) // Fondo blanco
            .padding(6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        // Ícono al inicio con color gris claro
        Icon(
            imageVector = Icons.Filled.Person,  // Aquí puedes poner el ícono que prefieras
            contentDescription = null,
            modifier = Modifier
                .padding(end = 8.dp) // Espacio entre el ícono y el TextField
                .size(24.dp), // Tamaño del ícono
            tint = commonColor // Usamos el color común para el ícono
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .drawBehind {
                    val strokeWidth = 2.dp.toPx()
                    val y = size.height - strokeWidth / 2
                    drawLine(
                        color = commonColor, // Usamos el mismo color para la línea
                        start = Offset(0f, y),
                        end = Offset(size.width, y),
                        strokeWidth = strokeWidth
                    )
                }
        ) {
            TextField(
                value = text,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                onValueChange = { newText ->
                    if (newText.length <= maxLength) {
                        onTextChanged(newText)
                    }
                },
                textStyle = TextStyle(
                    fontSize = 17.sp, // Tamaño del texto
                    fontWeight = FontWeight.Normal // Negrita
                ),
                placeholder = { Text(text = stringResource(id = R.string.usuario), color = commonColor) }, // Color del placeholder
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White, // Fondo blanco cuando está enfocado
                    unfocusedContainerColor = Color.White, // Fondo blanco cuando no está enfocado
                    disabledContainerColor = Color.White, // Fondo blanco cuando está deshabilitado
                    errorContainerColor = Color.White, // Fondo blanco si hay error
                    focusedIndicatorColor = commonColor, // Color de la línea cuando está enfocado
                    unfocusedIndicatorColor = commonColor  // Color de la línea cuando no está enfocado
                ),
            )
        }
    }
}

@Composable
fun BloqueTextFieldPassword(
    text: String,
    onTextChanged: (String) -> Unit,
    isPasswordVisible: Boolean,
    onPasswordVisibilityChanged: (Boolean) -> Unit,
    maxLength: Int
) {
    // Color común para el texto del placeholder y la línea
    val commonColor = Color.Gray

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White) // Fondo blanco
            .padding(6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        // Ícono al inicio con color gris claro
        Icon(
            imageVector = Icons.Filled.Visibility,  // Puedes cambiarlo por otro ícono si lo prefieres
            contentDescription = null,
            modifier = Modifier
                .padding(end = 8.dp) // Espacio entre el ícono y el TextField
                .size(24.dp), // Tamaño del ícono
            tint = commonColor // Usamos el color común para el ícono
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .drawBehind {
                    val strokeWidth = 2.dp.toPx()
                    val y = size.height - strokeWidth / 2
                    drawLine(
                        color = commonColor, // Usamos el mismo color para la línea
                        start = Offset(0f, y),
                        end = Offset(size.width, y),
                        strokeWidth = strokeWidth
                    )
                }
        ) {
            TextField(
                value = text,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                onValueChange = { newText ->
                    if (newText.length <= maxLength) {  // Limitar la longitud de la contraseña si lo deseas
                        onTextChanged(newText)
                    }
                },
                textStyle = TextStyle(
                    fontSize = 17.sp, // Tamaño del texto
                    fontWeight = FontWeight.Normal // Negrita
                ),
                placeholder = { Text(text = stringResource(id = R.string.contrasena), color = commonColor) }, // Color del placeholder
                singleLine = true,
                visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { onPasswordVisibilityChanged(!isPasswordVisible) }) {
                        Icon(
                            imageVector = if (isPasswordVisible) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                            contentDescription = null,
                            tint = commonColor
                        )
                    }
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White, // Fondo blanco cuando está enfocado
                    unfocusedContainerColor = Color.White, // Fondo blanco cuando no está enfocado
                    disabledContainerColor = Color.White, // Fondo blanco cuando está deshabilitado
                    errorContainerColor = Color.White, // Fondo blanco si hay error
                    focusedIndicatorColor = commonColor, // Color de la línea cuando está enfocado
                    unfocusedIndicatorColor = commonColor  // Color de la línea cuando no está enfocado
                ),
            )
        }
    }
}



@Composable
fun CustomModal1Boton(showDialog: Boolean, message: String, onDismiss: () -> Unit) {
    if (showDialog) {
        Dialog(onDismissRequest = {}) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth() // Ajusta el ancho al 80% de la pantalla
                    .background(Color.White, shape = RoundedCornerShape(16.dp))
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(4.dp), // Agrega padding alrededor del contenido
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = message,
                        fontSize = 18.sp,
                        color = colorResource(R.color.colorNegro),
                        modifier = Modifier.padding(bottom = 16.dp) // Espacio entre el texto y el botón
                    )
                    Button(
                        onClick = onDismiss,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(R.color.colorAzul),
                            contentColor = colorResource(R.color.colorBlanco),
                        ),
                    ) {
                        Text(text = stringResource(id = R.string.aceptar))
                    }
                }
            }
        }
    }
}



@Composable
fun CustomModal1BotonTitulo(
    showDialog: Boolean,
    title: String,
    message: String,
    onDismiss: () -> Unit
) {
    if (showDialog) {
        Dialog(onDismissRequest = {}) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, shape = RoundedCornerShape(16.dp))
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    // Título
                    Text(
                        text = title,
                        fontSize = 20.sp,
                        color = colorResource(R.color.colorNegro),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    // Mensaje
                    Text(
                        text = message,
                        fontSize = 16.sp,
                        color = colorResource(R.color.colorNegro),
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    // Botón
                    Button(
                        onClick = onDismiss,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(R.color.colorAzul),
                            contentColor = colorResource(R.color.colorBlanco),
                        ),
                    ) {
                        Text(text = stringResource(id = R.string.aceptar))
                    }
                }
            }
        }
    }
}



@Composable
fun LoadingModal(isLoading: Boolean, titulo:String = "Cargando...") {
    if (isLoading) {
        Dialog(onDismissRequest = { /* Evitar que se cierre el modal */ }) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(160.dp)
                    .background(Color.White, shape = RoundedCornerShape(16.dp))
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator(color = colorResource(R.color.colorAzul))
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = titulo,
                        fontSize = 18.sp,
                        color = colorResource(R.color.colorNegro)
                    )
                }
            }
        }
    }
}

enum class ToastType {
    SUCCESS,
    ERROR,
    INFO,
    WARNING
}

fun CustomToasty(context: Context, message: String, type: ToastType) {
    when (type) {
        ToastType.SUCCESS -> Toasty.success(context, message, Toasty.LENGTH_SHORT, true).show()
        ToastType.ERROR -> Toasty.error(context, message, Toasty.LENGTH_SHORT, true).show()
        ToastType.INFO -> Toasty.info(context, message, Toasty.LENGTH_SHORT, true).show()
        ToastType.WARNING -> Toasty.warning(context, message, Toasty.LENGTH_SHORT, true).show()
    }
}



val MontserratFont = FontFamily(
    Font(R.font.montserratregular, FontWeight.Normal),
    Font(R.font.montserratmedium, FontWeight.Bold)
)



@Composable
fun DrawerHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(75.dp)
            .background(colorResource(R.color.colorMoradoApp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp), // Este padding es interno, está bien
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.logotuncazoredondo),
                contentDescription = stringResource(R.string.logotipo),
                modifier = Modifier.size(54.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.titleLarge.copy(
                    color = Color.White,
                    fontFamily = MontserratFont,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                ),
                modifier = Modifier.padding(start = 4.dp)
            )
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BarraToolbarColorMenuPrincipal(titulo: String, backgroundColor: Color,
                                   onIconClick: () -> Unit ) {

    CenterAlignedTopAppBar(
        title = {
            Text(
                text = titulo,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                color = Color.White,
                fontWeight = FontWeight.Medium,
            )
        },

        actions = {
            IconButton(
                onClick = {
                    onIconClick()
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Update, // Cambia esto por tu ícono deseado
                    contentDescription = "Opciones",
                    tint = Color.White
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = backgroundColor, // Color de fondo de la barra
            navigationIconContentColor = Color.White, // Color del ícono de navegación
            titleContentColor = Color.White, // Color del título
            actionIconContentColor = Color.White // Color de las acciones
        ),
        modifier = Modifier
            .fillMaxWidth()
            .requiredHeightIn(min = 56.dp) // Define una altura mínima
    )
}




@Composable
fun DrawerBody(
    items: List<ItemsMenuLateral>,
    onItemClick: (ItemsMenuLateral) -> Unit
) {
    Column {
        items.forEach { item ->
            NavigationDrawerItem(
                icon = { Icon(item.icon, contentDescription = null) },
                label = {
                    Text(
                        stringResource(id = item.idString),
                        fontSize = 16.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Medium
                    )
                },
                selected = false,
                onClick = { onItemClick(item) },
                modifier = Modifier.padding(horizontal = 12.dp)
            )
        }
    }
}



@Composable
fun CustomModalCerrarSesion(
    showDialog: Boolean,
    message: String,
    onDismiss: () -> Unit,
    onAccept: () -> Unit
) {
    if (showDialog) {
        Dialog(onDismissRequest = { }) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Color.White,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(16.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = message,
                        fontSize = 18.sp,
                        color = colorResource(R.color.colorNegro),
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Button(
                            onClick = onDismiss,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorResource(R.color.colorGrisv1),
                                contentColor = colorResource(R.color.colorBlanco),
                            ),
                        ) {
                            Text(stringResource(id = R.string.no), color = Color.White)
                        }

                        Button(
                            onClick = onAccept,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorResource(R.color.colorAzul),
                                contentColor = colorResource(R.color.colorBlanco)
                            ),
                        ) {
                            Text(stringResource(id = R.string.si), color = Color.White)
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun CardHistorialOrden(
    orden: String,
    fecha: String,
    venta: String,
    estado: String?,
    haycupon: Int,
    cupon: String?,
    haypremio: Int,
    premio: String?, // textopremio
    cliente: String,
    direccion: String,
    telefono: String?,
    nota: String?,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            CampoTexto(stringResource(R.string.orden_numeral), orden)
            Spacer(modifier = Modifier.height(6.dp))
            CampoTexto(stringResource(R.string.fecha), fecha)
            Spacer(modifier = Modifier.height(6.dp))
            CampoTexto(stringResource(R.string.venta), venta)
            Spacer(modifier = Modifier.height(6.dp))
            CampoTexto(stringResource(R.string.estado), estado)

            if (haycupon == 1){
                Spacer(modifier = Modifier.height(6.dp))
                CampoTexto(stringResource(R.string.cupon), cupon, colorResource(R.color.colorRojo)) }
            if (haypremio == 1){
                Spacer(modifier = Modifier.height(6.dp))
                CampoTexto(stringResource(R.string.premio), premio, colorResource(R.color.colorRojo))
            }

            Spacer(modifier = Modifier.height(6.dp))
            CampoTexto(stringResource(R.string.cliente), cliente)
            Spacer(modifier = Modifier.height(6.dp))
            CampoTexto(stringResource(R.string.direccion), direccion)
            Spacer(modifier = Modifier.height(6.dp))
            CampoTexto(stringResource(R.string.telefono), telefono)

            if (!nota.isNullOrBlank()) CampoTexto(stringResource(R.string.nota), nota)
        }
    }
}


@Composable
fun CampoTexto(etiqueta: String, valor: String?,
               colorEtiqueta: Color = Color.Black
) {
    Row(modifier = Modifier.padding(vertical = 2.dp)) {
        Text(
            text = "$etiqueta: ",
            fontWeight = FontWeight.Bold,
            color = colorEtiqueta,
            fontSize = 18.sp,
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = valor ?: "",
            color = colorEtiqueta,
            fontSize = 17.sp,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}






@Composable
fun ProductoListadoHistorialItemCard(
    cantidad: String,
    hayImagen: Int,
    imagenUrl: String,
    titulo: String?,
    descripcion: String?, // lo que el cliente escribe ejemplo (pollo, res)
    precio: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Cantidad
            Box(
                modifier = Modifier
                    .background(color = colorResource(R.color.colorAzul), shape = RoundedCornerShape(6.dp))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = "${cantidad}x",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.width(5.dp))

            // Imagen del producto desde URL
            if(hayImagen == 1){
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(imagenUrl)
                        .crossfade(true)
                        .placeholder(R.drawable.spinloading)
                        .error(R.drawable.camaradefecto)
                        .build(),
                    contentDescription = stringResource(R.string.imagen_por_defecto),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(8.dp))
                )
            }else{
                Image(
                    painter = painterResource(id = R.drawable.camaradefecto),
                    contentDescription = stringResource(R.string.imagen_por_defecto),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(8.dp))
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Título y descripción
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = titulo?: "",
                    style = MaterialTheme.typography.titleMedium,
                    overflow = TextOverflow.Ellipsis
                )
                if(!descripcion.isNullOrBlank()){
                    Text(
                        text = descripcion?: "",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Red,
                        fontSize = 16.sp,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            // Precio
            Text(
                text = precio,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }
}





@Composable
fun CardNuevaOrden(
    orden: String,
    fecha: String,
    venta: String,
    haycupon: Int,
    cupon: String?,
    haypremio: Int,
    premio: String?,
    cliente: String,
    direccion: String,
    referencia: String?,
    telefono: String?,
    nota: String?,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            CampoTexto(stringResource(R.string.orden_numeral), orden)
            Spacer(modifier = Modifier.height(6.dp))
            CampoTexto(stringResource(R.string.fecha), fecha)
            Spacer(modifier = Modifier.height(6.dp))
            CampoTexto(stringResource(R.string.venta), venta)

            if (haycupon == 1){
                Spacer(modifier = Modifier.height(6.dp))
                CampoTexto(stringResource(R.string.cupon), cupon, colorResource(R.color.colorRojo)) }
            if (haypremio == 1){
                Spacer(modifier = Modifier.height(6.dp))
                CampoTexto(stringResource(R.string.premio), premio, colorResource(R.color.colorRojo))
            }

            Spacer(modifier = Modifier.height(6.dp))
            CampoTexto(stringResource(R.string.cliente), cliente)
            Spacer(modifier = Modifier.height(6.dp))
            CampoTexto(stringResource(R.string.direccion), direccion)
            Spacer(modifier = Modifier.height(6.dp))
            CampoTexto(stringResource(R.string.referencia), referencia)
            Spacer(modifier = Modifier.height(6.dp))
            CampoTexto(stringResource(R.string.telefono), telefono)
            if (!nota.isNullOrBlank()) CampoTexto(stringResource(R.string.nota), nota)
        }
    }
}






@Composable
fun ProductoItemCard(
    cantidad: String,
    hayImagen: Int,
    imagenUrl: String,
    titulo: String?,
    descripcion: String?, // lo que el cliente escribe ejemplo (pollo, res)
    precio: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Cantidad
            Box(
                modifier = Modifier
                    .background(color = colorResource(R.color.colorAzul), shape = RoundedCornerShape(6.dp))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = "${cantidad}x",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.width(5.dp))

            // Imagen del producto desde URL
            if(hayImagen == 1){
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(imagenUrl)
                        .crossfade(true)
                        .placeholder(R.drawable.spinloading)
                        .error(R.drawable.camaradefecto)
                        .build(),
                    contentDescription = stringResource(R.string.imagen_por_defecto),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(8.dp))
                )
            }else{
                Image(
                    painter = painterResource(id = R.drawable.camaradefecto),
                    contentDescription = stringResource(R.string.imagen_por_defecto),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(8.dp))
                )
            }


            Spacer(modifier = Modifier.width(8.dp))

            // Título y descripción
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = titulo?: "",
                    style = MaterialTheme.typography.titleMedium,
                    overflow = TextOverflow.Ellipsis
                )
                if(!descripcion.isNullOrBlank()){
                    Text(
                        text = descripcion?: "",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Red,
                        fontSize = 16.sp,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            // Precio
            Text(
                text = precio,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }
}




@Composable
fun CustomModal2Botones(
    showDialog: Boolean,
    message: String,
    onDismiss: () -> Unit,
    onAccept: () -> Unit,
    txtBtnAceptar: String,
    txtBtnCancelar: String
) {
    if (showDialog) {
        Dialog(onDismissRequest = { }) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Color.White,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(16.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = message,
                        fontSize = 17.sp,
                        color = colorResource(R.color.colorNegro),
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // Botón Cancelar (Gris claro)
                        Button(
                            onClick = onDismiss,
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorResource(R.color.colorGrisv2),
                                contentColor = Color.White
                            )
                        ) {
                            Text(txtBtnCancelar)
                        }

                        // Botón Aceptar (Verde)
                        Button(
                            onClick = onAccept,
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorResource(R.color.colorVerde), // Verde
                                contentColor = Color.White
                            )
                        ) {
                            Text(txtBtnAceptar)
                        }
                    }
                }
            }
        }
    }
}




@Composable
fun CardPreparacionOrden(
    orden: String,
    fecha: String,
    estado: String?,
    venta: String,
    haycupon: Int,
    cupon: String?,
    haypremio: Int,
    premio: String?,
    cliente: String,
    direccion: String,
    referencia: String?,
    telefono: String?,
    nota: String?,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            CampoTexto(stringResource(R.string.orden_numeral), orden)
            Spacer(modifier = Modifier.height(6.dp))
            CampoTexto(stringResource(R.string.fecha), fecha)
            Spacer(modifier = Modifier.height(6.dp))
            CampoTexto(stringResource(R.string.estado), estado)
            Spacer(modifier = Modifier.height(6.dp))
            CampoTexto(stringResource(R.string.total_a_cobrar), venta)

            if (haycupon == 1){
                Spacer(modifier = Modifier.height(6.dp))
                CampoTexto(stringResource(R.string.cupon), cupon, colorResource(R.color.colorRojo)) }
            if (haypremio == 1){
                Spacer(modifier = Modifier.height(6.dp))
                CampoTexto(stringResource(R.string.premio), premio, colorResource(R.color.colorRojo))
            }

            Spacer(modifier = Modifier.height(6.dp))
            CampoTexto(stringResource(R.string.cliente), cliente)
            Spacer(modifier = Modifier.height(6.dp))
            CampoTexto(stringResource(R.string.direccion), direccion)
            Spacer(modifier = Modifier.height(6.dp))
            CampoTexto(stringResource(R.string.referencia), referencia)
            Spacer(modifier = Modifier.height(6.dp))
            CampoTexto(stringResource(R.string.telefono), telefono)
            if (!nota.isNullOrBlank()) CampoTexto(stringResource(R.string.nota), nota)
        }
    }
}



@Composable
fun CardCanceladaOrden(
    orden: String,
    fecha: String,
    venta: String,
    haycupon: Int,
    cupon: String?,
    haypremio: Int,
    premio: String?,
    cliente: String,
    direccion: String,
    referencia: String?,
    telefono: String?,
    nota: String?,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            CampoTexto(stringResource(R.string.orden_numeral), orden)
            Spacer(modifier = Modifier.height(6.dp))
            CampoTexto(stringResource(R.string.fecha), fecha)
            Spacer(modifier = Modifier.height(6.dp))
            CampoTexto(stringResource(R.string.total_a_cobrar), venta)

            if (haycupon == 1){
                Spacer(modifier = Modifier.height(6.dp))
                CampoTexto(stringResource(R.string.cupon), cupon, colorResource(R.color.colorRojo)) }
            if (haypremio == 1){
                Spacer(modifier = Modifier.height(6.dp))
                CampoTexto(stringResource(R.string.premio), premio, colorResource(R.color.colorRojo))
            }

            Spacer(modifier = Modifier.height(6.dp))
            CampoTexto(stringResource(R.string.cliente), cliente)
            Spacer(modifier = Modifier.height(6.dp))
            CampoTexto(stringResource(R.string.direccion), direccion)
            Spacer(modifier = Modifier.height(6.dp))
            CampoTexto(stringResource(R.string.referencia), referencia)
            Spacer(modifier = Modifier.height(6.dp))
            CampoTexto(stringResource(R.string.telefono), telefono)
            if (!nota.isNullOrBlank()) CampoTexto(stringResource(R.string.nota), nota)
        }
    }
}




@Composable
fun CardCompletadasOrden(
    orden: String,
    fecha: String,
    venta: String,
    haycupon: Int,
    cupon: String?,
    haypremio: Int,
    premio: String?,
    cliente: String,
    direccion: String,
    referencia: String?,
    telefono: String?,
    nota: String?,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            CampoTexto(stringResource(R.string.orden_numeral), orden)
            Spacer(modifier = Modifier.height(6.dp))
            CampoTexto(stringResource(R.string.fecha), fecha)
            Spacer(modifier = Modifier.height(6.dp))
            CampoTexto(stringResource(R.string.total_a_cobrar), venta)

            if (haycupon == 1){
                Spacer(modifier = Modifier.height(6.dp))
                CampoTexto(stringResource(R.string.cupon), cupon, colorResource(R.color.colorRojo)) }
            if (haypremio == 1){
                Spacer(modifier = Modifier.height(6.dp))
                CampoTexto(stringResource(R.string.premio), premio, colorResource(R.color.colorRojo))
            }

            Spacer(modifier = Modifier.height(6.dp))
            CampoTexto(stringResource(R.string.cliente), cliente)
            Spacer(modifier = Modifier.height(6.dp))
            CampoTexto(stringResource(R.string.direccion), direccion)
            Spacer(modifier = Modifier.height(6.dp))
            CampoTexto(stringResource(R.string.referencia), referencia)
            Spacer(modifier = Modifier.height(6.dp))
            CampoTexto(stringResource(R.string.telefono), telefono)
            if (!nota.isNullOrBlank()) CampoTexto(stringResource(R.string.nota), nota)
        }
    }
}






