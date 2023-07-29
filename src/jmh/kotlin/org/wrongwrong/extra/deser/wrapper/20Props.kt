package org.wrongwrong.extra.deser.wrapper

import com.fasterxml.jackson.annotation.JsonCreator
import org.wrongwrong.extra.DataClass

open class T_20P_Ctor : DeserBase<T_20P_Ctor.Dst>(T_20P_Ctor::Dst, Dst::class) {
    data class Dst(
        val p00: DataClass = DataClass(-1),
        val p01: DataClass = DataClass(-1),
        val p02: DataClass = DataClass(-1),
        val p03: DataClass = DataClass(-1),
        val p04: DataClass = DataClass(-1),
        val p05: DataClass = DataClass(-1),
        val p06: DataClass = DataClass(-1),
        val p07: DataClass = DataClass(-1),
        val p08: DataClass = DataClass(-1),
        val p09: DataClass = DataClass(-1),
        val p10: DataClass = DataClass(-1),
        val p11: DataClass = DataClass(-1),
        val p12: DataClass = DataClass(-1),
        val p13: DataClass = DataClass(-1),
        val p14: DataClass = DataClass(-1),
        val p15: DataClass = DataClass(-1),
        val p16: DataClass = DataClass(-1),
        val p17: DataClass = DataClass(-1),
        val p18: DataClass = DataClass(-1),
        val p19: DataClass = DataClass(-1)
    )
}

open class T_20P_Func : DeserBase<T_20P_Func.Dst>(T_20P_Func::Dst, Dst::class) {
    data class Dst(
        val p00: DataClass,
        val p01: DataClass,
        val p02: DataClass,
        val p03: DataClass,
        val p04: DataClass,
        val p05: DataClass,
        val p06: DataClass,
        val p07: DataClass,
        val p08: DataClass,
        val p09: DataClass,
        val p10: DataClass,
        val p11: DataClass,
        val p12: DataClass,
        val p13: DataClass,
        val p14: DataClass,
        val p15: DataClass,
        val p16: DataClass,
        val p17: DataClass,
        val p18: DataClass,
        val p19: DataClass
    ) {
        companion object {
            @JvmStatic
            @JsonCreator
            fun creator(
                p00: DataClass = DataClass(-1),
                p01: DataClass = DataClass(-1),
                p02: DataClass = DataClass(-1),
                p03: DataClass = DataClass(-1),
                p04: DataClass = DataClass(-1),
                p05: DataClass = DataClass(-1),
                p06: DataClass = DataClass(-1),
                p07: DataClass = DataClass(-1),
                p08: DataClass = DataClass(-1),
                p09: DataClass = DataClass(-1),
                p10: DataClass = DataClass(-1),
                p11: DataClass = DataClass(-1),
                p12: DataClass = DataClass(-1),
                p13: DataClass = DataClass(-1),
                p14: DataClass = DataClass(-1),
                p15: DataClass = DataClass(-1),
                p16: DataClass = DataClass(-1),
                p17: DataClass = DataClass(-1),
                p18: DataClass = DataClass(-1),
                p19: DataClass = DataClass(-1)
            ) = Dst(p00, p01, p02, p03, p04, p05, p06, p07, p08, p09, p10, p11, p12, p13, p14, p15, p16, p17, p18, p19)
        }
    }
}
