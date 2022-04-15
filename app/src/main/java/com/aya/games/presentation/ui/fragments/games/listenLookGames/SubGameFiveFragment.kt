package com.aya.games.presentation.ui.fragments.games.listenLookGames

import android.content.ContentValues
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.aya.games.R
import com.aya.games.databinding.FragmentSubGameFiveBinding
import com.aya.games.databinding.FragmentSubGameTwoBinding
import com.aya.games.domain.model.General
import com.aya.games.domain.model.ListenLookGames
import com.aya.games.domain.model.LookGames
import com.aya.games.presentation.ui.adapter.AdapterSubGameFive
import com.aya.games.presentation.ui.adapter.AdapterSubGameTwo
import com.aya.games.presentation.ui.interfaces.OnClickSubGameFive
import com.aya.games.presentation.ui.interfaces.OnClickSubGameTwo
import com.aya.games.presentation.ui.viewModel.SubGameFiveViewModel
import com.aya.games.presentation.ui.viewModel.SubGameTwoViewModel
import com.aya.games.presentation.utils.Constants
import com.aya.games.presentation.utils.SharedPrefsHelper
import com.aya.games.presentation.utils.getRefrenceHiddenHome
import com.aya.games.presentation.utils.setGlideImageUrl
import com.google.gson.Gson
import java.io.IOException
import kotlin.collections.ArrayList

class SubGameFiveFragment :Fragment() , OnClickSubGameFive {

    private lateinit var binding: FragmentSubGameFiveBinding
    private lateinit var viewModel : SubGameFiveViewModel

    private val navController by lazy {
        val navHostFragment = activity?.supportFragmentManager
            ?.findFragmentById(R.id.homeframlayout) as NavHostFragment

        navHostFragment.navController
    }

    val mainActivity  by lazy { activity }
    var sharedPrefsHelper : SharedPrefsHelper? = null
    lateinit var data : ArrayList<ListenLookGames>
    lateinit var background : General
    var num_game = 0
    var answer = "0"
    var size_data = 0
    var question_sound  = ""
    var check_sound = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentSubGameFiveBinding.inflate(inflater , container , false)
        viewModel = ViewModelProvider(this).get(SubGameFiveViewModel::class.java)
        sharedPrefsHelper = SharedPrefsHelper(mainActivity!!.applicationContext)


        getRefrenceHiddenHome().autoRunSound(false)
        setGeneral()
        val id = arguments?.getString("category")

        if(id != null)
        viewModel.getListItems(id)

        viewModel.requestLiveData.observe(viewLifecycleOwner, Observer {
             data = it as ArrayList<ListenLookGames>
            size_data = data.size
            getCurrentQuestion(num_game)
        })

        clickable()

        return binding.root
    }

    private fun getCurrentQuestion(num:Int){
        answer = data[num].answer!!
        showGames(data[num].images!! , data[num_game].question!! ,data[num_game].question_sound!! , data[num_game].check_sound!! )

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


    private fun showGames(data : ArrayList<String> , question : String , question_sound : String , check_sound : String ) {
        binding.question.text = question
        this.check_sound = check_sound
        this.question_sound = question_sound

        // loading image
        val drawable = getResources().getDrawable(R.drawable.border_green_check)

        val adapter = AdapterSubGameFive(data,this,drawable,true)
        binding.game.adapter = adapter
    }

    private fun setGeneral() {
         background = Gson().fromJson(sharedPrefsHelper?.getStringValue(Constants.GENERAL), General::class.java)
        // loading image
        binding.progress.visibility = View.VISIBLE
        binding.layout.setGlideImageUrl(background.background_listen_look!!,binding.progress)
    }


    fun clickable(){
        binding.backHome.setOnClickListener {
           skip()
        }
        binding.sound.setOnClickListener {
            startSound(check_sound)
        }

        binding.question.setOnClickListener {
            startSound(question_sound)
        }
        binding.back.setOnClickListener {
            getCurrentQuestion(--num_game)
        }
        binding.next.setOnClickListener {
            getCurrentQuestion(++num_game)
        }


    }

    fun skip(){
        navController.navigate(R.id.SubGameFiveFragment_to_GameFiveFragment)
    }

    override fun onClickChooseGames(id: String) {

        if(id == answer){
            show_result(true)
        }else{
            show_result(false)
            Handler(Looper.getMainLooper()).postDelayed({
                getCurrentQuestion(num_game)
            }, 3000)

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
            Log.i(ContentValues.TAG, "playAudio: true")
        } catch (e: IOException) {
            e.printStackTrace()
            Log.i(ContentValues.TAG, "playAudio: false")
        }
    }



}