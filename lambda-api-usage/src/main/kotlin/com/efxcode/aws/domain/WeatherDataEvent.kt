package com.efxcode.aws.domain

import java.io.Serializable
import java.math.BigDecimal

data class WeatherDataEvent(
        val temperature: BigDecimal,
        val timestamp: Long?,
        val latitude: BigDecimal?,
        val longitude: BigDecimal?,
        val location: String
) : Serializable {
    constructor() : this(BigDecimal.ZERO, 0L, BigDecimal.ZERO, BigDecimal.ZERO, "") // to avoid jackson exceptions
}

