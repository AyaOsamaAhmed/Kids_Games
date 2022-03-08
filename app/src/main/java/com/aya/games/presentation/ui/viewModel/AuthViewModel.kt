package com.aya.games.presentation.ui.viewModel

import android.app.Activity
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.aya.games.domain.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.ActionCodeSettings
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import java.util.*


/**
 * Created by ( Eng Aya Osama )
 * Class do : AuthViewModel
 * Date 08/03/2022 - 11:23 AM
 */
//https://droidmentor.com/password-less-email-firebase-auth/
//https://stackoverflow.com/questions/56771366/authenticate-with-firebase-using-email-link-in-android

class AuthViewModel(application: Application) : AndroidViewModel(application) {

     var forgetPasswordData = MutableLiveData<Any>()
     var loginData = MutableLiveData<Any>()
     var registrationData = MutableLiveData<Any>()
     var userInfo = MutableLiveData<User>()
     var logoutData = MutableLiveData<Any>()

     private var auth: FirebaseAuth = Firebase.auth
     val TAG = "AuthViewModel"
     // Initialize Firebase Auth
     var db: FirebaseFirestore  = FirebaseFirestore.getInstance()


     fun  login (txtEmail: String, txtPassword: String){
          auth.signInWithEmailAndPassword(
               txtEmail,//.replace(" ", "")
               txtPassword
          )
               .addOnCompleteListener(Activity()) { task ->
                    if (task.isSuccessful) {
                         // Sign in success, update UI with the signed-in user's information
                         Log.d(TAG, "signInWithEmail:success")
                         val user = auth.currentUser
                         loginData.value = true
                    } else {
                         // If sign in fails, display a message to the user.
                         Log.w(TAG, "signInWithEmail:failure", task.exception)//(task.exception as FirebaseAuthInvalidCredentialsException).errorCode
                         //Toast.makeText(context, (task.exception as FirebaseAuthInvalidCredentialsException).errorCode, Toast.LENGTH_SHORT).show()
                    }
               }
     }


     fun registration(txtFirstName: String,txtSecondName: String,txtEmail: String, txtPassword: String ) {

          auth.createUserWithEmailAndPassword(txtEmail, txtPassword)
               .addOnCompleteListener(Activity()) { task ->
                    if (task.isSuccessful) {
                         // Sign in success, update UI with the signed-in user's information
                         Log.d(TAG, "createUserWithEmail:success")
                         val user = auth.currentUser
                         val UserId = auth.currentUser!!.uid
                           saveFirebasestore(UserId,txtFirstName,txtSecondName,txtEmail)
                         // saveData(UserId)
                    } else {
                         // If sign in fails, display a message to the user.
                         Log.w(TAG, "createUserWithEmail:failure", task.exception)
                         registrationData.value = false
                    }
               }

     }

     private fun saveFirebasestore(userId:String,txtFirstName: String,txtSecondName: String,txtEmail: String) {

          val user = hashMapOf(
               "email" to txtEmail,
               "firstName" to txtFirstName,
               "id" to userId,
               "lastName" to txtSecondName
          )

          db.collection("users").document(userId)
               .set(user as Map<String, Any>)
               .addOnSuccessListener {
                    Log.d(TAG, "Add User successfully written!")
                  registrationData.value = true

               }
               .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
     }

     fun forgetPassword(emailID: String) {
          val actionCodeSettings = ActionCodeSettings.newBuilder()
               .setUrl("https://yousimed.com/register")// https://yousimed.page.link/dmCn
               .setHandleCodeInApp(true)
               .setAndroidPackageName("com.scobrea.yousimed", false, null)
               .build();

          auth.sendSignInLinkToEmail(emailID, actionCodeSettings)
               .addOnCompleteListener(OnCompleteListener<Void?> { task ->
                    Log.d(TAG, "onComplete: ")
                    if (task.isSuccessful) {
                         Log.d(TAG, "Email sent.")
                         forgetPasswordData.value = true
                    } else {
                         Objects.requireNonNull(task.exception)?.printStackTrace()
                    }
               })

     }

     fun checkLogin():Boolean{
          return auth.currentUser  != null
     }

     fun updateProfile(first_name:String , last_name:String ) :Boolean{
          var result_first_name  = false
          var result_second_name  = false

          val docRef : DocumentReference = db.collection ("users").document(auth.currentUser!!.uid)
          docRef.update("firstName",first_name)
               .addOnCompleteListener{
                    if(it.isSuccessful)
                         result_first_name = true
               }
          docRef.update("lastName",last_name)
               .addOnCompleteListener{
                    if(it.isSuccessful)
                         result_second_name = true
               }
          return true
     }

     fun getInfoUser(){
          val userFirbase = auth.currentUser

          val docRef : DocumentReference = db.collection ("users").document(userFirbase!!.uid)
          docRef.get().addOnCompleteListener(OnCompleteListener {
               if (it.isSuccessful()) {
                    val document: DocumentSnapshot = it.getResult()!!
                    if (document.exists()) {
                         userInfo.value = User( document.data!!.get("id").toString() , document.data!!.get("firstName").toString()
                              , document.data!!.get("lastName").toString() , document.data!!.get("email").toString())
                    }
          }})

     }
     fun reLoginWithEmail(emailLink :String){
          auth.isSignInWithEmailLink(emailLink)
     }


     fun  logout(){
          Firebase.auth.signOut()
          loginData.value = true
     }
}