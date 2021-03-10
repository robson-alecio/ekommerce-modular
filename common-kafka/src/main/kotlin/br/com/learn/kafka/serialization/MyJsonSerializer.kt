package br.com.learn.kafka.serialization

import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.kafka.common.serialization.Serializer

class MyJsonSerializer<T> : Serializer<T> {
    override fun serialize(topic: String?, data: T): ByteArray {
        return ObjectMapper().writeValueAsBytes(data)
    }

}
