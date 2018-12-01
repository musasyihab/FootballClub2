package com.musasyihab.footballclub2.util

import com.musasyihab.footballclub2.model.EventModel
import com.musasyihab.footballclub2.model.FavoriteModel
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object Helper {

    private val defaultLocale = Locale("en", "UK")
    private const val defaultDateFormat = "yyyy-MM-dd"
    private const val fullHourFormat = "HH:mm:ssXXX"
    private const val fullDateFormat = "EEEE, d MMMM yyyy"
    private const val simpleHourFormat = "HH:mm"

    fun formatToReadableDate(date: String): String {
        return formatDate(defaultDateFormat, date, fullDateFormat)
    }

    fun formatToReadableTime(date: String): String {
        return formatDate(fullHourFormat, date, simpleHourFormat)
    }

    fun formatDate(dateFormat: String, date: String, toFormat: String): String {
        var formatted = ""
        var formatter: DateFormat = SimpleDateFormat(dateFormat, defaultLocale)
        try {
            val dateStr = formatter.parse(date)
            formatted = formatter.format(dateStr)
            val formatDate = formatter.parse(formatted)
            formatter = SimpleDateFormat(toFormat, defaultLocale)
            formatter.timeZone = TimeZone.getTimeZone("UK")
            formatted = formatter.format(formatDate)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return formatted
    }

    fun formatGoalDetail(goalDetail: String?): String {
        var result = ""
        val players: List<String> = goalDetail?.split(";") ?: Collections.emptyList()
        for((index, item) in players.withIndex()) {
            val details = item.split(":")
            if (details.size == 2) {
                result += details[1] + " (" + details[0] + ")"
                if (index != players.size - 1) {
                    result += "\n"
                }
            }
        }
        return result
    }

    fun formatLineup(lineup: String?): String {
        var result = ""
        val players: List<String> = lineup?.split(";") ?: Collections.emptyList()
        for((index, item) in players.withIndex()) {
            result += item.trim()
            if(index != players.size-1) {
                result += "\n"
            }
        }
        return result
    }

    fun isPastEvent(event: EventModel?): Boolean {
        if(event == null) return false
        val result = !event.intHomeScore.isNullOrEmpty()
        return !event.intHomeScore.isNullOrEmpty()
    }

    fun convertFavoriteToEvent(favorite: FavoriteModel): EventModel {
        val event = EventModel(favorite.idEvent, "", favorite.strEvent,
            "", "", "", "", "", "",
            favorite.strHomeTeam, favorite.strAwayTeam, favorite.intHomeScore, "", favorite.intAwayScore,
            "", "", "", "", "", "",
            "", "", "", "", "", "",
            "", "", "", "", "", "",
            "", "", "", favorite.dateEvent, "", favorite.strTime, "",
            favorite.idHomeTeam, favorite.idAwayTeam, "", "", "", "", "", "",
            "", "", "", "")
        return event
    }
}