package deathClock

import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.megacrit.cardcrawl.actions.common.DamageAction
import com.megacrit.cardcrawl.actions.common.ReducePowerAction
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.exordium.AcidSlime_L
import com.megacrit.cardcrawl.powers.AbstractPower
import deathClock.DeathClock
import deathClock.TextureLoader
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

class DeathMarkedPower(creature : AbstractCreature, amount : Int) : AbstractPower() {

    companion object{
        var deathCalledDamage = 50;
        val id = DeathClock.getId("DeathMarked")
        val logger: Logger = LogManager.getLogger(DeathMarkedPower::class.java)
    }

    private val powerStrings = CardCrawlGame.languagePack.getPowerStrings(id)

    init{
        this.region48 = TextureAtlas.AtlasRegion(TextureLoader.getTexture("images/powers/SusSmall.png"),0,0,32,32)
        this.region128 = TextureAtlas.AtlasRegion(TextureLoader.getTexture("images/powers/SusLarge.png"),0,0,84,84)
        this.type = PowerType.DEBUFF
        this.ID = id
        this.name = powerStrings.NAME
        this.owner =  creature
        this.amount = amount
    }

    override fun updateDescription() {
        description = powerStrings.DESCRIPTIONS[0]
    }

    override fun atEndOfTurn(isPlayer: Boolean) {
        owner.reduceDeathMarked(1)
    }

    override fun onRemove() {
        super.onRemove()
        owner.applyDeathMarked(6)
        owner.damage(deathCalledDamage, DamageInfo.DamageType.HP_LOSS)
    }
    
}

