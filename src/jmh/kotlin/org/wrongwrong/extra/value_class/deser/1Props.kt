package org.wrongwrong.extra.value_class.deser

import com.fasterxml.jackson.annotation.JsonCreator
import org.wrongwrong.extra.value_class.ValueClass

open class A_1Props_Constructor : DeserBase<A_1Props_Constructor.Dst>(A_1Props_Constructor::Dst, Dst::class) {
    data class Dst(val p0: ValueClass = ValueClass(-1))
}

open class A_1Props_Function : DeserBase<A_1Props_Function.Dst>(A_1Props_Function::Dst, Dst::class) {
    data class Dst(val p0: ValueClass) {
        companion object {
            @JvmStatic
            @JsonCreator
            fun creator(p0: ValueClass = ValueClass(-1)) = Dst(p0)
        }
    }
}
