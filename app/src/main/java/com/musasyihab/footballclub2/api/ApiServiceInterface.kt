package com.musasyihab.footballclub2.api

import com.musasyihab.footballclub2.BuildConfig
import com.musasyihab.footballclub2.model.MatchListModel
import com.musasyihab.footballclub2.model.TeamListModel
import com.musasyihab.footballclub2.util.Constants
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServiceInterface {

    @GET("eventsnextleague.php?id=${Constants.ENGLISH_PREMIERE_LEAGUE_ID}")
    fun getNextMatches(): Observable<MatchListModel>

    @GET("eventspastleague.php?id=${Constants.ENGLISH_PREMIERE_LEAGUE_ID}")
    fun getPrevMatches(): Observable<MatchListModel>

    @GET("lookupevent.php")
    fun getMatchDetail(@Query(value="id") id: String): Observable<MatchListModel>

    @GET("lookupteam.php")
    fun getTeamDetail(@Query(value="id") id: String): Observable<TeamListModel>

    companion object Factory {
        fun create(): ApiServiceInterface {
            val builder = OkHttpClient.Builder()
            if (BuildConfig.DEBUG) {
                val interceptor = HttpLoggingInterceptor()
                interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
                builder.addInterceptor(interceptor)
            }
            val client = builder.build()
            val retrofit = retrofit2.Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.BASE_URL)
                .client(client)
                .build()

            return retrofit.create(ApiServiceInterface::class.java)
        }
    }
}