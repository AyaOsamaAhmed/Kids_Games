package com.aya.games.presentation.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.aya.games.R
import com.aya.games.databinding.ActivityMainBinding
import com.aya.games.presentation.ui.viewModel.MainViewModel
import com.aya.games.presentation.utils.Constants
import com.aya.games.presentation.utils.ContextUtils
import com.aya.games.presentation.utils.SharedPrefsHelper
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel : MainViewModel
    var sharedPrefsHelper : SharedPrefsHelper? = null

    val TAG = "MainActivity11"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "onCreate: ")
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        sharedPrefsHelper = SharedPrefsHelper(this)

        changeLang()

    }

    fun changeLang(){
        val localeToSwitchTo = Locale("ar")
        sharedPrefsHelper!!.setValue(Constants.LANGUAGE, "ar")
        ContextUtils.updateLocale(this, localeToSwitchTo)

    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}