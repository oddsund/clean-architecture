package no.bekk.power.valuetypes

@JvmInline
value class MeteringPointName(val value: String) {
    init {
        require(value.length >= 3) { "Invalid lenght of meterintPointName: ${value.length}" }
    }
}
