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
import com.aya.games.databinding.FragmentLoginBinding
import com.aya.games.databinding.FragmentSignupBinding
import com.aya.games.domain.model.General
import com.aya.games.presentation.ui.viewModel.AuthViewModel
import com.aya.games.presentation.utils.Constants
import com.aya.games.presentation.utils.SharedPrefsHelper
import com.google.gson.Gson
import com.squareup.picasso.Picasso

class SignUpFragment :Fragment() {

    private lateinit var binding: FragmentSignupBinding
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

        binding = FragmentSignupBinding.inflate(inflater , container , false)
      viewModel = ViewModelProvider(this).get(AuthViewModel::class.java)
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
        binding.signUp.setOnClickListener {
            signUp()
        }
        binding.back.setOnClickListener {
            skip()
        }
    }

    fun skip(){
        navController.navigate(R.id.SignUpFragment_to_LoginFragment)
    }
    fun signUp(){
        if(checkValid(binding.firstName.text.toString(),binding.lastName.text.toString(),binding.userName.text.toString()
            , binding.password.text.toString()))
        navController.navigate(R.id.SignUpFragment_to_LoginFragment)
    }

    fun operationResult(){
        viewModel.loginData.observe(viewLifecycleOwner, Observer {
            // loadingDialog.dismiss()
            Toast.makeText(mainActivity, "Success Login , Congratulation", Toast.LENGTH_LONG).show();
    //        startActivity(Intent(mainActivity, MainActivity::class.java))
            requireActivity().finish()
        })

    }

    private fun checkValid(first_name: String ,last_name: String ,txtEmail: String , txtPassword: String): Boolean {


        var status = true

        // Check Validation Email
        if (first_name.isEmpty()) {
            binding.firstName.error = getText(R.string.msg_found)
            binding.firstName.requestFocus()
            status = false
        }

        // Check Validation Email
        if (last_name.isEmpty()) {
            binding.lastName.error = getText(R.string.msg_found)
            binding.lastName.requestFocus()
            status = false
        }

        // Check Validation Email
        if (txtEmail.isEmpty()) {
            binding.userName.error = getText(R.string.msg_found)
            binding.userName.requestFocus()
            status = false
        }

        // Check Validation Password
        if (txtPassword.isEmpty()) {
            binding.password.error = getText(R.string.msg_found)
            binding.password.requestFocus()
            status = false
        } else if (txtPassword.length < 6) {
            binding.password.error = getText(R.string.msg_password)
            binding.password.requestFocus()
            status = false
        }

        return status
    }


}