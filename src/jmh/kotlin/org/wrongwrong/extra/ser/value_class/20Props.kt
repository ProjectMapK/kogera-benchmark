package org.wrongwrong.extra.ser.value_class

import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.Level
import org.openjdk.jmh.annotations.Setup
import org.wrongwrong.BenchmarkBase
import org.wrongwrong.extra.ValueClass
import org.wrongwrong.extra.fromRandomIntValues

open class T_20P : BenchmarkBase() {
    data class Dst(
        val p00: ValueClass,
        val p01: ValueClass,
        val p02: ValueClass,
        val p03: ValueClass,
        val p04: ValueClass,
        val p05: ValueClass,
        val p06: ValueClass,
        val p07: ValueClass,
        val p08: ValueClass,
        val p09: ValueClass,
        val p10: ValueClass,
        val p11: ValueClass,
        val p12: ValueClass,
        val p13: ValueClass,
        val p14: ValueClass,
        val p15: ValueClass,
        val p16: ValueClass,
        val p17: ValueClass,
        val p18: ValueClass,
        val p19: ValueClass
    )
    lateinit var target: Dst

    @Setup(Level.Trial)
    fun setUp() {
        target = T_20P::Dst.fromRandomIntValues()
    }

    @Benchmark
    fun call() = mapper.writeValueAsString(target)
}
