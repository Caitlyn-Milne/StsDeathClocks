package deathClock

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.actions.common.DamageAction
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.dungeons.AbstractDungeon

fun AbstractCreature.applyDeathMarked(amount : Int = 1) {
    this.applyDeathMarked(this, amount)
}

fun AbstractCreature.applyDeathMarked(source : AbstractCreature, amount : Int = 1) {
    val action = ApplyPowerAction(this,source, DeathMarkedPower(this,amount))
    AbstractDungeon.actionManager.addToBottom(action)
}

fun AbstractCreature.reduceDeathMarked(amount : Int = 1) {
    this.reduceDeathMarked(this, amount)
}

fun AbstractCreature.reduceDeathMarked(source : AbstractCreature, amount : Int = 1) {
    val action = ReduceDeathMarkAction(this, source, amount)
    AbstractDungeon.actionManager.addToTop(action)
}


fun AbstractCreature.damage(
    amount: Int,
    source: AbstractCreature? = null,
    damageType: DamageInfo.DamageType = DamageInfo.DamageType.NORMAL,
    attackEffect: AttackEffect = AttackEffect.NONE){
    val damageInfo = DamageInfo(source ?: this, amount, damageType)
    val damage = DamageAction(this, damageInfo, attackEffect)
    AbstractDungeon.actionManager.addToBottom(damage)
}
