package org.wrongwrong.main.deser

import com.fasterxml.jackson.annotation.JsonCreator

open class A_1P_Ctor : DeserBase<A_1P_Ctor.Dst>(A_1P_Ctor::Dst, Dst::class) {
    data class Dst(val p0: Int = -1)
}

open class A_1P_Func : DeserBase<A_1P_Func.Dst>(A_1P_Func::Dst, Dst::class) {
    data class Dst(val p0: Int) {
        companion object {
            @JvmStatic
            @JsonCreator
            fun creator(p0: Int = -1) = Dst(p0)
        }
    }
}
