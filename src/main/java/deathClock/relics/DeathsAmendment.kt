package deathClock.relics

import basemod.abstracts.CustomRelic
import deathClock.DeathClock
import deathClock.SummonDeathPower
import deathClock.TextureLoader
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger


class DeathsAmendment : CustomRelic(
    ID,
    TextureLoader.getTexture("images/relics/DeathsCalling.png"),
    RelicTier.STARTER,
    LandingSound.CLINK
) {

    companion object {
        val logger: Logger = LogManager.getLogger(DeathsAmendment::class.java)
        val ID = DeathClock.getId("DeathsAmendment")
    }

    override fun getUpdatedDescription(): String {
        return DESCRIPTIONS[0]
    }

    override fun atBattleStart() {
        super.atBattleStart()
        SummonDeathPower.baseRequiredStacks = 4
    }

}