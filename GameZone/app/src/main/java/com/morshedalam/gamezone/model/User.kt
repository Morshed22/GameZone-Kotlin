package com.morshedalam.gamezone.model

import com.google.gson.annotations.SerializedName

data class User(
        val msg: String,
        val status: Int,
        val statuscode: Int,
        @SerializedName("user_data")
        val userData: UserData
) {
    data class UserData(
            @SerializedName("access_token")
            val accessToken: String,
            val email: String,
            @SerializedName("fb_id")
            val fbId: String,
            @SerializedName("gplus_id")
            val gplusId: Any?,
            val id: Int,
            val password: Any?,
            @SerializedName("total_coin")
            val totalCoin: Int,
            @SerializedName("total_played")
            val totalPlayed: Int,
            @SerializedName("user_defined_id")
            val userDefinedId: Any?,
            @SerializedName("user_id")
            val userId: String,
            @SerializedName("user_name")
            val userName: String,
            @SerializedName("user_picture")
            val userPicture: Any?
    )
}