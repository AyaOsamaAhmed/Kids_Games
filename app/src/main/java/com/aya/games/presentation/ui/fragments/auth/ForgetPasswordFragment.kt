package com.aya.games.presentation.ui.fragments.auth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.aya.games.R
import com.aya.games.databinding.FragmentForgetPasswordBinding
import com.aya.games.databinding.FragmentLoginBinding
import com.aya.games.presentation.ui.viewModel.AuthViewModel

class ForgetPasswordFragment :Fragment() {

    private lateinit var binding: FragmentForgetPasswordBinding
    private lateinit var viewModel : AuthViewModel

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

        binding = FragmentForgetPasswordBinding.inflate(inflater , container , false)
//      viewModel = ViewModelProvider(this).get(AuthViewModel::class.java)


      //  operationResult()
        clickable()

        return binding.root
    }

    fun clickable(){
        binding.forgetPassword.setOnClickListener {
            forgetPasswordOnClick()
        }
        binding.back.setOnClickListener {
            skip()
        }
    }

    fun skip(){
        navController.navigate(R.id.ForgetPasswordFragment_to_LoginFragment)
    }
    fun forgetPasswordOnClick(){
        if(checkValid(binding.userName.text.toString()))
        navController.navigate(R.id.ForgetPasswordFragment_to_LoginFragment)
    }




    fun operationResult(){
        viewModel.loginData.observe(viewLifecycleOwner, Observer {
            // loadingDialog.dismiss()
            Toast.makeText(mainActivity, "Success Login , Congratulation", Toast.LENGTH_LONG).show();
    //        startActivity(Intent(mainActivity, MainActivity::class.java))
            requireActivity().finish()
        })

    }

    private fun checkValid(txtEmail: String): Boolean {
        var status = true

        // Check Validation Email
        if (txtEmail.isEmpty()) {
            binding.userName.error = getText(R.string.msg_found)
            binding.userName.requestFocus()
            status = false
        }
        return status
    }


}