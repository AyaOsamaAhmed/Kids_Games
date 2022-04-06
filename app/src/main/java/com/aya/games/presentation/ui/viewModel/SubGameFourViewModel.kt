package com.aya.games.presentation.ui.viewModel

import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.aya.games.domain.model.*
import kotlin.collections.ArrayList

/**
 * Created by ( Eng Aya Osama )
 * Class do : SubGameFourViewModel
 */
class SubGameFourViewModel(application: Application) : AndroidViewModel(application) {

    var requestLiveData = MutableLiveData<Any>()
     var lookCategoryGames : ArrayList<MemoryGames> = arrayListOf()

    // type
    var requestTypeLiveData = MutableLiveData<Any>()
    var TypeGames : ArrayList<MemoryGamesType> = arrayListOf()

    // memory
    var requestMemoryLiveData = MutableLiveData<Any>()
    var MemoryGames : ArrayList<MemoryGamesPizzel> = arrayListOf()


    // Initialize Firebase store
     var db: FirebaseFirestore = FirebaseFirestore.getInstance()

      fun getListItems(id :String) {

          val docRef = db.collection("memory").document(id).collection("1")

         docRef.get()
               .addOnSuccessListener( OnSuccessListener<QuerySnapshot>() {
                    if(it.isEmpty)  Log.d(TAG, "onSuccess: LIST EMPTY");
                    else{
                      val   size =it.documents.size
                      var   list_id : MutableList<DocumentSnapshot> =  it.documents
                       repeat(size){
                          val document = list_id.get(it).data
                          var data  = MemoryGames()

                           data.id = document!!.get("id").toString()
                           data.question =  document.get("question").toString()
                           data.image  = document.get("image").toString()
                           data.time  = document.get("time").toString()
                           data.choose = document.get("choose") as ArrayList<String>
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

    fun getGamesTypeItems(id :String) {

        val docRef = db.collection("memory").document(id).collection("1")

        docRef.get()
            .addOnSuccessListener( OnSuccessListener<QuerySnapshot>() {
                if(it.isEmpty)  Log.d(TAG, "onSuccess: LIST EMPTY");
                else{
                    val   size =it.documents.size
                    var   list_id : MutableList<DocumentSnapshot> =  it.documents
                    repeat(size){
                        val document = list_id.get(it).data
                        var data  = MemoryGamesType()

                        data.id = document!!.get("id").toString()
                        data.question =  document.get("question").toString()
                        data.image  = document.get("image").toString()
                        data.letter  = document.get("letter").toString()
                        data.name  = document.get("name").toString()
                        data.time  = document.get("time").toString()
                        data.choose = document.get("choose") as ArrayList<String>
                        data.answer = document.get("answer").toString()

                        TypeGames.add(data)
                    }
                    requestTypeLiveData.value = TypeGames
                    Log.d(TAG, "onSuccess: $size")
                }

            })
            .addOnFailureListener {
                Log.d(TAG, "onFailure: $it")
            };
    }

    fun getListMemory(id :String) {

        val docRef = db.collection("memory").document(id).collection("1")

        docRef.get()
            .addOnSuccessListener( OnSuccessListener<QuerySnapshot>() {
                if(it.isEmpty)  Log.d(TAG, "onSuccess: LIST EMPTY");
                else{
                    val   size =it.documents.size
                    var   list_id : MutableList<DocumentSnapshot> =  it.documents
                    repeat(size){
                        val document = list_id.get(it).data
                        var data  = MemoryGamesPizzel()

                        data.id = document!!.get("id").toString()
                        data.question =  document.get("question").toString()
                        data.image  = document.get("image") as ArrayList<String>
                        data.time  = document.get("time").toString()
                     //   data.choose = document.get("choose") as ArrayList<String>
                     //   data.answer = document.get("answer").toString()

                        MemoryGames.add(data)
                    }
                    requestMemoryLiveData.value = MemoryGames
                    Log.d(TAG, "onSuccess: $size")
                }

            })
            .addOnFailureListener {
                Log.d(TAG, "onFailure: $it")
            };
    }

}