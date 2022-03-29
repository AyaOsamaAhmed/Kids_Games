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
import com.aya.games.databinding.FragmentGameThreeBinding
import com.aya.games.databinding.FragmentGameTwoBinding
import com.aya.games.domain.model.General
import com.aya.games.domain.model.ListenCategoryGames
import com.aya.games.domain.model.LookCategoryGames
import com.aya.games.domain.model.TalkGames
import com.aya.games.presentation.ui.adapter.AdapterGameThree
import com.aya.games.presentation.ui.adapter.AdapterGameTwo
import com.aya.games.presentation.ui.interfaces.OnClickGameThree
import com.aya.games.presentation.ui.viewModel.GameThreeViewModel
import com.aya.games.presentation.ui.viewModel.GameTwoViewModel
import com.aya.games.presentation.utils.Constants
import com.aya.games.presentation.utils.SharedPrefsHelper
import com.aya.games.presentation.utils.getRefrenceHiddenHome
import com.aya.games.presentation.utils.setGlideImageUrl
import com.google.gson.Gson
import kotlin.collections.ArrayList

class GameThreeFragment :Fragment() , OnClickGameThree {

    private lateinit var binding: FragmentGameThreeBinding
    private lateinit var viewModel : GameThreeViewModel

    private val navController by lazy {
        val navHostFragment = activity?.supportFragmentManager
            ?.findFragmentById(R.id.homeframlayout) as NavHostFragment

        navHostFragment.navController
    }

    val mainActivity  by lazy { activity }
    var sharedPrefsHelper : SharedPrefsHelper? = null
//    lateinit var data : ArrayList<ListenCategoryGames>
    lateinit var background : General


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentGameThreeBinding.inflate(inflater , container , false)
        viewModel = ViewModelProvider(this).get(GameThreeViewModel::class.java)
        sharedPrefsHelper = SharedPrefsHelper(mainActivity!!.applicationContext)


        getRefrenceHiddenHome().autoRunSound(false)
        setGeneral()
        viewModel.getListItems()
        viewModel.requestLiveData.observe(viewLifecycleOwner, Observer {
          val   data = it as ArrayList<ListenCategoryGames>

            showCategory(data)

        })

        clickable()

        return binding.root
    }

    private fun showCategory(data : ArrayList<ListenCategoryGames> ) {
        // loading list
        binding.game.layoutManager = GridLayoutManager(mainActivity,2)
        val adapter = AdapterGameThree(data,this)
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
    }

    fun skip(){
        navController.navigate(R.id.GameThreeFragment_to_HomeFragment)
    }

    override fun onClickChooseGames(id: String) {


    }


}