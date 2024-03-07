package com.dev.batchfinal.session

import android.content.Context
import android.content.SharedPreferences
/**
 * Created By@BBh
 * */
class UserSessionManager(con: Context?) {
    lateinit var pref: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    lateinit var con: Context
    var PRIVATE_MODE: Int = 0

    init {
        if (con != null) {
            this.con = con
        }
        if (con != null) {
            pref = con.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        }
        editor = pref.edit()
    }

    companion object {
        val PREF_NAME: String = "BATCH_FINAL_PREF"
        val KEY_USER_ID: String = "userId"
        val KEY_TOKEN_ID: String = "tokenId"
        val KEY_MOBILE_NO: String = "userMobile"
        val KEY_EMAIL: String = "userEmail"
        val KEY_USER_NAME: String = "userName"
        val KEY_LOGIN_STATUS: String = "LoginStatus"
    }


    fun createUserSession(
        user_id: String,
        token_id: String,
        mobile_no: String,
        email_id: String,
        user_name: String,
        status: Boolean?
    ) {
        editor.putString(KEY_USER_ID, user_id)
        editor.putString(KEY_TOKEN_ID, token_id)
        editor.putString(KEY_MOBILE_NO, mobile_no)
        editor.putString(KEY_EMAIL, email_id)
        editor.putString(KEY_USER_NAME, user_name)
        if (status != null) {
            editor.putBoolean(KEY_LOGIN_STATUS, status)
        }
        editor.commit()
    }





    fun isloggin(): Boolean {
        return pref.getBoolean(KEY_LOGIN_STATUS, false);
    }

    fun getUserId(): String {
        return pref.getString(KEY_USER_ID, null).toString();

    }
    fun getUserToken(): String {
        return pref.getString(KEY_TOKEN_ID, null).toString();

    }


    fun getMobileNo(): String {
        return pref.getString(KEY_MOBILE_NO, null).toString();
    }


    fun getName(): String {
        return pref.getString(KEY_USER_NAME, null).toString();
    }

    fun getEmail(): String {
        return pref.getString(KEY_EMAIL, null).toString();
    }


    fun getTokenID(): String {
        return pref.getString(KEY_TOKEN_ID, null).toString();
    }

    fun logoutUser() {
        // Clearing all user data from Shared Preferences
        editor.clear()
        editor.commit()

    }
}