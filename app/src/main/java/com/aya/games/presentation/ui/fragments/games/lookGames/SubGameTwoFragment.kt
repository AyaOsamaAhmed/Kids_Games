package com.aya.games.presentation.ui.fragments.games.lookGames

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
import com.aya.games.databinding.FragmentSubGameTwoBinding
import com.aya.games.domain.model.General
import com.aya.games.domain.model.LookGames
import com.aya.games.presentation.ui.adapter.AdapterSubGameTwo
import com.aya.games.presentation.ui.interfaces.OnClickSubGameTwo
import com.aya.games.presentation.ui.viewModel.SubGameTwoViewModel
import com.aya.games.presentation.utils.*
import com.google.gson.Gson
import java.io.IOException
import kotlin.collections.ArrayList

class SubGameTwoFragment :Fragment() , OnClickSubGameTwo {

    private lateinit var binding: FragmentSubGameTwoBinding
    private lateinit var viewModel : SubGameTwoViewModel

    private val navController by lazy {
        val navHostFragment = activity?.supportFragmentManager
            ?.findFragmentById(R.id.homeframlayout) as NavHostFragment

        navHostFragment.navController
    }

    val mainActivity  by lazy { activity }
    var sharedPrefsHelper : SharedPrefsHelper? = null
    lateinit var data : ArrayList<LookGames>
    lateinit var background : General
    var num_game = 0
    var answer = "0"
    var size_data = 0
    var category_id =""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentSubGameTwoBinding.inflate(inflater , container , false)
        viewModel = ViewModelProvider(this).get(SubGameTwoViewModel::class.java)
        sharedPrefsHelper = SharedPrefsHelper(mainActivity!!.applicationContext)

        setGeneral()
        category_id = arguments?.getString("category")!!

        if(category_id != null)
        viewModel.getListItems(category_id)

        viewModel.requestLiveData.observe(viewLifecycleOwner, Observer {
             data = it as ArrayList<LookGames>
            size_data = data.size
            getCurrentQuestion(num_game)
            if(!category_id.equals("3")) startSound(data[num_game].question_sound!!)
        })

        clickable()

        return binding.root
    }

    private fun getCurrentQuestion(num:Int){
        binding.result.visibility = View.GONE
        pauseSound()

        answer = data[num].answer!!
        showGames(data[num].images!! , data[num_game].question!!,data[num_game].question_sound!!)

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
    private fun showGames(data : ArrayList<String> , question : String , question_sound:String) {
        binding.question.text = question
        if(category_id.equals("3"))  startSound(question_sound)
        // loading image
        val drawable = getResources().getDrawable(R.drawable.border_green_check)

        val adapter = AdapterSubGameTwo(data,this,drawable,true)
        binding.game.adapter = adapter
    }

    private fun setGeneral() {
         background = Gson().fromJson(sharedPrefsHelper?.getStringValue(Constants.GENERAL), General::class.java)
        // loading image
        binding.progress.visibility = View.VISIBLE
        binding.layout.setGlideImageUrl(background.game_look!!,binding.progress)


    }


    fun clickable(){
        binding.backHome.setOnClickListener {
           skip()
        }
        binding.question.setOnClickListener {
            startSound(data[num_game].question_sound!!)
        }
        binding.back.setOnClickListener {
            getCurrentQuestion(--num_game)
        }
        binding.next.setOnClickListener {
            getCurrentQuestion(++num_game)
        }
    }

    fun skip(){
        navController.navigate(R.id.SubGameTwoFragment_to_GameTwoFragment)
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

    override fun onPause() {
        pauseSound()
        super.onPause()
    }
}