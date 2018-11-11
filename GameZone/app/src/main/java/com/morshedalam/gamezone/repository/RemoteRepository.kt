package com.morshedalam.gamezone.repository

import com.morshedalam.gamezone.model.Game
import com.morshedalam.gamezone.model.User
import com.morshedalam.gamezone.model.UserRequest
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RemoteRepository : Repository {


    private val api: GameApi


    init {
        val retrofit = Retrofit.Builder()
                .baseUrl("http://game.gagagugu.com/api/v4/gameapp/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        api = retrofit.create<GameApi>(GameApi::class.java)
    }

    override fun retrieveUserData(userRequest: UserRequest, callBack: Callback<User>) {
        api.afterFbOrGamilSignIn(userRequest).enqueue(callBack)
    }

    override fun retrieveAllGamesItems(userId: String, callBack: Callback<Game>) {
        api.getAllGames(userId).enqueue(callBack)
    }
}