package com.minhkhang.cloudphone
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.minhkhang.cloudphone.ui.screens.*
import com.minhkhang.cloudphone.ui.theme.CloudPhoneTheme
import com.minhkhang.cloudphone.data.AppState
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CloudPhoneTheme {
                val navController = rememberNavController()
                val state = AppState(this)
                Surface(modifier = Modifier.fillMaxSize(), color = Color(0xFF0F1115)) {
                    NavHost(navController = navController, startDestination = "login") {
                        composable("login") { LoginScreen(state) { navController.navigate("home") } }
                        composable("home") { HomeScreen(state, onOpenVm = { vmId -> navController.navigate("vm/$vmId") }, onLogout = { state.logout(); navController.navigate("login") { popUpTo("login") { inclusive = true } } }) }
                        composable("vm/{vmId}") { backStack -> val vmId = backStack.arguments?.getString("vmId") ?: ""; VmViewerScreen(state, vmId) { navController.popBackStack() } }
                    }
                }
            }
        }
    }
}
