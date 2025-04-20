package io.github.devalphagot.cl.connectors

import io.github.devalphagot.cl.Main.Companion.db
import io.github.devalphagot.cl.Main.Companion.main
import io.github.devalphagot.cl.events.ChzzkMessageEvent
import io.github.devalphagot.cl.exceptions.Player404Exception
import io.github.devalphagot.cl.types.MessageType
import xyz.r2turntrue.chzzk4j.ChzzkBuilder
import xyz.r2turntrue.chzzk4j.chat.ChatEventListener
import xyz.r2turntrue.chzzk4j.chat.ChatMessage
import xyz.r2turntrue.chzzk4j.chat.ChzzkChat
import xyz.r2turntrue.chzzk4j.chat.DonationMessage
import xyz.r2turntrue.chzzk4j.chat.MissionDonationMessage
import xyz.r2turntrue.chzzk4j.chat.SubscriptionMessage
import java.util.UUID

object ChzzkConnector {
    private val chzzkChats = mutableMapOf<UUID, ChzzkChat>()
    private val chzzk = ChzzkBuilder().build()

    fun connect(uuid: UUID){
        db.getConfigurationSection(uuid.toString())?.run {
            val key = getString("chzzk.key") ?: return@run

            var chater = chzzk.chat(key)

            chater.withAutoReconnect(true)
            chater = chater.withChatListener(object: ChatEventListener {
                lateinit var chat: ChzzkChat

                override fun onConnect(ch: ChzzkChat, isReconnecting: Boolean) {
                    chat = ch
                }

                override fun onChat(msg: ChatMessage) {
                    val enabled = getBoolean("chzzk.enabled.chat", true)
                    if(!enabled) return

                    main.server.scheduler.getMainThreadExecutor(main).execute {
                        main.server.pluginManager.callEvent(
                            ChzzkMessageEvent(
                                main.server.getPlayer(uuid) ?: throw Player404Exception(),
                                MessageType.CHAT, msg
                            )
                        )
                    }
                }

                override fun onDonationChat(msg: DonationMessage) {
                    val enabled = getBoolean("chzzk.enabled.donation", true)
                    if(!enabled) return

                    main.server.scheduler.getMainThreadExecutor(main).execute {
                        main.server.pluginManager.callEvent(
                            ChzzkMessageEvent(
                                main.server.getPlayer(uuid) ?: throw Player404Exception(),
                                MessageType.DONATION, msg
                            )
                        )
                    }
                }

                override fun onSubscriptionChat(msg: SubscriptionMessage) {
                    val enabled = getBoolean("chzzk.enabled.subscribe", true)
                    if(!enabled) return

                    main.server.scheduler.getMainThreadExecutor(main).execute {
                        main.server.pluginManager.callEvent(
                            ChzzkMessageEvent(
                                main.server.getPlayer(uuid) ?: throw Player404Exception(),
                                MessageType.SUBSCRIBE, msg
                            )
                        )
                    }
                }

                override fun onMissionDonationChat(msg: MissionDonationMessage) {
                    val enabled = getBoolean("chzzk.enabled.mission", true)
                    if(!enabled) return

                    main.server.scheduler.getMainThreadExecutor(main).execute {
                        main.server.pluginManager.callEvent(
                            ChzzkMessageEvent(
                                main.server.getPlayer(uuid) ?: throw Player404Exception(),
                                MessageType.MISSION, msg
                            )
                        )
                    }
                }
            })

            val ct = chater.build()
            ct.connectAsync()

            chzzkChats[uuid] = ct
        } ?: return
    }

    fun disconnect(uuid: UUID){
        chzzkChats.remove(uuid)?.closeBlocking()
    }

    fun dispose(){
        chzzkChats.values.forEach { it.closeBlocking() }
    }
}