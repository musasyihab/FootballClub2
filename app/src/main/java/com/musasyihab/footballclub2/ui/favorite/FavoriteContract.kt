package com.musasyihab.footballclub2.ui.favorite

import com.musasyihab.footballclub2.database.DBHelper
import com.musasyihab.footballclub2.model.EventModel
import com.musasyihab.footballclub2.ui.base.BaseContract

class FavoriteContract {

    interface View: BaseContract.View {
        fun showProgress(show: Boolean)
        fun showErrorMessage()
        fun loadDataSuccess(list: List<EventModel>)
    }

    interface Presenter: BaseContract.Presenter<FavoriteContract.View> {
        fun getMatches(db: DBHelper)
    }
}