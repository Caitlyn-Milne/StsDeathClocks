package deathClock.cards

import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import deathClock.*

class NotMyProblem : CustomCard(
    ID,
    name,
    "images/cards/DodgeDeath.png",
    cost,
    description,
    CardType.SKILL,
    AbstractCardEnum.DEATH_CLOCK_DEATH,
    CardRarity.UNCOMMON,
    CardTarget.SELF_AND_ENEMY
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
        upgradeName()
    }

    override fun use(p : AbstractPlayer?, monster : AbstractMonster) {
        val player = p ?: AbstractDungeon.player
        val playerAmount = player.getPower(SummonDeathPower.Id).amount

        if(upgraded) {
            player.resetSummonDeath()
        }
        monster.applySummonDeath(playerAmount)
    }
}