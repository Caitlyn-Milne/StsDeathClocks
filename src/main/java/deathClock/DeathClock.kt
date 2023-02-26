package deathClock

import basemod.BaseMod
import basemod.helpers.RelicType
import basemod.interfaces.EditCardsSubscriber
import basemod.interfaces.EditRelicsSubscriber
import basemod.interfaces.EditStringsSubscriber
import basemod.interfaces.PostCreateStartingDeckSubscriber
import basemod.interfaces.PostCreateStartingRelicsSubscriber
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.utils.compression.lzma.Base
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer
import com.megacrit.cardcrawl.cards.CardGroup
import com.megacrit.cardcrawl.cards.blue.Strike_Blue
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.localization.CardStrings
import com.megacrit.cardcrawl.localization.PowerStrings
import com.megacrit.cardcrawl.localization.RelicStrings
import deathClock.cards.DodgeDeath
import deathClock.cards.ScytheStrike
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import java.util.ArrayList

@SpireInitializer
class DeathClock :
    EditRelicsSubscriber,
    EditStringsSubscriber,
    EditCardsSubscriber,
    PostCreateStartingDeckSubscriber,
    PostCreateStartingRelicsSubscriber {

    companion object {
        val logger: Logger = LogManager.getLogger(DeathClock::class.java)

        @JvmStatic
        fun initialize() {
            DeathClock()
            BaseMod.addColor(
                /* color = */ AbstractCardEnum.DEATH_CLOCK_DEATH,
                /* bgColor = */ Color.RED,
                /* backColor = */ Color.RED,
                /* frameColor = */ Color.GREEN,
                /* frameOutlineColor = */ Color.MAGENTA,
                /* descBoxColor = */ Color.CORAL,
                /* trailVfxColor = */ Color.WHITE,
                /* glowColor = */ Color.WHITE,
                /* attackBg = */ "images/cardback/bg_attack.png",
                /* skillBg = */ "images/cardback/bg_skill.png",
                /* powerBg = */ "images/cardback/bg_power.png",
                /* energyOrb = */ "images/cardback/energy_orb.png",
                /* attackBgPortrait = */ "images/cardback/bg_attack_p.png",
                /* skillBgPortrait = */ "images/cardback/bg_skill_p.png",
                /* powerBgPortrait = */ "images/cardback/bg_power_p.png",
                /* energyOrbPortrait = */ "images/cardback/energy_orb_p.png"
            )
        }

        fun getId(name : String) : String{
            return "DeathClock:$name"
        }
    }

    init {
        BaseMod.subscribe(this)
    }

    override fun receiveEditStrings() {
        BaseMod.loadCustomStringsFile(
            RelicStrings::class.java,
            "localization/eng/RelicsStrings.json"
        )

        BaseMod.loadCustomStringsFile(
            PowerStrings::class.java,
            "localization/eng/PowersStrings.json"
        )

        BaseMod.loadCustomStringsFile(
            CardStrings::class.java,
            "localization/eng/CardsStrings.json"
        )
    }

    override fun receiveEditRelics() {
        BaseMod.addRelic(DeathsCalling(), RelicType.SHARED)
    }

    override fun receivePostCreateStartingRelics(player: AbstractPlayer.PlayerClass?, relics: ArrayList<String>) {
        relics.add(DeathsCalling.ID)

    }

    override fun receiveEditCards() {
        BaseMod.addCard(ScytheStrike())
        BaseMod.addCard(DodgeDeath())
    }

    override fun receivePostCreateStartingDeck(player : AbstractPlayer.PlayerClass, cards: CardGroup) {
        cards.addToBottom(ScytheStrike())
        cards.addToBottom(ScytheStrike())
        cards.addToBottom(ScytheStrike())
        cards.addToBottom(ScytheStrike())
        cards.addToBottom(ScytheStrike())
        cards.addToBottom(ScytheStrike())
        cards.addToBottom(DodgeDeath())
    }
}