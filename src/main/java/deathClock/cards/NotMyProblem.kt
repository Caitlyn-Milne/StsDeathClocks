package deathClock.cards

import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import deathClock.AbstractCardEnum
import deathClock.DeathClock
import deathClock.SummonDeathPower
import deathClock.applySummonDeath

class NotMyProblem : CustomCard(
    ID,
    name,
    "images/cards/TEMP.png",
    cost,
    description,
    CardType.SKILL,
    AbstractCardEnum.DEATH_CLOCK_DEATH,
    CardRarity.UNCOMMON,
    CardTarget.ENEMY
) {

    companion object {
        val ID = DeathClock.getId("NotMyProblem")
        private val cardStrings = CardCrawlGame.languagePack.getCardStrings(ID)
        val name = cardStrings.NAME!!
        val description = cardStrings.DESCRIPTION!!
        const val cost = 1
    }

    override fun upgrade() {
        if(upgraded) return
        this.rawDescription = cardStrings.UPGRADE_DESCRIPTION
        upgradeName()
        initializeDescription()
        target = CardTarget.ALL_ENEMY
    }

    override fun use(player : AbstractPlayer, monster : AbstractMonster?) {
        val playerAmount = player.getPower(SummonDeathPower.Id).amount

        if (!upgraded) {
            monster?.applySummonDeath(playerAmount)
            return
        }
        AbstractDungeon.getMonsters().monsters.forEach {
            it.applySummonDeath(playerAmount)
        }
    }
}