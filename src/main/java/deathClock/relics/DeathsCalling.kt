package deathClock

import basemod.abstracts.CustomRelic
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster

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
        AbstractDungeon.player!!.applyDeathMarked(DeathMarkedPower.reapplyAmount)
        AbstractDungeon.getMonsters().monsters.forEach { monster ->
            monster.applyDeathMarked(DeathMarkedPower.reapplyAmount)
        }
    }

    override fun onSpawnMonster(monster: AbstractMonster) {
        super.onSpawnMonster(monster)
        val player : AbstractPlayer = AbstractDungeon.player!!
        monster.applyDeathMarked(player,DeathMarkedPower.reapplyAmount)
    }
}