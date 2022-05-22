package no.bekk.power.meteringpoint

import no.bekk.power.valuetypes.Address
import no.bekk.power.valuetypes.MeteringPointId
import no.bekk.power.valuetypes.MeteringPointName
import no.bekk.power.valuetypes.PowerZone

class MeteringPointEntity(
    internal val meteringPointId: MeteringPointId,
    internal val meteringPointName: MeteringPointName,
    internal val address: Address,
    internal val powerZone: PowerZone
) {

    val id: MeteringPointId
        get() = meteringPointId

}
