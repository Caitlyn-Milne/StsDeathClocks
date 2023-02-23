package deathClock

import basemod.abstracts.CustomRelic
import com.brashmonkey.spriter.Player
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.rooms.AbstractRoom
import deathClock.DeathClock
import deathClock.TextureLoader

class DeathsCalling : CustomRelic(
    ID,
    TextureLoader.getTexture("images/relics/DeathsCalling.png"),
    RelicTier.STARTER,
    LandingSound.CLINK
) {

    companion object {
        val ID = DeathClock.getId("DeathsCalling")
    }

    override fun getUpdatedDescription(): String {
        return DESCRIPTIONS[0]
    }

    override fun atBattleStart() {
        super.atBattleStart()
        AbstractDungeon.player!!.applyDeathMarked(6)
        AbstractDungeon.getMonsters().monsters.forEach { monster ->
            monster.applyDeathMarked(6)
        }
    }

    override fun onSpawnMonster(monster: AbstractMonster) {
        super.onSpawnMonster(monster)
        val player : AbstractPlayer = AbstractDungeon.player!!
        monster.applyDeathMarked(player,6)
    }
}