package com.navarro.spotifygold.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.navarro.spotifygold.R
import com.navarro.spotifygold.StaticToast
import com.navarro.spotifygold.entities.DtoResultEntity
import com.navarro.spotifygold.ui.theme.Black0
import com.navarro.spotifygold.ui.theme.Black20
import com.navarro.spotifygold.ui.theme.Black50
import com.navarro.spotifygold.ui.theme.Black60
import com.navarro.spotifygold.ui.theme.Black80
import com.navarro.spotifygold.ui.theme.Black90
import com.navarro.spotifygold.ui.theme.White100
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException


interface SearchCallBack {
    fun onSuccess(audioList: List<DtoResultEntity>)
    fun onFailure(error: String)
}

// Return will be a list of AudioEntity
private fun search(
    query: String,
    callback: SearchCallBack
) {
    GlobalScope.launch(Dispatchers.IO) {
        val timeStart = System.currentTimeMillis()
        try {
            val client = OkHttpClient()
            val request = Request.Builder()
                .url("https://spotifygold.azurewebsites.net/api/yt/search?query=$query&maxResults=50")
                .build()

            val response = client.newCall(request).execute()
            val responseData = response.body?.string()

            // Parse the response, with the help of kotlinx.serialization
            if (response.isSuccessful) {
                val audioList = Json.decodeFromString<List<DtoResultEntity>>(responseData!!)
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    query: MutableState<String>,
    list: SnapshotStateList<DtoResultEntity>
) {
    TextField(
        value = query.value,
        onValueChange = { query.value = it },
        placeholder = {
            Text(
                text = stringResource(id = R.string.and_search_searchbox_placeholder),
                color = Black60,
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent),
        textStyle = TextStyle(color = Black20, fontSize = 18.sp),
        trailingIcon = {
            IconButton(
                onClick = {
                    search(
                        query.value,
                        object : SearchCallBack {
                            override fun onSuccess(audioList: List<DtoResultEntity>) {
                                list.clear()

                                for (audio in audioList) {
                                    list.add(audio)
                                }
                            }

                            override fun onFailure(error: String) {
                                Log.e("SearchScreen", "Error: $error")
                                StaticToast.showToast("Error: $error")
                            }
                        }
                    )
                },
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = Black20
                )
            }
        },
        colors = TextFieldDefaults.textFieldColors(
            textColor = Black0,
            containerColor = Black80,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        shape = RoundedCornerShape(5.dp),
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text, imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
            StaticToast.showToast(query.value)
        }),
    )
}