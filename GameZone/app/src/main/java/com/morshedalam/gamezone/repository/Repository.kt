package com.morshedalam.gamezone.repository

import com.morshedalam.gamezone.model.Game
import com.morshedalam.gamezone.model.User
import com.morshedalam.gamezone.model.UserRequest
import retrofit2.Callback

interface Repository {

    fun retrieveUserData(userRequest:UserRequest, callBack: Callback<User>)
    fun retrieveAllGamesItems(userId:String, callBack: Callback<Game>)
}