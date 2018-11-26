package com.morshedalam.gamezone.ui.BottomTabViews.Home

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.morshedalam.gamezone.R
import com.morshedalam.gamezone.app.inflate
import com.morshedalam.gamezone.model.Game
import kotlinx.android.synthetic.main.list_item_game.view.*
import com.bumptech.glide.request.RequestOptions



class GameItemAdapter(private val items: List<Game.Data>):RecyclerView.Adapter<GameItemAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
          ViewHolder(parent.inflate(R.layout.list_item_game))


    override fun getItemCount(): Int {
        return  items.size
    }

    override fun onBindViewHolder(holder: GameItemAdapter.ViewHolder, position: Int) {
        holder.bind(items[position])
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private lateinit var game:Game.Data

        fun bind(item:Game.Data){
            this.game = item
            val context = itemView.context
            val path =  "http://game.gagagugu.com/gameimages/${game.icon}"
            Glide.with(context)
                    .load(path)
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
                    .apply(RequestOptions.bitmapTransform(RoundedCorners(10)))
                    .into(itemView.gameImage)
            itemView.gameName.text = game.name
            itemView.gameCategory.text = game.category

        }
    }
}