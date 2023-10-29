package org.wrongwrong.extra.ser.json_k_unbox_value_class

import io.github.projectmapk.jackson.module.kogera.annotation.JsonKUnbox
import kotlin.random.Random
import kotlin.reflect.KFunction

@JvmInline
@JsonKUnbox
value class ValueClass(val value: Int)

fun <T> KFunction<T>.fromRandomIntValues() = call(
    *parameters.fold(arrayOfNulls(parameters.size)) { acc, cur ->
        acc.apply { this[cur.index] = ValueClass(Random.nextInt(10)) }
    }
)
