package io.github.devalphagot.cl.handler.message

import io.github.devalphagot.cl.Main.Companion.main
import io.github.devalphagot.cl.component
import io.github.devalphagot.cl.events.SoopMessageEvent
import io.github.devalphagot.cl.events.ToonationMessageEvent
import io.github.devalphagot.cl.timeText
import io.github.devalphagot.cl.types.MessageType.*
import me.taromati.afreecatv.event.implement.DonationChatEvent
import me.taromati.afreecatv.event.implement.MessageChatEvent
import net.kyori.adventure.sound.Sound
import net.kyori.adventure.title.Title
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import xyz.r2turntrue.chzzk4j.chat.DonationMessage
import xyz.r2turntrue.chzzk4j.chat.MissionDonationMessage
import xyz.r2turntrue.chzzk4j.chat.SubscriptionMessage
import java.time.Duration

class ToonationHandler: Listener {
    @EventHandler
    fun onChzzk(e: ToonationMessageEvent){
        when(e.messageType){
            DONATION -> {
                e.message.let { dm ->
                    e.player.showTitle(
                        Title.title(
                            "${dm.amount}원 / ${"<aqua>${dm.nickName}<reset>"}".component,
                            "<gray>${dm.comment}".component,
                            Title.Times.times(
                                Duration.ofMillis(5L * 50),
                                Duration.ofMillis(2L * 50 * (dm.comment.encodeToByteArray().size)),
                                Duration.ofMillis(5L * 50)
                            )
                        )
                    )
                    e.player.playSound(
                        main.sound, Sound.Emitter.self()
                    )
                }
            }
            else -> {}
        }
    }
}