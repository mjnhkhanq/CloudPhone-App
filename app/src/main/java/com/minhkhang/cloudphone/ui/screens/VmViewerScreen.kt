package com.minhkhang.cloudphone.ui.screens
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.minhkhang.cloudphone.data.AppState
import kotlinx.coroutines.delay
import kotlin.math.sin
@OptIn(ExperimentalMaterial3Api::class)
@Composable fun VmViewerScreen(state: AppState, vmId: String, onBack: () -> Unit) {
    val vm = state.vms.find { it.id == vmId }
    if (vm == null) { Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { Text("Lỗi") }; return }
    var frame by remember { mutableLongStateOf(0L) }
    var uptime by remember { mutableLongStateOf(0L) }
    LaunchedEffect(Unit) { while (true) { frame++; uptime = (System.currentTimeMillis() - vm.createdAt) / 1000; delay(16) } }
    Column(Modifier.fillMaxSize().background(Color.Black)) {
        TopAppBar(title = { Text(vm.name, fontWeight = FontWeight.Bold) }, navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, "Back") } }, actions = { Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(end = 12.dp)) { Box(Modifier.size(7.dp).background(Color(0xFF22C55E), shape = androidx.compose.foundation.shape.CircleShape)); Spacer(Modifier.width(6.dp)); Text("Đang chạy", fontSize = 11.sp, color = Color(0xFF22C55E)) } }, colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF1A1D24)))
        Box(Modifier.weight(1f).fillMaxWidth()) {
            Canvas(Modifier.fillMaxSize()) { val w = size.width; val h = size.height; val t = frame / 60f; drawRect(color = Color(0xFF05070A)); val waveY = h * 0.65f; val path = Path(); for (x in 0..w.toInt() step 2) { val y = waveY + sin(x * 0.025f + t * 2f) * 20f + sin(x * 0.06f + t) * 8f; if (x == 0) path.moveTo(x.toFloat(), y) else path.lineTo(x.toFloat(), y) }; drawPath(path, color = Color(vm.color), style = Stroke(width = 3f)) }
            Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                Text(vm.name, color = Color(vm.color), fontSize = 32.sp, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(8.dp))
                Text("${vm.cpu} vCPU · ${vm.ramGb}GB RAM", color = Color.White.copy(alpha = 0.7f), fontSize = 13.sp)
                Spacer(Modifier.height(16.dp))
                Text("Frame: $frame", color = Color.White.copy(alpha = 0.4f), fontSize = 11.sp)
                Text("Uptime: ${uptime}s", color = Color.White.copy(alpha = 0.4f), fontSize = 11.sp)
            }
        }
        Row(Modifier.fillMaxWidth().background(Color(0xFF1A1D24)).padding(12.dp), horizontalArrangement = Arrangement.SpaceEvenly) { Button(onClick = {}) { Text("Home") }; Button(onClick = {}) { Text("Back") }; Button(onClick = {}) { Text("Recent") }; Button(onClick = onBack, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7F1D1D))) { Text("Tắt") } }
    }
}
