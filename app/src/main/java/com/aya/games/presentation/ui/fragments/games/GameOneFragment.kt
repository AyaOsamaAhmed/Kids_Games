package com.aya.games.presentation.ui.fragments.games

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.google.gson.Gson
import com.squareup.picasso.Picasso

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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentGameOneBinding.inflate(inflater , container , false)
        viewModel = ViewModelProvider(this).get(GameOneViewModel::class.java)
        sharedPrefsHelper = SharedPrefsHelper(mainActivity!!.applicationContext)

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

    private fun showQuestion( num : Int) {
        binding.question.text = data[num].question_ar
        binding.questionTwo.text = data[num].question_ar_2
        Picasso.get().load(data[num].image).into(binding.image)

        if(page_num == 0)
            binding.back.visibility = View.GONE
        else
            binding.back.visibility = View.VISIBLE

        if(last_page == num)
            binding.next.visibility = View.GONE
        else
            binding.next.visibility = View.VISIBLE
    }

    private fun setGeneral() {
        val background : General = Gson().fromJson(sharedPrefsHelper?.getStringValue(Constants.GENERAL), General::class.java)
        Picasso.get().load(background.game_talk).into(binding.layout)


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
            skip()
        }
        binding.answerTwo.setOnClickListener {
            skip()
        }

    }

    fun skip(){
     //   startActivity(Intent(activity , MainActivity::class.java))
     //   requireActivity().finish()

    }

}