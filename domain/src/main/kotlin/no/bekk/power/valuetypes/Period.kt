package no.bekk.power.valuetypes

import java.time.LocalDateTime
import java.time.ZonedDateTime

// TODO: Validate that to is after from
class Period(val from: ZonedDateTime, val to: ZonedDateTime) {
    init {
        require(from <= to) {"From $from must be before or equal to $to"}
    }
}
