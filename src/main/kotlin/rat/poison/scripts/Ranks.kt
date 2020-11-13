package rat.poison.scripts

import rat.poison.game.entity.*
import rat.poison.game.forEntities
import rat.poison.game.rankName
import rat.poison.overlay.App.haveTarget
import rat.poison.overlay.opened
import rat.poison.ui.uiPanels.ranksTab
import rat.poison.ui.uiRefreshing
import rat.poison.utils.RanksPlayer
import rat.poison.utils.every
import rat.poison.utils.extensions.roundNDecimals

var ranksPlayerList = mutableListOf<RanksPlayer>()

fun ranks() = every(5000, true, inGameCheck = true) { //Rebuild every second
    if (!opened || !haveTarget) return@every

    //Bruh -- fix later
    ranksPlayerList.clear()
    forEntities(EntityType.CCSPlayer) {
        val entity = it.entity

        if (entity.hltv()) return@forEntities

        val entTeam = entity.team()

        val entName = entity.name()
        val entRank = entity.rank().rankName()
        val entKills = entity.kills().toString()
        val entDeaths = entity.deaths().toString()
        val entMoney = entity.money()
        val entScore = entity.score()
        val entKD = when (entDeaths) {
            "0" -> "N/A"
            else -> (entKills.toFloat() / entDeaths.toFloat()).roundNDecimals(2).toString()
        }
        val entWins = entity.wins().toString()

        val teamStr = when (entTeam) { //Bruh
            3L -> "CT"
            2L -> "T"
            else -> "N/A"
        }

        var steamID = entity.getValidSteamID()

        val player = RanksPlayer(entName, steamID, entRank, teamStr, entKills, entDeaths, entKD, entWins, entMoney, entTeam, entScore)
        ranksPlayerList.add(player)
    }
    ranksPlayerList.sort()

    if (!uiRefreshing) {
        ranksTab.updateRanks()
    }
}
