package ar.edu.unlam.mobile.scaffolding.ui.screens

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions

@Composable
fun QRScannerScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onQrFinished: (String) -> Unit,
) {
    var qrCodeContent by remember { mutableStateOf("") }
    val context = LocalContext.current
    val qrScannerLauncher =
        rememberLauncherForActivityResult(
            contract = ScanContract(),
        ) { result ->
            if (result.contents == null) {
                Toast.makeText(context, "Scan Cancelled", Toast.LENGTH_LONG).show()
            } else {
                qrCodeContent = result.contents ?: "No content"
                onQrFinished(qrCodeContent)
            }
        }
    Button(onClick = { startQRScanner(qrScannerLauncher) }) {
        Text("Scan QR")
    }
}

fun startQRScanner(qrScannerLauncher: ActivityResultLauncher<ScanOptions>) {
    val options =
        ScanOptions().apply {
            setDesiredBarcodeFormats(ScanOptions.QR_CODE)
            setPrompt("Scan a QR code")
            setBeepEnabled(true)
            setBarcodeImageEnabled(true)
        }
    qrScannerLauncher.launch(options)
}
