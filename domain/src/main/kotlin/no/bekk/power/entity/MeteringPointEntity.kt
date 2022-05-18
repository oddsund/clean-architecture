package no.bekk.power.entity

import no.bekk.power.valuetypes.Address
import no.bekk.power.valuetypes.MeteringPointID
import no.bekk.power.valuetypes.MeteringPointName
import no.bekk.power.valuetypes.PowerZone

class MeteringPointEntity(
    val meteringPointID: MeteringPointID,
    val meteringPointName: MeteringPointName,
    val address: Address,
    val powerZone: PowerZone) {
}
