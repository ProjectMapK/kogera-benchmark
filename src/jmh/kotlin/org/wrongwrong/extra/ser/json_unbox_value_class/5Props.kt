package org.wrongwrong.extra.ser.json_unbox_value_class

import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.Level
import org.openjdk.jmh.annotations.Setup
import org.wrongwrong.BenchmarkBase

open class E_5P : BenchmarkBase() {
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
        target = E_5P::Dst.fromRandomIntValues()
    }

    @Benchmark
    fun call() = mapper.writeValueAsString(target)
}
