package io.github.devalphagot.cl.types

enum class MessageType {
    CHAT,
    DONATION,
    SUBSCRIBE,
    MISSION,
    STICKER;

    val koreanName: String
        get() = when(this){
                CHAT -> "채팅"
                DONATION -> "후원"
                SUBSCRIBE -> "구독"
                MISSION -> "미션"
                STICKER -> "스티커"
            }
}