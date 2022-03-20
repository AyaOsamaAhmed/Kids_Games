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
import com.aya.games.domain.model.General
import com.aya.games.presentation.ui.viewModel.AuthViewModel
import com.aya.games.presentation.utils.Constants
import com.aya.games.presentation.utils.SharedPrefsHelper
import com.google.gson.Gson
import com.squareup.picasso.Picasso

class ForgetPasswordFragment :Fragment() {

    private lateinit var binding: FragmentForgetPasswordBinding
    private lateinit var viewModel : AuthViewModel

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

        binding = FragmentForgetPasswordBinding.inflate(inflater , container , false)
//      viewModel = ViewModelProvider(this).get(AuthViewModel::class.java)
        sharedPrefsHelper = SharedPrefsHelper(mainActivity!!.applicationContext)


      //  operationResult()
        setGeneral()
        clickable()

        return binding.root
    }

    private fun setGeneral() {
        val background : General = Gson().fromJson(sharedPrefsHelper?.getStringValue(Constants.GENERAL), General::class.java)
        Picasso.get().load(background.background_auth).into(binding.layout)

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