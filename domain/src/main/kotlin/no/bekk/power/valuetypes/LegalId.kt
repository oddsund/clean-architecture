package no.bekk.power.valuetypes

@JvmInline
value class LegalId(private val value: String) {

    init {
        require(value.length >= 8) { "Invalid length for legalId ${value.length}" }
    }
}
