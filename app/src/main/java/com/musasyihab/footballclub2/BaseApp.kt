package com.musasyihab.footballclub2

import android.app.Application
import com.musasyihab.footballclub2.di.component.ApplicationComponent
import com.musasyihab.footballclub2.di.component.DaggerApplicationComponent
import com.musasyihab.footballclub2.di.module.ApplicationModule



class BaseApp: Application() {

    lateinit var component: ApplicationComponent

    override fun onCreate() {
        super.onCreate()

        instance = this
        setup()

        if (BuildConfig.DEBUG) {
            // Add custom configuration for debug build
        }
    }

    fun setup() {
        component = DaggerApplicationComponent.builder()
            .applicationModule(ApplicationModule(this)).build()
        component.inject(this)
    }

    fun getApplicationComponent(): ApplicationComponent {
        return component
    }

    companion object {
        lateinit var instance: BaseApp private set
    }
}