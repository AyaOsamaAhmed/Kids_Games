package com.aya.games.presentation.ui.fragments.games

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
import com.aya.games.domain.model.General
import com.aya.games.domain.model.ListenCategoryGames
import com.aya.games.domain.model.MemoryCategoryGames
import com.aya.games.presentation.ui.adapter.AdapterGameFour
import com.aya.games.presentation.ui.adapter.AdapterGameThree
import com.aya.games.presentation.ui.interfaces.OnClickGameFour
import com.aya.games.presentation.ui.interfaces.OnClickGameThree
import com.aya.games.presentation.ui.viewModel.GameFourViewModel
import com.aya.games.presentation.ui.viewModel.GameThreeViewModel
import com.aya.games.presentation.utils.Constants
import com.aya.games.presentation.utils.SharedPrefsHelper
import com.aya.games.presentation.utils.getRefrenceHiddenHome
import com.aya.games.presentation.utils.setGlideImageUrl
import com.google.gson.Gson
import kotlin.collections.ArrayList

class GameFourFragment :Fragment() , OnClickGameFour {

    private lateinit var binding: FragmentGameFourBinding
    private lateinit var viewModel : GameFourViewModel

    private val navController by lazy {
        val navHostFragment = activity?.supportFragmentManager
            ?.findFragmentById(R.id.homeframlayout) as NavHostFragment

        navHostFragment.navController
    }

    val mainActivity  by lazy { activity }
    var sharedPrefsHelper : SharedPrefsHelper? = null
    lateinit var background : General


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentGameFourBinding.inflate(inflater , container , false)
        viewModel = ViewModelProvider(this).get(GameFourViewModel::class.java)
        sharedPrefsHelper = SharedPrefsHelper(mainActivity!!.applicationContext)


        getRefrenceHiddenHome().autoRunSound(false)
        setGeneral()
        viewModel.getListItems()
        viewModel.requestLiveData.observe(viewLifecycleOwner, Observer {
          val   data = it as ArrayList<MemoryCategoryGames>
            showCategory(data)
        })

        clickable()

        return binding.root
    }

    private fun showCategory(data : ArrayList<MemoryCategoryGames> ) {
        // loading list
        binding.game.layoutManager = GridLayoutManager(mainActivity,2)
        val adapter = AdapterGameFour(data,this)
        binding.game.adapter = adapter
    }

    private fun setGeneral() {
         background = Gson().fromJson(sharedPrefsHelper?.getStringValue(Constants.GENERAL), General::class.java)
        // loading image
        binding.progress.visibility = View.VISIBLE
        binding.layout.setGlideImageUrl(background.game_memory!!,binding.progress)
    }


    fun clickable(){
        binding.backHome.setOnClickListener {
           skip()
        }
    }

    fun skip(){
        navController.navigate(R.id.GameFourFragment_to_HomeFragment)
    }

    override fun onClickChooseGames(id: String , type:String) {
        if(type == "1") {
            val bundle = bundleOf("category" to id)
            navController.navigate(R.id.GameFourFragment_to_SubGameFourFragment, bundle)
        }else if (type == "2"){
            val bundle = bundleOf("category" to id)
            navController.navigate(R.id.GameFourFragment_to_SubGameFourTypeFragment, bundle)
        }else if (type == "3"){
            val bundle = bundleOf("category" to id)
            navController.navigate(R.id.GameFourFragment_to_SubGameFourMemoryFragment, bundle)
        }

    }



}