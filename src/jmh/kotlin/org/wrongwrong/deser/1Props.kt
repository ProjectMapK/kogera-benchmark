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
open class A_1Props_Constructor {
    data class Dst(val p0: Int = -1)

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
open class A_1Props_Function {
    data class Dst(val p0: Int) {
        companion object {
            @JvmStatic
            @JsonCreator
            fun creator(p0: Int = -1) = Dst(p0)
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
