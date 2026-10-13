package com.example.pr2pu5

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pr2pu5.ui.theme.Pr2Pu5Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Pr2Pu5Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppContent(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun AppContent(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "LoginContent") {
        composable("LoginContent") { LoginContent(navController) }
        composable("MenuContent") { MenuContent(navController) }
        composable("PacienteContent") { PacienteContent(navController) }
        composable("HistorialContent") { HistorialContent(navController) }
        composable("HabitacionContent") { HabitacionContent(navController) }
    }
}

@Composable
fun LoginContent(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var usuario by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.perfil),
            contentDescription = "Fondo de inicio de sesión",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Inicio de Sesión",
                fontSize = 24.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 24.dp)
            )

            Text(
                text = "Usuario:",
                color = Color.White,
                modifier = Modifier.padding(top = 16.dp)
            )
            TextField(
                value = usuario,
                onValueChange = { usuario = it },
                placeholder = { Text("Ingresa tu Usuario") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )

            Text(
                text = "Contraseña:",
                color = Color.White
            )
            TextField(
                value = contrasena,
                onValueChange = { contrasena = it },
                placeholder = { Text("Contraseña") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                visualTransformation = PasswordVisualTransformation()
            )

            Button(
                onClick = {
                    Toast.makeText(
                        context,
                        "Acceso concedido para: $usuario",
                        Toast.LENGTH_SHORT
                    ).show()
                    navController.navigate("MenuContent")
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Iniciar sesión")
            }
        }
    }
}

@Composable
fun MenuContent(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Panel de Consulta - Menú",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        Button(
            onClick = { navController.navigate("PacienteContent") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            Text(text = "Ir a Tabla Paciente")
        }

        Button(
            onClick = { navController.navigate("HistorialContent") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            Text(text = "Ir a Tabla Historial Médico")
        }

        Button(
            onClick = { navController.navigate("HabitacionContent") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp)
        ) {
            Text(text = "Ir a Tabla Habitación")
        }

        Button(
            onClick = { navController.navigate("LoginContent") },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cerrar Sesión")
        }
    }
}

data class PacienteItem(val curp: String, val fechaIngreso: String, val fechaSalida: String, val nombre: String, val domicilio: String)

@Composable
fun PacienteContent(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    val listaPacientes = remember {
        mutableStateListOf(
            PacienteItem(curp = "ABCD123456", fechaIngreso = "01/10/2026", fechaSalida = "05/10/2026", nombre = "Juan Pérez", domicilio = "Calle 1"),
            PacienteItem(curp = "EFGH789012", fechaIngreso = "02/10/2026", fechaSalida = "06/10/2026", nombre = "María López", domicilio = "Calle 2")
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Tabla: Paciente",
            fontSize = 20.sp,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier
                .weight(1f)
                .horizontalScroll(scrollState)
        ) {
            Row {
                Text("curp", modifier = Modifier.width(130.dp), fontWeight = FontWeight.Bold)
                Text("fecha_ingreso", modifier = Modifier.width(130.dp), fontWeight = FontWeight.Bold)
                Text("fecha_salida", modifier = Modifier.width(130.dp), fontWeight = FontWeight.Bold)
                Text("nombre", modifier = Modifier.width(130.dp), fontWeight = FontWeight.Bold)
                Text("domicilio", modifier = Modifier.width(130.dp), fontWeight = FontWeight.Bold)
                Text("Acción", modifier = Modifier.width(100.dp), fontWeight = FontWeight.Bold)
            }
            Divider()

            listaPacientes.forEachIndexed { index, item ->
                val bgColor = if (index % 2 == 0) Color(0xFFF5F5F5) else Color.White
                Row(
                    modifier = Modifier
                        .background(bgColor)
                        .padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(item.curp, modifier = Modifier.width(130.dp))
                    Text(item.fechaIngreso, modifier = Modifier.width(130.dp))
                    Text(item.fechaSalida, modifier = Modifier.width(130.dp))
                    Text(item.nombre, modifier = Modifier.width(130.dp))
                    Text(item.domicilio, modifier = Modifier.width(130.dp))
                    Button(
                        onClick = { listaPacientes.removeAt(index) },
                        modifier = Modifier.width(100.dp)
                    ) {
                        Text("Borrar")
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { navController.navigate("MenuContent") },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Regresar al Menú")
        }
    }
}

data class HistorialItem(val idHistorial: String, val fotoPaciente: String, val alergias: String, val cirugias: String, val diagnosticos: String)

@Composable
fun HistorialContent(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    val listaHistorial = remember {
        mutableStateListOf(
            HistorialItem(idHistorial = "H-001", fotoPaciente = "foto1.jpg", alergias = "Ninguna", cirugias = "Apendicitis", diagnosticos = "Estable"),
            HistorialItem(idHistorial = "H-002", fotoPaciente = "foto2.jpg", alergias = "Penicilina", cirugias = "Ninguna", diagnosticos = "En observación")
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Tabla: Historial Médico",
            fontSize = 20.sp,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier
                .weight(1f)
                .horizontalScroll(scrollState)
        ) {
            Row {
                Text("id_historial", modifier = Modifier.width(130.dp), fontWeight = FontWeight.Bold)
                Text("foto_paciente", modifier = Modifier.width(130.dp), fontWeight = FontWeight.Bold)
                Text("alergias", modifier = Modifier.width(130.dp), fontWeight = FontWeight.Bold)
                Text("cirugias", modifier = Modifier.width(130.dp), fontWeight = FontWeight.Bold)
                Text("diagnosticos", modifier = Modifier.width(130.dp), fontWeight = FontWeight.Bold)
                Text("Acción", modifier = Modifier.width(100.dp), fontWeight = FontWeight.Bold)
            }
            Divider()

            listaHistorial.forEachIndexed { index, item ->
                val bgColor = if (index % 2 == 0) Color(0xFFF5F5F5) else Color.White
                Row(
                    modifier = Modifier
                        .background(bgColor)
                        .padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(item.idHistorial, modifier = Modifier.width(130.dp))
                    Text(item.fotoPaciente, modifier = Modifier.width(130.dp))
                    Text(item.alergias, modifier = Modifier.width(130.dp))
                    Text(item.cirugias, modifier = Modifier.width(130.dp))
                    Text(item.diagnosticos, modifier = Modifier.width(130.dp))
                    Button(
                        onClick = { listaHistorial.removeAt(index) },
                        modifier = Modifier.width(100.dp)
                    ) {
                        Text("Borrar")
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { navController.navigate("MenuContent") },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Regresar al Menú")
        }
    }
}

data class HabitacionItem(val idHabitacion: String, val numeroHabitacion: String, val piso: String, val estado: String)

@Composable
fun HabitacionContent(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    val listaHabitacion = remember {
        mutableStateListOf(
            HabitacionItem(idHabitacion = "Hab-01", numeroHabitacion = "101", piso = "Piso 1", estado = "Libre"),
            HabitacionItem(idHabitacion = "Hab-02", numeroHabitacion = "102", piso = "Piso 1", estado = "Ocupada")
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Tabla: Habitación",
            fontSize = 20.sp,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier
                .weight(1f)
                .horizontalScroll(scrollState)
        ) {
            Row {
                Text("id_habitacion", modifier = Modifier.width(130.dp), fontWeight = FontWeight.Bold)
                Text("numero_hab", modifier = Modifier.width(130.dp), fontWeight = FontWeight.Bold)
                Text("piso", modifier = Modifier.width(130.dp), fontWeight = FontWeight.Bold)
                Text("estado", modifier = Modifier.width(130.dp), fontWeight = FontWeight.Bold)
                Text("Acción", modifier = Modifier.width(100.dp), fontWeight = FontWeight.Bold)
            }
            Divider()

            listaHabitacion.forEachIndexed { index, item ->
                val bgColor = if (index % 2 == 0) Color(0xFFF5F5F5) else Color.White
                Row(
                    modifier = Modifier
                        .background(bgColor)
                        .padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(item.idHabitacion, modifier = Modifier.width(130.dp))
                    Text(item.numeroHabitacion, modifier = Modifier.width(130.dp))
                    Text(item.piso, modifier = Modifier.width(130.dp))
                    Text(item.estado, modifier = Modifier.width(130.dp))
                    Button(
                        onClick = { listaHabitacion.removeAt(index) },
                        modifier = Modifier.width(100.dp)
                    ) {
                        Text("Borrar")
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { navController.navigate("MenuContent") },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Regresar al Menú")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppContentPreview() {
    Pr2Pu5Theme {
        AppContent()
    }
}