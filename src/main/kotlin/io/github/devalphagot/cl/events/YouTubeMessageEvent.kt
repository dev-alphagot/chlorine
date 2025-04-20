package io.github.devalphagot.cl.events

import com.github.kusaanko.youtubelivechat.ChatItem
import io.github.devalphagot.cl.types.MessageType
import org.bukkit.entity.Player
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

class YouTubeMessageEvent(
    val player: Player, val messageType: MessageType, val message: ChatItem
): Event() {
    override fun getHandlers(): HandlerList {
        return handlerList
    }

    companion object {
        @JvmStatic
        val handlerList: HandlerList = HandlerList()
    }
}