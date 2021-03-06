package com.aya.games.presentation.ui.fragments.games.memoryGames

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
import com.aya.games.domain.model.General
import com.aya.games.presentation.ui.viewModel.SubGameFourViewModel
import com.google.gson.Gson
import kotlin.collections.ArrayList
import android.os.CountDownTimer
import com.aya.games.databinding.FragmentSubGameFourMemoryBinding
import com.aya.games.domain.model.MemoryGamesPizzel
import com.aya.games.presentation.ui.adapter.AdapterSubGameFourData
import com.aya.games.presentation.ui.adapter.AdapterSubGameFourMemory
import com.aya.games.presentation.ui.adapter.AdapterSubGameFourPizzel
import com.aya.games.presentation.ui.interfaces.OnClickSubGameFourMemory
import com.aya.games.presentation.utils.*
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*


class SubGameFourMemoryFragment :Fragment() , OnClickSubGameFourMemory {

    private lateinit var binding: FragmentSubGameFourMemoryBinding
    private lateinit var viewModel : SubGameFourViewModel

    private val navController by lazy {
        val navHostFragment = activity?.supportFragmentManager
            ?.findFragmentById(R.id.homeframlayout) as NavHostFragment

        navHostFragment.navController
    }

    val mainActivity  by lazy { activity }
    var sharedPrefsHelper : SharedPrefsHelper? = null
   lateinit var data : ArrayList<MemoryGamesPizzel>
    var arrange_data : ArrayList<String> = ArrayList()
    lateinit var background : General
    val onclick : OnClickSubGameFourMemory = this
    var num_game = 0
    var trying : Boolean = false
    var arrange_image :ArrayList<Int> = ArrayList<Int>()
    var result_arrange_image :ArrayList<Int> = ArrayList<Int>()
    var resultData : AdapterSubGameFourData = AdapterSubGameFourData()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentSubGameFourMemoryBinding.inflate(inflater , container , false)
        viewModel = ViewModelProvider(this).get(SubGameFourViewModel::class.java)
        sharedPrefsHelper = SharedPrefsHelper(mainActivity!!.applicationContext)

        setGeneral()
        val id = arguments?.getString("category")

        if(id != null)
        viewModel.getListMemory(id)

        binding.progressGame.visibility =  View.VISIBLE

        viewModel.requestMemoryLiveData.observe(viewLifecycleOwner, Observer {
             data = it as ArrayList<MemoryGamesPizzel>
            repeat(data[num_game].image!!.size){
                result_arrange_image.add(it)
            }

            getCurrentQuestion(num_game , trying)
        })

        clickable()

        return binding.root
    }

    private fun getCurrentQuestion(num:Int , secondTry : Boolean){
        pauseSound()
        binding.result.visibility = View.GONE
        binding.question.visibility = View.GONE
        startSound(background.background_look_good!!)

        binding.image.visibility = View.VISIBLE
        binding.timer.visibility = View.VISIBLE
        binding.game.visibility = View.INVISIBLE
        binding.resultData.visibility = View.INVISIBLE
        binding.progressGame.visibility =View.INVISIBLE
        binding.reload.visibility = View.INVISIBLE

        resultData.clearList()
        arrange_image.clear()
        arrange_data.clear()
        repeat(data[num_game].image!!.size){
            arrange_data.add(data[num_game].image!![it])
        }
        showGames(arrange_data, data[num_game].question!!)

        //handel time
        if(secondTry)
            timeDown( data[num].time!!.toLong() /2)
            else
        timeDown( data[num].time!!.toLong())

        //back button
        if(num == 0)
            binding.back.visibility = View.GONE
        else
            binding.back.visibility = View.VISIBLE

        //next button
        if(num == data.size - 1 )
            binding.next.visibility = View.GONE
        else
            binding.next.visibility = View.VISIBLE
    }

    private fun showGames(  data : ArrayList<String> , question : String) {
        binding.question.text = question

        val  adapterView = AdapterSubGameFourMemory(data)
        binding.image.adapter = adapterView

    }

    private fun setGeneral() {
         background = Gson().fromJson(sharedPrefsHelper?.getStringValue(Constants.GENERAL), General::class.java)
        // loading image
        binding.progress.visibility = View.VISIBLE
        binding.layout.setGlideImageUrl(background.game_look!!,binding.progress)

    }

    fun clickable(){
        binding.backHome.setOnClickListener {
           skip()
        }
        binding.back.setOnClickListener {
            trying = false
            getCurrentQuestion(--num_game, trying)
        }
        binding.question.setOnClickListener {
            startSound(data[num_game].question_sound!!)
        }
        binding.next.setOnClickListener {
            trying = false
            getCurrentQuestion(++num_game , trying)
        }
        binding.reload.setOnClickListener {
            trying = true
            getCurrentQuestion(num_game , trying)
        }
    }

    fun skip(){
        navController.navigate(R.id.SubGameFourMemoryFragment_to_GameFourFragment)
    }

    override fun onClickChooseGames(id: Int , image : String) {
        pauseSound()
        val size = data[num_game].image!!.size

        resultData.addNewImage(image)
        binding.resultData.adapter = resultData
        binding.resultData.visibility = View.VISIBLE
        binding.reload.visibility = View.VISIBLE
        arrange_image.add(id)

        if(resultData.checkSize() == size){
           checkData()
        }
    }

    fun checkData(){

        if(result_arrange_image.equals(arrange_image)){
        show_result(true)
    }else{
        show_result(false)
            trying = true
        Handler(Looper.getMainLooper()).postDelayed({
            getCurrentQuestion(num_game,trying)
        }, 3000)

    }
    }

    fun show_result(result : Boolean){
        pauseSound()
        binding.result.visibility = View.VISIBLE

        if(result) {
            binding.result.setAnimation("correct_answer.json")
            startSound(background.correct_answer!!)
        }
        else {
            binding.result.setAnimation("wrong_answer.json")
            startSound(background.wrong_answer!!)
        }
        binding.result.playAnimation()
        Handler(Looper.getMainLooper()).postDelayed({
            binding.result.visibility = View.GONE
        }, 2000)

    }


    fun timeDown(time:Long){
        binding.next.isEnabled = false
        binding.back.isEnabled = false
        // countdown Interveal is 1sec = 1000 I have used
        object : CountDownTimer(time, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                // Used for formatting digit to be in 2 digits only
                val f: NumberFormat = DecimalFormat("00")
                val hour = millisUntilFinished / 3600000 % 24
                val min = millisUntilFinished / 60000 % 60
                val sec = millisUntilFinished / 1000 % 60
                binding.timer.text =  f.format(sec)
            }

            // When the task is over it will print 00:00:00 there
            override fun onFinish() {
                binding.timer.text = "00"
                binding.image.visibility = View.INVISIBLE
                binding.timer.visibility = View.INVISIBLE
                binding.game.visibility = View.VISIBLE

                binding.next.isEnabled = true
                binding.back.isEnabled = true
                binding.question.visibility = View.VISIBLE
                startSound(data[num_game].question_sound!!)

                // loading image
                val drawable = getResources().getDrawable(R.drawable.border_green_check)
                val memory = arrange_data

                Collections.shuffle(memory, Random())
                var indexs = ArrayList<Int>()
                for(i in memory){
                    var pos = 0
                    for (y in data.get(num_game).image!!){
                        if(i == y){
                            indexs.add(pos)
                        }
                        pos++
                    }

                }
                val adapterPizzel = AdapterSubGameFourPizzel(memory,indexs, onclick,drawable)
                binding.game.adapter = adapterPizzel

            }
        }.start()
    }

    override fun onPause() {
        pauseSound()
        super.onPause()
    }
}