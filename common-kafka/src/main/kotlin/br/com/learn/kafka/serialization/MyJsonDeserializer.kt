package br.com.learn.kafka.serialization

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.apache.kafka.common.serialization.Deserializer

open class MyJsonDeserializer<T>(private val targetClass: Class<T>): Deserializer<T> {

    override fun deserialize(topic: String?, data: ByteArray?): T {
        try {
            return ObjectMapper().registerModule(KotlinModule()).readValue(data, targetClass)
        } catch (e: JsonProcessingException) {
            throw RuntimeException("Could not deserialize from $topic to $targetClass -> $data", e)
        }
    }

}
