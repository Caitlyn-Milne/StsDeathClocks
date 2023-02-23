package deathClock

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.Texture.TextureFilter
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion
import com.badlogic.gdx.utils.GdxRuntimeException
import com.badlogic.gdx.utils.Logger
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch
import org.apache.logging.log4j.LogManager
import java.lang.Exception


object TextureLoader {
    private val logger = LogManager.getLogger(TextureLoader::class.java)
    private val textures = HashMap<String, Texture>()

    fun getTexture(textureString: String): Texture {
        if (textures[textureString] == null) {
            try {
                loadTexture(textureString)
            } catch (e: GdxRuntimeException) {
                logger.error("Could not find texture: $textureString")
                throw Exception("Could not find texture")
            }
        }
        return textures[textureString]!!
    }

    @Throws(GdxRuntimeException::class)
    private fun loadTexture(textureString: String) {
        logger.info("CutelynMod | Loading Texture: $textureString")
        val texture = Texture(textureString)
        texture.setFilter(TextureFilter.Linear, TextureFilter.Linear)
        textures[textureString] = texture
    }
}