package com.aya.games.presentation.ui.fragments.games

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.ActivityNotFoundException
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.aya.games.R
import com.aya.games.databinding.FragmentGameOneBinding
import com.aya.games.domain.model.General
import com.aya.games.domain.model.Home
import com.aya.games.domain.model.TalkGames
import com.aya.games.presentation.ui.viewModel.GameOneViewModel
import com.aya.games.presentation.ui.viewModel.MainViewModel
import com.aya.games.presentation.utils.Constants
import com.aya.games.presentation.utils.SharedPrefsHelper
import com.aya.games.presentation.utils.getRefrenceHiddenHome
import com.aya.games.presentation.utils.setGlideImageUrl
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList

class GameOneFragment :Fragment() {

    private lateinit var binding: FragmentGameOneBinding
    private lateinit var viewModel : GameOneViewModel

    private val navController by lazy {
        val navHostFragment = activity?.supportFragmentManager
            ?.findFragmentById(R.id.homeframlayout) as NavHostFragment

        navHostFragment.navController
    }

    val mainActivity  by lazy { activity }
    var sharedPrefsHelper : SharedPrefsHelper? = null
    lateinit var data : ArrayList<TalkGames>
    var page_num = 0
    var last_page = 0
    var answer_one = ""
    var answer_two = ""
    var answer_two_2 = ""
    lateinit var background : General

    private val textToSpeechEngine: TextToSpeech by lazy {
        TextToSpeech(context,
            TextToSpeech.OnInitListener { status ->
                if (status == TextToSpeech.SUCCESS) {
                    textToSpeechEngine.language = Locale.UK //Locale.forLanguageTag("ar")
                }
            })
    }
    companion object {
        private const val REQUEST_CODE_STT = 1
        private var REQUEST_ANSWER = 1
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentGameOneBinding.inflate(inflater , container , false)
        viewModel = ViewModelProvider(this).get(GameOneViewModel::class.java)
        sharedPrefsHelper = SharedPrefsHelper(mainActivity!!.applicationContext)

        viewModel.initial(textToSpeechEngine,startForResult)

        getRefrenceHiddenHome().autoRunSound(false)
        setGeneral()
        viewModel.getListItems()
        viewModel.requestLiveData.observe(viewLifecycleOwner, Observer {
             data = it as ArrayList<TalkGames>
            last_page = data.size
            showQuestion(page_num)

        })

        clickable()

        return binding.root
    }

    private fun showQuestion(num : Int) {
        binding.question.text = data[num].question_ar
        binding.questionTwo.text = data[num].question_ar_2
        answer_one = data[num].answer!!
        answer_two = data[num].answer_2!!
        answer_two_2 = data[num].answer_2_2!!

        // loading image
        binding.progress.visibility = View.VISIBLE
        binding.image.setGlideImageUrl(data[num].image!!,binding.progress)

        // Reset answer
        binding.answer.text = ""
        binding.answer.setBackgroundResource(R.drawable.bg_corner_white)
        val drawable = getResources().getDrawable(R.drawable.ic_mic)
        binding.answer.setCompoundDrawablesWithIntrinsicBounds(null,null,drawable,null)
        //Reset answer 2
        binding.answerTwo.text = ""
        binding.answerTwo.setBackgroundResource(R.drawable.bg_corner_white)
        binding.answerTwo.setCompoundDrawablesWithIntrinsicBounds(null,null,drawable,null)

        //back button
        if(page_num == 0)
            binding.back.visibility = View.GONE
        else
            binding.back.visibility = View.VISIBLE

        //next button
        if(last_page == num +1 )
            binding.next.visibility = View.GONE
        else
            binding.next.visibility = View.VISIBLE
    }

    private fun setGeneral() {
         background = Gson().fromJson(sharedPrefsHelper?.getStringValue(Constants.GENERAL), General::class.java)
      //  Picasso.get().load(background.game_talk).into(binding.layout)


    }


    fun clickable(){
        binding.back.setOnClickListener {
            showQuestion(--page_num)
        }
        binding.next.setOnClickListener {
             showQuestion(++page_num)
        }
        binding.backHome.setOnClickListener {
           skip()
        }
        binding.answer.setOnClickListener {
            REQUEST_ANSWER = 1
          startSpeechToText()
        }
        binding.answerTwo.setOnClickListener {
            REQUEST_ANSWER = 2
         startSpeechToText()
        }
        binding.question.setOnClickListener {
         //   viewModel.speak(binding.question.text.toString(),mainActivity!!.applicationContext)
        }
    }

    fun skip(){
        navController.navigate(R.id.GameOneFragment_to_HomeFragment)
    }

    private val startForResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val spokenText: String? =
                result.data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                    .let { text -> text?.get(0) }
            binding.question.setText(spokenText)
        }
    }

    private fun startSpeechToText() {
        val sttIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        sttIntent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        sttIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ar")//Locale.getDefault()
        sttIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak now!")

        try {
            startActivityForResult(sttIntent, REQUEST_CODE_STT)
        } catch (e: ActivityNotFoundException) {
            e.printStackTrace()
            Toast.makeText(context, "Your device does not support STT.", Toast.LENGTH_LONG).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_CODE_STT -> {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    val result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                    result?.let {
                        val recognizedText = it[0]
                        if(REQUEST_ANSWER == 1)
                             binding.answer.setText(recognizedText)
                        else
                            binding.answerTwo.setText(recognizedText)
                        correctAnswer(recognizedText)
                    }
                }
            }
        }
    }

    private fun correctAnswer(recognizedText: String?) {
    if(REQUEST_ANSWER == 1){
        if(recognizedText == answer_one){
            binding.answer.setBackgroundResource(R.drawable.bg_corner_green)
            binding.answer.setCompoundDrawables(null,null,null,null)
            show_result(true)
        }else{
            binding.answer.text = ""
            show_result(false)
        }
        }else{
        if(recognizedText == answer_two || recognizedText == answer_two_2){
            binding.answerTwo.setBackgroundResource(R.drawable.bg_corner_green)
            binding.answerTwo.setCompoundDrawables(null,null,null,null)
            show_result(true)
        }else{
            binding.answerTwo.text = ""
            show_result(false)
        }
        }
    }

    fun show_result(result : Boolean){
        binding.result.visibility = View.VISIBLE

        if(result) {
             binding.result.setAnimation("correct_answer.json")
            startSound(background.correct_answer!!)
           }
            else {
            binding.result.setAnimation("wrong_answer.json")
            startSound(background.wrong_answer!!)
            }
        binding.result.playAnimation()
        Handler(Looper.getMainLooper()).postDelayed({
            binding.result.visibility = View.GONE
        }, 5000)

    }

    fun startSound (sound : String){
        // stream type for our media player.
        val mediaPlayer  = MediaPlayer()
       mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        try {
            mediaPlayer.setDataSource(sound)
            mediaPlayer.prepare()
            mediaPlayer.start()
            Log.i(ContentValues.TAG, "playAudio: true")
        } catch (e: IOException) {
            e.printStackTrace()
            Log.i(ContentValues.TAG, "playAudio: false")
        }
    }

    override fun onPause() {
        textToSpeechEngine.stop()
        super.onPause()
    }

    override fun onDestroy() {
        textToSpeechEngine.shutdown()
        super.onDestroy()
    }


}