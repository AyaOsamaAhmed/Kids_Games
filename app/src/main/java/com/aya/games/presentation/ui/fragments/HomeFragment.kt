package com.aya.games.presentation.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.aya.games.R
import com.aya.games.databinding.FragmentHomeBinding
import com.aya.games.domain.model.General
import com.aya.games.domain.model.Home
import com.aya.games.presentation.ui.interfaces.OnClickHome
import com.aya.games.presentation.ui.adapter.AdapterHome
import com.aya.games.presentation.ui.viewModel.MainViewModel
import com.aya.games.presentation.utils.Constants
import com.aya.games.presentation.utils.SharedPrefsHelper
import com.aya.games.presentation.utils.getRefrenceHiddenHome
import com.aya.games.presentation.utils.setGlideImageUrl
import com.google.gson.Gson
import com.squareup.picasso.Picasso

class HomeFragment :Fragment() , OnClickHome {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel : MainViewModel

    var check_data = true
    private val navController by lazy {
        val navHostFragment = activity?.supportFragmentManager
            ?.findFragmentById(R.id.homeframlayout) as NavHostFragment
        navHostFragment.navController
    }

    val mainActivity  by lazy { activity }
    var lislHomeGames : ArrayList<Home> = arrayListOf()
    var sharedPrefsHelper : SharedPrefsHelper? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentHomeBinding.inflate(inflater , container , false)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        sharedPrefsHelper = SharedPrefsHelper(mainActivity!!.applicationContext)

        setGeneral()
        if(check_data)viewModel.getListItems()

        viewModel.requestLiveData.observe(viewLifecycleOwner, Observer {
            check_data = true
            lislHomeGames = it as ArrayList<Home>
            val adapterGmaes = AdapterHome(lislHomeGames , this)
            binding.recyclerGames.adapter =  adapterGmaes
        })

        clickable()

        return binding.root
    }

    private fun setGeneral() {
        val background : General= Gson().fromJson(sharedPrefsHelper?.getStringValue(Constants.GENERAL), General::class.java)
       // Picasso.get().load(background.background_main).into(binding.layout)
        binding.layout.setGlideImageUrl(background.background_main!!,binding.progress)

        if(!getRefrenceHiddenHome().checkIsPlaying())
        getRefrenceHiddenHome().autoRunSound(true)
    }

    fun clickable(){
        binding.setting.setOnClickListener {
          // navController.navigate(R.id.HomeFragment_to_SettingFragment)
        }
        binding.exam.setOnClickListener {
          // navController.navigate(R.id.HomeFragment_to_ExamFragment)
        }

    }

    override fun onClickChooseGames(id: String) {
        when(id){
            "1" ->   navController.navigate(R.id.HomeFragment_to_GameOneFragment)
            "2" ->   navController.navigate(R.id.HomeFragment_to_GameTwoFragment)
            "3" ->   navController.navigate(R.id.HomeFragment_to_GameThreeFragment)
        }
    }


}