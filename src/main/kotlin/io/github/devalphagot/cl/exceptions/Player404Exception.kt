package io.github.devalphagot.cl.exceptions

class Player404Exception: Exception {
    var msg: String

    constructor(){ Exception("플레이어를 찾을 수 없습니다."); msg = "플레이어를 찾을 수 없습니다." }
    constructor(e: String){ Exception(e); msg = e }

    override fun toString(): String {
        return msg
    }

    override fun getLocalizedMessage(): String? {
        return msg
    }
}