package com.minhkhang.cloudphone.ui.screens
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.minhkhang.cloudphone.data.AppState
import com.minhkhang.cloudphone.data.VmInstance
@OptIn(ExperimentalMaterial3Api::class)
@Composable fun HomeScreen(state: AppState, onOpenVm: (String) -> Unit, onLogout: () -> Unit) {
    var showCreate by remember { mutableStateOf(false) }
    Scaffold(topBar = { TopAppBar(title = { Column { Text("CloudPhone", fontWeight = FontWeight.Bold); Text("Admin: ${state.currentUser ?: ""}", fontSize = 11.sp, color = Color(0xFF8B919C)) } }, actions = { IconButton(onClick = onLogout) { Icon(Icons.Default.Logout, "Logout") } }, colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF1A1D24))) }, floatingActionButton = { ExtendedFloatingActionButton(onClick = { showCreate = true }, icon = { Icon(Icons.Default.Add, null) }, text = { Text("Tạo máy ảo") }, containerColor = Color(0xFF3B82F6)) }, containerColor = Color(0xFF0F1115)) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {
            Text("Máy ảo của tôi · ${state.vms.size}", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Text("Miễn phí vĩnh viễn", fontSize = 12.sp, color = Color(0xFF8B919C))
            Spacer(Modifier.height(16.dp))
            if (state.vms.isEmpty()) { Box(Modifier.fillMaxWidth().height(300.dp), contentAlignment = Alignment.Center) { Text("Chưa có máy ảo", color = Color(0xFF8B919C)) } }
            else { LazyVerticalGrid(columns = GridCells.Fixed(2), horizontalArrangement = Arrangement.spacedBy(12.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) { items(state.vms) { vm -> VmCard(vm, onClick = { onOpenVm(vm.id) }, onDelete = { state.deleteVm(vm.id) }) } } }
        }
    }
    if (showCreate) { CreateVmDialog(onDismiss = { showCreate = false }, onConfirm = { os, reg -> state.addVm(os, reg); showCreate = false }) }
}
@Composable fun VmCard(vm: VmInstance, onClick: () -> Unit, onDelete: () -> Unit) {
    Card(modifier = Modifier.fillMaxWidth().height(200.dp).clip(RoundedCornerShape(16.dp)).clickable(onClick = onClick), colors = CardDefaults.cardColors(containerColor = Color(0xFF1A1D24))) {
        Box(Modifier.fillMaxSize().background(Brush.radialGradient(colors = listOf(Color(vm.color).copy(alpha = 0.25f), Color(0xFF000000)))).padding(12.dp)) {
            Column(Modifier.fillMaxSize()) {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Box(Modifier.clip(RoundedCornerShape(6.dp)).background(Color(vm.color).copy(alpha = 0.2f)).padding(horizontal = 8.dp, vertical = 4.dp)) { Text(vm.osVersion, fontSize = 10.sp, color = Color(vm.color)) }
                    IconButton(onClick = onDelete, modifier = Modifier.size(28.dp)) { Icon(Icons.Default.Delete, null, tint = Color(0xFFEF4444), modifier = Modifier.size(18.dp)) }
                }
                Spacer(Modifier.weight(1f))
                Text(vm.name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text("${vm.cpu} vCPU · ${vm.ramGb}GB", fontSize = 11.sp, color = Color(0xFF8B919C))
                Text("Vùng: ${vm.region} · Miễn phí", fontSize = 10.sp, color = Color(0xFF22C55E))
            }
        }
    }
}
@Composable fun CreateVmDialog(onDismiss: () -> Unit, onConfirm: (String, String) -> Unit) {
    var os by remember { mutableStateOf("Android 14") }
    var region by remember { mutableStateOf("Hong Kong") }
    AlertDialog(onDismissRequest = onDismiss, title = { Text("Tạo máy ảo mới") }, text = { Column { Text("Hệ điều hành", fontWeight = FontWeight.Bold); Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) { listOf("Android 10", "Android 13", "Android 14", "Android 15").forEach { FilterChip(selected = os == it, onClick = { os = it }, label = { Text(it, fontSize = 11.sp) }) } }; Spacer(Modifier.height(16.dp)); Text("Vùng", fontWeight = FontWeight.Bold); Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) { listOf("Hong Kong", "US", "Singapore").forEach { FilterChip(selected = region == it, onClick = { region = it }, label = { Text(it, fontSize = 11.sp) }) } }; Spacer(Modifier.height(16.dp)); Text("Miễn phí", color = Color(0xFF22C55E), fontSize = 13.sp) } }, confirmButton = { TextButton(onClick = { onConfirm(os, region) }) { Text("Tạo") } }, dismissButton = { TextButton(onClick = onDismiss) { Text("Hủy") } })
}
