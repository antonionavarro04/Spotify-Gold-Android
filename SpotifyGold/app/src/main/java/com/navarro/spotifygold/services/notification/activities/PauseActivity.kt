package com.navarro.spotifygold.services.notification.activities

import android.app.Activity
import android.os.Bundle
import com.navarro.spotifygold.services.MediaPlayerSingleton

class PauseActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (MediaPlayerSingleton.mediaPlayer.isPlaying) {
            MediaPlayerSingleton.mediaPlayer.pause()
        } else {
            MediaPlayerSingleton.mediaPlayer.start()
        }

        finish()
    }
}
