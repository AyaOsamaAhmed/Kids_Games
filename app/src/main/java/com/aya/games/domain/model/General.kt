package com.aya.games.domain.model

data class General(
    var background_auth : String? = null,
    var background_main: String? = null,
    var sound :String? = null,
    var game_talk : String? = null,
    var correct_answer :String? = null,
    var wrong_answer :String? = null,
    //
    var game_look :String? = null,
    var game_listen : String? = null,
    var game_memory : String? = null
)