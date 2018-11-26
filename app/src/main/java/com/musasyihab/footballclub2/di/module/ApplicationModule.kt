package com.musasyihab.footballclub2.di.module

import android.app.Application
import com.musasyihab.footballclub2.BaseApp
import com.musasyihab.footballclub2.di.scope.PerApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(private val baseApp: BaseApp) {

    @Provides
    @Singleton
    @PerApplication
    fun provideApplication(): Application {
        return baseApp
    }
}