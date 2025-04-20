package io.github.devalphagot.cl.exceptions

class CannotReadFileException: Exception {
    var msg: String

    constructor(){ Exception("설정 파일을 불러올 수 없습니다."); msg = "설정 파일을 불러올 수 없습니다." }
    constructor(e: String){ Exception(e); msg = e }

    override fun toString(): String {
        return msg
    }

    override fun getLocalizedMessage(): String? {
        return msg
    }
}