package com.minhkhang.cloudphone.data
import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import org.json.JSONArray
import org.json.JSONObject
import java.util.UUID

data class VmInstance(val id: String, val name: String, val osVersion: String, val region: String, val cpu: Int, val ramGb: Int, val diskGb: Int, val color: Long, val createdAt: Long)
class AppState(context: Context) {
    private val prefs = context.getSharedPreferences("cloudphone_state", Context.MODE_PRIVATE)
    var currentUser by mutableStateOf<String?>(prefs.getString("user", null))
        private set
    val vms = mutableStateListOf<VmInstance>()
    init { loadVms() }
    fun login(user: String, pass: String): Boolean { if (user == "Admin Minh Khang" && pass == "admin123") { currentUser = user; prefs.edit().putString("user", user).apply(); return true }; return false }
    fun logout() { currentUser = null; vms.clear(); saveVms(); prefs.edit().remove("user").apply() }
    fun addVm(os: String, reg: String): VmInstance { val c = listOf(0xFF3B82F6, 0xFF8B5CF6, 0xFF22C55E, 0xFFEF4444, 0xFFF59E0B); val n = vms.size + 1; val vm = VmInstance(UUID.randomUUID().toString(), "Máy ảo $n", os, reg, 8, 8, 128, c[(n-1)%c.size], System.currentTimeMillis()); vms.add(vm); saveVms(); return vm }
    fun deleteVm(id: String) { vms.removeAll { it.id == id }; saveVms() }
    private fun saveVms() { val a = JSONArray(); vms.forEach { o -> a.put(JSONObject().apply { put("id", o.id); put("name", o.name); put("os", o.osVersion); put("region", o.region); put("cpu", o.cpu); put("ram", o.ramGb); put("disk", o.diskGb); put("color", o.color); put("created", o.createdAt) }) }; prefs.edit().putString("vms", a.toString()).apply() }
    private fun loadVms() { val r = prefs.getString("vms", null) ?: return; try { val a = JSONArray(r); for (i in 0 until a.length()) { val o = a.getJSONObject(i); vms.add(VmInstance(o.getString("id"), o.getString("name"), o.getString("os"), o.getString("region"), o.getInt("cpu"), o.getInt("ram"), o.getInt("disk"), o.getLong("color"), o.getLong("created"))) } } catch (_: Exception) {} }
}
