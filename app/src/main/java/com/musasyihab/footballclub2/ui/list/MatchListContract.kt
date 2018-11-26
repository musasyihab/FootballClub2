package com.musasyihab.footballclub2.ui.list

import com.musasyihab.footballclub2.model.EventModel
import com.musasyihab.footballclub2.ui.base.BaseContract

class MatchListContract {

    interface View: BaseContract.View {
        fun showProgress(show: Boolean)
        fun showErrorMessage()
        fun loadDataSuccess(list: List<EventModel>)
    }

    interface Presenter: BaseContract.Presenter<MatchListContract.View> {
        fun getMatches()
    }
}