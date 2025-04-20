package io.github.devalphagot.cl.events

import com.outstandingboy.donationalert.entity.Donation
import io.github.devalphagot.cl.types.MessageType
import org.bukkit.entity.Player
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

class ToonationMessageEvent(
    val player: Player, val messageType: MessageType, val message: Donation
): Event() {
    override fun getHandlers(): HandlerList {
        return handlerList
    }

    companion object {
        @JvmStatic
        val handlerList: HandlerList = HandlerList()
    }
}