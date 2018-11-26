package com.musasyihab.footballclub2.ui.list

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.musasyihab.footballclub2.R
import com.musasyihab.footballclub2.adapter.MatchListAdapter
import com.musasyihab.footballclub2.di.component.DaggerActivityComponent
import com.musasyihab.footballclub2.di.module.ActivityModule
import com.musasyihab.footballclub2.model.EventModel
import com.musasyihab.footballclub2.ui.detail.MatchDetailActivity
import com.musasyihab.footballclub2.ui.view.ErrorPageView
import java.util.*
import javax.inject.Inject
import kotlinx.android.synthetic.main.activity_match_list.*

class MatchListActivity : AppCompatActivity(), MatchListContract.View {

    @Inject
    lateinit var presenter: MatchListContract.Presenter

    private var matchList: ArrayList<EventModel> = ArrayList(Collections.emptyList())
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: MatchListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_list)
        injectDependency()
        presenter.attach(this)
        presenter.subscribe()

        initView()

        presenter.getMatches()
    }

    private fun initView() {

//        list = findViewById(R.id.matchListView)

        matchListError.visibility = View.GONE

        matchListError.setListener(object: ErrorPageView.OnReloadClick{
            override fun clickReload() {
                presenter.getMatches()
            }
        })

        linearLayoutManager = LinearLayoutManager(this)
        matchListView.layoutManager = linearLayoutManager

        adapter = MatchListAdapter(ArrayList(Collections.emptyList()), this, MatchListListener())
        matchListView.adapter = adapter

    }

    private fun injectDependency() {
        val activityComponent = DaggerActivityComponent.builder()
            .activityModule(ActivityModule(this))
            .build()

        activityComponent.inject(this)
    }

    override fun showProgress(show: Boolean) {
        matchListLoading.visibility = if(show) View.VISIBLE else View.GONE
        if(show) {
            matchListView.visibility = View.GONE
            matchListError.visibility = View.GONE
        }
    }

    override fun showErrorMessage() {
        matchListLoading.visibility = View.GONE
        matchListView.visibility = View.GONE
        matchListError.visibility = View.VISIBLE
    }

    private fun loadToView() {
        if (!matchList.isEmpty()) {
            matchListView.layoutManager = linearLayoutManager

            matchListView.visibility = View.VISIBLE
            adapter = MatchListAdapter(matchList, this, MatchListListener())
            matchListView.adapter = adapter
        }
    }

    override fun loadDataSuccess(list: List<EventModel>) {
        matchList = ArrayList(Collections.emptyList())
        for (item in list) {
            matchList.add(item)
        }
        loadToView()
    }

    private inner class MatchListListener : MatchListAdapter.Listener {

        override fun onItemClicked(item: EventModel, position: Int) {
            val detailIntent = Intent(this@MatchListActivity, MatchDetailActivity::class.java)
            detailIntent.putExtra(MatchDetailActivity.EXTRA.EVENT_ID, item.idEvent)
            detailIntent.putExtra(MatchDetailActivity.EXTRA.EVENT_NAME, item.strEvent)
            startActivity(detailIntent)
        }

    }
}
