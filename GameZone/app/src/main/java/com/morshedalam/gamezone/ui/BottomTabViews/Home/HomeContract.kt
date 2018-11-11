package com.morshedalam.gamezone.ui.BottomTabViews.Home

import com.morshedalam.gamezone.Base.BasePresenter
import com.morshedalam.gamezone.Base.BaseView
import com.morshedalam.gamezone.model.Game

interface HomeContract {

    interface View : BaseView<Presenter> {
        fun showHomesData(items: List<Game.Data>)
    }

    interface Presenter : BasePresenter {
        fun loadGameData()
    }
}