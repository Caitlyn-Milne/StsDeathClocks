package deathClock.cards

import basemod.abstracts.CustomCard
import basemod.abstracts.CustomReward
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.cards.blue.Strike_Blue
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import deathClock.DeathClock.Companion.getId
import deathClock.damage
import deathClock.reduceDeathMarked


class ScytheStrike : CustomCard(
    ID,
    name,
    "images/cards/ScytheStrike.png",
    cost,
    description,
    CardType.ATTACK,
    CardColor.COLORLESS,
    CardRarity.BASIC,
    CardTarget.ENEMY) {

    init {
        baseDamage = 4
        tags.add(CardTags.STRIKE)
        tags.add(CardTags.STARTER_STRIKE)
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
    }

    override fun use(player : AbstractPlayer, monster: AbstractMonster) {
        monster.damage(damage, player,attackEffect= AttackEffect.SLASH_DIAGONAL)
        monster.reduceDeathMarked(player,1)
    }
}