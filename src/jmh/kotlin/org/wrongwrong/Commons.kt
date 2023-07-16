package org.wrongwrong

import kotlin.random.Random
import kotlin.reflect.KFunction

fun <T> KFunction<T>.fromRandomInts() = call(*parameters.map { Random.nextInt(10) }.toTypedArray())
