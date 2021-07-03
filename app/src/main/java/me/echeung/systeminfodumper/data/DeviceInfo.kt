package me.echeung.systeminfodumper.data

import android.os.Build
import me.echeung.systeminfodumper.BuildConfig

object DeviceInfo {

    fun getAppInfo(): String {
        return """
            App version: ${BuildConfig.VERSION_NAME} (${BuildConfig.VERSION_CODE})
        """.trimIndent()
    }

    fun getDeviceInfo(): String {
        return """
            Android version: ${Build.VERSION.RELEASE} (SDK ${Build.VERSION.SDK_INT})
            Android build ID: ${Build.DISPLAY}
            Brand: ${Build.BRAND}
            Manufacturer: ${Build.MANUFACTURER}
            Name: ${Build.DEVICE}
            Model: ${Build.MODEL}
            Product name: ${Build.PRODUCT}
            ABIs: ${Build.SUPPORTED_ABIS.joinToString()}
        """.trimIndent()
    }
}