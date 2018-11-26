package com.musasyihab.footballclub2.di.component

import com.musasyihab.footballclub2.BaseApp
import com.musasyihab.footballclub2.di.module.ApplicationModule
import dagger.Component

@Component(modules = arrayOf(ApplicationModule::class))
interface ApplicationComponent {

    fun inject(application: BaseApp)

}