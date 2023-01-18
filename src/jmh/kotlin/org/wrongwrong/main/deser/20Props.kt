package org.wrongwrong.main.deser

import com.fasterxml.jackson.annotation.JsonCreator

open class T_20Props_Constructor : DeserBase<T_20Props_Constructor.Dst>(T_20Props_Constructor::Dst, Dst::class) {
    data class Dst(
        val p00: Int = -1,
        val p01: Int = -1,
        val p02: Int = -1,
        val p03: Int = -1,
        val p04: Int = -1,
        val p05: Int = -1,
        val p06: Int = -1,
        val p07: Int = -1,
        val p08: Int = -1,
        val p09: Int = -1,
        val p10: Int = -1,
        val p11: Int = -1,
        val p12: Int = -1,
        val p13: Int = -1,
        val p14: Int = -1,
        val p15: Int = -1,
        val p16: Int = -1,
        val p17: Int = -1,
        val p18: Int = -1,
        val p19: Int = -1
    )
}

open class T_20Props_Function : DeserBase<T_20Props_Function.Dst>(T_20Props_Function::Dst, Dst::class) {
    data class Dst(
        val p00: Int,
        val p01: Int,
        val p02: Int,
        val p03: Int,
        val p04: Int,
        val p05: Int,
        val p06: Int,
        val p07: Int,
        val p08: Int,
        val p09: Int,
        val p10: Int,
        val p11: Int,
        val p12: Int,
        val p13: Int,
        val p14: Int,
        val p15: Int,
        val p16: Int,
        val p17: Int,
        val p18: Int,
        val p19: Int
    ) {
        companion object {
            @JvmStatic
            @JsonCreator
            fun creator(
                p00: Int = -1,
                p01: Int = -1,
                p02: Int = -1,
                p03: Int = -1,
                p04: Int = -1,
                p05: Int = -1,
                p06: Int = -1,
                p07: Int = -1,
                p08: Int = -1,
                p09: Int = -1,
                p10: Int = -1,
                p11: Int = -1,
                p12: Int = -1,
                p13: Int = -1,
                p14: Int = -1,
                p15: Int = -1,
                p16: Int = -1,
                p17: Int = -1,
                p18: Int = -1,
                p19: Int = -1
            ) = Dst(p00, p01, p02, p03, p04, p05, p06, p07, p08, p09, p10, p11, p12, p13, p14, p15, p16, p17, p18, p19)
        }
    }
}
