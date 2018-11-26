package com.musasyihab.footballclub2.di.module

import android.app.Activity
import com.musasyihab.footballclub2.ui.detail.MatchDetailContract
import com.musasyihab.footballclub2.ui.detail.MatchDetailPresenter
import com.musasyihab.footballclub2.ui.list.MatchListContract
import com.musasyihab.footballclub2.ui.list.MatchListPresenter
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private var activity: Activity) {

    @Provides
    fun provideActivity(): Activity {
        return activity
    }

    @Provides
    fun provideMatchListPresenter(): MatchListContract.Presenter {
        return MatchListPresenter()
    }

    @Provides
    fun provideMatchDetailPresenter(): MatchDetailContract.Presenter {
        return MatchDetailPresenter()
    }
//
//    @Provides
//    fun provideStartPresenter(): StartContract.Presenter {
//        return StartPresenter()
//    }


}