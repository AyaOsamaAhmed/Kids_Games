package com.aya.games.presentation.ui.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.aya.games.R
import com.aya.games.databinding.FragmentSplashBinding
import com.aya.games.presentation.ui.viewModel.AuthViewModel

class SplashFragment : Fragment() {

    private lateinit var binding: FragmentSplashBinding

    private val navController by lazy {
        val navHostFragment = activity?.supportFragmentManager
            ?.findFragmentById(R.id.homeframlayout) as NavHostFragment

        navHostFragment.navController
    }
    val mainActivity  by lazy { activity }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentSplashBinding.inflate(inflater , container , false)


       Handler(Looper.getMainLooper()).postDelayed({
            // Create an Intent that will start.
            navController.navigate(R.id.SplashFragment_to_LoginFragment)
        }, 5000)




        return binding.root
    }

}