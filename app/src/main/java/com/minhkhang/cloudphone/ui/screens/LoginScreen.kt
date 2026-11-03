package com.minhkhang.cloudphone.ui.screens
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.minhkhang.cloudphone.data.AppState
@OptIn(ExperimentalMaterial3Api::class)
@Composable fun LoginScreen(state: AppState, onSuccess: () -> Unit) {
    var user by remember { mutableStateOf("Admin Minh Khang") }
    var pass by remember { mutableStateOf("admin123") }
    var error by remember { mutableStateOf<String?>(null) }
    Box(modifier = Modifier.fillMaxSize().background(Color(0xFF0F1115)), contentAlignment = Alignment.Center) {
        Column(modifier = Modifier.padding(32.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text("CloudPhone", fontSize = 32.sp, color = Color(0xFF3B82F6))
            Spacer(Modifier.height(8.dp))
            Text("Đăng nhập Admin", color = Color(0xFF8B919C))
            Spacer(Modifier.height(32.dp))
            OutlinedTextField(value = user, onValueChange = { user = it }, label = { Text("Tài khoản") }, singleLine = true, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(12.dp))
            OutlinedTextField(value = pass, onValueChange = { pass = it }, label = { Text("Mật khẩu") }, singleLine = true, visualTransformation = PasswordVisualTransformation(), keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password), modifier = Modifier.fillMaxWidth())
            error?.let { Spacer(Modifier.height(8.dp)); Text(it, color = Color(0xFFEF4444), fontSize = 13.sp) }
            Spacer(Modifier.height(24.dp))
            Button(onClick = { if (state.login(user.trim(), pass.trim())) onSuccess() else error = "Sai tài khoản" }, modifier = Modifier.fillMaxWidth().height(52.dp)) { Text("Đăng nhập", fontSize = 16.sp) }
        }
    }
}
