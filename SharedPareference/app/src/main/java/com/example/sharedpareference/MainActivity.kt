package com.example.sharedpareference
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(color = Color.White, modifier = Modifier.fillMaxSize()){
                Navigation(context = applicationContext)

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Navigation(context: Context){
    val navController= rememberNavController()
    var showSplashScreen by remember { mutableStateOf(true) }

    NavHost(navController = navController, startDestination = if (showSplashScreen) "splash_screen" else "main_screen"){
        composable("splash_screen"){
            SplashScreen(navController = navController) {
                // Splash screen finished, set the flag to false
                showSplashScreen = false
            }
        }
        composable("main_screen"){
            var name by remember { mutableStateOf( "") }
            var fatherName by remember { mutableStateOf( "") }

            Column(
                Modifier
                    .fillMaxSize()
                    .background(color = Color.White),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row {
                    TextField(
                        value = name, onValueChange = { name = it },
                        modifier = Modifier.padding(top = 200.dp)
                            .border(0.5.dp, Color.Gray, shape = RoundedCornerShape(4.dp)),
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = Color.White,
                            textColor = Color.Black, placeholderColor = Color.Black
                        ),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        placeholder = { Text("Enter Your Name") }
                    )
                }

                Row {
                    TextField(
                        value = fatherName, onValueChange = { fatherName = it },
                        modifier = Modifier.padding(top = 100.dp)
                            .border(0.5.dp, Color.Gray, shape = RoundedCornerShape(4.dp)),
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = Color.White,
                            textColor = Color.Black, placeholderColor = Color.Black
                        ),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        placeholder = { Text("Enter Your Father Name") }
                    )
                }

                Button(
                    onClick = {
                        saveDataToSharedPreferences(context, name, fatherName)
                        name=""
                        fatherName=""
                        Toast.makeText(context,"Data saved Successfully",Toast.LENGTH_LONG).show()
                        // Add the FLAG_ACTIVITY_NEW_TASK flag
                        val intent = Intent(context, SecondScreen::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        context.startActivity(intent)

                    },
                    modifier = Modifier
                        .padding(top = 16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Blue, contentColor = Color.White)) {
                    Text(text="Save Data",
                        color = Color.White)
                }
            }
        }
    }
}
@Composable
fun SplashScreen(navController: NavController, onSplashScreenFinished: () -> Unit){
    remember {
        androidx.compose.animation.core.Animatable(0f)
    }
    LaunchedEffect(key1=true){
        // Existing code for animating splash screen
        delay(3000L)
        onSplashScreenFinished()
        navController.navigate("main_screen")
    }

    Box(contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ){
        Text(text = "HELLO", color = Color.Red, fontSize = 50.sp, fontWeight = FontWeight.Bold)
    }
}
private fun saveDataToSharedPreferences(context: Context, name: String, fatherName: String) {
    val sharedPreferences = context.getSharedPreferences("data", MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putString("Name", name)
    editor.putString("FatherName", fatherName)
    editor.apply()
}