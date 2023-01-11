package org.wrongwrong

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import kotlin.random.Random
import kotlin.reflect.KFunction

val mapper = jacksonObjectMapper()
fun <T> KFunction<T>.fromRandomInts() = call(*parameters.map { Random.nextInt() }.toTypedArray())
