package br.com.learn.kafka

import br.com.learn.kafka.db.DatabaseAccessor
import br.com.learn.kafka.serialization.MyJsonDeserializer

object UserService {
    @JvmStatic
    fun main(args: Array<String>) {
        DatabaseAccessor.start()
        KafkaConsumerService<Order>("ecommerce_new_order", UserService::class.java.simpleName, OrderDeserializer::class) {
            println("----------------")
            println(it)
            val order = it.value()
            println("$order")
            if (!DatabaseAccessor.existsUserByEmail(order.email)) {
                DatabaseAccessor.insert(order.email)
            }
        }.run()
    }
}

class OrderDeserializer: MyJsonDeserializer<Order>(Order::class.java)