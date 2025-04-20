package io.github.devalphagot.cl.connectors

import io.github.devalphagot.cl.Main.Companion.db
import io.github.devalphagot.cl.Main.Companion.main
import io.github.devalphagot.cl.events.SoopMessageEvent
import io.github.devalphagot.cl.exceptions.Player404Exception
import io.github.devalphagot.cl.types.MessageType
import me.taromati.afreecatv.AfreecatvAPI
import me.taromati.afreecatv.AfreecatvAPI.AfreecatvBuilder
import me.taromati.afreecatv.event.implement.DonationChatEvent
import me.taromati.afreecatv.event.implement.MessageChatEvent
import me.taromati.afreecatv.listener.AfreecatvListener
import java.util.UUID

object SoopConnector {
    private val soopChats = mutableMapOf<UUID, AfreecatvAPI>()

    fun connect(uuid: UUID){
        db.getConfigurationSection(uuid.toString())?.run {
            val key = getString("soop.key") ?: return@run

            soopChats[uuid] = AfreecatvBuilder().withData(key).build()
                .addListeners(
                    object: AfreecatvListener {
                        override fun onMessageChat(e: MessageChatEvent) {
                            val enabled = getBoolean("soop.enabled.chat", true)
                            if(!enabled) return

                            main.server.scheduler.getMainThreadExecutor(main).execute {
                                main.server.pluginManager.callEvent(
                                    SoopMessageEvent(
                                        main.server.getPlayer(uuid) ?: throw Player404Exception(),
                                        MessageType.CHAT, e
                                    )
                                )
                            }
                        }

                        override fun onDonationChat(e: DonationChatEvent) {
                            val enabled = getBoolean("soop.enabled.donation", true)
                            if(!enabled) return

                            main.server.scheduler.getMainThreadExecutor(main).execute {
                                main.server.pluginManager.callEvent(
                                    SoopMessageEvent(
                                        main.server.getPlayer(uuid) ?: throw Player404Exception(),
                                        MessageType.DONATION, e
                                    )
                                )
                            }
                        }
                    }
                )
                .connect()
        } ?: return
    }

    fun disconnect(uuid: UUID){
        soopChats.remove(uuid)?.disconnect()
    }

    fun dispose(){
        soopChats.values.forEach { it.disconnect() }
    }
}