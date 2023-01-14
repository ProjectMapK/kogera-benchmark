package org.wrongwrong

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import kotlin.random.Random
import kotlin.reflect.KFunction

val mapper = jacksonObjectMapper().apply {
    disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
}
fun <T> KFunction<T>.fromRandomInts() = call(*parameters.map { Random.nextInt(10) }.toTypedArray())
