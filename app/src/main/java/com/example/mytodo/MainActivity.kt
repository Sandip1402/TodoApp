package com.example.mytodo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mytodo.screens.TodoApp
import com.example.mytodo.viewmodel.TodoViewModel
import com.example.mytodo.viewmodel.TodoViewModelFactory


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        // Create the ViewModel scoped to this Activity
        val db = TodoDatabase.getDatabase(applicationContext)
        val dao = db.todoDao()
        val viewModelFactory = TodoViewModelFactory(dao)
        val viewModel = ViewModelProvider(this, viewModelFactory)[TodoViewModel::class.java]

        // Set content using Compose
        setContent {
            App(viewModel)
        }
    }
}

@Composable
fun App(viewModel: TodoViewModel){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "FlashScreen"){
        composable(route = "FlashScreen"){
            FlashScreen(navController)
        }
        composable(route = "TodoApp"){
            TodoApp(viewModel)
        }
    }
}

@Composable
fun FlashScreen(navController: NavController){
    LaunchedEffect(Unit) {
        kotlinx.coroutines.delay(2000)
        navController.navigate("TodoApp"){
            popUpTo("FlashScreen") { inclusive = true }
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize(1f)
            .background(color = Color(0xFF4DB5FF)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Image(painter = painterResource(R.drawable.logo), contentDescription = "App Logo")
            Spacer(modifier = Modifier.height(8.dp))
            Text("MyTodo", fontSize = 25.sp, fontWeight = FontWeight.Bold)
        }

    }
}



