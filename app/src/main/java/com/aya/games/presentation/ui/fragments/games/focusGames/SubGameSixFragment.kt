package com.aya.games.presentation.ui.fragments.games.focusGames

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
import com.aya.games.databinding.FragmentSubGameSixBinding
import com.aya.games.domain.model.FocusGames
import com.aya.games.domain.model.General
import com.aya.games.presentation.ui.adapter.AdapterSubGameSix
import com.aya.games.presentation.ui.interfaces.OnClickSubGameSix
import com.aya.games.presentation.ui.viewModel.SubGameSixViewModel
import com.aya.games.presentation.utils.*
import com.google.gson.Gson
import kotlin.collections.ArrayList

class SubGameSixFragment :Fragment() , OnClickSubGameSix {

    private lateinit var binding: FragmentSubGameSixBinding
    private lateinit var viewModel : SubGameSixViewModel

    private val navController by lazy {
        val navHostFragment = activity?.supportFragmentManager
            ?.findFragmentById(R.id.homeframlayout) as NavHostFragment
        navHostFragment.navController
    }

    val mainActivity  by lazy { activity }
    var sharedPrefsHelper : SharedPrefsHelper? = null
    lateinit var data : ArrayList<FocusGames>
    lateinit var background : General
    var num_game = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentSubGameSixBinding.inflate(inflater , container , false)
        viewModel = ViewModelProvider(this).get(SubGameSixViewModel::class.java)
        sharedPrefsHelper = SharedPrefsHelper(mainActivity!!.applicationContext)

        setGeneral()
        val id = arguments?.getString("category")

        if(id != null)
        viewModel.getListItems(id)

        viewModel.requestLiveData.observe(viewLifecycleOwner, Observer {
             data = it as ArrayList<FocusGames>
            getCurrentQuestion(num_game)
            startSound( data[num_game].question_sound!!)
        })

        clickable()

        return binding.root
    }

    private fun getCurrentQuestion(num:Int){
        pauseSound()
        binding.result.visibility = View.GONE

        showGames(data[num].images!! , data[num_game].question!!  , data[num_game].check_image!! )

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


    private fun showGames(data : ArrayList<String> , question : String , check_image : String ) {
        binding.question.text = question
        binding.image.setGlideImageUrl(check_image,binding.progress)

        // loading image
        val drawable = getResources().getDrawable(R.drawable.border_green_check)

        val adapter = AdapterSubGameSix(data,this,drawable,true)
        binding.game.adapter = adapter
    }

    private fun setGeneral() {
         background = Gson().fromJson(sharedPrefsHelper?.getStringValue(Constants.GENERAL), General::class.java)
        // loading image
        binding.progress.visibility = View.VISIBLE
        binding.layout.setGlideImageUrl(background.background_focus!!,binding.progress)
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
        navController.navigate(R.id.SubGameSixFragment_to_GameSixFragment)
    }

    override fun onClickChooseGames(id: String) {
        pauseSound()
        if(id ==  data[num_game].answer!!){
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