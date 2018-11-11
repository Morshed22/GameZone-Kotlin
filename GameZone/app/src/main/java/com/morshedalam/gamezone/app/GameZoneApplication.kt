package com.morshedalam.gamezone.app

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import com.morshedalam.gamezone.repository.UserSharePrefsRepository

class GameZoneApplication : Application() {

    companion object {
        private lateinit var instance: GameZoneApplication

        fun getAppContext(): Context = instance.applicationContext
    }

    override fun onCreate() {
        instance = this
        super.onCreate()

    }
}