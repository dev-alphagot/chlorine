package io.github.devalphagot.cl.events

import io.github.devalphagot.cl.types.MessageType
import me.taromati.afreecatv.event.AfreecatvEvent
import org.bukkit.entity.Player
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

class SoopMessageEvent(
    val player: Player, val messageType: MessageType, val message: AfreecatvEvent
): Event() {
    override fun getHandlers(): HandlerList {
        return handlerList
    }

    companion object {
        @JvmStatic
        val handlerList: HandlerList = HandlerList()
    }
}