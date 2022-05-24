package no.bekk.power.domain.valuetypes

import java.time.OffsetDateTime

class Period(val from: OffsetDateTime, val to: OffsetDateTime) {
    init {
        require(from <= to) {"From $from must be before or equal to $to"}
    }
}
