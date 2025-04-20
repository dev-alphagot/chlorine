package io.github.devalphagot.cl

import dev.jorel.commandapi.CommandAPI
import dev.jorel.commandapi.CommandAPIBukkitConfig
import io.github.devalphagot.cl.commands.CommandGroup
import io.github.devalphagot.cl.connectors.ChzzkConnector
import io.github.devalphagot.cl.connectors.SoopConnector
import io.github.devalphagot.cl.connectors.ThreadHolder
import io.github.devalphagot.cl.exceptions.CannotReadFileException
import io.github.devalphagot.cl.exceptions.CannotWriteFileException
import io.github.devalphagot.cl.handler.common.JoinQuitListener
import io.github.devalphagot.cl.handler.message.ChzzkHandler
import io.github.devalphagot.cl.handler.message.SoopHandler
import io.github.devalphagot.cl.handler.message.ToonationHandler
import io.github.devalphagot.cl.handler.message.YouTubeHandler
import net.kyori.adventure.key.Key
import net.kyori.adventure.sound.Sound
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.java.JavaPlugin
import org.reflections.Reflections
import org.reflections.util.ConfigurationBuilder
import org.reflections.util.FilterBuilder
import java.io.File
import java.io.IOException

class Main: JavaPlugin() {
    companion object {
        lateinit var db: YamlConfiguration
        lateinit var main: Main
    }

    val dbf = File(dataFolder, "db.yml")

    lateinit var mm: MiniMessage

    val sound = Sound.sound(
        Key.key("entity.experience_orb.pickup"), Sound.Source.PLAYER, 1.0f, 2.0f
    )

    override fun onEnable() {
        main = this

        mm = MiniMessage.miniMessage()

        CommandAPI.onLoad(
            CommandAPIBukkitConfig(this)
                .usePluginNamespace()
                // .useLatestNMSVersion(true)
                .shouldHookPaperReload(true)
                .silentLogs(true)
        )
        CommandAPI.onEnable()

        dataFolder.mkdirs()
        saveDefaultConfig()

        dbf.let { file ->
            if(!file.canRead()){
                if(!file.canWrite() && file.exists()) throw CannotReadFileException()
                else file.createNewFile()
            }

            db = YamlConfiguration.loadConfiguration(
                file
            )
        }

        Reflections(
            ConfigurationBuilder()
                .forPackage("io.github.devalphagot.cl.commands")
                .filterInputsBy(FilterBuilder().includePackage("io.github.devalphagot.cl.commands"))
        ).getSubTypesOf(
            CommandGroup::class.java
        )?.forEach { clazz ->
            clazz.kotlin.objectInstance?.kommand()
        }

        listOf(
            JoinQuitListener(),
            ChzzkHandler(),
            SoopHandler(),
            ToonationHandler(),
            YouTubeHandler()
        ).forEach {
            server.pluginManager.registerEvents(it, this)
        }

        logger.info("방송 연동 플러그인 기동 완료")
        logger.info("- 연동 API는 모두 비공식이며, ")
        logger.info("  플랫폼별 업데이트에 따라 특정 기능이 작동하지 않을 수 있습니다.")
        logger.info("- 문의 사항이나 커스텀 의뢰, 오류 신고는")
        logger.info("  https://discord.gg/JU9SuNudJF 링크에서 부탁드립니다.")
        logger.info("- 명령어에 대한 설명은 /clhelp 명령어로 확인하세요.")
    }

    override fun onDisable() {
        try {
            ChzzkConnector.dispose()
            SoopConnector.dispose()
            ThreadHolder.dispose()
        } catch(_: Exception) {}

        try {
            if(db.getKeys(true).count() > 0) db.save(dbf)
        }
        catch(_: IOException){
            throw CannotWriteFileException()
        }
    }
}