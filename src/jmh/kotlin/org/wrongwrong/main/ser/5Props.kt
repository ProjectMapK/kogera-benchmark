package org.wrongwrong.main.ser

import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.Level
import org.openjdk.jmh.annotations.Setup
import org.wrongwrong.BenchmarkBase
import org.wrongwrong.main.fromRandomInts

open class E_5Props : BenchmarkBase() {
    data class Dst(
        val p0: Int,
        val p1: Int,
        val p2: Int,
        val p3: Int,
        val p4: Int
    )
    lateinit var target: Dst

    @Setup(Level.Trial)
    fun setUp() {
        target = E_5Props::Dst.fromRandomInts()
    }

    @Benchmark
    fun call() = mapper.writeValueAsString(target)
}
