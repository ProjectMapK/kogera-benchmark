package org.wrongwrong.extra.deser.wrapper

import com.fasterxml.jackson.annotation.JsonCreator
import org.wrongwrong.extra.DataClass

open class E_5Props_Constructor : DeserBase<E_5Props_Constructor.Dst>(E_5Props_Constructor::Dst, Dst::class) {
    data class Dst(
        val p0: DataClass = DataClass(-1),
        val p1: DataClass = DataClass(-1),
        val p2: DataClass = DataClass(-1),
        val p3: DataClass = DataClass(-1),
        val p4: DataClass = DataClass(-1)
    )
}

open class E_5Props_Function : DeserBase<E_5Props_Function.Dst>(E_5Props_Function::Dst, Dst::class) {
    data class Dst(
        val p0: DataClass,
        val p1: DataClass,
        val p2: DataClass,
        val p3: DataClass,
        val p4: DataClass
    ) {
        companion object {
            @JvmStatic
            @JsonCreator
            fun creator(
                p0: DataClass = DataClass(-1),
                p1: DataClass = DataClass(-1),
                p2: DataClass = DataClass(-1),
                p3: DataClass = DataClass(-1),
                p4: DataClass = DataClass(-1)
            ) = Dst(p0, p1, p2, p3, p4)
        }
    }
}
