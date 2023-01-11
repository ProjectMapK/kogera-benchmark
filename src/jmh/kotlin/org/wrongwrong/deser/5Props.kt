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
open class FiveProps {
    data class Dst(
        val p0: Int = -1,
        val p1: Int = -1,
        val p2: Int = -1,
        val p3: Int = -1,
        val p4: Int = -1
    )

    lateinit var fullJson: String

    @Setup(Level.Trial)
    fun setUp() {
        fullJson = mapper.writeValueAsString(::Dst.fromRandomInts())
    }

    @Benchmark
    fun e_five() = mapper.readValue<Dst>(fullJson)

    @Benchmark
    fun e_five_default() = mapper.readValue<Dst>("{}")
}
