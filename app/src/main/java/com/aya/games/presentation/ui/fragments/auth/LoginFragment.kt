package com.aya.games.presentation.ui.fragments.auth

import android.content.ContentValues.TAG
import android.media.AudioManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RemoteViews
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.aya.games.R
import com.aya.games.databinding.FragmentLoginBinding
import com.aya.games.domain.model.General
import com.aya.games.presentation.ui.viewModel.AuthViewModel
import com.aya.games.presentation.utils.Constants
import com.aya.games.presentation.utils.Constants.GENERAL
import com.aya.games.presentation.utils.SharedPrefsHelper
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import android.media.MediaPlayer
import android.util.Log
import java.io.IOException


class LoginFragment :Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var viewModel : AuthViewModel

    private val navController by lazy {
        val navHostFragment = activity?.supportFragmentManager
            ?.findFragmentById(R.id.homeframlayout) as NavHostFragment

        navHostFragment.navController
    }
    val mainActivity  by lazy { activity }
    var sharedPrefsHelper : SharedPrefsHelper? = null
    var  mediaPlayer = MediaPlayer()
    var play_Audio = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentLoginBinding.inflate(inflater , container , false)
        viewModel = ViewModelProvider(this).get(AuthViewModel::class.java)
        sharedPrefsHelper = SharedPrefsHelper(mainActivity!!.applicationContext)

        setGeneral()
        operationResult()
        clickable()

        return binding.root
    }

    private fun setGeneral() {
        val background : General= Gson().fromJson(sharedPrefsHelper?.getStringValue(Constants.GENERAL), General::class.java)
        Picasso.get().load(background.background_auth).into(binding.layout)
        if(!play_Audio)playAudio(background.sound!!)

    }

    fun clickable(){
        binding.signUp.setOnClickListener {
            signUp()
        }
        binding.signIn.setOnClickListener {
            login(binding.userName.text.toString(),binding.password.text.toString())
        }
        binding.forgetPassword.setOnClickListener {
            forgetPasswordOnClick()
        }

    }

    fun forgetPasswordOnClick(){
        navController.navigate(R.id.LoginFragment_to_ForgetPasswordFragment)
    }

    fun login(txtEmail: String, txtPassword: String){
        if (checkValid( txtEmail  , txtPassword )){
          //  viewModel.login(txtEmail  , txtPassword)
            navController.navigate(R.id.LoginFragment_to_HomeFragment)
          }
    }

    fun signUp(){
        navController.navigate(R.id.LoginFragment_to_SignUpFragment)
    }

    fun operationResult(){
        viewModel.loginData.observe(viewLifecycleOwner, Observer {
            // loadingDialog.dismiss()
            Toast.makeText(mainActivity, "Success Login , Congratulation", Toast.LENGTH_LONG).show();
    //        startActivity(Intent(mainActivity, MainActivity::class.java))
            requireActivity().finish()
        })

    }

    private fun checkValid(txtEmail: String , txtPassword: String): Boolean {
        var status = true

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

    fun playAudio( audioUrl:String) {


        // stream type for our media player.
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        try {
            mediaPlayer.setDataSource(audioUrl)
            mediaPlayer.prepare()
            mediaPlayer.start()
            mediaPlayer.isLooping= true
            play_Audio = true
            Log.i(TAG, "playAudio: true")
        } catch (e: IOException) {
            e.printStackTrace()
            Log.i(TAG, "playAudio: false")
        }

    }

    override fun onResume() {
        super.onResume()
        mediaPlayer.stop()
    }

}