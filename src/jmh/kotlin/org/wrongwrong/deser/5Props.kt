package org.wrongwrong.deser

import com.fasterxml.jackson.annotation.JsonCreator

open class E_5Props_Constructor : DeserBase<E_5Props_Constructor.Dst>(::Dst, Dst::class) {
    data class Dst(
        val p0: Int = -1,
        val p1: Int = -1,
        val p2: Int = -1,
        val p3: Int = -1,
        val p4: Int = -1
    )
}

open class E_5Props_Function : DeserBase<E_5Props_Function.Dst>(::Dst, Dst::class) {
    data class Dst(
        val p0: Int,
        val p1: Int,
        val p2: Int,
        val p3: Int,
        val p4: Int
    ) {
        companion object {
            @JvmStatic
            @JsonCreator
            fun creator(
                p0: Int = -1,
                p1: Int = -1,
                p2: Int = -1,
                p3: Int = -1,
                p4: Int = -1
            ) = Dst(p0, p1, p2, p3, p4)
        }
    }
}
