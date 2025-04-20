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

object ConfigCommand: CommandGroup {
    override fun kommand() {
        val cac = CommandAPICommand("config").withAliases("cfg", "conf")

        cac.copy().withArguments(
            MultiLiteralArgument("platform",
                "chzzk", "soop", "toonation", "youtube"
            ), CommonPredicates.enum<MessageType>("section"), BooleanArgument("enabled")
        ).withPermission("cl.general").executesPlayer(PlayerCommandExecutor { p, a ->
            val pf = a["platform"].toString()
            val sec = a["section"] as MessageType
            val en = a["enabled"] as Boolean

            if(db.contains(p.uniqueId.toString())){
                if(pf != "toonation"){
                    db["${p.uniqueId}.${pf}.enabled.${sec.toString().lowercase()}"] = en
                }
                else {
                    db["${p.uniqueId}.${pf}.enabled"] = en
                }
            }
            else {
                db.createSection(
                    p.uniqueId.toString()
                )

                if(pf != "toonation"){
                    db.createSection(
                        "${p.uniqueId}.${pf}"
                    )
                    db.createSection(
                        "${p.uniqueId}.${pf}.enabled",
                        MessageType.entries.associateWith {
                            it.name.lowercase() to true
                        }
                    )
                    db["${p.uniqueId}.${pf}.enabled.${sec.toString().lowercase()}"] = en
                }
                else {
                    db.createSection(
                        "${p.uniqueId}.${pf}",
                        mapOf(
                            "enabled" to en
                        )
                    )
                }
            }

            p.sendMessage(
                "플랫폼 ${
                    when(pf){
                        "chzzk" -> "치지직"
                        "soop" -> "숲"
                        "tonation" -> "투네이션"
                        "youtube" -> "유튜브"
                        else -> ""
                    }
                }의 ${
                    sec.koreanName
                } 기능을 ${if(en) "" else "비"}활성화했습니다.".component
            )
        }).register(main)
    }
}