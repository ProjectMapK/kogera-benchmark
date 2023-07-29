package org.wrongwrong.extra.deser.value_class

import com.fasterxml.jackson.annotation.JsonCreator
import org.wrongwrong.extra.ValueClass

open class E_5P_Ctor : DeserBase<E_5P_Ctor.Dst>(E_5P_Ctor::Dst, Dst::class) {
    data class Dst(
        val p0: ValueClass = ValueClass(-1),
        val p1: ValueClass = ValueClass(-1),
        val p2: ValueClass = ValueClass(-1),
        val p3: ValueClass = ValueClass(-1),
        val p4: ValueClass = ValueClass(-1)
    )
}

open class E_5P_Func : DeserBase<E_5P_Func.Dst>(E_5P_Func::Dst, Dst::class) {
    data class Dst(
        val p0: ValueClass,
        val p1: ValueClass,
        val p2: ValueClass,
        val p3: ValueClass,
        val p4: ValueClass
    ) {
        companion object {
            @JvmStatic
            @JsonCreator
            fun creator(
                p0: ValueClass = ValueClass(-1),
                p1: ValueClass = ValueClass(-1),
                p2: ValueClass = ValueClass(-1),
                p3: ValueClass = ValueClass(-1),
                p4: ValueClass = ValueClass(-1)
            ) = Dst(p0, p1, p2, p3, p4)
        }
    }
}
