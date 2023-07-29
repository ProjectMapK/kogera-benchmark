package org.wrongwrong.main.ser

import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.Level
import org.openjdk.jmh.annotations.Setup
import org.wrongwrong.BenchmarkBase
import org.wrongwrong.main.fromRandomInts

open class A_1P : BenchmarkBase() {
    data class Dst(val p0: Int)
    lateinit var target: Dst

    @Setup(Level.Trial)
    fun setUp() {
        target = A_1P::Dst.fromRandomInts()
    }

    @Benchmark
    fun call() = mapper.writeValueAsString(target)
}
