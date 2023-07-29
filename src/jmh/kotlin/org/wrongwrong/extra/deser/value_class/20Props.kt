package org.wrongwrong.extra.deser.value_class

import com.fasterxml.jackson.annotation.JsonCreator
import org.wrongwrong.extra.ValueClass

open class T_20P_Ctor : DeserBase<T_20P_Ctor.Dst>(T_20P_Ctor::Dst, Dst::class) {
    data class Dst(
        val p00: ValueClass = ValueClass(-1),
        val p01: ValueClass = ValueClass(-1),
        val p02: ValueClass = ValueClass(-1),
        val p03: ValueClass = ValueClass(-1),
        val p04: ValueClass = ValueClass(-1),
        val p05: ValueClass = ValueClass(-1),
        val p06: ValueClass = ValueClass(-1),
        val p07: ValueClass = ValueClass(-1),
        val p08: ValueClass = ValueClass(-1),
        val p09: ValueClass = ValueClass(-1),
        val p10: ValueClass = ValueClass(-1),
        val p11: ValueClass = ValueClass(-1),
        val p12: ValueClass = ValueClass(-1),
        val p13: ValueClass = ValueClass(-1),
        val p14: ValueClass = ValueClass(-1),
        val p15: ValueClass = ValueClass(-1),
        val p16: ValueClass = ValueClass(-1),
        val p17: ValueClass = ValueClass(-1),
        val p18: ValueClass = ValueClass(-1),
        val p19: ValueClass = ValueClass(-1)
    )
}

open class T_20P_Func : DeserBase<T_20P_Func.Dst>(T_20P_Func::Dst, Dst::class) {
    data class Dst(
        val p00: ValueClass,
        val p01: ValueClass,
        val p02: ValueClass,
        val p03: ValueClass,
        val p04: ValueClass,
        val p05: ValueClass,
        val p06: ValueClass,
        val p07: ValueClass,
        val p08: ValueClass,
        val p09: ValueClass,
        val p10: ValueClass,
        val p11: ValueClass,
        val p12: ValueClass,
        val p13: ValueClass,
        val p14: ValueClass,
        val p15: ValueClass,
        val p16: ValueClass,
        val p17: ValueClass,
        val p18: ValueClass,
        val p19: ValueClass
    ) {
        companion object {
            @JvmStatic
            @JsonCreator
            fun creator(
                p00: ValueClass = ValueClass(-1),
                p01: ValueClass = ValueClass(-1),
                p02: ValueClass = ValueClass(-1),
                p03: ValueClass = ValueClass(-1),
                p04: ValueClass = ValueClass(-1),
                p05: ValueClass = ValueClass(-1),
                p06: ValueClass = ValueClass(-1),
                p07: ValueClass = ValueClass(-1),
                p08: ValueClass = ValueClass(-1),
                p09: ValueClass = ValueClass(-1),
                p10: ValueClass = ValueClass(-1),
                p11: ValueClass = ValueClass(-1),
                p12: ValueClass = ValueClass(-1),
                p13: ValueClass = ValueClass(-1),
                p14: ValueClass = ValueClass(-1),
                p15: ValueClass = ValueClass(-1),
                p16: ValueClass = ValueClass(-1),
                p17: ValueClass = ValueClass(-1),
                p18: ValueClass = ValueClass(-1),
                p19: ValueClass = ValueClass(-1)
            ) = Dst(p00, p01, p02, p03, p04, p05, p06, p07, p08, p09, p10, p11, p12, p13, p14, p15, p16, p17, p18, p19)
        }
    }
}
