package me.echeung.systeminfodumper.data

import android.annotation.SuppressLint
import android.util.Log

object MiuiUtil {

    fun getMiuiInfo(): String {
        return """
            Is MIUI: ${isMiui()}
            MIUI Optimization disabled: ${isMiuiOptimizationDisabled()}
        """.trimIndent()
    }

    private fun isMiui(): Boolean {
        return getSystemProperty("ro.miui.ui.version.name")?.isNotEmpty() ?: false
    }

    @SuppressLint("PrivateApi")
    private fun isMiuiOptimizationDisabled(): Boolean {
        val sysProp = getSystemProperty("persist.sys.miui_optimization")
        Log.d(TAG, "persist.sys.miui_optimization (0/false == off): $sysProp")
        if ("0" == sysProp || "false" == sysProp) {
            return true
        }

        return try {
            val isXOptMode = Class.forName("android.miui.AppOpsUtils")
                .getDeclaredMethod("isXOptMode")
                .invoke(null) as Boolean
            Log.d(TAG, "isXOptMode (true == off): $isXOptMode")
            isXOptMode
        } catch (e: Exception) {
            false
        }
    }

    @SuppressLint("PrivateApi")
    private fun getSystemProperty(key: String?): String? {
        return try {
            Class.forName("android.os.SystemProperties")
                .getDeclaredMethod("get", String::class.java)
                .invoke(null, key) as String
        } catch (e: Exception) {
            Log.w(TAG, e)
            null
        }
    }
}

private val TAG = MiuiUtil.javaClass.simpleName