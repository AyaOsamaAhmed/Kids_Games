package com.aya.games.presentation.ui.viewModel

import android.app.Application
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.speech.RecognizerIntent
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import android.speech.tts.TextToSpeech
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.viewModelScope
import com.aya.games.domain.model.*
import com.google.firebase.firestore.DocumentReference
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by ( Eng Aya Osama )
 * Class do : GameFiveViewModel
 */
class GameSixViewModel(application: Application) : AndroidViewModel(application) {

    var requestLiveData = MutableLiveData<Any>()
     var FocusCategoryGames : ArrayList<ListenLookCategoryGames> = arrayListOf()

     // Initialize Firebase store
     var db: FirebaseFirestore = FirebaseFirestore.getInstance()

      fun getListItems() {

          db.collection("focus").get()
               .addOnSuccessListener( OnSuccessListener<QuerySnapshot>() {
                    if(it.isEmpty)  Log.d(TAG, "onSuccess: LIST EMPTY");
                    else{
                      val   size =it.documents.size
                      var   list_id : MutableList<DocumentSnapshot> =  it.documents
                        FocusCategoryGames.clear()
                       repeat(size){
                              val document = list_id.get(it).data
                              var data  = ListenLookCategoryGames()

                                data.id = document!!.get("id").toString()
                                data.image = document.get("image").toString()
                                data.name_ar = document.get("name_ar").toString()

                           FocusCategoryGames.add(data)
                         }
                        requestLiveData.value = FocusCategoryGames
                         Log.d(TAG, "onSuccess: $size")
                    }

               })
               .addOnFailureListener {
                    Log.d(TAG, "onFailure: $it")
               };
               }
}