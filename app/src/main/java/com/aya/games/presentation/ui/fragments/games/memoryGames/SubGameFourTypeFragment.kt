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
import com.aya.games.presentation.ui.interfaces.OnClickSubGameFour
import com.aya.games.presentation.ui.viewModel.SubGameFourViewModel
import com.google.gson.Gson
import kotlin.collections.ArrayList
import android.os.CountDownTimer
import com.aya.games.databinding.FragmentSubGameFourTypeBinding
import com.aya.games.domain.model.MemoryGamesType
import com.aya.games.presentation.ui.adapter.AdapterSubGameFourType
import java.text.DecimalFormat
import java.text.NumberFormat
import android.view.animation.RotateAnimation
import android.view.animation.Animation
import com.aya.games.presentation.utils.*


class SubGameFourTypeFragment :Fragment() , OnClickSubGameFour {

    private lateinit var binding: FragmentSubGameFourTypeBinding
    private lateinit var viewModel : SubGameFourViewModel

    private val navController by lazy {
        val navHostFragment = activity?.supportFragmentManager
            ?.findFragmentById(R.id.homeframlayout) as NavHostFragment

        navHostFragment.navController
    }

    val mainActivity  by lazy { activity }
    var sharedPrefsHelper : SharedPrefsHelper? = null
    lateinit var data : ArrayList<MemoryGamesType>
    lateinit var background : General
    var num_game = 0
    var trying : Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentSubGameFourTypeBinding.inflate(inflater , container , false)
        viewModel = ViewModelProvider(this).get(SubGameFourViewModel::class.java)
        sharedPrefsHelper = SharedPrefsHelper(mainActivity!!.applicationContext)

        setGeneral()
        val id = arguments?.getString("category")

        if(id != null)
        viewModel.getGamesTypeItems(id)

        binding.progressGame.visibility = View.VISIBLE
        viewModel.requestTypeLiveData.observe(viewLifecycleOwner, Observer {
             data = it as ArrayList<MemoryGamesType>
            getCurrentQuestion(num_game, trying)
        })

        clickable()

        return binding.root
    }

    private fun getCurrentQuestion(num:Int , secondTry : Boolean){
        pauseSound()
        binding.result.visibility = View.GONE

        startSound(background.background_look_good!!)

        binding.letter.visibility = View.VISIBLE
        binding.name.visibility = View.VISIBLE
        binding.timer.visibility = View.VISIBLE
        binding.game.visibility = View.INVISIBLE
        binding.question.visibility = View.INVISIBLE
        binding.progressGame.visibility = View.INVISIBLE

        showGames(data[num].letter!! , data[num].image!! , data[num].choose!! , data[num].name!! , data[num_game].question!!)

        //handel time
        if(secondTry)
            timeDown( data[num].time!!.toLong()/2)
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

    private fun showGames( letter : String , image : String , data : ArrayList<String> , name : String  , question : String) {
        binding.letter.setGlideImageUrl(letter,binding.progress)
        binding.name.setGlideImageUrl(name,binding.progress)
        binding.image.setGlideImageUrl(image,binding.progress)
        binding.question.text = question
        // loading image
        val drawable = getResources().getDrawable(R.drawable.border_green_check)

        val adapter = AdapterSubGameFourType(data,this,drawable,true)
        binding.game.adapter = adapter
    }

    private fun setGeneral() {
         background = Gson().fromJson(sharedPrefsHelper?.getStringValue(Constants.GENERAL), General::class.java)
        // loading image
        binding.progress.visibility = View.VISIBLE
        binding.layout.setGlideImageUrl(background.background_letter_play!!,binding.progress)
    }

    fun clickable(){
        binding.backHome.setOnClickListener {
           skip()
        }
        binding.question.setOnClickListener {
            startSound(data[num_game].question_sound!!)
        }
        binding.back.setOnClickListener {
            getCurrentQuestion(--num_game , trying)
        }
        binding.next.setOnClickListener {
            getCurrentQuestion(++num_game , trying)
        }


    }

    fun skip(){
        navController.navigate(R.id.SubGameFourTypeFragment_to_GameFourFragment)
    }

    override fun onClickChooseGames(id: String) {
        pauseSound()

        if(id == data[num_game].answer!!){
            show_result(true)
        }else{
            show_result(false)
            trying = true
            Handler(Looper.getMainLooper()).postDelayed({
                getCurrentQuestion(num_game, trying)
            }, 3000)
        }
    }

    fun show_result(result : Boolean){
        binding.result.visibility = View.VISIBLE
        pauseSound()

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
        val an: Animation = RotateAnimation(0.0f, 90.0f, 250f, 273f)
        an.setFillAfter(true);
     //   binding.barTimer.startAnimation(an);

        object : CountDownTimer(time, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                // Used for formatting digit to be in 2 digits only
                val f: NumberFormat = DecimalFormat("00")
                val min = millisUntilFinished / 60000 % 60
                val sec = millisUntilFinished / 1000 % 60
                binding.timer.text =  f.format(sec)
                val seconds: Int = (30000 / 1000).toInt()
             //   binding.barTimer.setProgress(seconds)
            }

            // When the task is over it will print 00:00:00 there
            override fun onFinish() {
                binding.timer.text = "00"
                binding.letter.visibility = View.INVISIBLE
                binding.name.visibility = View.INVISIBLE
                binding.timer.visibility = View.INVISIBLE
                binding.question.visibility = View.VISIBLE
                binding.game.visibility = View.VISIBLE
                startSound(data[num_game].question_sound!!)

                binding.next.isEnabled = true
                binding.back.isEnabled = true
            }
        }.start()
    }
}