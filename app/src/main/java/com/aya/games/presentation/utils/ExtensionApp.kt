package com.aya.games.presentation.utils

import android.content.ContentValues
import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.util.Log
import com.aya.games.presentation.ui.interfaces.OnClickMain
import java.io.IOException

lateinit var showOrHide : OnClickMain

fun setRefrenceHiddenHome(NewShowOrHide : OnClickMain) {
    showOrHide = NewShowOrHide
}

fun getRefrenceHiddenHome():OnClickMain{
    return showOrHide
}

var  mediaPlayer = MediaPlayer()

fun startSound (sound : String){
        pauseSound()
    try {
        mediaPlayer.reset()
        mediaPlayer.setDataSource(sound)
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.prepareAsync()
        mediaPlayer.setOnPreparedListener {
            it.start()
        }
        Log.i(ContentValues.TAG, "playAudio: true")
    } catch (e: IOException) {
        e.printStackTrace()
        Log.i(ContentValues.TAG, "playAudio: false")
    }
}

fun pauseSound(){
    mediaPlayer.pause()
    mediaPlayer.reset()
}
