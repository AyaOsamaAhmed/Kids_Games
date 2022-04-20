package com.aya.games.presentation.ui.viewModel

import android.app.Application
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.aya.games.domain.model.Home
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.ktx.Firebase
import kotlin.properties.Delegates


/**
 * Created by ( Eng Aya Osama )
 * Class do : MainViewModel
 */
class MainViewModel(application: Application) : AndroidViewModel(application) {

    var requestLiveData = MutableLiveData<Any>()

     var lislHomeGames : ArrayList<Home> = arrayListOf()


     // Initialize Firebase store
     var db: FirebaseFirestore = FirebaseFirestore.getInstance()

      fun getListItems() {
          db.collection("home_games").get()
               .addOnSuccessListener( OnSuccessListener<QuerySnapshot>() {
                    if(it.isEmpty)  Log.d(TAG, "onSuccess: LIST EMPTY");
                    else{
                      val   size =it.documents.size
                      var   list_id : MutableList<DocumentSnapshot> =  it.documents
                        lislHomeGames.clear()
                       repeat(size){
                              val document = list_id.get(it).data
                              var data: Home = Home()
                           // Log.d(TAG, "onSuccess: LIST $size item $it");
                            //  data.title = document!!.get("name_en").toString()
                              data.title = document!!.get("name_ar").toString()
                              data.id = document!!.get("id").toString()
                              data.image = document!!.get("image").toString()

                              lislHomeGames.add(data)
                         }
                         requestLiveData.value = lislHomeGames
                         Log.d(TAG, "onSuccess: $size")
                    }

               })
               .addOnFailureListener {
                    Log.d(TAG, "onFailure: $it")
               };
               }
/*
     fun  getListGames() {
          for (i : Int in list_id.indices ){

               val doc_id = list_id[i]

          val docRef: DocumentReference = db.collection("home_games").document(doc_id)
          docRef.get().addOnCompleteListener(object : OnCompleteListener<DocumentSnapshot> {
               override fun onComplete(task: Task<DocumentSnapshot?>) {
                    if (task.isSuccessful()) {
                         val document: DocumentSnapshot = task.getResult()!!
                         if (document.exists()) {
                              Log.d(ContentValues.TAG, "DocumentSnapshot data: " + document.data!!.get("title").toString())
                              var data: Home = Home()

                              data.title = document.data!!.get("title").toString()
                              data.id = document.data!!.get("id").toString()
                              data.image = document.data!!.get("image").toString()

                              lislHomeGames.add(data)
                              requestLiveData.value = lislHomeGames

                         } else {
                              Log.d(ContentValues.TAG, "No such document")
                         }
                    } else {
                         Log.d(ContentValues.TAG, "get failed with ", task.getException())
                    }
               }
          })
     }
     }

     fun setRecyclerList(list_title:ArrayList<List_model>){
          listAdapter = ListAdapter(mContext ,list_title )
          val gridLayoutManager = GridLayoutManager(mContext, 1, GridLayoutManager.VERTICAL, false)
          binding.listRecyclerview.layoutManager = gridLayoutManager
          binding.listRecyclerview.setHasFixedSize(true)
          binding.listRecyclerview.adapter = listAdapter
     }

 */
}