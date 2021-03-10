package br.com.learn.kafka

import br.com.learn.kafka.serialization.MyJsonSerializer
import org.apache.kafka.clients.producer.*
import org.apache.kafka.common.serialization.StringSerializer
import java.util.*

class KafkaDispatcher<T> {

    fun send(topic: String, key: String, value: T) {
        KafkaProducer<String, T>(properties()).use {
            it.send(ProducerRecord(topic, key, value), recordCallback()).get()
        }
    }

    private fun properties(): Properties {
        val properties = Properties()
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092")
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer::class.java.name)
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, MyJsonSerializer::class.java.name)
        return properties
    }

    private fun recordCallback(): Callback {
        return Callback { recordMetadata: RecordMetadata, e: Exception? ->
            e?.printStackTrace() ?: println("Done: " + recordMetadata.topic() + " offset " + recordMetadata.offset())
        }
    }

}