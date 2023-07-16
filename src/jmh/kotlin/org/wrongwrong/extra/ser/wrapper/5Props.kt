package org.wrongwrong.extra.ser.wrapper

import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.Level
import org.openjdk.jmh.annotations.Setup
import org.wrongwrong.BenchmarkBase
import org.wrongwrong.extra.DataClass
import org.wrongwrong.extra.fromRandomIntDatas

open class E_5Props : BenchmarkBase() {
    data class Dst(
        val p0: DataClass,
        val p1: DataClass,
        val p2: DataClass,
        val p3: DataClass,
        val p4: DataClass
    )
    lateinit var target: Dst

    @Setup(Level.Trial)
    fun setUp() {
        target = E_5Props::Dst.fromRandomIntDatas()
    }

    @Benchmark
    fun call() = mapper.writeValueAsString(target)
}
