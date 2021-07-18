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
        if ("0" == getSystemProperty("persist.sys.miui_optimization")) {
            return true
        }

        return try {
            Class.forName("android.miui.AppOpsUtils")
                .getDeclaredMethod("isXOptMode")
                .invoke(null) as Boolean
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
            Log.w(MiuiUtil.javaClass.simpleName, e)
            null
        }
    }
}