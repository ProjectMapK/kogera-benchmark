package org.wrongwrong.extra

import kotlin.random.Random
import kotlin.reflect.KFunction

fun <T> KFunction<T>.fromRandomIntValues() = call(
    *parameters.fold(arrayOfNulls(parameters.size)) { acc, cur ->
        acc.apply { this[cur.index] = ValueClass(Random.nextInt(10)) }
    }
)

@JvmInline
value class ValueClass(val value: Int)
