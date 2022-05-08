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
 * Class do : GameTwoViewModel
 */
class SubGameThreeViewModel(application: Application) : AndroidViewModel(application) {

    var requestLiveData = MutableLiveData<Any>()
     var lookCategoryGames : ArrayList<ListenGames> = arrayListOf()

     // Initialize Firebase store
     var db: FirebaseFirestore = FirebaseFirestore.getInstance()

      fun getListItems(id :String) {

          val docRef = db.collection("listen").document(id).collection("1")

         docRef.get()
               .addOnSuccessListener( OnSuccessListener<QuerySnapshot>() {
                    if(it.isEmpty)  Log.d(TAG, "onSuccess: LIST EMPTY");
                    else{
                      val   size =it.documents.size
                      var   list_id : MutableList<DocumentSnapshot> =  it.documents
                        lookCategoryGames.clear()
                       repeat(size){
                          val document = list_id.get(it).data
                          var data  = ListenGames()

                           data.id = document!!.get("id").toString()
                           data.question =  document.get("question").toString()
                           data.question_sound =  document.get("question_sound").toString()
                           data.sound = document.get("sound") as ArrayList<String>
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