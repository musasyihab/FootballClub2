package com.musasyihab.footballclub2.di.component

import com.musasyihab.footballclub2.ui.list.MatchListActivity
import com.musasyihab.footballclub2.di.module.ActivityModule
import com.musasyihab.footballclub2.ui.detail.MatchDetailActivity
import com.musasyihab.footballclub2.ui.favorite.FavoriteActivity
import dagger.Component

@Component(modules = arrayOf(ActivityModule::class))
interface ActivityComponent {

    fun inject(matchListActivity: MatchListActivity)
    fun inject(matchDetailActivity: MatchDetailActivity)
    fun inject(favoriteActivity: FavoriteActivity)

}