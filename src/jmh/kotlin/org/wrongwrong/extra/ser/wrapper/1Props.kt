package org.wrongwrong.extra.ser.wrapper

import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.Level
import org.openjdk.jmh.annotations.Setup
import org.wrongwrong.BenchmarkBase
import org.wrongwrong.extra.DataClass
import org.wrongwrong.extra.fromRandomIntDatas

open class A_1P : BenchmarkBase() {
    data class Dst(val p0: DataClass)
    lateinit var target: Dst

    @Setup(Level.Trial)
    fun setUp() {
        target = A_1P::Dst.fromRandomIntDatas()
    }

    @Benchmark
    fun call() = mapper.writeValueAsString(target)
}
