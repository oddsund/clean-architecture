package no.bekk.power.domain.meteringpoint

import no.bekk.power.domain.valuetypes.Address
import no.bekk.power.domain.valuetypes.MeteringPointId
import no.bekk.power.domain.valuetypes.MeteringPointName
import no.bekk.power.domain.valuetypes.PowerZone

class MeteringPointEntity(
    private val meteringPointId: MeteringPointId,
    val name: MeteringPointName,
    val address: Address,
    val powerZone: PowerZone
) {

    constructor(meteringPointId: String, name: String, street: String, zip: String, powerZone: String) :
            this(
                MeteringPointId(meteringPointId),
                MeteringPointName(name),
                Address(street, zip),
                PowerZone.valueOf(powerZone)
            )

    val id: MeteringPointId
        get() = meteringPointId
}
