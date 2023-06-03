package deathClock

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.actions.common.DamageAction
import com.megacrit.cardcrawl.actions.common.ReducePowerAction
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.dungeons.AbstractDungeon

fun AbstractCreature.applySummonDeath(amount : Int = 1) {
    this.applySummonDeath(this, amount)
}

fun AbstractCreature.applySummonDeath(source : AbstractCreature, amount : Int = 1) {
    val action = ApplyPowerAction(this,source, SummonDeathPower(this,amount))
    AbstractDungeon.actionManager.addToBottom(action)
}

fun AbstractCreature.reduceSummonDeath(amount : Int = 1) {
    this.reduceSummonDeath(this, amount)
}

fun AbstractCreature.reduceSummonDeath(source : AbstractCreature, amount : Int = 1) {
    val action = ReducePowerAction(this, source, SummonDeathPower.Id, amount)
    AbstractDungeon.actionManager.addToTop(action)
}

fun AbstractCreature.resetSummonDeath(source : AbstractCreature? = null) {
    val action = RemoveSpecificPowerAction(this, source ?: this, SummonDeathPower.Id)
    AbstractDungeon.actionManager.addToBottom(action)
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

fun AbstractCreature.getSummonDeathAmount() : Int {
    return this.getPower(SummonDeathPower.Id)?.amount ?: 0
}
