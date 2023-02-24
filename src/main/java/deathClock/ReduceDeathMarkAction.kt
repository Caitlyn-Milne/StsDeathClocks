package deathClock

import com.megacrit.cardcrawl.actions.common.ReducePowerAction
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.powers.AbstractPower
/**
 * Action for reducing the death mark, used because normal game removals should not call death
 */
class ReduceDeathMarkAction(
    target: AbstractCreature,
    source: AbstractCreature,
    amount: Int
) : ReducePowerAction(
    target,
    source,
    DeathMarkedPower.Id,
    amount
) {
    override fun update() {
        if (duration != startDuration) {
            tickDuration()
            return
        }
        val reduceMe: AbstractPower = target.getPower(DeathMarkedPower.Id) ?: return

        if (amount < reduceMe.amount) {
            reduceMe.reducePower(amount)
            reduceMe.updateDescription()
            AbstractDungeon.onModifyPower()
        } else if (reduceMe is DeathMarkedPower) {
            reduceMe.reducePower(1)
            reduceMe.callDeath()
            reduceMe.reapply()
        }
        tickDuration()
    }
}