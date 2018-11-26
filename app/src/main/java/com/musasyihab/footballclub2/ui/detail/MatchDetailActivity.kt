package com.musasyihab.footballclub2.ui.detail

import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.musasyihab.footballclub2.R
import com.musasyihab.footballclub2.di.component.DaggerActivityComponent
import com.musasyihab.footballclub2.di.module.ActivityModule
import com.musasyihab.footballclub2.model.EventModel
import kotlinx.android.synthetic.main.activity_match_detail.*
import javax.inject.Inject
import com.bumptech.glide.request.RequestOptions
import com.musasyihab.footballclub2.ui.view.ErrorPageView
import com.musasyihab.footballclub2.util.Helper


class MatchDetailActivity : AppCompatActivity(), MatchDetailContract.View {

    object EXTRA {
        const val EVENT_ID: String = "EVENT_ID"
        const val EVENT_NAME: String = "EVENT_NAME"
    }

    @Inject
    lateinit var presenter: MatchDetailContract.Presenter

    private var event: EventModel? = null
    private lateinit var glideContext: RequestManager
    private var eventId: String = ""
    private var eventName: String = ""
    private var actionBar: ActionBar? = null

    private val options = RequestOptions()
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .fitCenter()
        .error(R.drawable.ic_error)
        .priority(Priority.HIGH)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_detail)
        injectDependency()
        presenter.attach(this)
        presenter.subscribe()

        actionBar = supportActionBar

        if(intent.hasExtra(EXTRA.EVENT_ID)) {
            eventId = intent.getStringExtra(EXTRA.EVENT_ID)
        }

        if(intent.hasExtra(EXTRA.EVENT_NAME)) {
            eventName = intent.getStringExtra(EXTRA.EVENT_NAME)
            actionBar?.title = eventName
        }

        glideContext = Glide.with(this)

        initView()

        presenter.getMatchDetail(eventId)
    }

    private fun initView() {
        matchDetailDataLayout.visibility = View.GONE
        matchDetailLayout.visibility = View.GONE
        matchDetailError.visibility = View.GONE

        matchDetailError.setListener(object: ErrorPageView.OnReloadClick{
            override fun clickReload() {
                presenter.getMatchDetail(eventId)
            }
        })
    }

    private fun injectDependency() {
        val activityComponent = DaggerActivityComponent.builder()
            .activityModule(ActivityModule(this))
            .build()

        activityComponent.inject(this)
    }

    override fun showProgress(show: Boolean) {
        matchDetailLoading.visibility = if(show) View.VISIBLE else View.GONE
        if(show) {
            matchDetailLayout.visibility = View.GONE
            matchDetailError.visibility = View.GONE
        }
    }

    override fun showErrorMessage() {
        matchDetailLoading.visibility = View.GONE
        matchDetailLayout.visibility = View.GONE
        matchDetailError.visibility = View.VISIBLE
    }

    private fun loadToView(data: EventModel?) {
        matchDetailHomeClub.text = data?.strHomeTeam
        matchDetailAwayClub.text = data?.strAwayTeam

        matchDetailLeague.text = data?.strLeague
        matchDetailDate.text = Helper.formatToReadableDate(data?.dateEvent ?: "")
        matchDetailTime.text = Helper.formatToReadableTime(data?.strTime ?: "")

        if(Helper.isPastEvent(data)) {
            matchDetailHomeScore.visibility = View.VISIBLE
            matchDetailAwayScore.visibility = View.VISIBLE
            matchDetailHomeScore.text = data?.intHomeScore
            matchDetailAwayScore.text = data?.intAwayScore

            matchDetailDataLayout.visibility = View.VISIBLE
            matchDetailGoalsHome.text = Helper.formatGoalDetail(data?.strHomeGoalDetails)
            matchDetailGoalsAway.text = Helper.formatGoalDetail(data?.strAwayGoalDetails)
            matchDetailShotsHome.text = data?.intHomeShots
            matchDetailShotsAway.text = data?.intAwayShots
            matchDetailGoalkeeperHome.text = Helper.formatLineup(data?.strHomeLineupGoalkeeper)
            matchDetailGoalkeeperAway.text = Helper.formatLineup(data?.strAwayLineupGoalkeeper)
            matchDetailDefenseHome.text = Helper.formatLineup(data?.strHomeLineupDefense)
            matchDetailDefenseAway.text = Helper.formatLineup(data?.strAwayLineupDefense)
            matchDetailMidfieldHome.text = Helper.formatLineup(data?.strHomeLineupMidfield)
            matchDetailMidfieldAway.text = Helper.formatLineup(data?.strAwayLineupMidfield)
            matchDetailForwardHome.text = Helper.formatLineup(data?.strHomeLineupForward)
            matchDetailForwardAway.text = Helper.formatLineup(data?.strAwayLineupForward)
            matchDetailSubstitutesHome.text = Helper.formatLineup(data?.strHomeLineupSubstitutes)
            matchDetailSubstitutesAway.text = Helper.formatLineup(data?.strAwayLineupSubstitutes)
        } else {
            matchDetailHomeScore.visibility = View.GONE
            matchDetailAwayScore.visibility = View.GONE
            matchDetailDataLayout.visibility = View.GONE
        }
    }

    override fun loadDataSuccess(data: EventModel?) {
        event = data
        if(data != null) {
            matchDetailLayout.visibility = View.VISIBLE
            matchDetailError.visibility = View.GONE
            loadToView(data)
        }
    }

    override fun loadHomeTeamAvatar(avatar: String?) {

        Glide.with(this)
            .load(avatar)
            .apply(options)
            .into(matchDetailHomeIcon)

    }

    override fun loadAwayTeamAvatar(avatar: String?) {

        Glide.with(this)
            .load(avatar)
            .apply(options)
            .into(matchDetailAwayIcon)
    }
}
