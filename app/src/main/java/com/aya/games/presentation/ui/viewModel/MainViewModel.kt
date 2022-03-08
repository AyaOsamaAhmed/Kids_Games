package com.aya.games.presentation.ui.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


/**
 * Created by ( Eng Aya Osama )
 * Class do : MainViewModel
 */
class MainViewModel(application: Application) : AndroidViewModel(application) {

     private var requestLiveData = MutableLiveData<Any>()
/*
     fun  getListTitle() {
          for (i : Int in list_type.indices ){

               val doc_id = list_type[i]
               val docRef : DocumentReference = db.collection ("listings").document(doc_id)
               docRef.get().addOnCompleteListener(object : OnCompleteListener<DocumentSnapshot> {
                    override fun onComplete(task: Task<DocumentSnapshot?>) {
                         if (task.isSuccessful()) {
                              val document: DocumentSnapshot = task.getResult()!!
                              if (document.exists()) {
                                   Log.d(ContentValues.TAG, "DocumentSnapshot data: " + document.data!!.get("title").toString())
                                   val list_type :List_model = List_model()
                                   list_type.title = document.data!!.get("title").toString()
                                   list_type.id = document.data!!.get("id").toString()
                                   list_type.description = document.data!!.get("description").toString()
                                   list_type.price =document.data!!.get("price").toString()

                                   list_type_title.add(list_type)
                                   setRecyclerList(list_type_title)
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