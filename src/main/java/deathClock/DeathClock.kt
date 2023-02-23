package deathClock

import basemod.BaseMod
import basemod.helpers.RelicType
import basemod.interfaces.EditRelicsSubscriber
import basemod.interfaces.EditStringsSubscriber
import basemod.interfaces.PostCreateStartingRelicsSubscriber
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.localization.PowerStrings
import com.megacrit.cardcrawl.localization.RelicStrings
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import java.util.ArrayList

@SpireInitializer
class DeathClock : EditRelicsSubscriber, EditStringsSubscriber, PostCreateStartingRelicsSubscriber {

    companion object {
        val logger: Logger = LogManager.getLogger(DeathClock::class.java)

        @JvmStatic
        fun initialize() {
            DeathClock()
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
    }

    override fun receiveEditRelics() {
        BaseMod.addRelic(DeathsCalling(), RelicType.SHARED)
    }

    override fun receivePostCreateStartingRelics(player: AbstractPlayer.PlayerClass?, relics: ArrayList<String>) {
        relics.add(DeathsCalling.ID)

    }
}