package com.aya.games.presentation.ui.viewModel

import android.app.Application
import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.aya.games.domain.model.General
import com.google.firebase.firestore.FirebaseFirestore
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot

/**
 * Created by ( Eng Aya Osama )
 * Class do : General
 */
class GeneralViewModel(application: Application) : AndroidViewModel(application) {

     var requestLiveData = MutableLiveData<Any>()

      val TAG = "GeneralViewModel"
     // Initialize Firebase store
     var db: FirebaseFirestore  = FirebaseFirestore.getInstance()

     fun getGeneralItems() {
          db.collection("general").get()
               .addOnSuccessListener( OnSuccessListener<QuerySnapshot>() {
                    if(it.isEmpty)  Log.d(ContentValues.TAG, "onSuccess: LIST EMPTY");
                    else{
                         val   size =it.documents.size
                         var   list_id : MutableList<DocumentSnapshot> =  it.documents
                         repeat(size){
                              val document = list_id.get(it).data
                              var data = General()
                                   data.background_auth = document!!.get("background_auth").toString()
                                   data.background_main = document!!.get("background_main").toString()
                                   data.sound = document!!.get("sound").toString()
                                   data.game_talk = document!!.get("game_talk").toString()
                                   data.correct_answer = document!!.get("correct_answer").toString()
                                   data.wrong_answer = document!!.get("wrong_answer").toString()
                                   data.game_listen = document!!.get("game_listen").toString()
                                   data.game_memory = document!!.get("game_memory").toString()
                                   data.game_look = document.get("game_look").toString()
                              requestLiveData.value = data
                         }

                         Log.d(ContentValues.TAG, "onSuccess: $size")
                    }

               })
               .addOnFailureListener {
                    Log.d(ContentValues.TAG, "onFailure: $it")
               };
     }



}