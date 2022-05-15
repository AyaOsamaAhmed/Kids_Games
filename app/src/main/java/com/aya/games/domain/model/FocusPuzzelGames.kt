package com.aya.games.domain.model

data class FocusPuzzelGames(
    var id : String? = null,
    var image: String? = null,
    var question: String? = null,
    var question_sound: String? = null,
    var list_images : ArrayList<String>? = null,
    var list_ans_images : ArrayList<String>? = null

    )