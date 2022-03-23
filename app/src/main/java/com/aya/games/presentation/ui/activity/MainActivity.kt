package com.aya.games.presentation.ui.activity

import android.content.ContentValues
import android.media.AudioManager
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.RadioButton
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.aya.games.R
import com.aya.games.databinding.ActivityMainBinding
import com.aya.games.domain.model.General
import com.aya.games.presentation.ui.interfaces.OnClickMain
import com.aya.games.presentation.ui.viewModel.MainViewModel
import com.aya.games.presentation.utils.Constants
import com.aya.games.presentation.utils.ContextUtils
import com.aya.games.presentation.utils.SharedPrefsHelper
import com.aya.games.presentation.utils.setRefrenceHiddenHome
import com.google.gson.Gson
import java.io.IOException
import java.util.*
import kotlin.math.log

class MainActivity : AppCompatActivity() , OnClickMain {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel : MainViewModel
    var sharedPrefsHelper : SharedPrefsHelper? = null
    lateinit var background : General
    var  mediaPlayer = MediaPlayer()
    val TAG = "MainActivity11"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "onCreate: ")
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        sharedPrefsHelper = SharedPrefsHelper(this)
        background  = Gson().fromJson(sharedPrefsHelper?.getStringValue(Constants.GENERAL), General::class.java)

        setRefrenceHiddenHome(this)
        changeLang()
        Handler(Looper.getMainLooper()).postDelayed({
             startSound()
        }, 5000)
    }

    fun changeLang(){
        val localeToSwitchTo = Locale("ar")
        sharedPrefsHelper!!.setValue(Constants.LANGUAGE, "ar")
        ContextUtils.updateLocale(this, localeToSwitchTo)

    }
     fun startSound (){

        // stream type for our media player.
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        try {
            mediaPlayer.setDataSource(background.sound)
            mediaPlayer.prepare()
            mediaPlayer.start()
            mediaPlayer.isLooping= true
            Log.i(ContentValues.TAG, "playAudio: true")
        } catch (e: IOException) {
            e.printStackTrace()
            Log.i(ContentValues.TAG, "playAudio: false")
        }
    }

    override fun autoRunSound(check_sound : Boolean) {
       if(check_sound){
           mediaPlayer.start()
        }else{
            mediaPlayer.pause()
        }
    }

    override fun checkIsPlaying(): Boolean {
        return mediaPlayer.isPlaying
    }

    override fun onPause() {
        super.onPause()
        Log.i(TAG, "onPause: ")
        mediaPlayer.pause()
    }
    override fun onRestart() {
        super.onRestart()
        Log.i(TAG, "onRestart: ")
        mediaPlayer.start()
    }
}