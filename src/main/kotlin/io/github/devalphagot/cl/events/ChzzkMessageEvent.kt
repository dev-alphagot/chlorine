package io.github.devalphagot.cl.events

import io.github.devalphagot.cl.types.MessageType
import org.bukkit.entity.Player
import org.bukkit.event.Event
import org.bukkit.event.HandlerList
import xyz.r2turntrue.chzzk4j.chat.ChatMessage

class ChzzkMessageEvent(
    val player: Player, val messageType: MessageType, val message: ChatMessage
): Event() {
    override fun getHandlers(): HandlerList {
        return handlerList
    }

    companion object {
        @JvmStatic
        val handlerList: HandlerList = HandlerList()
    }
}