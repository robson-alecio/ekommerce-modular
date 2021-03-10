package br.com.learn.kafka

import br.com.learn.kafka.serialization.MyJsonDeserializer

object EmailSendService {
    @JvmStatic
    fun main(args: Array<String>) {
        KafkaConsumerService<String>("ecommerce_send_email", EmailSendService::class.java.simpleName, EmailDeserializer::class) {
            println("----------------")
            println(it)
            println("Email sent")
        }.run()
    }
}

class EmailDeserializer: MyJsonDeserializer<Email>(Email::class.java)