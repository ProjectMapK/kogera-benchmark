package org.wrongwrong

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper as originalMapper
import io.github.projectmapk.jackson.module.kogera.KotlinFeature as KogeraFeature
import io.github.projectmapk.jackson.module.kogera.jacksonObjectMapper as kogeraMapper

private fun ObjectMapper.applyCommonOptions() =
    this.apply { disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES) }

enum class Mapper {
    Original {
        override val value: ObjectMapper = originalMapper {
            enable(KotlinFeature.KotlinPropertyNameAsImplicitName)
            disable(KotlinFeature.SingletonSupport)
            disable(KotlinFeature.StrictNullChecks)
        }.applyCommonOptions()
    },
    Kogera {
        override val value: ObjectMapper = kogeraMapper {
            disable(KogeraFeature.SingletonSupport)
            disable(KogeraFeature.StrictNullChecks)
        }.applyCommonOptions()
    },
    OriginalStrictNullCheck {
        override val value: ObjectMapper = originalMapper {
            enable(KotlinFeature.KotlinPropertyNameAsImplicitName)
            enable(KotlinFeature.StrictNullChecks)
            disable(KotlinFeature.StrictNullChecks)
        }.applyCommonOptions()
    },
    KogeraStrictNullCheck {
        override val value: ObjectMapper = kogeraMapper {
            enable(KogeraFeature.StrictNullChecks)
            disable(KogeraFeature.SingletonSupport)
        }.applyCommonOptions()
    };

    abstract val value: ObjectMapper
}
