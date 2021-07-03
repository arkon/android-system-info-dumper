package me.echeung.systeminfodumper.data

import android.app.KeyguardManager
import android.content.Context
import android.os.Build
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators
import androidx.core.content.getSystemService

object AuthenticatorUtil {

    fun getInfo(context: Context): String {
        return """
            Supported: ${isSupported(context)}
            Is legacy: ${isLegacySecured(context)}
            Device credentials allowed: ${isDeviceCredentialAllowed(context)}
            Strong: ${isValidAuthenticator(context, Authenticators.BIOMETRIC_STRONG)}
            Weak: ${isValidAuthenticator(context, Authenticators.BIOMETRIC_WEAK)}
            Device credentials: ${isValidAuthenticator(context, Authenticators.DEVICE_CREDENTIAL)}
        """.trimIndent()
    }

    private fun getSupportedAuthenticators(context: Context): Int {
        if (isLegacySecured(context)) {
            return Authenticators.BIOMETRIC_WEAK or Authenticators.DEVICE_CREDENTIAL
        }

        return listOf(
            Authenticators.BIOMETRIC_STRONG,
            Authenticators.BIOMETRIC_WEAK,
            Authenticators.DEVICE_CREDENTIAL,
        )
            .filter { BiometricManager.from(context).canAuthenticate(it) == BiometricManager.BIOMETRIC_SUCCESS }
            .fold(0) { acc, auth -> acc or auth }
    }

    private fun isValidAuthenticator(context: Context, authenticatorType: Int): Boolean {
        return BiometricManager.from(context).canAuthenticate(authenticatorType) == BiometricManager.BIOMETRIC_SUCCESS
    }

    private fun isSupported(context: Context): Boolean {
        return isLegacySecured(context) || getSupportedAuthenticators(context) != 0
    }

    private fun isDeviceCredentialAllowed(context: Context): Boolean {
        return isLegacySecured(context) || (getSupportedAuthenticators(context) and Authenticators.DEVICE_CREDENTIAL != 0)
    }

    /**
     * Returns whether the device is secured with a PIN, pattern or password.
     */
    private fun isLegacySecured(context: Context): Boolean {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.Q) {
            if (context.getSystemService<KeyguardManager>()!!.isDeviceSecure) {
                return true
            }
        }
        return false
    }
}