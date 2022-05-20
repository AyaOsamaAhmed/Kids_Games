package com.aya.games.presentation.ui.fragments.games.listenGames

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.ContentValues
import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.aya.games.R
import com.aya.games.databinding.FragmentSubGameThreeBinding
import com.aya.games.domain.model.General
import com.aya.games.domain.model.ListenGames
import com.aya.games.domain.model.LookGames
import com.aya.games.presentation.ui.adapter.AdapterSubGameThree
import com.aya.games.presentation.ui.fragments.games.TalkGames.SubGameOneFragment
import com.aya.games.presentation.ui.interfaces.OnClickSubGameThree
import com.aya.games.presentation.ui.viewModel.SubGameThreeViewModel
import com.aya.games.presentation.utils.*
import com.google.gson.Gson
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList

class SubGameThreeFragment :Fragment()  {

    private lateinit var binding: FragmentSubGameThreeBinding
    private lateinit var viewModel : SubGameThreeViewModel

    private val navController by lazy {
        val navHostFragment = activity?.supportFragmentManager
            ?.findFragmentById(R.id.homeframlayout) as NavHostFragment

        navHostFragment.navController
    }

    companion object {
        private const val REQUEST_CODE_STT = 1
        private const val TAG = "SubGameThreeFragment"
    }
    val mainActivity  by lazy { activity }
    var sharedPrefsHelper : SharedPrefsHelper? = null
    lateinit var data : ArrayList<ListenGames>
    lateinit var background : General
    var num_game = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentSubGameThreeBinding.inflate(inflater , container , false)
        viewModel = ViewModelProvider(this).get(SubGameThreeViewModel::class.java)
        sharedPrefsHelper = SharedPrefsHelper(mainActivity!!.applicationContext)

        setGeneral()
        val id = arguments?.getString("category")

        if(id != null)
        viewModel.getListItems(id)

        viewModel.requestLiveData.observe(viewLifecycleOwner, Observer {
             data = it as ArrayList<ListenGames>
            getCurrentQuestion(num_game)
            startSound(data[0].question_sound!!)
        })

        clickable()

        return binding.root
    }

    private fun getCurrentQuestion(num:Int){
        pauseSound()
        binding.result.visibility = View.GONE


        binding.question.text = data[num_game].question!!

        Handler(Looper.getMainLooper()).postDelayed({
            startSound(data[num].sound!![0]) }, 7000)

        // Reset answer
        binding.answer.text = ""
        binding.answer.setBackgroundResource(R.drawable.bg_corner_white)
        val drawable = getResources().getDrawable(R.drawable.ic_mic)
        binding.answer.setCompoundDrawablesWithIntrinsicBounds(null,null,drawable,null)

        //back button
        if(num == 0)
            binding.back.visibility = View.GONE
        else
            binding.back.visibility = View.VISIBLE

        //next button
        if(num == data.size - 1 )
            binding.next.visibility = View.GONE
        else
            binding.next.visibility = View.VISIBLE
    }


    private fun setGeneral() {
         background = Gson().fromJson(sharedPrefsHelper?.getStringValue(Constants.GENERAL), General::class.java)
        // loading image
        binding.progress.visibility = View.VISIBLE
        binding.layout.setGlideImageUrl(background.game_listen!!,binding.progress)
    }

    fun clickable(){
        binding.backHome.setOnClickListener {
           skip()
        }

        binding.back.setOnClickListener {
            getCurrentQuestion(--num_game)
        }
        binding.next.setOnClickListener {
            getCurrentQuestion(++num_game)
        }
        binding.image.setOnClickListener {
            startSound(data[num_game].sound!![0])
        }
        binding.question.setOnClickListener {
            startSound(data[0].question_sound!!)
        }
        binding.answer.setOnClickListener {
            startSpeechToText()
        }
    }

    private fun startSpeechToText() {
        pauseSound()
        binding.result.visibility = View.GONE

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
                             binding.answer.setText(recognizedText)
                             correctAnswer(recognizedText)
                    }
                }
            }
        }
    }

    private fun correctAnswer(recognizedText: String?) {
            if(recognizedText ==  data[num_game].answer!!){
                binding.answer.setBackgroundResource(R.drawable.bg_corner_green)
                binding.answer.setCompoundDrawables(null,null,null,null)
                show_result(true)
            }else{
                binding.answer.text = ""
                show_result(false)
            }
    }

    fun skip(){
        navController.navigate(R.id.SubGameThreeFragment_to_GameThreeFragment)
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
        }, 2000)

    }

    override fun onPause() {
        pauseSound()
        super.onPause()
    }


}