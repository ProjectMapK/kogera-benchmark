package org.wrongwrong.deser

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.module.kotlin.readValue
import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.Level
import org.openjdk.jmh.annotations.Scope
import org.openjdk.jmh.annotations.Setup
import org.openjdk.jmh.annotations.State
import org.wrongwrong.fromRandomInts
import org.wrongwrong.mapper

@State(Scope.Benchmark)
open class T_20Props_Constructor {
    data class Dst(
        val p00: Int = -1,
        val p01: Int = -1,
        val p02: Int = -1,
        val p03: Int = -1,
        val p04: Int = -1,
        val p05: Int = -1,
        val p06: Int = -1,
        val p07: Int = -1,
        val p08: Int = -1,
        val p09: Int = -1,
        val p10: Int = -1,
        val p11: Int = -1,
        val p12: Int = -1,
        val p13: Int = -1,
        val p14: Int = -1,
        val p15: Int = -1,
        val p16: Int = -1,
        val p17: Int = -1,
        val p18: Int = -1,
        val p19: Int = -1
    )

    lateinit var fullJson: String

    @Setup(Level.Trial)
    fun setUp() {
        fullJson = mapper.writeValueAsString(::Dst.fromRandomInts())
    }

    @Benchmark
    fun call() = mapper.readValue<Dst>(fullJson)

    @Benchmark
    fun call_default() = mapper.readValue<Dst>("{}")
}

@State(Scope.Benchmark)
open class T_20Props_Function {
    data class Dst(
        val p00: Int,
        val p01: Int,
        val p02: Int,
        val p03: Int,
        val p04: Int,
        val p05: Int,
        val p06: Int,
        val p07: Int,
        val p08: Int,
        val p09: Int,
        val p10: Int,
        val p11: Int,
        val p12: Int,
        val p13: Int,
        val p14: Int,
        val p15: Int,
        val p16: Int,
        val p17: Int,
        val p18: Int,
        val p19: Int
    ) {
        companion object {
            @JvmStatic
            @JsonCreator
            fun creator(
                p00: Int = -1,
                p01: Int = -1,
                p02: Int = -1,
                p03: Int = -1,
                p04: Int = -1,
                p05: Int = -1,
                p06: Int = -1,
                p07: Int = -1,
                p08: Int = -1,
                p09: Int = -1,
                p10: Int = -1,
                p11: Int = -1,
                p12: Int = -1,
                p13: Int = -1,
                p14: Int = -1,
                p15: Int = -1,
                p16: Int = -1,
                p17: Int = -1,
                p18: Int = -1,
                p19: Int = -1
            ) = Dst(p00, p01, p02, p03, p04, p05, p06, p07, p08, p09, p10, p11, p12, p13, p14, p15, p16, p17, p18, p19)
        }
    }

    lateinit var fullJson: String

    @Setup(Level.Trial)
    fun setUp() {
        fullJson = mapper.writeValueAsString(::Dst.fromRandomInts())
    }

    @Benchmark
    fun call() = mapper.readValue<Dst>(fullJson)

    @Benchmark
    fun call_default() = mapper.readValue<Dst>("{}")
}
