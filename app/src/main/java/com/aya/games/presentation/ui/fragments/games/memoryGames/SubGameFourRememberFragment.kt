package com.aya.games.presentation.ui.fragments.games.memoryGames

import android.os.Bundle
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
import com.aya.games.databinding.FragmentGameFourBinding
import com.aya.games.databinding.FragmentSubGameFourRememberBinding
import com.aya.games.domain.model.*
import com.aya.games.presentation.ui.adapter.AdapterGameFour
import com.aya.games.presentation.ui.adapter.AdapterGameThree
import com.aya.games.presentation.ui.adapter.AdapterSubGameFourRemember
import com.aya.games.presentation.ui.adapter.AdapterSubGameOne
import com.aya.games.presentation.ui.interfaces.OnClickGameFour
import com.aya.games.presentation.ui.interfaces.OnClickGameThree
import com.aya.games.presentation.ui.interfaces.OnClickSubGameFourRemember
import com.aya.games.presentation.ui.viewModel.GameFourViewModel
import com.aya.games.presentation.ui.viewModel.GameThreeViewModel
import com.aya.games.presentation.ui.viewModel.SubGameFourViewModel
import com.aya.games.presentation.utils.Constants
import com.aya.games.presentation.utils.SharedPrefsHelper
import com.aya.games.presentation.utils.getRefrenceHiddenHome
import com.aya.games.presentation.utils.setGlideImageUrl
import com.google.gson.Gson
import kotlin.collections.ArrayList

class SubGameFourRememberFragment :Fragment() , OnClickSubGameFourRemember {

    private lateinit var binding: FragmentSubGameFourRememberBinding
    private lateinit var viewModel : SubGameFourViewModel

    private val navController by lazy {
        val navHostFragment = activity?.supportFragmentManager
            ?.findFragmentById(R.id.homeframlayout) as NavHostFragment

        navHostFragment.navController
    }

    val mainActivity  by lazy { activity }
    var sharedPrefsHelper : SharedPrefsHelper? = null
    lateinit var background : General
    var category_id  = "0"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentSubGameFourRememberBinding.inflate(inflater , container , false)
        viewModel = ViewModelProvider(this).get(SubGameFourViewModel::class.java)
        sharedPrefsHelper = SharedPrefsHelper(mainActivity!!.applicationContext)


        getRefrenceHiddenHome().autoRunSound(false)
        setGeneral()
        val id = arguments?.getString("category")

        if(id != null)
            viewModel.getListRemember(id)
       // binding.progressGame.visibility = View.VISIBLE

        viewModel.requestRememberLiveData.observe(viewLifecycleOwner, Observer {
            val   data = it as ArrayList<MemoryGamesRemember>
            category_id = id!!
            showCategory(data)
        })

        clickable()


        return binding.root
    }

    private fun showCategory(data : ArrayList<MemoryGamesRemember> ) {
        // loading list
        binding.game.layoutManager = GridLayoutManager(mainActivity,3)
        val adapter = AdapterSubGameFourRemember(data,this)
        binding.game.adapter = adapter
    }

    private fun setGeneral() {
         background = Gson().fromJson(sharedPrefsHelper?.getStringValue(Constants.GENERAL), General::class.java)
        // loading image
        binding.progress.visibility = View.VISIBLE
        binding.layout.setGlideImageUrl(background.background_remember!!,binding.progress)
    }


    fun clickable(){
        binding.backHome.setOnClickListener {
           skip()
        }
    }

    fun skip(){
        navController.navigate(R.id.SubGameFourRememberFragment_to_GameFourFragment)
    }

    override fun onClickChooseGames(phase_id: Int , layout_num : Int) {
        val bundle = bundleOf("category" to category_id , "phase" to phase_id.toString() , "layoutCount" to layout_num)
        navController.navigate(R.id.SubGameFourRememberFragment_to_SubGameFourRememberPhaseFragment, bundle)

    }



}