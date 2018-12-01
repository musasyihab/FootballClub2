package com.musasyihab.footballclub2.ui.favorite

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.musasyihab.footballclub2.R
import com.musasyihab.footballclub2.adapter.MatchListAdapter
import com.musasyihab.footballclub2.database.DBHelper
import com.musasyihab.footballclub2.di.component.DaggerActivityComponent
import com.musasyihab.footballclub2.di.module.ActivityModule
import com.musasyihab.footballclub2.model.EventModel
import com.musasyihab.footballclub2.ui.detail.MatchDetailActivity
import com.musasyihab.footballclub2.ui.view.ErrorPageView
import kotlinx.android.synthetic.main.activity_favorite_list.*
import java.util.*
import javax.inject.Inject

class FavoriteActivity : AppCompatActivity(), FavoriteContract.View {

    @Inject
    lateinit var presenter: FavoriteContract.Presenter

    private var matchList: ArrayList<EventModel> = ArrayList(Collections.emptyList())
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: MatchListAdapter
    private lateinit var db: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_list)
        injectDependency()
        presenter.attach(this)
        presenter.subscribe()
        db = DBHelper.getInstance(applicationContext)

        initView()

        presenter.getMatches(db)
    }

    private fun initView() {

        favoriteListError.visibility = View.GONE

        favoriteListError.setListener(object: ErrorPageView.OnReloadClick{
            override fun clickReload() {
                presenter.getMatches(db)
            }
        })

        linearLayoutManager = LinearLayoutManager(this)
        favoriteListView.layoutManager = linearLayoutManager

        adapter = MatchListAdapter(ArrayList(Collections.emptyList()), this, MatchListListener())
        favoriteListView.adapter = adapter

    }

    private fun injectDependency() {
        val activityComponent = DaggerActivityComponent.builder()
            .activityModule(ActivityModule(this))
            .build()

        activityComponent.inject(this)
    }

    override fun showProgress(show: Boolean) {
        favoriteListLoading.visibility = if(show) View.VISIBLE else View.GONE
        if(show) {
            favoriteListView.visibility = View.GONE
            favoriteListError.visibility = View.GONE
        }
    }

    override fun showErrorMessage() {
        favoriteListLoading.visibility = View.GONE
        favoriteListView.visibility = View.GONE
        favoriteListError.visibility = View.VISIBLE
    }

    private fun loadToView() {
        if (!matchList.isEmpty()) {
            favoriteListView.layoutManager = linearLayoutManager

            favoriteListView.visibility = View.VISIBLE
            adapter = MatchListAdapter(matchList, this, MatchListListener())
            favoriteListView.adapter = adapter
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
            val detailIntent = Intent(this@FavoriteActivity, MatchDetailActivity::class.java)
            detailIntent.putExtra(MatchDetailActivity.EXTRA.EVENT_ID, item.idEvent)
            detailIntent.putExtra(MatchDetailActivity.EXTRA.EVENT_NAME, item.strEvent)
            startActivity(detailIntent)
        }

    }
}
