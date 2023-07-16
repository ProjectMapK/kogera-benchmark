package org.wrongwrong.extra.ser.wrapper

import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.Level
import org.openjdk.jmh.annotations.Setup
import org.wrongwrong.BenchmarkBase
import org.wrongwrong.extra.DataClass
import org.wrongwrong.extra.fromRandomIntDatas

open class T_20Props : BenchmarkBase() {
    data class Dst(
        val p00: DataClass,
        val p01: DataClass,
        val p02: DataClass,
        val p03: DataClass,
        val p04: DataClass,
        val p05: DataClass,
        val p06: DataClass,
        val p07: DataClass,
        val p08: DataClass,
        val p09: DataClass,
        val p10: DataClass,
        val p11: DataClass,
        val p12: DataClass,
        val p13: DataClass,
        val p14: DataClass,
        val p15: DataClass,
        val p16: DataClass,
        val p17: DataClass,
        val p18: DataClass,
        val p19: DataClass
    )
    lateinit var target: Dst

    @Setup(Level.Trial)
    fun setUp() {
        target = T_20Props::Dst.fromRandomIntDatas()
    }

    @Benchmark
    fun call() = mapper.writeValueAsString(target)
}
