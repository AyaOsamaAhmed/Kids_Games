package com.aya.games.domain.model

data class TalkGames(
    var id : String? = null,
    var image: String? = null,
    var question : ArrayList<String>? = null,
    var answer : ArrayList<String>?  = null

)