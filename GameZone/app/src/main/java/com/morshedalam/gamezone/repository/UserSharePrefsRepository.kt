package com.morshedalam.gamezone.repository

import android.content.Context
import com.google.gson.Gson
import com.morshedalam.gamezone.app.GameZoneApplication
import com.morshedalam.gamezone.model.User

object UserSharePrefsRepository {

    private const val USER_SHARED_PREFS_REPOSITORY = "USER_SHARED_PREFS_REPOSITORY"
    private  const val KEY = "user_data"
    private val gson = Gson()

    private fun sharedPrefs() =  GameZoneApplication.getAppContext()
            .getSharedPreferences(USER_SHARED_PREFS_REPOSITORY, Context.MODE_PRIVATE)


    fun save(userData:User.UserData){

        sharedPrefs().edit().putString(KEY, gson.toJson(userData)).apply()
    }

    fun getUserData():User.UserData?{
       return gson.fromJson(sharedPrefs().getString(KEY,""), User.UserData::class.java)
    }

    fun clearUserData(){
        sharedPrefs().edit().clear().apply()
    }


}