package org.wrongwrong.extra.deser.wrapper

import com.fasterxml.jackson.annotation.JsonCreator
import org.wrongwrong.extra.DataClass

open class A_1P_Ctor : DeserBase<A_1P_Ctor.Dst>(A_1P_Ctor::Dst, Dst::class) {
    data class Dst(val p0: DataClass = DataClass(-1))
}

open class A_1P_Func : DeserBase<A_1P_Func.Dst>(A_1P_Func::Dst, Dst::class) {
    data class Dst(val p0: DataClass) {
        companion object {
            @JvmStatic
            @JsonCreator
            fun creator(p0: DataClass = DataClass(-1)) = Dst(p0)
        }
    }
}
