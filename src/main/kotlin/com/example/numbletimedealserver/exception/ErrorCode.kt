package com.example.numbletimedealserver.exception

sealed class ErrorCode(val status:Int, val message:String) {
    object INVALID_PARAMETER:ErrorCode(400,"잘못된 요청입니다.")
    object UNAUTHORIZED:ErrorCode(401, "로그인이 필요합니다.")
    object PRODUCT_NOT_FOUND:ErrorCode(404,"존재하지 않는 상품입니다.")
    object FORBIDDEN:ErrorCode(403, "권한이 없습니다.")
    object USER_NOT_FOUND:ErrorCode(404, "존재하지 않는 유저입니다.")
    object USER_CONFLICT:ErrorCode(409, "이미 존재하는 유저명입니다.")
}