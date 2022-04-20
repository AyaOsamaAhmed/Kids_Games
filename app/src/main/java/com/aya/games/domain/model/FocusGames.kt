package com.aya.games.domain.model

data class FocusGames(
    var id : String? = null,
    var images: ArrayList<String>? = null,
    var question:String? = null ,
    var question_sound:String? = null ,
    var check_image:String? = null ,
    var answer :String? = null

)