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

      /*  val A = arrayOf(1,8,2,0,-1)
        var arrang_arr = arrayListOf<Int>()
        for(num in A){
            if(num > 0){
                if(!arrang_arr.contains(num))
                    arrang_arr.add(num)
            }
        }
        arrang_arr.sort()
        var message = "Codility We test coders"
        var new_words = ""
        var index_last_space = 0
        var k : Int = 140
        // write your code in Kotlin 1.3.11 (Linux)
        var new_words :String
        var words = message
        var index_last_space = 0
        var new_length = K
        if(K > message.length){
            words += " "
            new_length = words.length

        }
        repeat(new_length){
            //  new_words += message[it]

            if(words[it].equals(' '))
                index_last_space = it
            if(it+1 < words.length)
                if(words[it+1].equals(' '))
                    index_last_space = it+1
        }
        new_words= words.subSequence(0,index_last_space).toString()
        return new_words


        var inputArray  = arrayListOf(3, 6, -2, -5, 7, 3)
        var result : Int = 0
        var check_num = 0
        var num_id = 0
        for ((index,num)in inputArray.withIndex() ){
            num_id = index
            if(check_num < num ){
                check_num = num
               result =  inputArray[5]
            }
        } */


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