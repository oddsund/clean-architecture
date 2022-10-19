package no.bekk.power.domain.valuetypes

data class MeteringPointName(val value: String) {
    init {
        require(value.length >= 3) { "Invalid length of meterintPointName: ${value.length}" }
    }
}
