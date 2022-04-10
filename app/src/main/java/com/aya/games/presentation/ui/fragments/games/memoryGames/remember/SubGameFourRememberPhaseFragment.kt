package com.aya.games.presentation.ui.fragments.games.memoryGames.remember

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.GridLayoutManager
import com.aya.games.R
import com.aya.games.databinding.FragmentGameFourBinding
import com.aya.games.databinding.FragmentSubGameFourRememberBinding
import com.aya.games.databinding.FragmentSubGameFourRememberPhaseBinding
import com.aya.games.domain.model.*
import com.aya.games.presentation.ui.adapter.*
import com.aya.games.presentation.ui.interfaces.OnClickGameFour
import com.aya.games.presentation.ui.interfaces.OnClickGameThree
import com.aya.games.presentation.ui.interfaces.OnClickSubGameFourRemember
import com.aya.games.presentation.ui.interfaces.OnClickSubGameFourRememberPhase
import com.aya.games.presentation.ui.viewModel.GameFourViewModel
import com.aya.games.presentation.ui.viewModel.GameThreeViewModel
import com.aya.games.presentation.ui.viewModel.SubGameFourViewModel
import com.aya.games.presentation.utils.Constants
import com.aya.games.presentation.utils.SharedPrefsHelper
import com.aya.games.presentation.utils.getRefrenceHiddenHome
import com.aya.games.presentation.utils.setGlideImageUrl
import com.google.gson.Gson
import kotlin.collections.ArrayList

class SubGameFourRememberPhaseFragment :Fragment() , OnClickSubGameFourRememberPhase {

    private lateinit var binding: FragmentSubGameFourRememberPhaseBinding
    private lateinit var viewModel : SubGameFourViewModel

    private val navController by lazy {
        val navHostFragment = activity?.supportFragmentManager
            ?.findFragmentById(R.id.homeframlayout) as NavHostFragment

        navHostFragment.navController
    }

    val mainActivity  by lazy { activity }
    var sharedPrefsHelper : SharedPrefsHelper? = null
    lateinit var background : General

    var data :ArrayList<MemoryGamesRememberPhase> = ArrayList()
    var num_game = 0
    var img_cover  = ""
    var category_id : String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentSubGameFourRememberPhaseBinding.inflate(inflater , container , false)
        viewModel = ViewModelProvider(this).get(SubGameFourViewModel::class.java)
        sharedPrefsHelper = SharedPrefsHelper(mainActivity!!.applicationContext)


        getRefrenceHiddenHome().autoRunSound(false)
        setGeneral()
        category_id = arguments?.getString("category")
        val phase_id = arguments?.getString("phase")
        val layoutCount = arguments?.getInt("layoutCount")

        if(category_id != null && phase_id != null)
            viewModel.getListRememberPhase(category_id!! , phase_id)

        viewModel.requestRememberPhaseLiveData.observe(viewLifecycleOwner, Observer {
            data = it as ArrayList<MemoryGamesRememberPhase>
            showCategory(num_game,data , layoutCount!!)
        })

        clickable()


        return binding.root
    }

    private fun showCategory(num : Int , data : ArrayList<MemoryGamesRememberPhase> , layoutCount : Int) {
        // loading list
        binding.game.layoutManager = GridLayoutManager(mainActivity,layoutCount)
        val adapter = AdapterSubGameFourRememberPhase(data[num].images!!,this,img_cover)
        binding.game.adapter = adapter
    }

    private fun setGeneral() {
         background = Gson().fromJson(sharedPrefsHelper?.getStringValue(Constants.GENERAL), General::class.java)
        // loading image
        binding.progress.visibility = View.VISIBLE
        binding.layout.setGlideImageUrl(background.background_remember!!,binding.progress)
        img_cover = background.background_question_remember!!
    }


    fun clickable(){
        binding.backHome.setOnClickListener {
           skip()
        }
    }

    fun skip(){
        val bundle = bundleOf("category" to category_id )
        navController.navigate(R.id.SubGameFourRememberPhaseFragment_to_SubGameFourRememberFragment,bundle)
    }

    override fun onClickChooseGames(  answer_id_1 : Int , answer_id_2 : Int ) : Boolean {
        var result = false

        for (ans in data[num_game].answer!!) {

        if (ans.equals("$answer_id_1-$answer_id_2")) {
            result = true
            Toast.makeText(mainActivity, "congratulation", Toast.LENGTH_LONG).show()

        }
    }
        return result
    }


}