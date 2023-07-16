package org.wrongwrong

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper as originalMapper
import io.github.projectmapk.jackson.module.kogera.KotlinFeature as KogeraFeature
import io.github.projectmapk.jackson.module.kogera.KotlinModule as KogeraModule
import io.github.projectmapk.jackson.module.kogera.jacksonObjectMapper as kogeraMapper

private fun ObjectMapper.applyCommonOptions() =
    this.apply { disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES) }

enum class Mapper {
    Original {
        override val value: ObjectMapper = originalMapper().applyCommonOptions()
    },
    Kogera {
        override val value: ObjectMapper = kogeraMapper().applyCommonOptions()
    },
    OriginalStrictNullCheck {
        override val value: ObjectMapper = ObjectMapper()
            .registerModule(KotlinModule.Builder().enable(KotlinFeature.StrictNullChecks).build())
            .applyCommonOptions()
    },
    KogeraStrictNullCheck {
        override val value: ObjectMapper = ObjectMapper()
            .registerModule(KogeraModule.Builder().enable(KogeraFeature.StrictNullChecks).build())
            .applyCommonOptions()
    };

    abstract val value: ObjectMapper
}
