package org.wrongwrong.main.ser

import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.Level
import org.openjdk.jmh.annotations.Setup
import org.wrongwrong.BenchmarkBase
import org.wrongwrong.fromRandomInts

open class T_20Props : BenchmarkBase() {
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
    )
    lateinit var target: Dst

    @Setup(Level.Trial)
    fun setUp() {
        target = T_20Props::Dst.fromRandomInts()
    }

    @Benchmark
    fun call() = mapper.writeValueAsString(target)
}
