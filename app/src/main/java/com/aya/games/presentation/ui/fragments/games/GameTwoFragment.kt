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
import com.aya.games.R
import com.aya.games.databinding.FragmentGameOneBinding
import com.aya.games.databinding.FragmentGameTwoBinding
import com.aya.games.domain.model.General
import com.aya.games.domain.model.LookCategoryGames
import com.aya.games.domain.model.TalkGames
import com.aya.games.presentation.ui.viewModel.GameTwoViewModel
import com.aya.games.presentation.utils.Constants
import com.aya.games.presentation.utils.SharedPrefsHelper
import com.aya.games.presentation.utils.getRefrenceHiddenHome
import com.aya.games.presentation.utils.setGlideImageUrl
import com.google.gson.Gson
import kotlin.collections.ArrayList

class GameTwoFragment :Fragment() {

    private lateinit var binding: FragmentGameTwoBinding
    private lateinit var viewModel : GameTwoViewModel

    private val navController by lazy {
        val navHostFragment = activity?.supportFragmentManager
            ?.findFragmentById(R.id.homeframlayout) as NavHostFragment

        navHostFragment.navController
    }

    val mainActivity  by lazy { activity }
    var sharedPrefsHelper : SharedPrefsHelper? = null
    lateinit var data : ArrayList<LookCategoryGames>
    lateinit var background : General


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentGameTwoBinding.inflate(inflater , container , false)
        viewModel = ViewModelProvider(this).get(GameTwoViewModel::class.java)
        sharedPrefsHelper = SharedPrefsHelper(mainActivity!!.applicationContext)


        getRefrenceHiddenHome().autoRunSound(false)
        setGeneral()
        viewModel.getListItems()
        viewModel.requestLiveData.observe(viewLifecycleOwner, Observer {
             data = it as ArrayList<LookCategoryGames>

            showCategories(data)

        })

        clickable()

        return binding.root
    }

    private fun showCategories(data : ArrayList<LookCategoryGames>) {
        // loading image
        binding.progress.visibility = View.VISIBLE
        binding.shaps.setGlideImageUrl(data[0].image!!, binding.progress)
        binding.shapsTx.text = data[0].name_ar
        binding.color.setGlideImageUrl(data[1].image!!, binding.progress)
        binding.colorTx.text = data[1].name_ar
        binding.size.setGlideImageUrl(data[2].image!!, binding.progress)
        binding.sizeTx.text = data[2].name_ar
        binding.direction.setGlideImageUrl(data[3].image!!, binding.progress)
        binding.directionTx.text = data[3].name_ar
        binding.kind.setGlideImageUrl(data[4].image!!, binding.progress)
        binding.kindTx.text = data[4].name_ar
        binding.letter.setGlideImageUrl(data[5].image!!, binding.progress)
        binding.letterTx.text = data[5].name_ar

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
        binding.letter.setOnClickListener {
            val bundle = bundleOf("category" to data[5].id)
            navController.navigate(R.id.GameTwoFragment_to_SubGameTwoFragment,bundle)
        }
        binding.kind.setOnClickListener {
            val bundle = bundleOf("category" to data[4].id)
            navController.navigate(R.id.GameTwoFragment_to_SubGameTwoFragment,bundle)
        }
        binding.color.setOnClickListener {
            val bundle = bundleOf("category" to data[1].id)
            navController.navigate(R.id.GameTwoFragment_to_SubGameTwoFragment,bundle)
        }
        binding.shaps.setOnClickListener {
            val bundle = bundleOf("category" to data[0].id)
            navController.navigate(R.id.GameTwoFragment_to_SubGameTwoFragment,bundle)
        }
        binding.direction.setOnClickListener {
            val bundle = bundleOf("category" to data[3].id)
            navController.navigate(R.id.GameTwoFragment_to_SubGameTwoFragment,bundle)
        }
        binding.size.setOnClickListener {
            val bundle = bundleOf("category" to data[2].id)
            navController.navigate(R.id.GameTwoFragment_to_SubGameTwoFragment,bundle)
        }



    }

    fun skip(){
        navController.navigate(R.id.GameTwoFragment_to_HomeFragment)
    }




}