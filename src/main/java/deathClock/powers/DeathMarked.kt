package deathClock

import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.actions.common.DamageAction
import com.megacrit.cardcrawl.actions.utility.SFXAction
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.dungeons.AbstractDungeon.actionManager
import com.megacrit.cardcrawl.powers.AbstractPower
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

class DeathMarkedPower(creature : AbstractCreature, amount : Int) : AbstractPower() {

    companion object{
        var deathCalledDamage = 40
        val Id = DeathClock.getId("DeathMarked")
        val logger: Logger = LogManager.getLogger(DeathMarkedPower::class.java)
        const val reapplyAmount = 5
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
    }

    override fun updateDescription() {
        description = powerStrings.DESCRIPTIONS[0]
    }

    override fun atStartOfTurn() {
        super.atStartOfTurn()
        owner.reduceDeathMarked(1)
    }

    override fun onRemove() {
        super.onRemove()
    }

    fun callDeath() {
        val damageInfo = DamageInfo(owner,deathCalledDamage,DamageInfo.DamageType.HP_LOSS)
        val damageAction = DamageAction(owner, damageInfo, AbstractGameAction.AttackEffect.SLASH_HEAVY)
        val sfxAction = SFXAction("MONSTER_CHAMP_SLAP")

        actionManager.addToBottom(sfxAction)
        actionManager.addToBottom(damageAction)

    }

    fun reapply() {
        owner.applyDeathMarked(reapplyAmount)
    }

}

