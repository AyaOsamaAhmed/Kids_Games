package com.aya.games.presentation.ui.fragments.games.listenLookGames

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.aya.games.R
import com.aya.games.databinding.FragmentSubGameFiveBinding
import com.aya.games.domain.model.General
import com.aya.games.domain.model.ListenLookGames
import com.aya.games.presentation.ui.adapter.AdapterSubGameFive
import com.aya.games.presentation.ui.interfaces.OnClickSubGameFive
import com.aya.games.presentation.ui.viewModel.SubGameFiveViewModel
import com.aya.games.presentation.utils.*
import com.google.gson.Gson
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
    var time = 5500
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentSubGameFiveBinding.inflate(inflater , container , false)
        viewModel = ViewModelProvider(this).get(SubGameFiveViewModel::class.java)
        sharedPrefsHelper = SharedPrefsHelper(mainActivity!!.applicationContext)

        setGeneral()
        val id = arguments?.getString("category")

        if(id != null)
        viewModel.getListItems(id)

        viewModel.requestLiveData.observe(viewLifecycleOwner, Observer {
             data = it as ArrayList<ListenLookGames>
            getCurrentQuestion(num_game)
            startSound( data[num_game].question_sound!!)
        })

        clickable()

        return binding.root
    }

    private fun getCurrentQuestion(num:Int){
        pauseSound()
        binding.result.visibility = View.GONE


        Handler(Looper.getMainLooper()).postDelayed({
            startSound(data[num].check_sound!!)
        }, time.toLong())

        time = 0
        showGames(data[num].images!! , data[num_game].question!!)

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


    private fun showGames(data : ArrayList<String> , question : String  ) {
        binding.question.text = question

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
            startSound(data[num_game].check_sound!!)
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
        navController.navigate(R.id.SubGameFiveFragment_to_GameFiveFragment)
    }

    override fun onClickChooseGames(id: String) {
        pauseSound()
        if(id == data[num_game].answer!!){
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