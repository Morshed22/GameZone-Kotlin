package com.morshedalam.gamezone.model

import com.google.gson.annotations.SerializedName

data class Game(
        val `data`: List<Data>
) {
    data class Data(
            val banner: String,
            val category: String,
            val description: String,
            val favorite: Int,
            val featured: Int,
            @SerializedName("featured_order")
            val featuredOrder: Int,
            val gameuid: String,
            val icon: String,
            val id: Int,
            @SerializedName("large_icon")
            val largeIcon: String,
            val name: String,
            val popularity: Int,
            @SerializedName("score_type")
            val scoreType: String,
            val slider: String
    )
}