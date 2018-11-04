package com.morshedalam.gamezone.repository

import com.morshedalam.gamezone.model.User
import com.morshedalam.gamezone.model.UserRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface GameApi {
    @POST("gameappauth")
    fun afterFbOrGamilSignIn(@Body body: UserRequest):Call<User>
}