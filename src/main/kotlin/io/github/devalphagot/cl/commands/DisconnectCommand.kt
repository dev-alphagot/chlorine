package io.github.devalphagot.cl.commands

import dev.jorel.commandapi.CommandAPICommand
import dev.jorel.commandapi.arguments.BooleanArgument
import dev.jorel.commandapi.arguments.LiteralArgument
import dev.jorel.commandapi.arguments.MultiLiteralArgument
import dev.jorel.commandapi.arguments.StringArgument
import dev.jorel.commandapi.executors.PlayerCommandExecutor
import io.github.devalphagot.cl.Main.Companion.db
import io.github.devalphagot.cl.Main.Companion.main
import io.github.devalphagot.cl.component
import io.github.devalphagot.cl.connectors.ChzzkConnector
import io.github.devalphagot.cl.connectors.SoopConnector
import io.github.devalphagot.cl.connectors.ThreadHolder
import io.github.devalphagot.cl.types.MessageType
import io.github.devalphagot.cl.types.MessageType.*

object DisconnectCommand: CommandGroup {
    override fun kommand() {
        val cac = CommandAPICommand("disconnect").withAliases("disconn", "dconn")

        cac.copy().withArguments(
            MultiLiteralArgument("platform",
                "chzzk", "soop", "toonation", "youtube"
            )
        ).withPermission("cl.general").executesPlayer(PlayerCommandExecutor { p, a ->
            val pf = a["platform"].toString()

            when(pf){
                "chzzk" -> ChzzkConnector.disconnect(p.uniqueId)
                "soop" -> SoopConnector.disconnect(p.uniqueId)
                "tonation" -> ThreadHolder.disconnectToonation(p.uniqueId)
                "youtube" -> ThreadHolder.disconnectYouTube(p.uniqueId)
                else -> ""
            }

            db.set("${p.uniqueId}.${pf}", null)

            p.sendMessage(
                "플랫폼 ${
                    when(pf){
                        "chzzk" -> "치지직"
                        "soop" -> "숲"
                        "tonation" -> "투네이션"
                        "youtube" -> "유튜브"
                        else -> ""
                    }
                }와(과)의 연결을 해제했습니다.".component
            )
        }).register(main)
    }
}