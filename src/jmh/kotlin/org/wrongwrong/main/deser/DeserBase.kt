package org.wrongwrong.main.deser

import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.Level
import org.openjdk.jmh.annotations.Scope
import org.openjdk.jmh.annotations.Setup
import org.openjdk.jmh.annotations.State
import org.wrongwrong.fromRandomInts
import org.wrongwrong.mapper
import kotlin.reflect.KClass
import kotlin.reflect.KFunction

@State(Scope.Benchmark)
abstract class DeserBase<T : Any>(
    private val creator: KFunction<T>,
    clazz: KClass<T>
) {
    private val clazz = clazz.java
    private lateinit var fullJson: String
    private lateinit var replacedJson: String

    @Setup(Level.Trial)
    fun setUp() {
        fullJson = mapper.writeValueAsString(creator.fromRandomInts())
        // Since the speed of reading JSON was affecting the benchmark,
        // adjust the performance so that it is aligned with the case without default values.
        replacedJson = fullJson.replace("p", "q")
    }

    @Benchmark
    fun call(): T = mapper.readValue(fullJson, clazz)

    @Benchmark
    fun call_default(): T = mapper.readValue(replacedJson, clazz)
}
