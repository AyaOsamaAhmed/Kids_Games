package com.aya.games.presentation.utils

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by ( Eng Aya Osama Ahmed)
 * Class do :Constant
 */

object Constants {

    const val IS_WIZARD = "IS_WIZARD"
    const val IS_LOGIN = "IS_LOGIN"
    const val TOKEN = "TOKEN"
    const val NOTIFICATIONS = "NOTIFICATIONS"
    const val GENERAL = "GENERAL"
    const val USER_DATA = "USER_DATA"
    const val LOCATION = "LOCATION_DATA"
    const val DATA_USER_ACCESS_TOKEN = "DATA_USER_ACCESS_TOKEN"
    const val LANGUAGE = "language"
    const val Email = "email"
    const val NOTIFICATION_SETTINGS = "NOTIFICATION_SETTINGS"

    val FULL_DATE_FORMATTER = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH)
    val DATE_FORMAT = SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.ENGLISH)
    val DD_MMM_YYYY = SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH)
    val YYYY_MM_DD = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)

    object DateFormatCodes {
        const val YMD_HMS = 0
        const val YMD = 1
    }

}