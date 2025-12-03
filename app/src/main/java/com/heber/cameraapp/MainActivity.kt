package com.heber.cameraapp

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.FileProvider
import coil.compose.rememberImagePainter
import com.heber.cameraapp.ui.theme.CameraAppTheme
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Objects

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CameraAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CameraScreen(modifier = Modifier.padding(innerPadding), this)
                }
            }
        }
    }
}

@Composable
fun CameraScreen(modifier: Modifier = Modifier, context: Context) {
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var tempUri by remember { mutableStateOf<Uri?>(null) }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success ->
            if (success && tempUri != null) {
                imageUri = tempUri
            }
        }
    )

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            val uri = createImageUri(context)
            tempUri = uri
            cameraLauncher.launch(uri)
        }) {
            Text("Abrir Cámara")
        }

        imageUri?.let {
            Image(
                painter = rememberImagePainter(it),
                contentDescription = "Imagen Capturada",
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

private fun createImageUri(context: Context): Uri {
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
    val imageFileName = "JPEG_" + timeStamp + "_"
    val storageDir = context.externalCacheDir
    val image = File.createTempFile(
        imageFileName, /* prefix */
        ".jpg", /* suffix */
        storageDir      /* directory */
    )
    return FileProvider.getUriForFile(
        Objects.requireNonNull(context),
        context.packageName + ".provider",
        image
    )
}

@Preview(showBackground = true)
@Composable
fun CameraScreenPreview() {
    CameraAppTheme {
        // CameraScreen()
    }
}