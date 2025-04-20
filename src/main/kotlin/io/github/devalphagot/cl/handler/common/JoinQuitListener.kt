package io.github.devalphagot.cl.handler.common

import io.github.devalphagot.cl.connectors.ChzzkConnector
import io.github.devalphagot.cl.connectors.SoopConnector
import io.github.devalphagot.cl.connectors.ThreadHolder
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

class JoinQuitListener: Listener {
    @EventHandler
    fun onJoin(e: PlayerJoinEvent){
        ChzzkConnector.connect(e.player.uniqueId)
        SoopConnector.connect(e.player.uniqueId)
        ThreadHolder.connectToonation(e.player.uniqueId)
    }

    @EventHandler
    fun onQuit(e: PlayerQuitEvent){
        ChzzkConnector.disconnect(e.player.uniqueId)
        SoopConnector.disconnect(e.player.uniqueId)
        ThreadHolder.disconnectToonation(e.player.uniqueId)
        ThreadHolder.disconnectYouTube(e.player.uniqueId)
    }
}