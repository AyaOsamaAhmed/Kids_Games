package com.aya.games.presentation.ui.fragments.games.focusGames

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
import com.aya.games.databinding.FragmentGameFiveBinding
import com.aya.games.databinding.FragmentGameSixBinding
import com.aya.games.domain.model.FocusCategoryGames
import com.aya.games.domain.model.General
import com.aya.games.domain.model.ListenLookCategoryGames
import com.aya.games.domain.model.MemoryCategoryGames
import com.aya.games.presentation.ui.adapter.AdapterGameFive
import com.aya.games.presentation.ui.adapter.AdapterGameSix
import com.aya.games.presentation.ui.interfaces.OnClickGameFive
import com.aya.games.presentation.ui.interfaces.OnClickGameSix
import com.aya.games.presentation.ui.viewModel.GameFiveViewModel
import com.aya.games.presentation.ui.viewModel.GameSixViewModel
import com.aya.games.presentation.utils.Constants
import com.aya.games.presentation.utils.SharedPrefsHelper
import com.aya.games.presentation.utils.setGlideImageUrl
import com.google.gson.Gson
import kotlin.collections.ArrayList

class PuzzelCategoryGameSixFragment :Fragment() , OnClickGameSix {

    private lateinit var binding: FragmentGameSixBinding
    private lateinit var viewModel : GameSixViewModel

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

        binding = FragmentGameSixBinding.inflate(inflater , container , false)
        viewModel = ViewModelProvider(this).get(GameSixViewModel::class.java)
        sharedPrefsHelper = SharedPrefsHelper(mainActivity!!.applicationContext)

        setGeneral()
        viewModel.getListItems()
        viewModel.requestLiveData.observe(viewLifecycleOwner, Observer {
          val   data = it as ArrayList<FocusCategoryGames>
            showCategory(data)
        })

        clickable()

        return binding.root
    }

    private fun showCategory(data : ArrayList<FocusCategoryGames> ) {
        // loading list
        binding.game.layoutManager = GridLayoutManager(mainActivity,3)
        val adapter = AdapterGameSix(data,this)
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
    }

    fun skip(){
        navController.navigate(R.id.GameSixFragment_to_HomeFragment)
    }

    override fun onClickChooseGames(id: String , type:String ) {

        when(type){
            "1" -> {  val bundle = bundleOf("category" to id)
                    navController.navigate(R.id.GameSixFragment_to_SubGameSixFragment, bundle)
                    }
            "2" -> {
                val bundle = bundleOf("category" to id)
                navController.navigate(R.id.GameSixFragment_to_SubGameSixFragment, bundle)

            }

    }}



}