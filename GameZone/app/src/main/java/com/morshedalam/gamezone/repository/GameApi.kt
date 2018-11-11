package com.morshedalam.gamezone.repository

import com.morshedalam.gamezone.model.Game
import com.morshedalam.gamezone.model.User
import com.morshedalam.gamezone.model.UserRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface GameApi {
    @POST("gameappauth")
    fun afterFbOrGamilSignIn(@Body body: UserRequest):Call<User>

    @GET("getallgameforapp")
    fun getAllGames(@Query("user_id") userID:String):Call<Game>

}