package br.com.learn.kafka

import java.math.BigDecimal
import java.util.*

data class Order(val productId: Int, val value: BigDecimal, val email: String, val id: String = UUID.randomUUID().toString())