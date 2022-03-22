package com.aya.games.presentation.utils

import com.aya.games.presentation.ui.interfaces.OnClickMain

lateinit var showOrHide : OnClickMain

fun setRefrenceHiddenHome(NewShowOrHide : OnClickMain) {
    showOrHide = NewShowOrHide
}

fun getRefrenceHiddenHome():OnClickMain{
    return showOrHide
}
