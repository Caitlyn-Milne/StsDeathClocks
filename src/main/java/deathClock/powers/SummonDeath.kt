package deathClock

import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.actions.common.DamageAction
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction
import com.megacrit.cardcrawl.actions.utility.SFXAction
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon.actionManager
import com.megacrit.cardcrawl.powers.AbstractPower
import com.megacrit.cardcrawl.powers.TheBombPower
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

class SummonDeathPower(creature : AbstractCreature, amount : Int) : AbstractPower() {

    companion object{
        var deathCalledDamage = 40
        val Id = DeathClock.getId("SummonDeath")
        val logger: Logger = LogManager.getLogger(SummonDeathPower::class.java)
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

    override fun updateDescription() {
        description = powerStrings.DESCRIPTIONS[0].replace("!D!","$deathCalledDamage")
    }

    override fun onApplyPower(power: AbstractPower, target: AbstractCreature, source: AbstractCreature) {
        super.onApplyPower(power, target, source)
        if(power is SummonDeathPower && power.amount + amount >= 5) {
            power.summonDeath()
        }
    }


    override fun onRemove() {
        super.onRemove()
    }

    fun summonDeath() {
        val damageInfo = DamageInfo(owner,deathCalledDamage,DamageInfo.DamageType.HP_LOSS)
        val damageAction = DamageAction(owner, damageInfo, AbstractGameAction.AttackEffect.SLASH_HEAVY)
        val sfxAction = SFXAction("MONSTER_CHAMP_SLAP")
        val removeAction = RemoveSpecificPowerAction(owner,owner,this)

        actionManager.addToBottom(sfxAction)
        actionManager.addToBottom(damageAction)
        actionManager.addToBottom(removeAction)
    }
}

