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
                                   data.background_letter_play = document!!.get("background_letter_play").toString()
                                   data.background_listen_look = document!!.get("background_listen_look").toString()
                                   data.background_look_good = document!!.get("background_look_good").toString()
                                   data.puzzel_excellent = document!!.get("puzzel_excellent").toString()
                                   data.sound = document!!.get("sound").toString()
                                   data.game_talk = document!!.get("game_talk").toString()
                                   data.correct_answer = document!!.get("correct_answer").toString()
                                   data.wrong_answer = document!!.get("wrong_answer").toString()
                                   data.game_listen = document!!.get("game_listen").toString()
                                   data.game_memory = document!!.get("game_memory").toString()
                                   data.game_look = document.get("game_look").toString()
                                   data.background_question_remember = document.get("background_question_remember").toString()
                                   data.background_remember = document.get("background_remember").toString()
                                   data.background_focus = document.get("background_focus").toString()
                                   data.puzzel_background_ans = document.get("puzzel_background_ans").toString()
                                   data.puzzel_background_hint = document.get("puzzel_background_hint").toString()

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