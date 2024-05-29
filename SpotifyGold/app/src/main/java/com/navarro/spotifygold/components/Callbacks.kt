package com.navarro.spotifygold.components

import com.navarro.spotifygold.entities.DtoResultEntity

interface SearchCallBack {
    fun onSuccess(newAudioList: List<DtoResultEntity>)
    fun onFailure(error: String)
}