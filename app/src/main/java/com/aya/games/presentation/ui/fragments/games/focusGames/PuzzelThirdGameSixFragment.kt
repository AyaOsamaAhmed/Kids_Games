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
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.GridLayoutManager
import com.aya.games.R
import com.aya.games.databinding.*
import com.aya.games.domain.model.*
import com.aya.games.presentation.ui.adapter.*
import com.aya.games.presentation.ui.interfaces.OnClickGameFive
import com.aya.games.presentation.ui.interfaces.OnClickGameSix
import com.aya.games.presentation.ui.interfaces.OnClickPuzzelAnsGameSix
import com.aya.games.presentation.ui.interfaces.OnClickPuzzelGameSix
import com.aya.games.presentation.ui.viewModel.GameFiveViewModel
import com.aya.games.presentation.ui.viewModel.GameSixViewModel
import com.aya.games.presentation.ui.viewModel.SubGameSixViewModel
import com.aya.games.presentation.utils.Constants
import com.aya.games.presentation.utils.SharedPrefsHelper
import com.aya.games.presentation.utils.setGlideImageUrl
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.gson.Gson
import com.google.protobuf.LazyStringArrayList
import java.io.IOException
import kotlin.collections.ArrayList

class PuzzelThirdGameSixFragment :Fragment() , OnClickPuzzelGameSix , OnClickPuzzelAnsGameSix{

    private lateinit var binding: FragmentPuzzelThirdGameSixBinding
    private lateinit var viewModel : SubGameSixViewModel

    private val navController by lazy {
        val navHostFragment = activity?.supportFragmentManager
            ?.findFragmentById(R.id.homeframlayout) as NavHostFragment

        navHostFragment.navController
    }

    val mainActivity  by lazy { activity }
    var sharedPrefsHelper : SharedPrefsHelper? = null
    lateinit var background : General
    lateinit var data : ArrayList<FocusPuzzelGames>
    var media_player  : MediaPlayer? = null
    var imgAns : ArrayList<String> = arrayListOf()
    var imgQuestion : ArrayList<String> = arrayListOf()
    var imageAnswer : ArrayList<String> = arrayListOf()
    var category_id = ""
    var level_id = 0
    var num_game = 0
    var answer = "0"
    var size_data = 0
    var selected_id = -1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentPuzzelThirdGameSixBinding.inflate(inflater , container , false)
        viewModel = ViewModelProvider(this).get(SubGameSixViewModel::class.java)
        sharedPrefsHelper = SharedPrefsHelper(mainActivity!!.applicationContext)

        val id = arguments?.getString("level")
         category_id = arguments?.getString("category")!!

        if(id != null && category_id != null) {
            viewModel.getPuzzelItems(category_id, id)
            level_id = id.toInt()
            level_id++
        }
        setGeneral()
        viewModel.requestpuzzelLiveData.observe(viewLifecycleOwner, Observer {
            data = it as  ArrayList<FocusPuzzelGames>
            size_data = data.size

            showQuestion(num_game)

        })

        clickable()

        return binding.root
    }

    private fun showQuestion(num:Int) {
        binding.imgHint.setGlideImageUrl(data[num].image!!,binding.progress)

        imageAnswer.clear()
        imgQuestion.clear()

        imageAnswer.addAll(data[num].list_ans_images!!)
        val adapter = AdapterPuzzelGameSix(data[num].list_images!!,this)
        binding.game.adapter = adapter

        imgAns.clear()
        imgAns.add("")
        imgAns.add("")
        imgAns.add("")
        imgAns.add("")
        imgAns.add("")
        imgAns.add("")

        binding.image.layoutManager = GridLayoutManager(mainActivity,level_id)
        val adapterAns = AdapterPuzzelThirdAnsGameSix(imgAns,this)
        binding.image.adapter = adapterAns
        imgQuestion.addAll(data[num_game].list_images!!)

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

    private fun setGeneral() {
         background = Gson().fromJson(sharedPrefsHelper?.getStringValue(Constants.GENERAL), General::class.java)
        // loading image
        binding.progress.visibility = View.VISIBLE
        binding.layout.setGlideImageUrl(background.background_focus!!,binding.progress)
        binding.imgHint2.setGlideImageUrl(background.puzzel_background_hint!!,binding.progress)

    }


    fun clickable(){
        binding.backHome.setOnClickListener {
           skip()
        }

        binding.reload.setOnClickListener {
            showQuestion(num_game)
        }
        binding.back.setOnClickListener {
            if(media_player!= null) setPauseMedia()
            showQuestion(--num_game)
        }
        binding.next.setOnClickListener {
            if(media_player!= null) setPauseMedia()
            showQuestion(++num_game)
        }

    }

    fun skip(){
        val bundle = bundleOf( "category" to category_id)

        navController.navigate(R.id.PuzzelThirdGameSixFragment_to_PuzzelCategoryGameSixFragment,bundle)
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
        val mediaPlayer  : MediaPlayer = MediaPlayer()
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

    override fun onClickChooseGames(id: Int) {

        selected_id = id

    }

    override fun onClickChooseAnsGames(id: Int) {
        if(selected_id != -1) {
            val img = imgQuestion[id]

            imgAns[selected_id] = img
            binding.image.layoutManager = GridLayoutManager(mainActivity, level_id)
            val adapterAns = AdapterPuzzelThirdAnsGameSix(imgAns, this)
            binding.image.adapter = adapterAns

            //
            imgQuestion.removeAt(id)
            val adapter = AdapterPuzzelGameSix(imgQuestion, this)
            binding.game.adapter = adapter
            if (imgQuestion.isEmpty()) {
                if (imageAnswer.equals(imgAns))
                    show_result(true)
                else {
                    show_result(false)
                    showQuestion(num_game)
                }

            }

            selected_id = -1
        }

    }


}