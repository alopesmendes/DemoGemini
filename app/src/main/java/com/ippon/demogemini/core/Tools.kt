package com.ippon.demogemini.core

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri

object Tools {
    fun uriToBitmap(context: Context, uri: Uri): Bitmap? {
        val contentResolver = context.contentResolver
        return try {
            val inputStream = contentResolver.openInputStream(uri)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            return bitmap
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}