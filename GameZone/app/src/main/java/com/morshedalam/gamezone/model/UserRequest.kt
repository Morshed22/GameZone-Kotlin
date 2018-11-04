package com.morshedalam.gamezone.model

import com.google.gson.annotations.SerializedName

data class UserRequest(@SerializedName("login_type") val loginType:String,
                       @SerializedName("fb_id") val fbId:String? = null,
                       @SerializedName("gplus_id") val gplus:String? = null,
                       @SerializedName("user_name") val userName:String,
                       @SerializedName("user_picture") val userPicTure:String? = null,
                       val email:String) {

}