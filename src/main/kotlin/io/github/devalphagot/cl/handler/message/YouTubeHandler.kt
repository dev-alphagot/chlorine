package io.github.devalphagot.cl.handler.message

import io.github.devalphagot.cl.Main.Companion.main
import io.github.devalphagot.cl.component
import io.github.devalphagot.cl.events.ChzzkMessageEvent
import io.github.devalphagot.cl.events.YouTubeMessageEvent
import io.github.devalphagot.cl.timeText
import io.github.devalphagot.cl.types.MessageType.*
import net.kyori.adventure.sound.Sound
import net.kyori.adventure.text.event.ClickEvent
import net.kyori.adventure.title.Title
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import xyz.r2turntrue.chzzk4j.chat.DonationMessage
import xyz.r2turntrue.chzzk4j.chat.MissionDonationMessage
import xyz.r2turntrue.chzzk4j.chat.SubscriptionMessage
import java.time.Duration

class YouTubeHandler: Listener {
    @EventHandler
    fun onChzzk(e: YouTubeMessageEvent){
        when(e.messageType){
            CHAT -> {
                e.player.sendMessage(
                    (
                            e.message.let { pf ->
                                "<${
                                    if(pf.isAuthorOwner || pf.isAuthorModerator) "green"
                                    else if(pf.isAuthorMember || pf.isAuthorVerified) "gold"
                                    else "gray"
                                }>${pf.authorName}<reset>: "
                            } ?: "<gray>(익명의 시청자)<reset>: "
                    ).component.append(
                        e.message.message.component
                    )
                )
            }
            DONATION -> {
                e.message.let { dm ->
                    e.player.showTitle(
                        Title.title(
                            "${dm.purchaseAmount}원 / ${
                                "<${
                                    if(dm.isAuthorOwner || dm.isAuthorModerator) "green"
                                    else if(dm.isAuthorMember || dm.isAuthorVerified) "gold"
                                    else "gray"
                                }>${dm.authorName}<reset>"
                            }".component,
                            "<gray>${dm.message}".component,
                            Title.Times.times(
                                Duration.ofMillis(5L * 50),
                                Duration.ofMillis(2L * 50 * (dm.message.encodeToByteArray().size)),
                                Duration.ofMillis(5L * 50)
                            )
                        )
                    )
                    e.player.playSound(
                        main.sound, Sound.Emitter.self()
                    )
                }
            }
            SUBSCRIBE -> {
                e.message.let { dm ->
                    e.player.showTitle(
                        Title.title(
                            "<${
                                if(dm.isAuthorOwner || dm.isAuthorModerator) "green"
                                else if(dm.isAuthorMember || dm.isAuthorVerified) "gold"
                                else "gray"
                            }>${dm.authorName}<reset>".component,
                            "신규 멤버십 가입!".component,
                            Title.Times.times(
                                Duration.ofMillis(5L * 50),
                                Duration.ofMillis(50L * 50),
                                Duration.ofMillis(5L * 50)
                            )
                        )
                    )
                    e.player.playSound(
                        main.sound, Sound.Emitter.self()
                    )
                }
            }
            STICKER -> {
                e.message.let { dm ->
                    e.player.showTitle(
                        Title.title(
                            "${dm.purchaseAmount}원 / ${
                                "<${
                                    if(dm.isAuthorOwner || dm.isAuthorModerator) "green"
                                    else if(dm.isAuthorMember || dm.isAuthorVerified) "gold"
                                    else "gray"
                                }>${dm.authorName}<reset>"
                            }".component,
                            "<gray>스티커를 보냈습니다.".component,
                            Title.Times.times(
                                Duration.ofMillis(5L * 50),
                                Duration.ofMillis(35L * 50),
                                Duration.ofMillis(5L * 50)
                            )
                        )
                    )
                    e.player.sendMessage(
                        (
                                e.message.let { pf ->
                                    "<${
                                        if(pf.isAuthorOwner || pf.isAuthorModerator) "green"
                                        else if(pf.isAuthorMember || pf.isAuthorVerified) "gold"
                                        else "gray"
                                    }>${pf.authorName}<reset>: "
                                } ?: "<gray>(익명의 시청자)<reset>: "
                                ).component.append(
                                    "[스티커를 보려면 클릭]".component.clickEvent(
                                        ClickEvent.openUrl(
                                            e.message.stickerIconURL
                                        )
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