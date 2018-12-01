package com.musasyihab.footballclub2.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.google.gson.Gson
import com.musasyihab.footballclub2.model.EventModel
import com.musasyihab.footballclub2.model.FavoriteModel
import org.jetbrains.anko.db.*
import java.util.*

class DBHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "FavoriteEvent.db", null, 1) {
    companion object {
        private var instance: DBHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): DBHelper {
            if (instance == null) {
                instance = DBHelper(ctx.applicationContext)
            }
            return instance as DBHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Here you create tables
        db.createTable(FavoriteModel.TABLE_FAVORITE, true,
            FavoriteModel.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            FavoriteModel.ID_EVENT to TEXT + UNIQUE,
            FavoriteModel.STR_EVENT to TEXT,
            FavoriteModel.STR_HOME_TEAM to TEXT,
            FavoriteModel.STR_AWAY_TEAM to TEXT,
            FavoriteModel.INT_HOME_SCORE to TEXT,
            FavoriteModel.INT_AWAY_SCORE to TEXT,
            FavoriteModel.DATE_EVENT to TEXT,
            FavoriteModel.STR_TIME to TEXT,
            FavoriteModel.ID_HOME_TEAM to TEXT,
            FavoriteModel.ID_AWAY_TEAM to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Here you can upgrade tables, as usual
        db.dropTable(FavoriteModel.TABLE_FAVORITE, true)
    }

    fun getFavoriteList(): List<FavoriteModel> {
        var favorites = Collections.emptyList<FavoriteModel>()
        instance?.use {
            val result = select(FavoriteModel.TABLE_FAVORITE)
            favorites = result.parseList(classParser())
        }
        return favorites
    }

    fun getFavoriteById(id: String): FavoriteModel? {
        var favorite: FavoriteModel? = null
        instance?.use {
            val result = select(FavoriteModel.TABLE_FAVORITE)
                .whereArgs("(${FavoriteModel.ID_EVENT} = {id})",
                    "id" to id)
            val list = result.parseList(classParser<FavoriteModel>())
            if(!list.isEmpty()) favorite = list[0]
        }
        val json = Gson().toJson(favorite)
        Log.e("favorite", json)
        return favorite
    }

    fun addToFavoriteList(event: EventModel) {
        instance?.use {
            insert(FavoriteModel.TABLE_FAVORITE,
                FavoriteModel.ID_EVENT to event.idEvent,
                FavoriteModel.STR_EVENT to event.strEvent,
                FavoriteModel.STR_HOME_TEAM to event.strHomeTeam,
                FavoriteModel.STR_AWAY_TEAM to event.strAwayTeam,
                FavoriteModel.INT_HOME_SCORE to event.intHomeScore,
                FavoriteModel.INT_AWAY_SCORE to event.intAwayScore,
                FavoriteModel.DATE_EVENT to event.dateEvent,
                FavoriteModel.STR_TIME to event.strTime,
                FavoriteModel.ID_HOME_TEAM to event.idHomeTeam,
                FavoriteModel.ID_AWAY_TEAM to event.idAwayTeam)
        }
    }

    fun deleteFromFavoriteList(id: String) {
        instance?.use {
            delete(FavoriteModel.TABLE_FAVORITE, "(${FavoriteModel.ID_EVENT} = {id})",
                "id" to id)
        }
    }
}
