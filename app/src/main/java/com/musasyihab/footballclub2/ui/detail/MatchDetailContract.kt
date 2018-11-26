package com.musasyihab.footballclub2.ui.detail

import com.musasyihab.footballclub2.model.EventModel
import com.musasyihab.footballclub2.ui.base.BaseContract

class MatchDetailContract {

    interface View: BaseContract.View {
        fun showProgress(show: Boolean)
        fun showErrorMessage()
        fun loadDataSuccess(data: EventModel?)
        fun loadHomeTeamAvatar(avatar: String?)
        fun loadAwayTeamAvatar(avatar: String?)
    }

    interface Presenter: BaseContract.Presenter<MatchDetailContract.View> {
        fun getMatchDetail(id: String)
        fun getHomeTeamDetail(id: String)
        fun getAwayTeamDetail(id: String)
    }
}