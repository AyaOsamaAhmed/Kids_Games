package com.aya.games.presentation.ui.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.aya.games.R
import com.aya.games.databinding.FragmentSplashBinding
import com.aya.games.domain.model.General
import com.aya.games.presentation.ui.interfaces.OnClickMain
import com.aya.games.presentation.ui.viewModel.AuthViewModel
import com.aya.games.presentation.ui.viewModel.GeneralViewModel
import com.aya.games.presentation.utils.Constants
import com.aya.games.presentation.utils.SharedPrefsHelper
import com.squareup.picasso.Picasso

class SplashFragment : Fragment() {

    private lateinit var binding: FragmentSplashBinding

    private lateinit var viewModel: GeneralViewModel

    private val navController by lazy {
        val navHostFragment = activity?.supportFragmentManager
            ?.findFragmentById(R.id.homeframlayout) as NavHostFragment

        navHostFragment.navController
    }
    val mainActivity  by lazy { activity }
    var sharedPrefsHelper : SharedPrefsHelper? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentSplashBinding.inflate(inflater , container , false)
        viewModel = ViewModelProvider(this).get(GeneralViewModel::class.java)
        sharedPrefsHelper = SharedPrefsHelper(mainActivity!!.applicationContext)

        viewModel.getGeneralItems()
        viewModel.requestLiveData.observe(viewLifecycleOwner, Observer {
            val data = it as General
            sharedPrefsHelper?.setValue(Constants.GENERAL,data)
        })

       Handler(Looper.getMainLooper()).postDelayed({
            // Create an Intent that will start.
            navController.navigate(R.id.SplashFragment_to_LoginFragment)

           //navController.navigate(R.id.GameOneFragment)
        }, 5000)




        return binding.root
    }





}