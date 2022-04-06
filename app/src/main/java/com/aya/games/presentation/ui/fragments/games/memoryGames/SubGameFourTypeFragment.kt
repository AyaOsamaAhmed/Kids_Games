package com.aya.games.presentation.ui.fragments.games.memoryGames

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
import com.aya.games.domain.model.General
import com.aya.games.presentation.ui.adapter.AdapterSubGameFour
import com.aya.games.presentation.ui.interfaces.OnClickSubGameFour
import com.aya.games.presentation.ui.viewModel.SubGameFourViewModel
import com.aya.games.presentation.utils.Constants
import com.aya.games.presentation.utils.SharedPrefsHelper
import com.aya.games.presentation.utils.getRefrenceHiddenHome
import com.aya.games.presentation.utils.setGlideImageUrl
import com.google.gson.Gson
import java.io.IOException
import kotlin.collections.ArrayList
import android.os.CountDownTimer
import com.aya.games.databinding.FragmentSubGameFourTypeBinding
import com.aya.games.domain.model.MemoryGamesType
import com.aya.games.presentation.ui.adapter.AdapterSubGameFourType
import java.text.DecimalFormat
import java.text.NumberFormat
import android.view.animation.RotateAnimation

import android.view.animation.Animation





class SubGameFourTypeFragment :Fragment() , OnClickSubGameFour {

    private lateinit var binding: FragmentSubGameFourTypeBinding
    private lateinit var viewModel : SubGameFourViewModel

    private val navController by lazy {
        val navHostFragment = activity?.supportFragmentManager
            ?.findFragmentById(R.id.homeframlayout) as NavHostFragment

        navHostFragment.navController
    }

    val mainActivity  by lazy { activity }
    var sharedPrefsHelper : SharedPrefsHelper? = null
    lateinit var data : ArrayList<MemoryGamesType>
    lateinit var background : General
    var num_game = 0
    var answer = "0"
    var size_data = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentSubGameFourTypeBinding.inflate(inflater , container , false)
        viewModel = ViewModelProvider(this).get(SubGameFourViewModel::class.java)
        sharedPrefsHelper = SharedPrefsHelper(mainActivity!!.applicationContext)

        getRefrenceHiddenHome().autoRunSound(false)
        setGeneral()
        val id = arguments?.getString("category")

        if(id != null)
        viewModel.getGamesTypeItems(id)

        viewModel.requestTypeLiveData.observe(viewLifecycleOwner, Observer {
             data = it as ArrayList<MemoryGamesType>
            size_data = data.size
            getCurrentQuestion(num_game)
        })

        clickable()

        return binding.root
    }

    private fun getCurrentQuestion(num:Int){
        binding.letter.visibility = View.VISIBLE
        binding.name.visibility = View.VISIBLE
        binding.timer.visibility = View.VISIBLE
        binding.game.visibility = View.INVISIBLE
        binding.question.visibility = View.INVISIBLE
        answer = data[num].answer!!
        showGames(data[num].letter!! , data[num].image!! , data[num].choose!! , data[num].name!! , data[num_game].question!!)

        //handel time
        timeDown( data[num].time!!.toLong())

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

    private fun showGames( letter : String , image : String , data : ArrayList<String> , name : String  , question : String) {
        binding.letter.setGlideImageUrl(letter!!,binding.progress)
        binding.name.setGlideImageUrl(name!!,binding.progress)
        binding.image.setGlideImageUrl(image!!,binding.progress)
        binding.question.text = question
        // loading image
        val drawable = getResources().getDrawable(R.drawable.border_green_check)

        val adapter = AdapterSubGameFourType(data,this,drawable,true)
        binding.game.adapter = adapter
    }

    private fun setGeneral() {
         background = Gson().fromJson(sharedPrefsHelper?.getStringValue(Constants.GENERAL), General::class.java)
        // loading image
        binding.progress.visibility = View.VISIBLE
        binding.layout.setGlideImageUrl(background.background_letter_play!!,binding.progress)
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


    }

    fun skip(){
        navController.navigate(R.id.SubGameFourTypeFragment_to_GameFourFragment)
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

    fun timeDown(time:Long){
        // countdown Interveal is 1sec = 1000 I have used
        val an: Animation = RotateAnimation(0.0f, 90.0f, 250f, 273f)
        an.setFillAfter(true);
        binding.barTimer.startAnimation(an);

        object : CountDownTimer(time, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                // Used for formatting digit to be in 2 digits only
                val f: NumberFormat = DecimalFormat("00")
                val min = millisUntilFinished / 60000 % 60
                val sec = millisUntilFinished / 1000 % 60
                binding.timer.text =  f.format(min) + ":" + f.format(sec)
                val seconds: Int = (30000 / 1000).toInt()
                binding.barTimer.setProgress(seconds)
            }

            // When the task is over it will print 00:00:00 there
            override fun onFinish() {
                binding.timer.text = "00:00:00"
                binding.letter.visibility = View.INVISIBLE
                binding.name.visibility = View.INVISIBLE
                binding.timer.visibility = View.INVISIBLE
                binding.question.visibility = View.VISIBLE
                binding.game.visibility = View.VISIBLE
            }
        }.start()
    }
}