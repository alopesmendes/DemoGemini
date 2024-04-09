package com.ippon.demogemini.core

import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest

@Composable
fun CustomTextImageField(
    modifier: Modifier = Modifier,
    prompt: String,
    onSend: (Bitmap?, String) -> Unit,
    onValueChange: (String) -> Unit,
) {
    val context = LocalContext.current
    var photoUri: Uri? by remember { mutableStateOf(null) }
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia()) { uri ->
            photoUri = uri
        }
    Column(modifier = modifier.fillMaxWidth()) {

        CustomTextField(
            prompt = prompt,
            onValueChange = onValueChange,
            onSend = {
                onSend(photoUri?.let { uri -> Tools.uriToBitmap(context, uri) }, prompt)
                photoUri = null
            },
            leadingIcon = {
                IconButton(
                    onClick = {
                        launcher.launch(
                            PickVisualMediaRequest(
                                mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly,
                            )
                        )
                    },
                    enabled = photoUri != null,
                ) {
                    Icon(
                        Icons.Filled.Add,
                        contentDescription = "Add image to prompt"
                    )
                }
            },
            supportingData = {
                if (photoUri != null) {
                    //Use Coil to display the selected image
                    val painter = rememberAsyncImagePainter(
                        ImageRequest
                            .Builder(LocalContext.current)
                            .data(data = photoUri)
                            .build()
                    )

                    Image(
                        painter = painter,
                        contentDescription = "The image",
                        contentScale = ContentScale.Crop,
                    )
                }
            }
        )
    }


}