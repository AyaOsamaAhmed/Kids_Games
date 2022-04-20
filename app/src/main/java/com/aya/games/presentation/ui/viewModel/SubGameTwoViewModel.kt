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
import com.aya.games.domain.model.LookCategoryGames
import com.aya.games.domain.model.LookGames
import com.google.firebase.firestore.DocumentReference
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by ( Eng Aya Osama )
 * Class do : GameTwoViewModel
 */
class SubGameTwoViewModel(application: Application) : AndroidViewModel(application) {

    var requestLiveData = MutableLiveData<Any>()
     var lookCategoryGames : ArrayList<LookGames> = arrayListOf()

     // Initialize Firebase store
     var db: FirebaseFirestore = FirebaseFirestore.getInstance()

      fun getListItems(id :String) {

          val docRef = db.collection("look").document(id).collection("1")

         docRef.get()
               .addOnSuccessListener( OnSuccessListener<QuerySnapshot>() {
                    if(it.isEmpty)  Log.d(TAG, "onSuccess: LIST EMPTY");
                    else{
                      val   size =it.documents.size
                      var   list_id : MutableList<DocumentSnapshot> =  it.documents
                        lookCategoryGames.clear()
                       repeat(size){
                          val document = list_id.get(it).data
                          var data  = LookGames()

                           data.id = document!!.get("id").toString()
                           data.question =  document.get("question").toString()
                           data.images = document.get("images") as ArrayList<String>
                           data.answer = document.get("answer").toString()

                           lookCategoryGames.add(data)
                         }
                        requestLiveData.value = lookCategoryGames
                         Log.d(TAG, "onSuccess: $size")
                    }

               })
               .addOnFailureListener {
                    Log.d(TAG, "onFailure: $it")
               };
               }
}