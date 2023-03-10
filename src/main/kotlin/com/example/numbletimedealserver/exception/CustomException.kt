package com.example.numbletimedealserver.exception

sealed class CustomException(val code:ErrorCode) :RuntimeException(code.message) {
    class ProductNotFoundException:CustomException(ErrorCode.PRODUCT_NOT_FOUND)
    class NotLoggedInException:CustomException(ErrorCode.UNAUTHORIZED)
    class UserConflictException:CustomException(ErrorCode.USER_CONFLICT)
    class UserNotFoundException:CustomException(ErrorCode.USER_NOT_FOUND)
    class BadRequestException:CustomException(ErrorCode.INVALID_PARAMETER)
    class ForbiddenException:CustomException(ErrorCode.FORBIDDEN)
}