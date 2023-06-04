package deathClock.cards

import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.actions.common.GainBlockAction
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import deathClock.AbstractCardEnum
import deathClock.DeathClock
import deathClock.reduceSummonDeath

class DodgeDeath() : CustomCard(
    ID,
    name,
    "images/cards/DodgeDeath.png",
    cost,
    description,
    CardType.SKILL,
    AbstractCardEnum.DEATH_CLOCK_DEATH,
    CardRarity.BASIC,
    CardTarget.SELF
) {


    companion object {
        val ID = DeathClock.getId("DodgeDeath")
        private val cardStrings = CardCrawlGame.languagePack.getCardStrings(ID)
        val name = cardStrings.NAME!!
        val description = cardStrings.DESCRIPTION!!
        const val cost = 1
    }

    init {
        baseBlock = 4
        baseMagicNumber = 1
    }


    override fun upgrade() {
        if(upgraded) return
        upgradeBlock(3)
        upgradeName()
    }

    override fun use(player : AbstractPlayer, monster : AbstractMonster?) {
        val action = GainBlockAction(player, block)
        AbstractDungeon.actionManager.addToTop(action)
        player.reduceSummonDeath(baseMagicNumber)
    }
}