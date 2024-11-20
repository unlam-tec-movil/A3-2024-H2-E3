package ar.edu.unlam.mobile.scaffolding.ui.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.core.graphics.scale
import java.io.ByteArrayOutputStream

fun Bitmap.toByteArray(): ByteArray =
    ByteArrayOutputStream()
        .apply {
            this@toByteArray.compress(Bitmap.CompressFormat.PNG, 100, this)
        }.toByteArray()

fun ByteArray.toImageBitmap(): ImageBitmap = BitmapFactory.decodeByteArray(this, 0, size).asImageBitmap()

fun ByteArray.toScaledBitmap(): Bitmap = BitmapFactory.decodeByteArray(this, 0, size).scale(200, 200)
