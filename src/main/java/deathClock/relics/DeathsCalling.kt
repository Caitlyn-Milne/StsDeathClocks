package deathClock

import basemod.abstracts.CustomRelic
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

class DeathsCalling : CustomRelic(
    ID,
    TextureLoader.getTexture("images/relics/DeathsCalling.png"),
    RelicTier.STARTER,
    LandingSound.CLINK
) {

    companion object {
        val logger: Logger = LogManager.getLogger(DeathsCalling::class.java)
        val ID = DeathClock.getId("DeathsCalling")
    }

    override fun getUpdatedDescription(): String {
        return DESCRIPTIONS[0]
    }

    override fun atTurnStart() {
        super.atTurnStart()
        AbstractDungeon.getMonsters().monsters.forEach { monster ->
            monster.applySummonDeath(1)
        }
        AbstractDungeon.player!!.applySummonDeath(1)
    }

}