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
import com.aya.games.presentation.ui.viewModel.AuthViewModel

class LoginFragment :Fragment() {

    private lateinit var binding: FragmentLoginBinding
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

        binding = FragmentLoginBinding.inflate(inflater , container , false)
//      viewModel = ViewModelProvider(this).get(AuthViewModel::class.java)


      //  operationResult()
     //   clickable()

        return binding.root
    }

/*
    fun clickable(){
        binding.signUp.setOnClickListener {
            signUp()
        }
        binding.signIn.setOnClickListener {
            login(binding.emailOrMobileNumber.text.toString(),binding.password.text.toString())
        }
        binding.forgrtPassword.setOnClickListener {
            forgetPasswordOnClick()
        }
        binding.imageBack.setOnClickListener {
            skip()
        }
    }


 */
    fun skip(){
     //   startActivity(Intent(activity , MainActivity::class.java))
     //   requireActivity().finish()

    }
    fun forgetPasswordOnClick(){
    //    navController.navigate(R.id.LoginFragment_to_ForgetPasswordFragment)
    }

    fun login(txtEmail: String, txtPassword: String){
    /*    if (checkValid( txtEmail  , txtPassword )){
            viewModel.login(txtEmail  , txtPassword)
          }

     */
    }

    fun signUp(){
     //   navController.navigate(R.id.LoginFragment_to_SignUpFragment)
    }

    fun operationResult(){
        viewModel.loginData.observe(viewLifecycleOwner, Observer {
            // loadingDialog.dismiss()
            Toast.makeText(mainActivity, "Success Login , Congratulation", Toast.LENGTH_LONG).show();
    //        startActivity(Intent(mainActivity, MainActivity::class.java))
            requireActivity().finish()
        })

    }

    fun loginByGmail(){

    }
    fun signInFacebook(){

    }

/*
    private fun checkValid(txtEmail: String , txtPassword: String): Boolean {


        var status = true

        // Check Validation Email
        if (txtEmail.isEmpty()) {
            binding.emailOrMobileNumber.error = getText(R.string.msg_found)
            binding.emailOrMobileNumber.requestFocus()
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
*/

}