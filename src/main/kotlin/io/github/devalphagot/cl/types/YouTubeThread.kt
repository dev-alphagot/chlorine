package io.github.devalphagot.cl.types

import com.github.kusaanko.youtubelivechat.ChatItemType.MESSAGE
import com.github.kusaanko.youtubelivechat.ChatItemType.PAID_MESSAGE
import com.github.kusaanko.youtubelivechat.ChatItemType.PAID_STICKER
import com.github.kusaanko.youtubelivechat.ChatItemType.TICKER_PAID_MESSAGE
import com.github.kusaanko.youtubelivechat.ChatItemType.NEW_MEMBER_MESSAGE
import com.github.kusaanko.youtubelivechat.IdType
import com.github.kusaanko.youtubelivechat.YouTubeLiveChat
import io.github.devalphagot.cl.Main.Companion.db
import io.github.devalphagot.cl.Main.Companion.main
import io.github.devalphagot.cl.events.YouTubeMessageEvent
import io.github.devalphagot.cl.exceptions.Player404Exception
import io.github.devalphagot.cl.connectors.ThreadHolder
import java.lang.Thread.sleep
import java.util.Locale
import java.util.UUID

class YouTubeThread(
    private val videoId: String,
    private val owner: UUID
) {
    private var thread: Thread = Thread {
        try {
            val chat = YouTubeLiveChat(
                videoId,
                true,
                IdType.VIDEO
            )
            chat.setLocale(Locale.KOREA)

            var liveStatusCheckCycle = 0

            val cc = (db.getConfigurationSection(owner.toString()) ?: return@Thread).getBoolean("youtube.enabled.chat", true)
            var ccd = false

            db["${owner}.youtube.enabled.chat"] = false

            while (true) {
                chat.update()

                for (item in chat.chatItems) {
                    val enabled = (db.getConfigurationSection(owner.toString()) ?: continue).getBoolean("youtube.enabled.${
                        when(item.type){
                            MESSAGE -> "chat"
                            PAID_MESSAGE, TICKER_PAID_MESSAGE -> "donation"
                            PAID_STICKER -> "sticker"
                            NEW_MEMBER_MESSAGE -> "subscribe"
                        }
                    }", true)
                    if(!enabled) continue

                    main.server.scheduler.getMainThreadExecutor(main).execute {
                        main.server.pluginManager.callEvent(
                            YouTubeMessageEvent(
                                main.server.getPlayer(owner) ?: throw Player404Exception(),
                                when(item.type){
                                    MESSAGE -> MessageType.CHAT
                                    PAID_MESSAGE, TICKER_PAID_MESSAGE -> MessageType.DONATION
                                    PAID_STICKER -> MessageType.STICKER
                                    NEW_MEMBER_MESSAGE -> MessageType.SUBSCRIBE
                                }, item
                            )
                        )
                    }
                }

                liveStatusCheckCycle++
                if (liveStatusCheckCycle >= 10) {
                    // Calling this method frequently, cpu usage and network usage become higher because this method requests a http request.
                    if (!chat.broadcastInfo.liveNow) {
                        break
                        ThreadHolder.youtubeThread.remove(owner)
                    }
                    liveStatusCheckCycle = 0
                }

                try {
                    sleep(2000)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }

                if(!ccd){
                    db["${owner}.youtube.enabled.chat"] = cc
                    ccd = true
                }
            }
        } catch (_: InterruptedException) {
            ThreadHolder.youtubeThread.remove(owner)
        }
    }

    init {
        thread.start()
    }

    fun cancel(){
        thread.stop()
        thread.join()
    }
}