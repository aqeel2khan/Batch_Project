package com.example.bottomanimationmydemo.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

private const val BATCH_FINAL_SHARED_PREFERENCES = "BATCH_FINAL_SHARED_PREFERENCES"
private const val KEY_TOKEN = "KEY_TOKEN"
private const val KEY_USERID = "KEY_TOKEN"
private const val KEY_EMAIL = "KEY_EMAIL"
private const val KEY_NAME = "KEY_NAME"
private const val KEY_MOBILE = "KEY_MOBILE"
private const val KEY_COMPANY_NAME = "KEY_COMPANY_NAME"
private const val KEY_EMPLOYER_ID = "KEY_EMPLOYER_ID"
private const val KEY_COMPANY_ID = "KEY_COMPANY_ID"
private const val KEY_INDUSTRY_ID = "KEY_INDUSTRY_ID"
private const val KEY_PROFILE_PIC = "KEY_PROFILE_PIC"
private const val KEY_REMEMBER_ME = "KEY_REMEMBER_ME"
private const val KEY_REMEMBER_ME_PASS = "KEY_REMEMBER_ME_PASS"
private const val KEY_MY_COURSE_ID = "KEY_MY_COURSE_ID"
private const val KEY_MY_WORK_CHAT_LIST = "KEY_MY_WORK_CHAT_LIST"
private const val KEY_FCM_TOKEN = "KEY_FCM_TOKEN"
private const val KEY_RADIO_STATE = "KEY_RADIO_STATE"
private const val KEY_GENDER = "KEY_GENDER"

class AppSharedPreferences  @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private var sharedPreferences: SharedPreferences = context.getSharedPreferences(BATCH_FINAL_SHARED_PREFERENCES, Context.MODE_PRIVATE)


    fun saveRadioState(state: String) {
        sharedPreferences.putValues {
            it.putString(KEY_RADIO_STATE, state)
        }
    }
    var state: String? = null
        get() {
            val storedValue = sharedPreferences.getString(KEY_RADIO_STATE, "")
            state = storedValue
            return field
        }

    fun saveUserId(userId: String) {
        sharedPreferences.putValues {
            it.putString(KEY_USERID, userId)
        }
    }
    var userId: String? = null
        get() {
            val storedValue = sharedPreferences.getString(KEY_USERID, "")
            userId = storedValue
            return field
        }

    // set and get token
    fun saveToken(token: String) {
        sharedPreferences.putValues {
            it.putString(KEY_TOKEN, token)
        }
    }
    var token: String? = null
        get() {
            val storedValue = sharedPreferences.getString(KEY_TOKEN, "")
            token = storedValue
            return field
        }

    //set and get fcm token
    fun saveGender(gender: String){
        sharedPreferences.putValues {
            it.putString(KEY_GENDER, gender)
        }
    }

    var gender: String? = null
        get() {
            val storedValue = sharedPreferences.getString(KEY_GENDER, "")
            gender = storedValue
            return field
        }

    //set and get fcm token
    fun saveFcmToken(fcmToken: String){
        sharedPreferences.putValues {
            it.putString(KEY_FCM_TOKEN, fcmToken)
        }
    }

    var fcmToken: String? = null
        get() {
            val storedValue = sharedPreferences.getString(KEY_FCM_TOKEN, "")
            fcmToken = storedValue
            return field
        }

    // set and get email
    fun saveEmail(email: String) {
        sharedPreferences.putValues {
            it.putString(KEY_EMAIL, email)
        }
    }
    var email: String? = null
        get() {
            val storedValue = sharedPreferences.getString(KEY_EMAIL, "")
            email = storedValue
            return field
        }

    fun saveCourseId(userId: String){
        sharedPreferences.putValues {
            it.putString(KEY_MY_COURSE_ID, userId)
        }
    }

    var myCourseId: String? = null
        get() {
            val storedValue = sharedPreferences.getString(KEY_MY_COURSE_ID, "")
            myCourseId = storedValue
            return field
        }

    //work chat list save
    fun saveMyWorkChatList(userId: String){
        sharedPreferences.putValues {
            it.putString(KEY_MY_WORK_CHAT_LIST, userId)
        }
    }

    var MyWorkChatList: String? = null
        get() {
            val storedValue = sharedPreferences.getString(KEY_MY_WORK_CHAT_LIST, "")
            MyWorkChatList = storedValue
            return field
        }

    // set and get name
    fun saveName(name: String) {
        sharedPreferences.putValues {
            it.putString(KEY_NAME, name)
        }
    }
    var name: String? = null
        get() {
            val storedValue = sharedPreferences.getString(KEY_NAME, "")
            name = storedValue
            return field
        }

    // set and get mobile
    fun saveMobileNo(mobile: String) {
        sharedPreferences.putValues {
            it.putString(KEY_MOBILE, mobile)
        }
    }
    var mobile: String? = null
        get() {
            val storedValue = sharedPreferences.getString(KEY_MOBILE, "")
            mobile = storedValue
            return field
        }


    // set and get employer_id
    fun saveEmployer_Id(employer_id: String) {
        sharedPreferences.putValues {
            it.putString(KEY_EMPLOYER_ID, employer_id)
        }
    }
    var employer_id: String? = null
        get() {
            val storedValue = sharedPreferences.getString(KEY_EMPLOYER_ID, "")
            employer_id = storedValue
            return field
        }

    // set and get company_id
    fun saveCompany_Id(company_id: String) {
        sharedPreferences.putValues {
            it.putString(KEY_COMPANY_ID, company_id)
        }
    }
    var company_id: String? = null
        get() {
            val storedValue = sharedPreferences.getString(KEY_COMPANY_ID, "")
            company_id = storedValue
            return field
        }
    // set and get industry_id
    fun saveIndustry_Id(industry_id: String) {
        sharedPreferences.putValues {
            it.putString(KEY_INDUSTRY_ID, industry_id)
        }
    }
    var industry_id: String? = null
        get() {
            val storedValue = sharedPreferences.getString(KEY_INDUSTRY_ID, "")
            industry_id = storedValue
            return field
        }

    // set profile_pic
    fun saveProfile_pic(profile_pic: String) {
        sharedPreferences.putValues {
            it.putString(KEY_PROFILE_PIC, profile_pic)
        }
    }
    var profile_pic: String? = null
        get() {
            val storedValue = sharedPreferences.getString(KEY_PROFILE_PIC, "")
            profile_pic = storedValue
            return field
        }

    // set and get remember me
    fun saveRemember_me(remember_me: String) {
        sharedPreferences.putValues {
            it.putString(KEY_REMEMBER_ME, remember_me)
        }
    }
    var remember_me: String? = null
        get() {
            val storedValue = sharedPreferences.getString(KEY_REMEMBER_ME, "")
            remember_me = storedValue
            return field
        }


    // set and get remember me pass
    fun saveRemember_me_pass(remember_me_pass: String) {
        sharedPreferences.putValues {
            it.putString(KEY_REMEMBER_ME_PASS, remember_me_pass)
        }
    }
    var remember_me_pass: String? = null
        get() {
            val storedValue = sharedPreferences.getString(KEY_REMEMBER_ME_PASS, "")
            remember_me_pass = storedValue
            return field
        }

    /**
     * Extension function for shared preferences
     */
    private fun SharedPreferences.putValues(func:(SharedPreferences.Editor)-> Unit) {
        val editor: SharedPreferences.Editor = this.edit()
        func(editor)
        editor.apply()
    }

    private fun setString(key: String?, value: String?) {
        if (key != null && value != null) {
            try {
                if (_pref != null) {
                    val editor = _pref!!.edit()
                    editor.putString(key, value)
                    editor.commit()
                }
            } catch (e: Exception) {
                Log.e(TAG, "Unable to set " + key + "= " + value + "in shared preference", e)
            }

        }
    }

    private fun getString(key: String?, defaultValue: String): String? {
        return if (_pref != null && key != null && _pref!!.contains(key)) {
            _pref!!.getString(key, defaultValue)
        } else defaultValue
    }

    companion object{
        val TAG = AppSharedPreferences::class.java.name
        private var _pref: SharedPreferences? = null
        private var _instance: AppSharedPreferences? = null
        private val PRIVATE_MODE = 0
        val SHARED_PREF_NAME = "RMC_"

        @SuppressLint("WrongConstant")
        fun getInstance(context: Context): AppSharedPreferences {
            if (_pref == null) {
                _pref = context
                    .getSharedPreferences(SHARED_PREF_NAME, PRIVATE_MODE)
            }
            if (_instance == null)  {
                _instance = AppSharedPreferences(context)
            }
            return _instance as AppSharedPreferences
        }
    }
}