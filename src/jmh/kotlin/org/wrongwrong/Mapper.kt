package org.wrongwrong

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper as originalMapper
import io.github.projectmapk.jackson.module.kogera.KotlinFeature as KogeraFeature
import io.github.projectmapk.jackson.module.kogera.KotlinModule as KogeraModule
import io.github.projectmapk.jackson.module.kogera.jacksonObjectMapper as kogeraMapper

private fun ObjectMapper.applyCommonOptions() =
    this.apply { disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES) }

enum class Mapper {
    Original {
        override val value: ObjectMapper = originalMapper {
            enable(KotlinFeature.KotlinPropertyNameAsImplicitName)
        }.applyCommonOptions()
    },
    Kogera {
        override val value: ObjectMapper = kogeraMapper().applyCommonOptions()
    },
    OriginalStrictNullCheck {
        override val value: ObjectMapper = ObjectMapper()
            .registerKotlinModule {
                enable(KotlinFeature.StrictNullChecks)
                    .enable(KotlinFeature.KotlinPropertyNameAsImplicitName)
            }
            .applyCommonOptions()
    },
    KogeraStrictNullCheck {
        override val value: ObjectMapper = ObjectMapper()
            .registerModule(KogeraModule.Builder().enable(KogeraFeature.StrictNullChecks).build())
            .applyCommonOptions()
    };

    abstract val value: ObjectMapper
}
