package no.bekk.power.domain.valuetypes

import java.time.ZonedDateTime

class Period(val from: ZonedDateTime, val to: ZonedDateTime) {
    init {
        require(from <= to) {"From $from must be before or equal to $to"}
    }
}
