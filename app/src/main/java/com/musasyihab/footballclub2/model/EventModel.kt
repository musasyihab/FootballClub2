package com.musasyihab.footballclub2.model

data class EventModel (
    val idEvent: String,
    val idSoccerXML: String,
    val strEvent: String,
    val strFilename: String,
    val strSport: String,
    val idLeague: String,
    val strLeague: String,
    val strSeason: String,
    val strDescriptionEN: String,
    val strHomeTeam: String,
    val strAwayTeam: String,
    val intHomeScore: String?,
    val intRound: String,
    val intAwayScore: String?,
    val intSpectators: String,
    val strHomeGoalDetails: String,
    val strHomeRedCards: String,
    val strHomeYellowCards: String,
    val strHomeLineupGoalkeeper: String,
    val strHomeLineupDefense: String,
    val strHomeLineupMidfield: String,
    val strHomeLineupForward: String,
    val strHomeLineupSubstitutes: String,
    val strHomeFormation: String,
    val strAwayRedCards: String,
    val strAwayYellowCards: String,
    val strAwayGoalDetails: String,
    val strAwayLineupGoalkeeper: String,
    val strAwayLineupDefense: String,
    val strAwayLineupMidfield: String,
    val strAwayLineupForward: String,
    val strAwayLineupSubstitutes: String,
    val strAwayFormation: String,
    val intHomeShots: String,
    val intAwayShots: String,
    val dateEvent: String,
    val strDate: String,
    val strTime: String,
    val strTVStation: String,
    val idHomeTeam: String,
    val idAwayTeam: String,
    val strResult: String,
    val strCircuit: String,
    val strCountry: String,
    val strCity: String,
    val strPoster: String,
    val strFanart: String,
    val strThumb: String,
    val strBanner: String,
    val strMap: String,
    val strLocked: String
)