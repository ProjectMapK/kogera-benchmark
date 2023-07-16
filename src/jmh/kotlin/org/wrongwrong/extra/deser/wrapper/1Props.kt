package org.wrongwrong.extra.deser.wrapper

import com.fasterxml.jackson.annotation.JsonCreator
import org.wrongwrong.extra.DataClass

open class A_1Props_Constructor : DeserBase<A_1Props_Constructor.Dst>(A_1Props_Constructor::Dst, Dst::class) {
    data class Dst(val p0: DataClass = DataClass(-1))
}

open class A_1Props_Function : DeserBase<A_1Props_Function.Dst>(A_1Props_Function::Dst, Dst::class) {
    data class Dst(val p0: DataClass) {
        companion object {
            @JvmStatic
            @JsonCreator
            fun creator(p0: DataClass = DataClass(-1)) = Dst(p0)
        }
    }
}
