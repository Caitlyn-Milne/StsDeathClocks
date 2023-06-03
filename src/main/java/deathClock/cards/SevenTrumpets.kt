package deathClock.cards

import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.monsters.AbstractMonster
import deathClock.AbstractCardEnum
import deathClock.DeathClock
import deathClock.SummonDeathPower

class SevenTrumpets : CustomCard(
    ID,
    name,
    "images/cards/DodgeDeath.png",
    cost,
    description,
    CardType.POWER,
    AbstractCardEnum.DEATH_CLOCK_DEATH,
    CardRarity.RARE,
    CardTarget.NONE
) {

    companion object {
        val ID = DeathClock.getId("SevenTrumpets")
        private val cardStrings = CardCrawlGame.languagePack.getCardStrings(ID)
        val name = cardStrings.NAME!!
        val description = cardStrings.DESCRIPTION!!
        const val cost = 2
        var summonDeathDamageDelta = 0;
    }

    init {
        baseMagicNumber = 15
        magicNumber = baseMagicNumber
    }



    override fun upgrade() {
        if(upgraded) return
        isInnate = true
        rawDescription = cardStrings.UPGRADE_DESCRIPTION
        upgradeName()
        initializeDescription()
    }

    override fun use(p : AbstractPlayer?, monster : AbstractMonster?) {
        SummonDeathPower.increaseDamageForCombat(magicNumber)
    }
}