package com.aya.games.presentation.ui.fragments.games.listenGames

import android.content.ContentValues
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.aya.games.R
import com.aya.games.databinding.FragmentSubGameThreeBinding
import com.aya.games.databinding.FragmentSubGameTypeThreeBinding
import com.aya.games.domain.model.General
import com.aya.games.domain.model.ListenGames
import com.aya.games.presentation.ui.viewModel.SubGameThreeViewModel
import com.aya.games.presentation.utils.Constants
import com.aya.games.presentation.utils.SharedPrefsHelper
import com.aya.games.presentation.utils.setGlideImageUrl
import com.google.gson.Gson
import java.io.IOException
import kotlin.collections.ArrayList

class SubGameThreeTypeFragment :Fragment() {

    private lateinit var binding: FragmentSubGameTypeThreeBinding
    private lateinit var viewModel : SubGameThreeViewModel

    private val navController by lazy {
        val navHostFragment = activity?.supportFragmentManager
            ?.findFragmentById(R.id.homeframlayout) as NavHostFragment

        navHostFragment.navController
    }
    var media_player : MediaPlayer? = null
    val mainActivity  by lazy { activity }
    var sharedPrefsHelper : SharedPrefsHelper? = null
    lateinit var data : ArrayList<ListenGames>
    lateinit var background : General
    var num_game = 0
    var answer = "0"
    var size_data = 0
    var sound:String = ""
    var sound2:String = ""
    var access_check = true
    var question_sound = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentSubGameTypeThreeBinding.inflate(inflater , container , false)
        viewModel = ViewModelProvider(this).get(SubGameThreeViewModel::class.java)
        sharedPrefsHelper = SharedPrefsHelper(mainActivity!!.applicationContext)

        setGeneral()
        val id = arguments?.getString("category")

        if(id != null)
        viewModel.getListItems(id)

        viewModel.requestLiveData.observe(viewLifecycleOwner, Observer {
             data = it as ArrayList<ListenGames>
            size_data = data.size
            getCurrentQuestion(num_game)
        })

        clickable()

        return binding.root
    }

    private fun getCurrentQuestion(num:Int){
        answer = data[num].answer!!
        showGames(data[num].sound!! , data[num_game].question!!)
         question_sound = data[num].question_sound!!
        startSound(question_sound)
        //
        val drawable2 = getResources().getDrawable(R.drawable.bg_corner_white)
        binding.answer1.background = drawable2
        binding.answer2.background = drawable2
        //back button
        if(num == 0)
            binding.back.visibility = View.GONE
        else
            binding.back.visibility = View.VISIBLE

        //next button
        if(num == size_data - 1 )
            binding.next.visibility = View.GONE
        else
            binding.next.visibility = View.VISIBLE
    }
    private fun showGames(data : ArrayList<String> , question : String) {
        binding.question.text = question
        sound = data[0]
        sound2 = data[1]
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

        binding.question.setOnClickListener {
            startSound(question_sound)
        }
        // check when be correct answer
        if(access_check) {
            binding.image.setOnClickListener {
                if (media_player != null) setPauseMedia()
                startSound(sound)
            }
            binding.image2.setOnClickListener {
                if (media_player != null) setPauseMedia()
                startSound(sound2)
            }
            val drawable = getResources().getDrawable(R.drawable.border_green_check)

            binding.answer1.setOnClickListener {
                binding.answer1.background = drawable
                checkAnswer("0")
            }
            binding.answer2.setOnClickListener {
                binding.answer2.background = drawable
                checkAnswer("1")
            }
        }
    }

    private fun checkAnswer(id: String) {
        if(media_player != null) setPauseMedia()
        if(id == answer){
            access_check = false
            show_result(true)
        }else{
            val drawable2 = getResources().getDrawable(R.drawable.bg_corner_white)
            binding.answer1.background = drawable2
            binding.answer2.background = drawable2
            show_result(false)
            Handler(Looper.getMainLooper()).postDelayed({
                getCurrentQuestion(num_game)
            }, 3000)

        }
    }

    fun skip(){
        navController.navigate(R.id.SubGameThreeTypeFragment_to_GameThreeFragment)
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

    fun startSound (sound : String){
        // stream type for our media player.
        val mediaPlayer  = MediaPlayer()
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        try {
            mediaPlayer.setDataSource(sound)
            mediaPlayer.prepare()
            mediaPlayer.start()
            media_player = mediaPlayer
            Log.i(ContentValues.TAG, "playAudio: true")
        } catch (e: IOException) {
            e.printStackTrace()
            Log.i(ContentValues.TAG, "playAudio: false")
        }
    }

    override fun onPause() {
        super.onPause()
        if(media_player!= null) setPauseMedia()
    }

    fun setPauseMedia(){
        media_player!!.pause()
    }

}