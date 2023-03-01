package deathClock.cards

import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.monsters.AbstractMonster
import deathClock.AbstractCardEnum
import deathClock.DeathClock.Companion.getId
import deathClock.applySummonDeath
import deathClock.damage
import deathClock.reduceSummonDeath


class ScytheStrike : CustomCard(
    ID,
    name,
    "images/cards/ScytheStrike.png",
    cost,
    description,
    CardType.ATTACK,
    AbstractCardEnum.DEATH_CLOCK_DEATH,
    CardRarity.BASIC,
    CardTarget.ENEMY) {

    init {
        baseDamage = 6
        baseMagicNumber = 1
        tags.add(CardTags.STRIKE)
    }

    companion object{
        val ID = getId("ScytheStrike")
        private val cardStrings = CardCrawlGame.languagePack.getCardStrings(ID)
        val name = cardStrings.NAME!!
        val description = cardStrings.DESCRIPTION!!
        const val cost = 1
    }

    override fun upgrade() {
        if(upgraded) return
        upgradeDamage(3)
        upgradeName()
    }

    override fun use(player : AbstractPlayer, monster: AbstractMonster) {
        monster.damage(damage, player,attackEffect= AttackEffect.SLASH_DIAGONAL)
        monster.applySummonDeath(player,1)
    }
}