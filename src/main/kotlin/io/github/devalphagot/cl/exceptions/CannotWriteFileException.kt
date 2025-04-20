package io.github.devalphagot.cl.exceptions

class CannotWriteFileException: Exception {
    var msg: String

    constructor(){ Exception("설정 파일을 저장할 수 없습니다."); msg = "설정 파일을 저장할 수 없습니다." }
    constructor(e: String){ Exception(e); msg = e }

    override fun toString(): String {
        return msg
    }

    override fun getLocalizedMessage(): String? {
        return msg
    }
}