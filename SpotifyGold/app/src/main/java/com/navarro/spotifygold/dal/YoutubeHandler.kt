package com.navarro.spotifygold.dal

import android.content.Context
import android.os.Environment
import android.util.Log
import androidx.compose.ui.res.stringResource
import com.navarro.spotifygold.R
import com.navarro.spotifygold.StaticToast
import com.navarro.spotifygold.components.SearchCallBack
import com.navarro.spotifygold.entities.DtoResultEntity
import com.navarro.spotifygold.utils.Constants
import com.navarro.spotifygold.utils.hasManageExternalStoragePermission
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.util.regex.Pattern

private const val localUrl = "${Constants.url}yt/"

suspend fun downloadSong(
    context: Context,
    audioInfo: DtoResultEntity,
    mode: Int
) {
    Log.d("Download", "Downloading ${audioInfo.title}")
    val id = audioInfo.id
    val url = "${localUrl}$id/download?quality=$mode"

    if (!hasManageExternalStoragePermission(context)) {
        Log.e("DownloadError", "Permission denied")
    }

    StaticToast.showToast("Downloading ${audioInfo.title}")

    withContext(Dispatchers.IO) {
        val client = OkHttpClient()
        val request = Request.Builder().url(url).build()

        try {
            val response = client.newCall(request).execute()

            if (response.isSuccessful) {
                val fileName = getFileNameFromResponse(response) ?: "${Constants.vendor} ${audioInfo.id}.mp3"
                response.body?.byteStream()?.let { inputStream ->
                    saveFileToStorage(context, inputStream, fileName)
                }
            } else {
                Log.e("DownloadError", "Error: ${response.code}")
            }
        } catch (e: IOException) {
            Log.e("DownloadError", "Error downloading file: ${e.message}")
        }
    }
}

fun search(
    query: String,
    callback: SearchCallBack
) {
    GlobalScope.launch(Dispatchers.IO) {
        val timeStart = System.currentTimeMillis()
        try {
            val client = OkHttpClient()
            val request = Request.Builder()
                .url("${localUrl}search?query=$query&maxResults=5")
                .build()

            val response = client.newCall(request).execute()
            val responseData = response.body?.string()

            // Parse the response, with the help of kotlinx.serialization
            if (response.isSuccessful) {
                var audioList = emptyList<DtoResultEntity>()
                try {
                    audioList = Json.decodeFromString(responseData!!)
                } catch (e: Exception) {
                    Log.e("SearchScreen", "Error parsing response: ${e.message}", e)
                }
                withContext(Dispatchers.Main) {
                    callback.onSuccess(audioList)
                }
            } else {
                withContext(Dispatchers.Main) {
                    callback.onFailure("Error: ${response.code} - ${response.message}")
                }
            }
        } catch (e: IOException) {
            Log.e("SearchScreen", "Error during network call: ${e.message}", e)
            // Handle error (e.g., show error message)
        }
        Log.d("SearchScreen", "Time taken: ${System.currentTimeMillis() - timeStart}ms")
    }
}

fun saveFileToStorage(context: Context, inputStream: InputStream, fileName: String) {
    val storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC)
    val file = File(storageDir, fileName)

    Log.d("Download", "Saving to ${file.absolutePath}")
    FileOutputStream(file).use { outputStream ->
        val buffer = ByteArray(4096)
        var bytesRead: Int
        while (inputStream.read(buffer).also { bytesRead = it } != -1) {
            outputStream.write(buffer, 0, bytesRead)
        }
    }
    StaticToast.showToast(context.getString(R.string.download_saved_successfully))
    Log.d("Download", "File saved to ${file.absolutePath}")
    inputStream.close()
}

fun getFileNameFromResponse(response: Response): String? {
    val contentDisposition = response.header("Content-Disposition")
    contentDisposition?.let {
        val regex = "filename=\"(.+?)\""
        val pattern = Pattern.compile(regex)
        val matcher = pattern.matcher(it)
        if (matcher.find()) {
            return matcher.group(1)
        }
    }
    return null
}
