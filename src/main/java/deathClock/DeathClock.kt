package deathClock

import basemod.BaseMod
import basemod.abstracts.CustomCard
import basemod.helpers.RelicType
import basemod.interfaces.*
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer
import com.google.gson.Gson
import com.megacrit.cardcrawl.cards.CardGroup
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.localization.CardStrings
import com.megacrit.cardcrawl.localization.PowerStrings
import com.megacrit.cardcrawl.localization.RelicStrings
import deathClock.cards.*
import deathClock.relics.DeathsAmendment
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import java.lang.String.*
import java.nio.charset.StandardCharsets

class KeywordInfo(
    var NAMES: Array<String>,
    var DESCRIPTION: String,
    var PROPER_NAME: String
) {
    override fun toString(): String {
        return "NAMES: [${NAMES.joinToString(",")}]\n" +
                "DESCRIPTION: $DESCRIPTION\n" +
                "PROPER_NAME: $PROPER_NAME"
    }
}

@SpireInitializer
class DeathClock :
    EditRelicsSubscriber,
    EditStringsSubscriber,
    EditCardsSubscriber,
    PostCreateStartingDeckSubscriber,
    PostCreateStartingRelicsSubscriber,
    EditKeywordsSubscriber{

    companion object {
        val logger: Logger = LogManager.getLogger(DeathClock::class.java)

        val DeathCards : List<CustomCard> by lazy { listOf(
            Satanic(),
            SevenTrumpets(),
            Resurrection(),
            NotMyProblem(),
            Sacrifice(),
            DodgeDeath(),
            ScytheStrike()
        ) }


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

            BaseMod.addPower(SummonDeathPower::class.java, SummonDeathPower.Id)
        }

        const val modId = "DeathClock"

        fun getId(name : String) : String{
            return "$modId:$name"
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
        BaseMod.addRelic(DeathsAmendment(), RelicType.SHARED)
    }

    override fun receivePostCreateStartingRelics(player: AbstractPlayer.PlayerClass?, relics: ArrayList<String>) {
        relics.add(DeathsCalling.ID)
    }

    override fun receiveEditCards() {
        logger.info("EDIT CARD -> ADDING CARDS")

        DeathCards.forEach { card ->
            BaseMod.addCard(card)
        }
    }

    override fun receivePostCreateStartingDeck(player : AbstractPlayer.PlayerClass, cards: CardGroup) {
        cards.addToBottom(ScytheStrike())
        cards.addToBottom(DodgeDeath())
        cards.addToBottom(Sacrifice())
        cards.addToBottom(NotMyProblem())
        cards.addToBottom(Resurrection())
        cards.addToBottom(SevenTrumpets())
        cards.addToBottom(Satanic())
    }

    override fun receiveEditKeywords() {
        logger.info("KEYWORD ----------------------------------------------------------------")
        val gson = Gson()
        val json: String = Gdx.files.internal("localization/eng/Keywords.json")
            .readString(valueOf(StandardCharsets.UTF_8))

        val keywords = gson.fromJson(json, Array<KeywordInfo>::class.java)

        keywords.forEach { keyword ->

            BaseMod.addKeyword(modId.lowercase(), keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION)
            logger.info("added keyword: $keyword")
        }
    }
}