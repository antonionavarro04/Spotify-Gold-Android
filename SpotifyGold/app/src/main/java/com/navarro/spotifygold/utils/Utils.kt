package com.navarro.spotifygold.utils

import android.content.Context
import android.os.Build
import android.os.Environment
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker

fun hasManageExternalStoragePermission(context: Context): Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        Environment.isExternalStorageManager()
    } else {
        ContextCompat.checkSelfPermission(
            context, android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PermissionChecker.PERMISSION_GRANTED
    }
}

fun formatTime(seconds: Long): String {
    val hours = seconds / 3600
    val minutes = (seconds % 3600) / 60
    val remainingSeconds = seconds % 60

    return if (hours > 0) {
        String.format("%02d:%02d:%02d", hours, minutes, remainingSeconds)
    } else {
        String.format("%02d:%02d", minutes, remainingSeconds)
    }
}

fun formatViews(views: Long): String {
    return formatNumber(views)
}

fun formatLikes(likes: Long): String {
    val ftt = formatNumber(likes)

    // If equals 0 return "?"
    return if (ftt == "0") "?" else ftt
}

fun formatNumber(number: Long): String {
    return when {
        number < 1000 -> number.toString()
        number < 1000000 -> "${number / 1000}k"
        else -> "${number / 1000000}m"
    }
}

