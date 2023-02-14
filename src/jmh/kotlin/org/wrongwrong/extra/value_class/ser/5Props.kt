package org.wrongwrong.extra.value_class.ser

import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.Level
import org.openjdk.jmh.annotations.Scope
import org.openjdk.jmh.annotations.Setup
import org.openjdk.jmh.annotations.State
import org.wrongwrong.extra.value_class.ValueClass
import org.wrongwrong.extra.value_class.fromRandomIntValues
import org.wrongwrong.fromRandomInts
import org.wrongwrong.mapper

@State(Scope.Benchmark)
open class E_5Props {
    data class Dst(
        val p0: ValueClass,
        val p1: ValueClass,
        val p2: ValueClass,
        val p3: ValueClass,
        val p4: ValueClass
    )
    lateinit var target: Dst

    @Setup(Level.Trial)
    fun setUp() {
        target = E_5Props::Dst.fromRandomIntValues()
    }

    @Benchmark
    fun call() = mapper.writeValueAsString(target)
}
