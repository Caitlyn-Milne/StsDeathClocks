package deathClock

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.actions.common.DamageAction
import com.megacrit.cardcrawl.actions.common.ReducePowerAction
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.dungeons.AbstractDungeon

fun AbstractCreature.applyDeathMarked(amount : Int = 1) {
    this.applyDeathMarked(this, amount)
}

fun AbstractCreature.applyDeathMarked(source : AbstractCreature, amount : Int = 1) {
    val action = ApplyPowerAction(this,source, DeathMarkedPower(this,amount))
    AbstractDungeon.actionManager.addToTop(action)
}

fun AbstractCreature.reduceDeathMarked(amount : Int = 1) {
    this.reduceDeathMarked(this, amount)
}

fun AbstractCreature.reduceDeathMarked(source : AbstractCreature, amount : Int = 1) {
    val action = ReducePowerAction(this, source, DeathMarkedPower.id, amount)
    AbstractDungeon.actionManager.addToTop(action)
}

fun AbstractCreature.damage(amount : Int, damageType : DamageInfo.DamageType = DamageInfo.DamageType.NORMAL) {
    this.damage(amount, this, damageType)
}

fun AbstractCreature.damage(amount : Int ,source : AbstractCreature, damageType : DamageInfo.DamageType = DamageInfo.DamageType.NORMAL){
    val damageInfo = DamageInfo(source, amount, damageType)
    val damage = DamageAction(this, damageInfo)
    AbstractDungeon.actionManager.addToBottom(damage)
}
