package com.navarro.spotifygold.services

import android.util.Log
import com.navarro.spotifygold.entities.DtoLogIn
import com.navarro.spotifygold.utils.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

fun logIn(
    usernameEmail: String,
    password: String
) {
    val isEmail = usernameEmail.contains('@')
    val dto = DtoLogIn(
        username = if (!isEmail) usernameEmail else null,
        password = password,
        email = if (isEmail) usernameEmail else null
    )

    val jsonString = Json.encodeToString(dto)
    val client = OkHttpClient()
    val requestBody = jsonString.toRequestBody("application/json; charset=utf-8".toMediaType())

    val request = Request.Builder()
        .url("${Constants.url}login")
        .post(requestBody)
        .build()

    CoroutineScope(Dispatchers.IO).launch {
        client.newCall(request).execute().use { response ->
            Log.d("LoginHandler", response.body?.string() ?: "No response")
            if (!response.isSuccessful) {

            }
        }
    }
}

fun signUp(
    username: String,
    email: String,
    password: String
) {

}
