package com.aya.games.presentation.ui.fragments.games.focusGames

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
import com.aya.games.databinding.FragmentDiffGameSixBinding
import com.aya.games.databinding.FragmentSubGameFiveBinding
import com.aya.games.databinding.FragmentSubGameSixBinding
import com.aya.games.domain.model.FocusDiffGames
import com.aya.games.domain.model.FocusGames
import com.aya.games.domain.model.General
import com.aya.games.domain.model.ListenLookGames
import com.aya.games.presentation.ui.adapter.AdapterSubGameFive
import com.aya.games.presentation.ui.adapter.AdapterSubGameSix
import com.aya.games.presentation.ui.interfaces.OnClickSubGameSix
import com.aya.games.presentation.ui.viewModel.SubGameFiveViewModel
import com.aya.games.presentation.ui.viewModel.SubGameSixViewModel
import com.aya.games.presentation.utils.Constants
import com.aya.games.presentation.utils.SharedPrefsHelper
import com.aya.games.presentation.utils.setGlideImageUrl
import com.google.gson.Gson
import java.io.IOException
import kotlin.collections.ArrayList

class DiffGameSixFragment :Fragment() , OnClickSubGameSix {

    private lateinit var binding: FragmentDiffGameSixBinding
    private lateinit var viewModel : SubGameSixViewModel

    private val navController by lazy {
        val navHostFragment = activity?.supportFragmentManager
            ?.findFragmentById(R.id.homeframlayout) as NavHostFragment

        navHostFragment.navController
    }

    val mainActivity  by lazy { activity }
    var media_player  : MediaPlayer? = null
    var sharedPrefsHelper : SharedPrefsHelper? = null
    lateinit var data : ArrayList<FocusDiffGames>
    lateinit var background : General
    var num_game = 0
    var answer = ""
    var size_data = 0
    var question_sound  = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentDiffGameSixBinding.inflate(inflater , container , false)
        viewModel = ViewModelProvider(this).get(SubGameSixViewModel::class.java)
        sharedPrefsHelper = SharedPrefsHelper(mainActivity!!.applicationContext)

        setGeneral()
        val id = arguments?.getString("category")

        if(id != null)
        viewModel.getListDiffItems(id)

        viewModel.requestDiffLiveData.observe(viewLifecycleOwner, Observer {
             data = it as ArrayList<FocusDiffGames>
            size_data = data.size
            getCurrentQuestion(num_game)
         //   startSound( data[num_game].question_sound!!)
        })

        clickable()

        return binding.root
    }

    private fun getCurrentQuestion(num:Int){
         showGames(data[num].list_image!! , data[num].background!! , data[num].list!! )

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


    private fun showGames(data : ArrayList<String> , image : String ,list : ArrayList<String> ) {
      //  binding.question.text = question
        binding.image.setGlideImageUrl(image,binding.progress)
        binding.imageAns.setGlideImageUrl(image,binding.progress)

        clearImages()
        for ((index,num) in list.withIndex() ) {
         when(num){
             "1" ->  binding.imgTop.setGlideImageUrl(data[index], binding.progress)
             "2" ->  binding.imgBottom.setGlideImageUrl(data[index], binding.progress)
             "3" ->  binding.imgStart.setGlideImageUrl(data[index], binding.progress)
             "4" ->  binding.imgEnd.setGlideImageUrl(data[index], binding.progress)
             "5" ->  binding.imgBottomEnd.setGlideImageUrl(data[index],binding.progress)
         }

        }

    }

    fun clearImages(){
        binding.imgStart.setImageResource(0)
        binding.imgBottom.setImageResource(0)
        binding.imgEnd.setImageResource(0)
        binding.imgBottomEnd.setImageResource(0)
        binding.imgTop.setImageResource(0)


    }
    private fun setGeneral() {
         background = Gson().fromJson(sharedPrefsHelper?.getStringValue(Constants.GENERAL), General::class.java)
        // loading image
        binding.progress.visibility = View.VISIBLE
        binding.layout.setGlideImageUrl(background.background_focus!!,binding.progress)
    }


    fun clickable(){
        binding.backHome.setOnClickListener {
            if(media_player!= null) setPauseMedia()
           skip()
        }

      /*  binding.question.setOnClickListener {
            if(media_player!= null) setPauseMedia()
            startSound(question_sound)
        }*/
        binding.imgStart.setOnClickListener {
            binding.imgStart.setBackgroundResource(R.drawable.background_border_pink)

        }
        binding.imgEnd.setOnClickListener {
            binding.imgEnd.setBackgroundResource(R.drawable.background_border_pink)

        }
        binding.imgBottom.setOnClickListener {
            binding.imgBottom.setBackgroundResource(R.drawable.background_border_pink)

        }
        binding.imgTop.setOnClickListener {
            binding.imgTop.setBackgroundResource(R.drawable.background_border_pink)

        }
        binding.back.setOnClickListener {
            if(media_player!= null) setPauseMedia()
            getCurrentQuestion(--num_game)
        }
        binding.next.setOnClickListener {
            if(media_player!= null) setPauseMedia()
            getCurrentQuestion(++num_game)
        }


    }

    fun skip(){
        navController.navigate(R.id.DiffGameSixFragment_to_GameSixFragment)
    }

    override fun onClickChooseGames(id: String) {
        if(media_player!= null) setPauseMedia()
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
        media_player = MediaPlayer()

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