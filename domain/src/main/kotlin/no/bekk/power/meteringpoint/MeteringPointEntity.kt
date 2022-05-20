package no.bekk.power.meteringpoint

import no.bekk.power.valuetypes.Address
import no.bekk.power.valuetypes.MeteringPointId
import no.bekk.power.valuetypes.MeteringPointName
import no.bekk.power.valuetypes.PowerZone

class MeteringPointEntity(
    val meteringPointId: MeteringPointId,
    val meteringPointName: MeteringPointName,
    val address: Address,
    val powerZone: PowerZone) {
}
