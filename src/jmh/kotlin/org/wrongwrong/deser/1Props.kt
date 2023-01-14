package org.wrongwrong.deser

import com.fasterxml.jackson.annotation.JsonCreator

open class A_1Props_Constructor : DeserBase<A_1Props_Constructor.Dst>(::Dst, Dst::class) {
    data class Dst(val p0: Int = -1)
}

open class A_1Props_Function : DeserBase<A_1Props_Function.Dst>(::Dst, Dst::class) {
    data class Dst(val p0: Int) {
        companion object {
            @JvmStatic
            @JsonCreator
            fun creator(p0: Int = -1) = Dst(p0)
        }
    }
}
