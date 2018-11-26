package com.musasyihab.footballclub2.ui.list

import com.musasyihab.footballclub2.api.ApiServiceInterface
import com.musasyihab.footballclub2.model.EventModel
import com.musasyihab.footballclub2.model.MatchListModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*

class MatchListPresenter: MatchListContract.Presenter {
    private val subscriptions = CompositeDisposable()
    var api: ApiServiceInterface = ApiServiceInterface.create()
    private lateinit var view: MatchListContract.View

    override fun subscribe() {

    }

    override fun unsubscribe() {
        subscriptions.clear()
    }

    override fun attach(view: MatchListContract.View) {
        this.view = view
    }

    override fun getMatches() {
        view.showProgress(true)
        val obs = api.getNextMatches() // get list of next matches first
        val subscribe = obs.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response: MatchListModel? ->
                val nextList = response?.events ?: Collections.emptyList()
                getPrevMatches(nextList)
            }, { error ->
                error.printStackTrace()
                getPrevMatches()
            })

        subscriptions.add(subscribe)
    }

    fun getPrevMatches(nextMatches: List<EventModel> = Collections.emptyList()) {
        val obs = api.getPrevMatches() // get list of prev matches
        val subscribe = obs.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response: MatchListModel? ->
                val list = nextMatches.toMutableList()
                val prevList = response?.events ?: Collections.emptyList()
                list.addAll(prevList)
                view.loadDataSuccess(list)
                view.showProgress(false)
            }, { error ->
                error.printStackTrace()
                view.showErrorMessage()
                view.showProgress(false)
            })

        subscriptions.add(subscribe)
    }

}