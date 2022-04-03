package com.aya.games.domain.model

data class MemoryGames(
    var id : String? = null,
    var image : String? = null,
    var question:String? = null ,
    var answer :String? = null,
    var time :String? = null,
    var choose : ArrayList<String>? = null

)