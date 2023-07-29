package org.wrongwrong.extra.deser.value_class

import com.fasterxml.jackson.annotation.JsonCreator
import org.wrongwrong.extra.ValueClass

open class A_1P_Ctor : DeserBase<A_1P_Ctor.Dst>(A_1P_Ctor::Dst, Dst::class) {
    data class Dst(val p0: ValueClass = ValueClass(-1))
}

open class A_1P_Func : DeserBase<A_1P_Func.Dst>(A_1P_Func::Dst, Dst::class) {
    data class Dst(val p0: ValueClass) {
        companion object {
            @JvmStatic
            @JsonCreator
            fun creator(p0: ValueClass = ValueClass(-1)) = Dst(p0)
        }
    }
}
