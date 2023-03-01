package deathClock.cards

import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import deathClock.*

class NewLife : CustomCard(
    ID,
    name,
    "images/cards/DodgeDeath.png",
    cost,
    description,
    CardType.SKILL,
    AbstractCardEnum.DEATH_CLOCK_DEATH,
    CardRarity.COMMON,
    CardTarget.SELF
) {

    companion object {
        val ID = DeathClock.getId("NewLife")
        private val cardStrings = CardCrawlGame.languagePack.getCardStrings(ID)
        val name = cardStrings.NAME!!
        val description = cardStrings.DESCRIPTION!!
        const val cost = 2
    }

    override fun upgrade() {
        if(upgraded) return
        upgradeBaseCost(1)
        upgradeName()
    }

    override fun use(p : AbstractPlayer?, monster : AbstractMonster) {
        val player = p ?: AbstractDungeon.player
        player.resetSummonDeath()
    }
}