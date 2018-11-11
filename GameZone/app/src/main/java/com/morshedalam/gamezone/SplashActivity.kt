package com.morshedalam.gamezone

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.morshedalam.gamezone.repository.UserSharePrefsRepository
import com.morshedalam.gamezone.ui.BottomTabViews.BottomTabActivity
import com.morshedalam.gamezone.ui.Login.LoginActivity

class SplashActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val userData = UserSharePrefsRepository.getUserData()

        if (userData != null) {
            val intent = Intent(this, BottomTabActivity::class.java)
            startActivity(intent)
        } else {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        finish()


    }
}
