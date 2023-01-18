package org.wrongwrong.extra.deser

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.Level
import org.openjdk.jmh.annotations.Scope
import org.openjdk.jmh.annotations.Setup
import org.openjdk.jmh.annotations.State
import org.wrongwrong.mapper

@State(Scope.Benchmark)
open class StrictNullChecks {
    companion object {
        val strictNullCheckMapper = ObjectMapper().apply {
            val module = KotlinModule.Builder().enable(KotlinFeature.StrictNullChecks).build()
            this.registerModule(module)
        }
    }

    class ArrayWrapper(val value: Array<Int>)
    data class ListWrapper(val value: List<Int>)
    data class MapWrapper(val value: Map<String, Int>)

    lateinit var arraySrc: String
    lateinit var mapSrc: String

    @Setup(Level.Trial)
    fun setUp() {
        arraySrc = strictNullCheckMapper.writeValueAsString(ListWrapper(listOf(1)))
        mapSrc = strictNullCheckMapper.writeValueAsString(MapWrapper(mapOf("foo" to 1)))
    }

    @Benchmark
    fun array() = mapper.readValue<ArrayWrapper>(arraySrc)

    @Benchmark
    fun arrayStrict() = strictNullCheckMapper.readValue<ArrayWrapper>(arraySrc)

    @Benchmark
    fun list() = mapper.readValue<ListWrapper>(arraySrc)

    @Benchmark
    fun listStrict() = strictNullCheckMapper.readValue<ListWrapper>(arraySrc)

    @Benchmark
    fun map() = mapper.readValue<MapWrapper>(mapSrc)

    @Benchmark
    fun mapStrict() = strictNullCheckMapper.readValue<MapWrapper>(mapSrc)
}
