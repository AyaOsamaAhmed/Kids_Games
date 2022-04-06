package com.aya.games.domain.model

data class MemoryGamesPizzel(
    var id : String? = null,
    var image : ArrayList<String>? = null,
    var question:String? = null ,
    var answer :String? = null,
    var time :String? = null,
    var choose : ArrayList<String>? = null

)