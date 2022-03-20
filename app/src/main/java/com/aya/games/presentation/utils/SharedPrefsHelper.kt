package com.aya.games.presentation.utils

import android.content.Context
import android.content.SharedPreferences
import com.aya.games.R
import com.aya.games.domain.model.General
import com.google.gson.Gson

/**
 * Created by ( Eng Aya Osama Ahmed)
 * Class do : shared prefrence
 */


class SharedPrefsHelper (context: Context){

    private val mSharedPreferences: SharedPreferences =  context.getSharedPreferences(R.string.app_name.toString(),Context.MODE_PRIVATE)
    private var mSharedPreferencesEditor: SharedPreferences.Editor = mSharedPreferences.edit()

    init {
        mSharedPreferencesEditor.apply()
    }

    fun getStringValue(key: String, defaultValue: String = ""): String {
        return mSharedPreferences.getString(key, defaultValue)!!
    }

    fun setValue(key: String, value: Any?) {
        when (value) {
            is Int? -> {
                mSharedPreferencesEditor.putInt(key, value!!)
                mSharedPreferencesEditor.apply()
            }
            is String? -> {
                mSharedPreferencesEditor.putString(key, value!!)
                mSharedPreferencesEditor.apply()
            }
            is Double? -> {
                mSharedPreferencesEditor.putString(key, value.toString())
                mSharedPreferencesEditor.apply()
            }
            is Long? -> {
                mSharedPreferencesEditor.putLong(key, value!!)
                mSharedPreferencesEditor.apply()
            }
            is Boolean? -> {
                mSharedPreferencesEditor.putBoolean(key, value!!)
                mSharedPreferencesEditor.apply()
            }
            is General? -> {
                mSharedPreferencesEditor.putString(key, Gson().toJson(value!!))
                mSharedPreferencesEditor.apply()
            }
        }
    }


    fun getIntValue(key: String, defaultValue: Int): Int {
        return mSharedPreferences.getInt(key, defaultValue)
    }

    fun getLongValue(key: String, defaultValue: Long): Long {
        return mSharedPreferences.getLong(key, defaultValue)
    }

    fun getBooleanValue(keyFlag: String, defaultValue: Boolean = false): Boolean {
        return mSharedPreferences.getBoolean(keyFlag, defaultValue)
    }

    fun removeKey(key: String) {
        mSharedPreferencesEditor.remove(key)
        mSharedPreferencesEditor.apply()
    }

    fun clear() {
        mSharedPreferencesEditor.clear().apply()
    }

}