package io.github.devalphagot.cl.commands

import dev.jorel.commandapi.CommandAPICommand
import dev.jorel.commandapi.arguments.LiteralArgument
import dev.jorel.commandapi.arguments.StringArgument
import dev.jorel.commandapi.executors.PlayerCommandExecutor
import io.github.devalphagot.cl.Main.Companion.db
import io.github.devalphagot.cl.Main.Companion.main
import io.github.devalphagot.cl.component
import io.github.devalphagot.cl.connectors.ChzzkConnector
import io.github.devalphagot.cl.connectors.SoopConnector
import io.github.devalphagot.cl.connectors.ThreadHolder
import io.github.devalphagot.cl.types.MessageType

object ConnectCommand: CommandGroup {
    override fun kommand() {
        val cac = CommandAPICommand("connect").withAliases("conn")

        cac.copy().withArguments(
            LiteralArgument("chzzk"), StringArgument("key")
        ).withPermission("cl.general").executesPlayer(PlayerCommandExecutor { p, a ->
            val key = a.get("key")

            if(db.contains(p.uniqueId.toString())){
                ChzzkConnector.disconnect(p.uniqueId)
                db["${p.uniqueId}.chzzk.key"] = key
            }
            else {
                db.createSection(
                    p.uniqueId.toString()
                )
                db.createSection(
                    "${p.uniqueId}.chzzk",
                    mapOf(
                        "key" to key
                    )
                )
                db.createSection(
                    "${p.uniqueId}.chzzk.enabled",
                    MessageType.entries.associate {
                        it.name.lowercase() to true
                    }
                )
            }

            ChzzkConnector.connect(p.uniqueId)

            p.sendMessage(
                "$key 채널을 등록했습니다.".component
            )
        }).register(main)

        cac.copy().withArguments(
            LiteralArgument("soop"), StringArgument("key")
        ).withPermission("cl.general").executesPlayer(PlayerCommandExecutor { p, a ->
            val key = a.get("key")

            if(db.contains(p.uniqueId.toString())){
                SoopConnector.disconnect(p.uniqueId)
                db["${p.uniqueId}.soop.key"] = key
            }
            else {
                db.createSection(
                    p.uniqueId.toString()
                )
                db.createSection(
                    "${p.uniqueId}.soop",
                    mapOf(
                        "key" to key
                    )
                )
                db.createSection(
                    "${p.uniqueId}.soop.enabled",
                    MessageType.entries.associate {
                        it.name.lowercase() to true
                    }
                )
            }

            SoopConnector.connect(p.uniqueId)

            p.sendMessage(
                "$key 채널을 등록했습니다.".component
            )
        }).register(main)

        cac.copy().withArguments(
            LiteralArgument("toonation"), StringArgument("key")
        ).withPermission("cl.general").executesPlayer(PlayerCommandExecutor { p, a ->
            val key = a.get("key")

            if(db.contains(p.uniqueId.toString())){
                ThreadHolder.disconnectToonation(p.uniqueId)
                db["${p.uniqueId}.toonation.key"] = key
            }
            else {
                db.createSection(
                    p.uniqueId.toString()
                )
                db.createSection(
                    "${p.uniqueId}.toonation",
                    mapOf(
                        "key" to key,
                        "enabled" to true
                    )
                )
            }

            ThreadHolder.connectToonation(p.uniqueId)

            p.sendMessage(
                "$key 투네이션을 등록했습니다.".component
            )
        }).register(main)

        cac.copy().withArguments(
            LiteralArgument("youtube"), StringArgument("key")
        ).withPermission("cl.general").executesPlayer(PlayerCommandExecutor { p, a ->
            val key = a.get("key").toString()

            if(db.contains(p.uniqueId.toString())){
                ThreadHolder.disconnectYouTube(p.uniqueId)
                db["${p.uniqueId}.youtube.key"] = key
            }
            else {
                db.createSection(
                    p.uniqueId.toString()
                )
                db.createSection(
                    "${p.uniqueId}.youtube",
                    mapOf(
                        "key" to key
                    )
                )
                db.createSection(
                    "${p.uniqueId}.youtube.enabled",
                    MessageType.entries.associate {
                        it.name.lowercase() to true
                    }
                )
            }

            ThreadHolder.connectYouTube(p.uniqueId, key)

            p.sendMessage(
                "$key 실시간 방송을 등록했습니다.".component
            )
        }).register(main)
    }
}