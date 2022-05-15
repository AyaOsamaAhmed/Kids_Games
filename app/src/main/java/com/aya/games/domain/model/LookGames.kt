package com.aya.games.domain.model

data class LookGames(
    var id : String? = null,
    var images: ArrayList<String>? = null,
    var question:String? = null ,
    var question_sound:String? = null ,
    var answer :String? = null

)