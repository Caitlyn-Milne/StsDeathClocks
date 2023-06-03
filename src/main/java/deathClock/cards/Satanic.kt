package deathClock.cards

import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.monsters.AbstractMonster
import deathClock.*

class Satanic : CustomCard(
    ID,
    name,
    "images/cards/DodgeDeath.png",
    cost,
    description,
    CardType.ATTACK,
    AbstractCardEnum.DEATH_CLOCK_DEATH,
    CardRarity.UNCOMMON,
    CardTarget.SELF_AND_ENEMY
) {

    companion object {
        val ID = DeathClock.getId("Satanic")
        private val cardStrings = CardCrawlGame.languagePack.getCardStrings(ID)
        val name = cardStrings.NAME!!
        val description = cardStrings.DESCRIPTION!!
        const val cost = 1
    }

    init {
        baseDamage = 4
    }

    override fun upgrade() {
        if(upgraded) return
        upgradeDamage(3)
        upgradeName()
    }

    override fun use(player : AbstractPlayer, monster : AbstractMonster) {
        val summonDeathAmount = player.getSummonDeathAmount()
        for (i in 0 until summonDeathAmount) {
            monster.damage(baseDamage, player, attackEffect=AbstractGameAction.AttackEffect.POISON)
        }
        player.applySummonDeath(baseMagicNumber)
    }
}