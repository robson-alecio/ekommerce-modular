package br.com.learn.kafka

import br.com.learn.kafka.serialization.MyJsonDeserializer

object FraudDetectorService {
    @JvmStatic
    fun main(args: Array<String>) {
        KafkaConsumerService<Order>("ecommerce_new_order", FraudDetectorService::class.java.simpleName, OrderDeserializer::class) {
            println("----------------")
            println(it)
            val order = it.value()
            val topic = if (order.isFraudDetected()) {
                "ecommerce_reproved"
            } else {
                "ecommerce_approved"
            }
            KafkaDispatcher<Order>().send(topic, order.email, order)
            println("Order processed - $topic")
        }.run()
    }
}

class OrderDeserializer: MyJsonDeserializer<Order>(Order::class.java)