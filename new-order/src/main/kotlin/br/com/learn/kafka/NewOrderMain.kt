package br.com.learn.kafka

import java.math.BigDecimal
import java.math.RoundingMode
import java.util.*

object NewOrderMain {
    @JvmStatic
    fun main(args: Array<String>) {
        repeat((1..10).count()) {
            val order = Order(
                852,
                BigDecimal(Math.random() * 5_000).setScale(2, RoundingMode.HALF_EVEN),
                "${Math.random()}@email.com"
            )
            KafkaDispatcher<Order>().send("ecommerce_new_order", order.email, order)
            val email = Email(
                "client." + UUID.randomUUID().toString().substring(10),
                "Welcome",
                "Thank you and blah, blah, blah"
            )
            KafkaDispatcher<Email>().send("ecommerce_send_email", email.toString(), email)
        }
    }
}
