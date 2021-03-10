package br.com.learn.kafka

import br.com.learn.kafka.serialization.MyJsonDeserializer
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.serialization.Deserializer
import org.apache.kafka.common.serialization.StringDeserializer
import java.time.Duration
import java.util.*
import java.util.regex.Pattern
import kotlin.reflect.KClass

class KafkaConsumerService<T>(private val consumerGroup: String,
                              private val consume: (ConsumerRecord<String, T>) -> Unit,
                              private val deserializerClass: KClass<out Deserializer<*>> = StringDeserializer::class) {

    private var topic: String? = null
    private var pattern: Pattern? = null

    constructor(topic: String?, consumerGroup: String, consume: (ConsumerRecord<String, T>) -> Unit) : this(consumerGroup, consume) {
        this.topic = topic
    }

    constructor(topic: String?, consumerGroup: String, deserializerClass: KClass<out Deserializer<*>>, consume: (ConsumerRecord<String, T>) -> Unit):
            this(consumerGroup, consume, deserializerClass) {
        this.topic = topic
    }

    constructor(pattern: Pattern?, consumerGroup: String, consume: (ConsumerRecord<String, T>) -> Unit) : this(consumerGroup, consume) {
        this.pattern = pattern
    }

    private fun properties(): Properties {
        val properties = Properties()
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092")
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer::class.java.name)
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, deserializerClass.java.name)
//        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer::class.java.name)
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, consumerGroup)
        properties.setProperty(ConsumerConfig.CLIENT_ID_CONFIG, consumerGroup + UUID.randomUUID())
        properties.setProperty(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "1")
        return properties
    }

    fun run() {
        KafkaConsumer<String, T>(properties()).use { consumer ->
            subscribe(consumer)
            do {
                val records = consumer.poll(Duration.ofMillis(100))
                for (record in records) {
                    consume(record)
                }
            } while (true)
        }
    }

    private fun subscribe(consumer: KafkaConsumer<String, T>) {
        topic ?: pattern ?: throw RuntimeException("Are you even trying to connect to a topic?")

        topic?.let { consumer.subscribe(listOf(topic)) } ?: consumer.subscribe(pattern)
    }
}