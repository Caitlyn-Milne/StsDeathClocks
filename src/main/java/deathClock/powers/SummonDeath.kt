package deathClock

import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.actions.common.DamageAction
import com.megacrit.cardcrawl.actions.common.ReducePowerAction
import com.megacrit.cardcrawl.actions.utility.SFXAction
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.dungeons.AbstractDungeon.actionManager
import com.megacrit.cardcrawl.powers.AbstractPower
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

class SummonDeathPower(creature : AbstractCreature, amount : Int) : AbstractPower() {

    companion object{
        var baseDamage = 30
        val damage get() = baseDamage + combatDamageDelta
        var baseRequiredStacks = 5
        val Id = DeathClock.getId("SummonDeath")
        val logger: Logger = LogManager.getLogger(SummonDeathPower::class.java)
        var combatDamageDelta = 0
        fun increaseDamageForCombat(damage : Int) {
            combatDamageDelta += damage
            AbstractDungeon.player.getPower(Id)?.updateDescription()
            AbstractDungeon.getMonsters().monsters.forEach { monster ->
                monster.getPower(Id)?.updateDescription()
            }
        }
    }

    private val powerStrings = CardCrawlGame.languagePack.getPowerStrings(Id)

    init{
        this.region48 = TextureAtlas.AtlasRegion(TextureLoader.getTexture("images/powers/GrimReaperSmall.png"),0,0,32,32)
        this.region128 = TextureAtlas.AtlasRegion(TextureLoader.getTexture("images/powers/GrimReaperLarge.png"),0,0,84,84)
        this.type = PowerType.DEBUFF
        this.ID = Id
        this.name = powerStrings.NAME
        this.owner =  creature
        this.amount = amount

        updateDescription()

        logger.info("owner $creature")
    }

    override fun onInitialApplication() {
        super.onInitialApplication()
        logger.info("initial  application ${owner}")

    }

    override fun onVictory() {
        combatDamageDelta = 0
    }

    override fun updateDescription() {
        description = powerStrings.DESCRIPTIONS[0]
            .replace("!D!","$damage")
            .replace("!S!", "$baseRequiredStacks")
    }

    override fun onApplyPower(power: AbstractPower, target: AbstractCreature, source: AbstractCreature) {
        super.onApplyPower(power, target, source)
        if(power !is SummonDeathPower) return
        val currentAmount : Int = target.getPower(Id)?.amount ?: 0
        if (currentAmount + power.amount >= baseRequiredStacks) {
            power.summonDeath()
        }
    }


    override fun onRemove() {
        super.onRemove()
    }

    fun summonDeath() {
        val damageInfo = DamageInfo(owner,damage,DamageInfo.DamageType.HP_LOSS)
        val damageAction = DamageAction(owner, damageInfo, AbstractGameAction.AttackEffect.SLASH_HEAVY)
        val sfxAction = SFXAction("MONSTER_CHAMP_SLAP")
        val reduceAction = ReducePowerAction(owner,owner, ID, baseRequiredStacks)

        actionManager.addToBottom(reduceAction)
        actionManager.addToBottom(sfxAction)
        actionManager.addToBottom(damageAction)

    }
}

