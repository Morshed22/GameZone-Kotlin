package com.morshedalam.gamezone.ui.Login

import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseUser
import com.morshedalam.gamezone.model.User
import com.morshedalam.gamezone.model.UserRequest

interface LoginContract {

    interface View {
        fun showUser(user: User)
        fun showErrorRetrievingUser()
    }

    interface Presenter {
        fun retrieveUser(socialType:SocialType, user: FirebaseUser?)

    }
}