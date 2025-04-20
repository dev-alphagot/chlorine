package io.github.devalphagot.cl.commands

import dev.jorel.commandapi.CommandAPICommand
import dev.jorel.commandapi.executors.CommandExecutor
import io.github.devalphagot.cl.Main.Companion.main
import io.github.devalphagot.cl.component

object HelpCommand: CommandGroup {
    override fun kommand() {
        val cac = CommandAPICommand("clhelp").withAliases("clh", "conf")

        cac.withPermission("cl.general").executes(CommandExecutor { p, _ ->
            listOf(
                "<yellow>/connect (플랫폼) (ID)</yellow>: 해당 플랫폼에 연결합니다. 각 플랫폼에는 한 채널씩만 연동 가능합니다.",
                " <light_purple>-<reset> <gray>(플랫폼)은 chzzk (치지직), soop (숲), toonation (투네이션), youtube (유튜브)가 있습니다.</gray>",
                " <light_purple>-<reset> <gray>(ID)는 플랫폼별로</gray>",
                "     <gray>치지직은 방송 URL의 'live/' 뒤 값 (ex. d7ddd7585a271e55159ae47c0ce9a9dd),</gray>",
                "     <gray>숲은 스트리머 ID (ex. andeqr0r0),</gray>",
                "     <gray>투네이션은 Alertbox URL의 'alertbox/' 뒤 값,</gray>",
                "     <gray>유튜브는 방송 URL (채널 URL 아님)의 'watch?v=' 뒤 값 (ex. dQw4w9WgXcQ)</gray>",
                "   <gray>을 입력해야 합니다.</gray>",
                "<yellow>/config (플랫폼) (기능) (활성화 / 비활성화 여부)</yellow>: 플랫폼별 기능을 설정합니다.",
                " <light_purple>-<reset> <gray>(기능)은 chat (채팅), donation (후원), mission (미션), subscribe (구독),</gray>",
                "   <gray>sticker (스티커)가 있습니다.</gray>",
                " <light_purple>-<reset> <gray>(활성화 / 비활성화 여부)는 각각 true / false로 입력해야 합니다.</gray>",
                "<yellow>/disconnect (플랫폼)</yellow>: 해당 플랫폼과의 연결을 해제합니다.",
            ).map { it.component }.map(p::sendMessage)
        }).register(main)
    }
}