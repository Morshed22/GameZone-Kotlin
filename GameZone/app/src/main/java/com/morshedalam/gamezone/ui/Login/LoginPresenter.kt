package com.morshedalam.gamezone.ui.Login

import com.google.firebase.auth.FirebaseUser
import com.morshedalam.gamezone.model.User
import com.morshedalam.gamezone.model.UserRequest
import com.morshedalam.gamezone.repository.Repository
import com.morshedalam.gamezone.repository.UserSharePrefsRepository
import com.morshedalam.gamezone.ui.Login.LoginContract
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginPresenter(val repository: Repository, val view: LoginContract.View): LoginContract.Presenter {


    override fun retrieveUser(socialType: SocialType, user: FirebaseUser?) {

        user?.let { user ->

            var loginType = ""
            var fbId:String? = null
            var gmailId:String? = null

            if (socialType == SocialType.FACEBOOK){
                loginType = "fb"
                fbId = user.uid
            }else{
                loginType = "gplus"
                gmailId = user.uid
            }

            val userRequest = UserRequest(loginType, fbId, gmailId, user.displayName
                    ?: "", user.photoUrl.toString(), user.email ?: "")

            repository.retrieveUserData(userRequest, object : Callback<User> {

                override fun onFailure(call: Call<User>?, t: Throwable?) {
                    view.showErrorRetrievingUser()
                }

                override fun onResponse(call: Call<User>?, response: Response<User>?) {
                    val user = response?.body()

                    if (user != null) {
                        UserSharePrefsRepository.save(user.userData)
                        view.showUser(user)
                    } else {
                        view.showErrorRetrievingUser()
                    }

                }

            })

        }

    }


}


enum class SocialType{
    GMAIL,
    FACEBOOK
}