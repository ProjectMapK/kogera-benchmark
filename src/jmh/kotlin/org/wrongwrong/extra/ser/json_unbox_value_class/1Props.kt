package org.wrongwrong.extra.ser.json_unbox_value_class

import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.Level
import org.openjdk.jmh.annotations.Setup
import org.wrongwrong.BenchmarkBase

open class A_1P : BenchmarkBase() {
    data class Dst(val p0: ValueClass)
    lateinit var target: Dst

    @Setup(Level.Trial)
    fun setUp() {
        target = A_1P::Dst.fromRandomIntValues()
    }

    @Benchmark
    fun call() = mapper.writeValueAsString(target)
}
