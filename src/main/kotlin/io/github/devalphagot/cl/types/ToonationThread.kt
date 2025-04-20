package io.github.devalphagot.cl.types

import com.outstandingboy.donationalert.platform.Toonation
import io.github.devalphagot.cl.Main.Companion.db
import io.github.devalphagot.cl.Main.Companion.main
import io.github.devalphagot.cl.events.ToonationMessageEvent
import io.github.devalphagot.cl.exceptions.Player404Exception
import io.github.devalphagot.cl.connectors.ThreadHolder
import java.util.UUID

class ToonationThread(
    private val key: String,
    private val owner: UUID
) {
    private var thread: Thread = Thread {
        try {
            val toonation = Toonation(key)

            toonation.subscribeDonation { donation ->
                val enabled = (db.getConfigurationSection(owner.toString()) ?: return@subscribeDonation).getBoolean("toonation.enabled", true)
                if(!enabled) return@subscribeDonation

                main.server.scheduler.getMainThreadExecutor(main).execute {
                    main.server.pluginManager.callEvent(
                        ToonationMessageEvent(
                            main.server.getPlayer(
                                owner
                            ) ?: throw Player404Exception(),
                            MessageType.DONATION, donation
                        )
                    )
                }
            }
        }
        catch(_: InterruptedException) {
            ThreadHolder.toonationThread.remove(owner)
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