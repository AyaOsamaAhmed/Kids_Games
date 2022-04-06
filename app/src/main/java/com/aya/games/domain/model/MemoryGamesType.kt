package com.aya.games.domain.model

data class MemoryGamesType(
    var id : String? = null,
    var image : String? = null,
    var name : String? = null,
    var question:String? = null ,
    var answer :String? = null,
    var letter:String? = null,
    var time :String? = null,
    var choose : ArrayList<String>? = null

)