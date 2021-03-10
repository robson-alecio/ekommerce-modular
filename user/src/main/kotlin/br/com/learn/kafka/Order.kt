package br.com.learn.kafka

import java.math.BigDecimal

data class Order(val id: String, val clientId: Int, val productId: Int, val value: BigDecimal, val email: String)