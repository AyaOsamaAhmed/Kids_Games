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
import com.aya.games.databinding.FragmentGameTwoBinding
import com.aya.games.domain.model.General
import com.aya.games.domain.model.LookCategoryGames
import com.aya.games.domain.model.TalkCategoryGames
import com.aya.games.domain.model.TalkGames
import com.aya.games.presentation.ui.adapter.AdapterGameOne
import com.aya.games.presentation.ui.adapter.AdapterGameTwo
import com.aya.games.presentation.ui.interfaces.OnClickGameTwo
import com.aya.games.presentation.ui.viewModel.GameTwoViewModel
import com.aya.games.presentation.utils.Constants
import com.aya.games.presentation.utils.SharedPrefsHelper
import com.aya.games.presentation.utils.setGlideImageUrl
import com.google.gson.Gson
import kotlin.collections.ArrayList

class GameTwoFragment :Fragment() , OnClickGameTwo{

    private lateinit var binding: FragmentGameTwoBinding
    private lateinit var viewModel : GameTwoViewModel

    private val navController by lazy {
        val navHostFragment = activity?.supportFragmentManager
            ?.findFragmentById(R.id.homeframlayout) as NavHostFragment

        navHostFragment.navController
    }

    val mainActivity  by lazy { activity }
    var sharedPrefsHelper : SharedPrefsHelper? = null
 //   lateinit var data : ArrayList<LookCategoryGames>
    lateinit var background : General


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentGameTwoBinding.inflate(inflater , container , false)
        viewModel = ViewModelProvider(this).get(GameTwoViewModel::class.java)
        sharedPrefsHelper = SharedPrefsHelper(mainActivity!!.applicationContext)

        setGeneral()
        viewModel.getListItems()
        viewModel.requestLiveData.observe(viewLifecycleOwner, Observer {
            val   data = it as ArrayList<LookCategoryGames>

            showCategory(data)

        })

        clickable()

        return binding.root
    }

    private fun showCategory(data : ArrayList<LookCategoryGames> ) {
        // loading list
        binding.game.layoutManager = GridLayoutManager(mainActivity,3)
        val adapter = AdapterGameTwo(data,this)
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
        navController.navigate(R.id.GameTwoFragment_to_HomeFragment)
    }

    override fun onClickChooseGames(id: String) {
        val bundle = bundleOf("category" to id)
        navController.navigate(R.id.GameTwoFragment_to_SubGameTwoFragment,bundle)
    }


}