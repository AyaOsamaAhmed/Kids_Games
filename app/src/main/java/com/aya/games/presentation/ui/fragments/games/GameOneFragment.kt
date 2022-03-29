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
import com.aya.games.databinding.FragmentGameOneBinding
import com.aya.games.domain.model.General
import com.aya.games.domain.model.TalkCategoryGames
import com.aya.games.domain.model.TalkGames
import com.aya.games.presentation.ui.adapter.AdapterGameOne
import com.aya.games.presentation.ui.adapter.AdapterSubGameTwo
import com.aya.games.presentation.ui.interfaces.OnClickGameOne
import com.aya.games.presentation.ui.viewModel.GameOneViewModel
import com.aya.games.presentation.utils.Constants
import com.aya.games.presentation.utils.SharedPrefsHelper
import com.aya.games.presentation.utils.getRefrenceHiddenHome
import com.aya.games.presentation.utils.setGlideImageUrl
import com.google.gson.Gson
import kotlin.collections.ArrayList

class GameOneFragment :Fragment() ,OnClickGameOne{

    private lateinit var binding: FragmentGameOneBinding
    private lateinit var viewModel : GameOneViewModel

    private val navController by lazy {
        val navHostFragment = activity?.supportFragmentManager
            ?.findFragmentById(R.id.homeframlayout) as NavHostFragment

        navHostFragment.navController
    }

    val mainActivity  by lazy { activity }
    var sharedPrefsHelper : SharedPrefsHelper? = null
   // lateinit var data : ArrayList<TalkCategoryGames>
    lateinit var background : General


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentGameOneBinding.inflate(inflater , container , false)
        viewModel = ViewModelProvider(this).get(GameOneViewModel::class.java)
        sharedPrefsHelper = SharedPrefsHelper(mainActivity!!.applicationContext)

        getRefrenceHiddenHome().autoRunSound(false)
        setGeneral()

        viewModel.getListItems()
        viewModel.requestLiveData.observe(viewLifecycleOwner, Observer {
         val    data = it as ArrayList<TalkCategoryGames>
            showCategory(data)
        })


        clickable()

        return binding.root
    }


    private fun setGeneral() {
        background = Gson().fromJson(sharedPrefsHelper?.getStringValue(Constants.GENERAL), General::class.java)
        //
        binding.progress.visibility = View.VISIBLE
        binding.layout.setGlideImageUrl(background.game_talk!!,binding.progress)

    }

    private fun showCategory(data : ArrayList<TalkCategoryGames> ) {
        // loading list
        binding.game.layoutManager = GridLayoutManager(mainActivity,2)
        val adapter = AdapterGameOne(data,this)
        binding.game.adapter = adapter
    }
    fun clickable(){

        binding.backHome.setOnClickListener {
           skip()
        }

    }

    fun skip(){
        navController.navigate(R.id.GameOneFragment_to_HomeFragment)
    }

    override fun onClickChooseGames(id: String) {
        val bundle = bundleOf("category" to id)
        navController.navigate(R.id.GameOneFragment_to_SubGameOneFragment,bundle)
    }

}