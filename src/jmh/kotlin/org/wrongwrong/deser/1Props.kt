package org.wrongwrong.deser

import com.fasterxml.jackson.module.kotlin.readValue
import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.Level
import org.openjdk.jmh.annotations.Scope
import org.openjdk.jmh.annotations.Setup
import org.openjdk.jmh.annotations.State
import org.wrongwrong.fromRandomInts
import org.wrongwrong.mapper

@State(Scope.Benchmark)
open class OneProps {
    data class Dst(val p0: Int = -1)

    lateinit var fullJson: String

    @Setup(Level.Trial)
    fun setUp() {
        fullJson = mapper.writeValueAsString(::Dst.fromRandomInts())
    }

    @Benchmark
    fun a_one() = mapper.readValue<Dst>(fullJson)

    @Benchmark
    fun a_one_default() = mapper.readValue<Dst>("{}")
}
