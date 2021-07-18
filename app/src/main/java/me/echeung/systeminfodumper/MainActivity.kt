package me.echeung.systeminfodumper

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import me.echeung.systeminfodumper.data.AuthenticatorUtil
import me.echeung.systeminfodumper.data.DeviceInfo
import me.echeung.systeminfodumper.data.MiuiUtil
import me.echeung.systeminfodumper.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background,
                ) {
                    SystemInfo()
                }
            }
        }
    }
}

@Composable
fun SystemInfo() {
    val context = LocalContext.current

    LazyColumn(
        modifier = Modifier.padding(16.dp),
    ) {
        systemInfoSection("Device info") { Text(DeviceInfo.getDeviceInfo()) }

        systemInfoSection("Biometrics info") { Text(AuthenticatorUtil.getInfo(context)) }

        systemInfoSection("MIUI info") { Text(MiuiUtil.getMiuiInfo()) }

        systemInfoSection("App info") { Text(DeviceInfo.getAppInfo()) }
    }
}

fun LazyListScope.systemInfoSection(title: String, content: @Composable () -> Unit) {
    item { Text(title, style = MaterialTheme.typography.h4) }
    item { content() }

    item { Divider(Modifier.padding(vertical = 8.dp)) }
}