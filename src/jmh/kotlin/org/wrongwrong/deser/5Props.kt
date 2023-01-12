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
open class E_FiveProps_Constructor {
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
    fun five() = mapper.readValue<Dst>(fullJson)

    @Benchmark
    fun five_default() = mapper.readValue<Dst>("{}")
}

@State(Scope.Benchmark)
open class E_FiveProps_Function {
    data class Dst(
        val p0: Int,
        val p1: Int,
        val p2: Int,
        val p3: Int,
        val p4: Int
    ) {
        companion object {
            @JvmStatic
            @JsonCreator
            fun creator(
                p0: Int = -1,
                p1: Int = -1,
                p2: Int = -1,
                p3: Int = -1,
                p4: Int = -1
            ) = Dst(p0, p1, p2, p3, p4)
        }
    }

    lateinit var fullJson: String

    @Setup(Level.Trial)
    fun setUp() {
        fullJson = mapper.writeValueAsString(::Dst.fromRandomInts())
    }

    @Benchmark
    fun five() = mapper.readValue<Dst>(fullJson)

    @Benchmark
    fun five_default() = mapper.readValue<Dst>("{}")
}
