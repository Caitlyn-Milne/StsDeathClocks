package deathClock.patches

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import deathClock.DeathClock
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

@SpirePatch(clz = AbstractDungeon::class, method = "initializeCardPools", paramtypez = []) object CardPoolPatch {

    val logger: Logger = LogManager.getLogger(CardPoolPatch::class.java)

    @SpireInsertPatch(rloc=11, localvars=["tmpPool"])
    @JvmStatic
    fun insert(d: AbstractDungeon, tmpPool: ArrayList<AbstractCard>) {
        addDeathCards(tmpPool)
    }

    @JvmStatic
    fun addDeathCards(tmpPool: ArrayList<AbstractCard>) {
        logger.info("[INFO] Adding death cards into card pool.")

        DeathClock.DeathCards.forEach { card ->
            if (card.rarity == AbstractCard.CardRarity.BASIC) return@forEach
            tmpPool.add(card)
        }
    }
}