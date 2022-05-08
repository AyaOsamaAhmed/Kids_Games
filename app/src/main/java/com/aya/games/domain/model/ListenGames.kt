package com.aya.games.domain.model

data class ListenGames(
    var id : String? = null,
    var sound : ArrayList<String>? = null,
    var question:String? = null ,
    var question_sound : String? = null,
    var answer :String? = null

)