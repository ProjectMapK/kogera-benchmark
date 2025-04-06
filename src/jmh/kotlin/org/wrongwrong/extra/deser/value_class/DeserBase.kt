package org.wrongwrong.extra.deser.value_class

import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.Level
import org.openjdk.jmh.annotations.Setup
import org.wrongwrong.BenchmarkBase
import org.wrongwrong.extra.fromRandomIntValues
import kotlin.reflect.KClass
import kotlin.reflect.KFunction

abstract class DeserBase<T : Any>(
    private val creator: KFunction<T>,
    clazz: KClass<T>
) : BenchmarkBase() {
    private val type = mapper.constructType(clazz.java)
    private lateinit var fullJson: String
    private lateinit var replacedJson: String

    @Setup(Level.Trial)
    fun setUp() {
        fullJson = mapper.writeValueAsString(creator.fromRandomIntValues())
        // Since the speed of reading JSON was affecting the benchmark,
        // adjust the performance so that it is aligned with the case without default values.
        replacedJson = fullJson.replace("p", "q")
    }

    @Benchmark
    fun present(): T = mapper.readValue(fullJson, type)
}
