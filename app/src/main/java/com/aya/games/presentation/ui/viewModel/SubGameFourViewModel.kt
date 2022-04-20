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

    // remember
    var requestRememberLiveData = MutableLiveData<Any>()
    var RememberGames : ArrayList<MemoryGamesRemember> = arrayListOf()

    // rememberPhase
    var requestRememberPhaseLiveData = MutableLiveData<Any>()
    var RememberPhaseGames : ArrayList<MemoryGamesRememberPhase> = arrayListOf()


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
                        lookCategoryGames.clear()
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
                    lookCategoryGames.clear()
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
                    MemoryGames.clear()
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

    fun getListRemember(id :String) {

        val docRef = db.collection("memory").document(id).collection("1")

        docRef.get()
            .addOnSuccessListener( OnSuccessListener<QuerySnapshot>() {
                if(it.isEmpty)  Log.d(TAG, "onSuccess: LIST EMPTY");
                else{
                    val   size =it.documents.size
                    var   list_id : MutableList<DocumentSnapshot> =  it.documents
                    RememberGames.clear()
                    repeat(size){
                        val document = list_id.get(it).data
                        var data  = MemoryGamesRemember()

                        data.id = document!!.get("id").toString()
                        data.name =  document.get("name").toString()
                        data.image  = document.get("image").toString()
                        data.num = document.get("num").toString().toInt()

                        RememberGames.add(data)
                    }
                    requestRememberLiveData.value = RememberGames
                    Log.d(TAG, "onSuccess: $size")
                }

            })
            .addOnFailureListener {
                Log.d(TAG, "onFailure: $it")
            };
    }

    fun getListRememberPhase(id :String , phase : String ) {

        val docRef = db.collection("memory").document(id).collection("1").document(phase).collection("1")

        docRef.get()
            .addOnSuccessListener( OnSuccessListener<QuerySnapshot>() {
                if(it.isEmpty)  Log.d(TAG, "onSuccess: LIST EMPTY");
                else{
                    val   size =it.documents.size
                    var   list_id : MutableList<DocumentSnapshot> =  it.documents
                    RememberPhaseGames.clear()
                    repeat(size){
                        val document = list_id.get(it).data
                        var data  = MemoryGamesRememberPhase()

                        data.id = document!!.get("id").toString()
                        data.images  = document.get("images") as ArrayList<String>
                        data.answer  = document.get("answer") as ArrayList<String>

                        RememberPhaseGames.add(data)
                    }
                    requestRememberPhaseLiveData.value = RememberPhaseGames
                    Log.d(TAG, "onSuccess: $size")
                }

            })
            .addOnFailureListener {
                Log.d(TAG, "onFailure: $it")
            };
    }
}