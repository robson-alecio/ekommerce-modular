package br.com.learn.kafka

import java.util.regex.Pattern

object LogService {
    @JvmStatic
    fun main(args: Array<String>) {
        KafkaConsumerService<String>(Pattern.compile("ecommerce.*"), LogService::class.java.simpleName) { record ->
            println("LOG: $record")
        }.run()
    }
}