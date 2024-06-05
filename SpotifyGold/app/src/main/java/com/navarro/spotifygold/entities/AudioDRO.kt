package com.navarro.spotifygold.entities

import com.navarro.spotifygold.entities.metadata.MetadataEntity
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AudioDRO(
    var metadata: MetadataEntity?,
    var route: String,
    var pos: Int
) {
    var uploadDate: Date = Date()

    init {
        parseDate()
    }

    private fun parseDate() {
        if (this.metadata != null) {
            val strDate = this.metadata!!.uploadAt

            val sdf = SimpleDateFormat("M/d/yyyy h:mm:ss a Z", Locale.US)
            try {
                // Parse the input date string
                this.uploadDate = sdf.parse(strDate) ?: Date()
            } catch (e: ParseException) {
                // Handle parse exception
                e.printStackTrace()
            }
        }
    }

    fun getSafeTitle(): String {
        return this.metadata?.title ?: this.route.split("/").last().split(".").first()
    }

    fun getSafeArtist(): String {
        return this.metadata?.author?.name ?: this.route.split(".").last()
    }
}
