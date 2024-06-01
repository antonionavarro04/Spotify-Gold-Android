package com.navarro.spotifygold.entities

import com.navarro.spotifygold.entities.metadata.MetadataEntity
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date

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

            val sdf = SimpleDateFormat("M/d/yyyy h:mm:ss a Z")
            try {
                // Parse the input date string
                this.uploadDate = sdf.parse(strDate)
            } catch (e: ParseException) {
                // Handle parse exception
                e.printStackTrace()
            }
        }
    }
}
