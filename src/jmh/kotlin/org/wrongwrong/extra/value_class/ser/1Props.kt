package org.wrongwrong.extra.value_class.ser

import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.Level
import org.openjdk.jmh.annotations.Setup
import org.wrongwrong.BenchmarkBase
import org.wrongwrong.extra.value_class.ValueClass
import org.wrongwrong.extra.value_class.fromRandomIntValues

open class A_1Props : BenchmarkBase() {
    data class Dst(val p0: ValueClass)
    lateinit var target: Dst

    @Setup(Level.Trial)
    fun setUp() {
        target = A_1Props::Dst.fromRandomIntValues()
    }

    @Benchmark
    fun call() = mapper.writeValueAsString(target)
}
