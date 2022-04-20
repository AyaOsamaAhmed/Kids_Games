package com.aya.games.presentation.ui.viewModel

import android.app.Application
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.speech.RecognizerIntent
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.aya.games.domain.model.Home
import com.aya.games.domain.model.TalkGames
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import android.speech.tts.TextToSpeech
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.viewModelScope
import com.aya.games.domain.model.LookGames
import com.aya.games.domain.model.TalkCategoryGames
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by ( Eng Aya Osama )
 * Class do : GameOneViewModel
 */
class SubGameOneViewModel(application: Application) : AndroidViewModel(application) {

    var requestLiveData = MutableLiveData<Any>()
     var TalkGames : ArrayList<TalkGames> = arrayListOf()
    private lateinit var textToSpeechEngine: TextToSpeech
    private lateinit var startForResult: ActivityResultLauncher<Intent>



     // Initialize Firebase store
     var db: FirebaseFirestore = FirebaseFirestore.getInstance()


    fun initial(
        engine: TextToSpeech, launcher: ActivityResultLauncher<Intent>
    ) = viewModelScope.launch {
        textToSpeechEngine = engine
        startForResult = launcher
    }

    fun displaySpeechRecognizer() {
        startForResult.launch(Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale("in_ID"))
            putExtra(RecognizerIntent.EXTRA_PROMPT, Locale("Bicara sekarang"))
        })
    }



    fun speak(text: String , context: Context) = viewModelScope.launch{
        textToSpeechEngine.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    fun getListItems(id :String) {

        val docRef = db.collection("talk_game").document(id).collection("1")

        docRef.get()
            .addOnSuccessListener( OnSuccessListener<QuerySnapshot>() {
                if(it.isEmpty)  Log.d(TAG, "onSuccess: LIST EMPTY");
                else{
                    val   size =it.documents.size
                    var   list_id : MutableList<DocumentSnapshot> =  it.documents
                    TalkGames.clear()
                    repeat(size){
                        val document = list_id.get(it).data
                        var data  = TalkGames()

                        data.id = document!!.get("id").toString()
                        data.question =  document.get("question") as ArrayList<String>
                        data.image = document.get("image").toString()
                        data.answer = document.get("answer") as ArrayList<String>

                        TalkGames.add(data)
                    }
                    requestLiveData.value = TalkGames
                    Log.d(TAG, "onSuccess: $size")
                }

            })
            .addOnFailureListener {
                Log.d(TAG, "onFailure: $it")
            };
    }
}