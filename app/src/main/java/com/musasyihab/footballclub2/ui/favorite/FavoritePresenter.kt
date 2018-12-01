package com.musasyihab.footballclub2.ui.favorite

import android.database.sqlite.SQLiteConstraintException
import com.musasyihab.footballclub2.api.ApiServiceInterface
import com.musasyihab.footballclub2.database.DBHelper
import com.musasyihab.footballclub2.model.FavoriteModel
import com.musasyihab.footballclub2.util.Helper
import io.reactivex.disposables.CompositeDisposable

class FavoritePresenter: FavoriteContract.Presenter {
    private val subscriptions = CompositeDisposable()
    var api: ApiServiceInterface = ApiServiceInterface.create()
    private lateinit var view: FavoriteContract.View

    override fun subscribe() {

    }

    override fun unsubscribe() {
        subscriptions.clear()
    }

    override fun attach(view: FavoriteContract.View) {
        this.view = view
    }

    override fun getMatches(db: DBHelper) {
        view.showProgress(true)
        try {
            val favorites: List<FavoriteModel> = db.getFavoriteList()
            val list = favorites.map { it -> Helper.convertFavoriteToEvent(it) }
            view.loadDataSuccess(list)
            view.showProgress(false)
        } catch (e: SQLiteConstraintException) {
            e.printStackTrace()
            view.showErrorMessage()
            view.showProgress(false)
        }

    }


}