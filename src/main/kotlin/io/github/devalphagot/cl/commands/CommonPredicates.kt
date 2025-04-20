package io.github.devalphagot.cl.commands

import dev.jorel.commandapi.CommandAPICommand
import dev.jorel.commandapi.arguments.CustomArgument
import dev.jorel.commandapi.arguments.MultiLiteralArgument
import dev.jorel.commandapi.arguments.StringArgument
import io.github.devalphagot.cl.component
import org.bukkit.entity.Player
import java.util.EnumSet

interface CommandDispatcherSeparated {
    public fun command(cac: CommandAPICommand)
}

interface CommandGroup {
    public fun kommand()
}

object CommonPredicates {
//    fun party() = CustomArgument<Party, String>(GreedyStringArgument("party")) { groupName ->
//        Oboete.getGroup(groupName.input) ?: throw CustomArgumentException.fromAdventureComponent(
//            "해당하는 협동조합이 없습니다.".addPrefix().component
//        )
//    }

    @OptIn(ExperimentalStdlibApi::class)
    inline fun <reified T : Enum<T>> enum(nn: String) = CustomArgument<T, String>(StringArgument(
        nn
    )) { enumName ->
        enumValueOf<T>(enumName.input.uppercase())
    }

//    private fun playerInGroup(p: Player) = p.group != null
//    private fun playerIsOwner(p: Player) = p.group?.getPermission(p) == Group.Permission.OWNER
//    private fun playerIsModerator(p: Player) = p.group?.getPermission(p) in listOf(Group.Permission.MODERATOR, Group.Permission.OWNER)
//
//    fun requirePlayerNotInAnyGroup(p: Player): Boolean {
//        if(playerInGroup(p)){
//            p.sendMessage(
//                "이미 협동조합에 소속되어 있습니다.".addPrefix().component
//            )
//            return false
//        }
//
//        return true
//    }
//
//    fun requirePlayerIsInGroup(p: Player): Boolean {
//        if(!playerInGroup(p)){
//            p.sendMessage(
//                "협동조합이 없습니다.".addPrefix().component
//            )
//            return false
//        }
//
//        return true
//    }

    fun requireNotRecursive(p: Player, t: Player): Boolean {
        return (p.uniqueId != t.uniqueId).run {
            if(!this){
                p.sendMessage(
                    "나 자신은 인생의 영원한 친구입니다.".component
                )
            }

            this
        }
    }

//    private fun requirePermission(p: Player, g: Group.Permission): Boolean {
//        if(!if(g == Group.Permission.MODERATOR) playerIsModerator(p) else playerIsOwner(p)){
//            p.sendMessage(
//                "<${g.accentColor}>권한<reset>이 부족합니다.".addPrefix().component
//            )
//            return false
//        }
//
//        return true
//    }

//    fun requireModerator(p: Player) = requirePermission(p, Group.Permission.MODERATOR)
//    fun requireOwner(p: Player) = requirePermission(p, Group.Permission.OWNER)
//
//    fun requireUniqueGroup(p: Player, g: String): Boolean {
//        if(Oboete.getGroup(g) != null){
//            p.sendMessage(
//                "이미 해당 이름을 사용하는 협동조합이 있습니다.".addPrefix().component
//            )
//            return false
//        }
//
//        return true
//    }
//
//    fun requireGroup(p: Player, g: String): Boolean {
//        if(Oboete.getGroup(g) == null){
//            p.sendMessage(
//                "해당하는 협동조합이 없습니다.".addPrefix().component
//            )
//            return false
//        }
//
//        return true
//    }
}