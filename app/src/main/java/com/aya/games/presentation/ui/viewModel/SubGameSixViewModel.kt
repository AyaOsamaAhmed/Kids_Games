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
 * Class do : GameFiveViewModel
 */
class SubGameSixViewModel(application: Application) : AndroidViewModel(application) {

    var requestLiveData = MutableLiveData<Any>()
     var FocusGames : ArrayList<FocusGames> = arrayListOf()

    var requestCategoryLiveData = MutableLiveData<Any>()
    var PuzzelCategoryGames : ArrayList<FocusCategoryGames> = arrayListOf()

    var requestpuzzelLiveData = MutableLiveData<Any>()
    var PuzzelGames : ArrayList<FocusPuzzelGames> = arrayListOf()

    var requestDiffLiveData = MutableLiveData<Any>()
    var DiffGames : ArrayList<FocusDiffGames> = arrayListOf()


    // Initialize Firebase store
     var db: FirebaseFirestore = FirebaseFirestore.getInstance()

      fun getListItems(id :String) {

          val docRef = db.collection("focus").document(id).collection("1")

         docRef.get()
               .addOnSuccessListener( OnSuccessListener<QuerySnapshot>() {
                    if(it.isEmpty)  Log.d(TAG, "onSuccess: LIST EMPTY");
                    else{
                      val   size =it.documents.size
                      var   list_id : MutableList<DocumentSnapshot> =  it.documents
                        FocusGames.clear()
                       repeat(size){
                          val document = list_id.get(it).data
                          var data  = FocusGames()

                           data.id = document!!.get("id").toString()
                           data.question =  document.get("question").toString()
                           data.question_sound =  document.get("question_sound").toString()
                           data.check_image =  document.get("check_image").toString()
                           data.images = document.get("images") as ArrayList<String>
                           data.answer = document.get("answer").toString()

                           FocusGames.add(data)
                         }
                        requestLiveData.value = FocusGames
                         Log.d(TAG, "onSuccess: $size")
                    }

               })
               .addOnFailureListener {
                    Log.d(TAG, "onFailure: $it")
               };
               }


    fun getListCategoryPuzzelItems( id :String) {

        val docRef = db.collection("focus").document(id).collection("1")

        docRef.get()
            .addOnSuccessListener( OnSuccessListener<QuerySnapshot>() {
                if(it.isEmpty)  Log.d(TAG, "onSuccess: LIST EMPTY");
                else{
                    val   size =it.documents.size
                    var   list_id : MutableList<DocumentSnapshot> =  it.documents
                    PuzzelCategoryGames.clear()
                    repeat(size){
                        val document = list_id.get(it).data
                        var data  = FocusCategoryGames()

                        data.id = document!!.get("id").toString()
                        data.name_ar =  document.get("name").toString()
                        data.image =  document.get("image").toString()

                        PuzzelCategoryGames.add(data)
                    }
                    requestCategoryLiveData.value = PuzzelCategoryGames
                    Log.d(TAG, "onSuccess: $size")
                }

            })
            .addOnFailureListener {
                Log.d(TAG, "onFailure: $it")
            };
    }


    fun getPuzzelItems(category:String ,id :String) {

        val docRef = db.collection("focus").document(category).collection("1").document(id).collection("1")

        docRef.get()
            .addOnSuccessListener( OnSuccessListener<QuerySnapshot>() {
                if(it.isEmpty)  Log.d(TAG, "onSuccess: LIST EMPTY");
                else{
                    val   size =it.documents.size
                    var   list_id : MutableList<DocumentSnapshot> =  it.documents
                    PuzzelGames.clear()
                    repeat(size){
                        val document = list_id.get(it).data
                        var data  = FocusPuzzelGames()

                        data.id = document!!.get("id").toString()
                        data.image =  document.get("image").toString()
                        data.list_images =  document.get("list_images") as ArrayList<String>
                        data.list_ans_images =  document.get("list_ans_images") as ArrayList<String>

                        PuzzelGames.add(data)
                    }
                    requestpuzzelLiveData.value = PuzzelGames
                    Log.d(TAG, "onSuccess: $size")
                }

            })
            .addOnFailureListener {
                Log.d(TAG, "onFailure: $it")
            };
    }


    fun getListDiffItems( id :String) {

        val docRef = db.collection("focus").document(id).collection("1")

        docRef.get()
            .addOnSuccessListener( OnSuccessListener<QuerySnapshot>() {
                if(it.isEmpty)  Log.d(TAG, "onSuccess: LIST EMPTY");
                else{
                    val   size =it.documents.size
                    var   list_id : MutableList<DocumentSnapshot> =  it.documents
                    DiffGames.clear()
                    repeat(size){
                        val document = list_id.get(it).data
                        var data  = FocusDiffGames()

                        data.id = document!!.get("id").toString()
                        data.question = document!!.get("question").toString()
                        data.question_sound = document!!.get("question_sound").toString()
                        data.list =  document.get("list") as ArrayList<String>
                        data.background =  document.get("background").toString()
                        data.list_image =  document.get("list_image") as ArrayList<String>

                        DiffGames.add(data)
                    }
                    requestDiffLiveData.value = DiffGames
                    Log.d(TAG, "onSuccess: $size")
                }

            })
            .addOnFailureListener {
                Log.d(TAG, "onFailure: $it")
            };
    }

}