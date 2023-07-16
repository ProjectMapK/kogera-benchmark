package org.wrongwrong.extra.deser

import com.fasterxml.jackson.module.kotlin.readValue
import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.Level
import org.openjdk.jmh.annotations.Setup
import org.wrongwrong.BenchmarkBase

open class Collections : BenchmarkBase() {
    class ArrayWrapper(val value: Array<Int>)
    data class ListWrapper(val value: List<Int>)
    data class MapWrapper(val value: Map<String, Int>)

    lateinit var arraySrc: String
    lateinit var mapSrc: String

    @Setup(Level.Trial)
    fun setUp() {
        arraySrc = mapper.writeValueAsString(ListWrapper(listOf(1, 2, 3, 4, 5)))
        mapSrc = mapper.writeValueAsString(
            MapWrapper(
                mapOf(
                    "foo" to 1,
                    "bar" to 2,
                    "baz" to 3,
                    "qux" to 4,
                    "quux" to 5
                )
            )
        )
    }

    @Benchmark
    fun array() = mapper.readValue<ArrayWrapper>(arraySrc)

    @Benchmark
    fun list() = mapper.readValue<ListWrapper>(arraySrc)

    @Benchmark
    fun map() = mapper.readValue<MapWrapper>(mapSrc)
}
