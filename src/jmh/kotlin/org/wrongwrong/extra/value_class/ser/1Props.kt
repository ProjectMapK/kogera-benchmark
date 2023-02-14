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
open class A_1Props {
    data class Dst(val p0: ValueClass)
    lateinit var target: Dst

    @Setup(Level.Trial)
    fun setUp() {
        target = A_1Props::Dst.fromRandomIntValues()
    }

    @Benchmark
    fun call() = mapper.writeValueAsString(target)
}
