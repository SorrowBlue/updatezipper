package com.example.updatezipper.component

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Android
import androidx.compose.material.icons.filled.FolderOpen
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.darkrockstudios.libraries.mpfilepicker.DirectoryPicker
import com.darkrockstudios.libraries.mpfilepicker.FilePicker

@Composable
internal fun OutputDirectoryTextField(
    path: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    var showDirectoryPicker by remember { mutableStateOf(false) }
    DirectoryPicker(show = showDirectoryPicker) { s ->
        showDirectoryPicker = false
        onValueChange(s.orEmpty())
    }
    TextField(
        value = path,
        onValueChange = {},
        readOnly = true,
        trailingIcon = {
            IconButton(onClick = {
                showDirectoryPicker = true
            }) {
                Icon(Icons.Filled.FolderOpen, null)
            }
        },
        modifier = modifier,
    )
}

@Composable
internal fun ApkFileTextField(
    path: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    var showFilePicker by remember { mutableStateOf(false) }
    val extensions = remember { listOf("apk") }
    FilePicker(show = showFilePicker, fileExtensions = extensions, title = "apkファイルを選択") { mpFile ->
        showFilePicker = false
        onValueChange(mpFile?.path.orEmpty())
    }
    TextField(
        value = path,
        onValueChange = {},
        readOnly = true,
        trailingIcon = {
            IconButton(onClick = {
                showFilePicker = true
            }) {
                Icon(Icons.Filled.Android, null)
            }
        },
        modifier = modifier,
    )
}
