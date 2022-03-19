package com.dio.businesscard.util

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import com.dio.businesscard.R
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

class Image {
    companion object {
        fun share(context: Context, card: View) {
            val bitmap = getScreenshotFromView(card)

            bitmap?.let {
                saveMediaToStorage(context, bitmap)
            }
        }

        private fun getScreenshotFromView(card: View): Bitmap? {
            var screenShot: Bitmap? = null
            try {
                screenShot = Bitmap.createBitmap(card.measuredWidth, card.measuredHeight, Bitmap.Config.ARGB_8888)
                val canvas = Canvas(screenShot)
                card.draw(canvas)
            } catch (e: Exception) {
                Log.e("Error -> ", "Falha ao capturar imagem: ${e.message}")
            }

            return screenShot
        }

        private fun saveMediaToStorage(context: Context, bitmap: Bitmap) {
            val fileName = "${System.currentTimeMillis()}.jpg"

            var fos: OutputStream? = null

            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
                context.contentResolver?.also { contentResolver ->
                    val contentValues = ContentValues().apply {
                        put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
                        put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                        put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                    }

                    val imageUri: Uri? = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

                    fos = imageUri?.let {
                        shareIntent(context, imageUri)
                        contentResolver.openOutputStream(it)
                    }
                }
            } else {
                val imageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                val image = File(imageDir, fileName)
                shareIntent(context, Uri.fromFile(image))
                fos = FileOutputStream(image)
            }

            fos?.use {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
                Toast.makeText(context, context.resources.getText(R.string.label_share), Toast.LENGTH_SHORT).show()
            }
        }

        private fun shareIntent(context: Context, imageUri: Uri) {
            val shareIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_STREAM, imageUri)
                type = "image/jpeg"
            }
            context.startActivity(Intent.createChooser(shareIntent, context.resources.getText(R.string.label_share)))
        }
    }
}