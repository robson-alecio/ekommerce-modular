package br.com.learn.kafka

import java.math.BigDecimal

data class Order(val id: String, val productId: Int, val value: BigDecimal, val email: String) {
    fun isFraudDetected(): Boolean {
        return value >= BigDecimal("4500")
    }
}