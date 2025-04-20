package io.github.devalphagot.cl.handler.message

import io.github.devalphagot.cl.Main.Companion.main
import io.github.devalphagot.cl.component
import io.github.devalphagot.cl.events.ChzzkMessageEvent
import io.github.devalphagot.cl.timeText
import io.github.devalphagot.cl.types.MessageType.*
import net.kyori.adventure.sound.Sound
import net.kyori.adventure.title.Title
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import xyz.r2turntrue.chzzk4j.chat.DonationMessage
import xyz.r2turntrue.chzzk4j.chat.MissionDonationMessage
import xyz.r2turntrue.chzzk4j.chat.SubscriptionMessage
import java.time.Duration

class ChzzkHandler: Listener {
    @EventHandler
    fun onChzzk(e: ChzzkMessageEvent){
        when(e.messageType){
            CHAT -> {
                e.player.sendMessage(
                    (
                            e.message.profile?.let { pf ->
                                "${
                                    if(pf.hasSubscription()) pf.subscription!!.let { ss ->
                                        "[<light_purple>${ss.tier}</light_purple>티어] "
                                    }
                                    else ""
                                }<${
                                    if(pf.isVerifiedMark) "green"
                                    else if(pf.hasSubscription()) "gold"
                                    else "gray"
                                }>${pf.nickname}<reset>: "
                            } ?: "<gray>(익명의 시청자)<reset>: "
                    ).component.append(
                        e.message.content.component
                    )
                )
            }
            DONATION -> {
                (e.message as DonationMessage).let { dm ->
                    e.player.showTitle(
                        Title.title(
                            "${dm.payAmount}원 / ${
                                e.message.profile?.let { pf ->
                                    "<${
                                        if(pf.isVerifiedMark) "green"
                                        else if(pf.hasSubscription()) "gold"
                                        else "gray"
                                    }>${pf.nickname}님"
                                } ?: "<gray>(익명의 시청자)"
                            }".component,
                            "<gray>${dm.content}".component,
                            Title.Times.times(
                                Duration.ofMillis(5L * 50),
                                Duration.ofMillis(2L * 50 * (dm.content.encodeToByteArray().size)),
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
                (e.message as SubscriptionMessage).let { dm ->
                    e.player.showTitle(
                        Title.title(
                            "${dm.subscriptionMonth}개월 / ${
                                e.message.profile?.let { pf ->
                                    "<${
                                        if(pf.isVerifiedMark) "green"
                                        else if(pf.hasSubscription()) "gold"
                                        else "gray"
                                    }>${pf.nickname}님"
                                } ?: "<gray>(익명의 시청자)"
                            }".component,
                            "<gray>${dm.subscriptionTierName}".component,
                            Title.Times.times(
                                Duration.ofMillis(5L * 50),
                                Duration.ofMillis(35L * 50),
                                Duration.ofMillis(5L * 50)
                            )
                        )
                    )
                    e.player.playSound(
                        main.sound, Sound.Emitter.self()
                    )
                }
            }
            MISSION -> {
                (e.message as MissionDonationMessage).let { dm ->
                    e.player.showTitle(
                        Title.title(
                            "${dm.payAmount}원 / ${
                                e.message.profile?.let { pf ->
                                    "<${
                                        if(pf.isVerifiedMark) "green"
                                        else if(pf.hasSubscription()) "gold"
                                        else "gray"
                                    }>${pf.nickname}님"
                                } ?: "<gray>(익명의 시청자)"
                            }".component,
                            "${dm.durationTime.toLong().timeText} / <gray>${dm.missionText}".component,
                            Title.Times.times(
                                Duration.ofMillis(5L * 50),
                                Duration.ofMillis(2L * 50 * (dm.missionText.encodeToByteArray().size)),
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