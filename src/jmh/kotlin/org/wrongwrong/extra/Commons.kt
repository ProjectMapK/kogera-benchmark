package org.wrongwrong.extra

import com.fasterxml.jackson.annotation.JsonValue
import kotlin.random.Random
import kotlin.reflect.KFunction

fun <T> KFunction<T>.fromRandomIntValues() = call(
    *parameters.fold(arrayOfNulls(parameters.size)) { acc, cur ->
        acc.apply { this[cur.index] = ValueClass(Random.nextInt(10)) }
    }
)

@JvmInline
value class ValueClass(val value: Int)

fun <T> KFunction<T>.fromRandomIntDatas() = call(
    *parameters.fold(arrayOfNulls(parameters.size)) { acc, cur ->
        acc.apply { this[cur.index] = DataClass(Random.nextInt(10)) }
    }
)

data class DataClass(@JsonValue val value: Int)
