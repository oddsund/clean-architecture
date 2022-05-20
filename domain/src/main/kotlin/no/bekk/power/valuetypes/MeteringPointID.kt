package no.bekk.power.valuetypes

@JvmInline
value class MeteringPointId(val value: String) {
    init {
        require(value.length == 18) { "meteringPointId must be 18 digits" }
    }
}
