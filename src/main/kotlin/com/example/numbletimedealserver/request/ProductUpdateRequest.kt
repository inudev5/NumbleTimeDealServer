package com.example.numbletimedealserver.request

import java.io.Serializable
import java.time.LocalTime

data class ProductUpdateRequest(
    val name: String?,
    val description: String?,
    val appointedTime: LocalTime?,
    val appointedQuantity: Long?
): Serializable
