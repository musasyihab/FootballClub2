package com.musasyihab.footballclub2.ui.detail

import com.musasyihab.footballclub2.database.DBHelper
import com.musasyihab.footballclub2.model.EventModel
import com.musasyihab.footballclub2.ui.base.BaseContract

class MatchDetailContract {

    interface View: BaseContract.View {
        fun showProgress(show: Boolean)
        fun showErrorMessage()
        fun loadDataSuccess(data: EventModel?)
        fun loadHomeTeamAvatar(avatar: String?)
        fun loadAwayTeamAvatar(avatar: String?)
        fun toggleFavorite()
        fun showSnackbar(message: String)
        fun setupMenu()
    }

    interface Presenter: BaseContract.Presenter<MatchDetailContract.View> {
        fun getMatchDetail(id: String)
        fun getHomeTeamDetail(id: String)
        fun getAwayTeamDetail(id: String)
        fun addToFavorite(db: DBHelper, event: EventModel?)
        fun removeFromFavorite(db: DBHelper, id: String)
        fun getFavoriteState(db: DBHelper, id: String): Boolean
    }
}