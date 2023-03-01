package deathClock.cards

import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import deathClock.*

//been replaced with not my problem
class Sacrifice : CustomCard(
    ID,
    name,
    "images/cards/DodgeDeath.png",
    cost,
    description,
    CardType.SKILL,
    AbstractCardEnum.DEATH_CLOCK_DEATH,
    CardRarity.COMMON,
    CardTarget.SELF_AND_ENEMY
) {

    companion object {
        val ID = DeathClock.getId("Sacrifice")
        private val cardStrings = CardCrawlGame.languagePack.getCardStrings(ID)
        val name = cardStrings.NAME!!
        val description = cardStrings.DESCRIPTION!!
        const val cost = 0
    }

    override fun upgrade() {
        if(upgraded) return
        this.rawDescription = cardStrings.UPGRADE_DESCRIPTION
        initializeDescription()
        upgradeName()
    }


    override fun use(p : AbstractPlayer?, monster : AbstractMonster) {
        val player = p ?: AbstractDungeon.player
        val playerAmount = player.getPower(SummonDeathPower.Id).amount
        val monsterAmount = monster.getPower(SummonDeathPower.Id).amount
        val delta = playerAmount - monsterAmount

        if (upgraded) {
            player.resetSummonDeath()
        }
        if (delta == 0) return
        if (delta > 0) {
            monster.applySummonDeath(delta)
            if (upgraded) return
            player.reduceSummonDeath(delta)
        }
        else {
            monster.reduceSummonDeath(-delta)
            if (upgraded) return
            player.applySummonDeath(-delta)
        }
    }
}