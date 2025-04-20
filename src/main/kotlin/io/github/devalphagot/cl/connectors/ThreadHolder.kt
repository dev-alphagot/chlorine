package io.github.devalphagot.cl.connectors

import io.github.devalphagot.cl.Main.Companion.db
import io.github.devalphagot.cl.types.ToonationThread
import io.github.devalphagot.cl.types.YouTubeThread
import java.util.UUID

object ThreadHolder {
    val youtubeThread = mutableMapOf<UUID, YouTubeThread>()
    val toonationThread = mutableMapOf<UUID, ToonationThread>()

    fun connectToonation(owner: UUID){
        toonationThread[owner] = ToonationThread(
            (
                    db.getConfigurationSection(owner.toString()) ?: return
            ).getString("key") ?: return, owner
        )
    }

    fun connectYouTube(owner: UUID, videoId: String){
        youtubeThread[owner] = YouTubeThread(
            videoId, owner
        )
    }

    fun disconnectToonation(owner: UUID){
        toonationThread.remove(owner)?.cancel()
    }

    fun disconnectYouTube(owner: UUID){
        youtubeThread.remove(owner)?.cancel()
    }

    fun dispose(){
        youtubeThread.values.forEach { it.cancel() }
        toonationThread.values.forEach { it.cancel() }
    }
}