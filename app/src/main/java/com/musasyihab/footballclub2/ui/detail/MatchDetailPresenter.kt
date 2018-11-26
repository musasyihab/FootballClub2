package com.musasyihab.footballclub2.ui.detail

import com.musasyihab.footballclub2.api.ApiServiceInterface
import com.musasyihab.footballclub2.model.EventModel
import com.musasyihab.footballclub2.model.MatchListModel
import com.musasyihab.footballclub2.model.TeamListModel
import com.musasyihab.footballclub2.model.TeamModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*

class MatchDetailPresenter: MatchDetailContract.Presenter {
    private val subscriptions = CompositeDisposable()
    var api: ApiServiceInterface = ApiServiceInterface.create()
    private lateinit var view: MatchDetailContract.View

    override fun subscribe() {

    }

    override fun unsubscribe() {
        subscriptions.clear()
    }

    override fun attach(view: MatchDetailContract.View) {
        this.view = view
    }

    override fun getMatchDetail(id: String) {
        view.showProgress(true)
        val obs = api.getMatchDetail(id)
        val subscribe = obs.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response: MatchListModel? ->
                val events = response?.events ?: Collections.emptyList()
                val data: EventModel? = if (events.isNotEmpty()) events[0] else null
                getHomeTeamDetail(data?.idHomeTeam ?: "")
                getAwayTeamDetail(data?.idAwayTeam ?: "")
                view.loadDataSuccess(data)
                view.showProgress(false)
            }, { error ->
                error.printStackTrace()
                view.showErrorMessage()
                view.showProgress(false)
            })
        subscriptions.add(subscribe)
    }

    override fun getHomeTeamDetail(id: String) {
        val obs = api.getTeamDetail(id)
        val subscribe = obs.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response: TeamListModel? ->
                val teams = response?.teams ?: Collections.emptyList()
                val data: TeamModel? = if (teams.isNotEmpty()) teams[0] else null
                val avatar: String? = data?.strTeamBadge
                view.loadHomeTeamAvatar(avatar)
            }, { error ->
                error.printStackTrace()
            })
        subscriptions.add(subscribe)
    }

    override fun getAwayTeamDetail(id: String) {
        val obs = api.getTeamDetail(id)
        val subscribe = obs.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response: TeamListModel? ->
                val teams = response?.teams ?: Collections.emptyList()
                val data: TeamModel? = if (teams.isNotEmpty()) teams[0] else null
                val avatar: String? = data?.strTeamBadge
                view.loadAwayTeamAvatar(avatar)
            }, { error ->
                error.printStackTrace()
            })
        subscriptions.add(subscribe)
    }

}