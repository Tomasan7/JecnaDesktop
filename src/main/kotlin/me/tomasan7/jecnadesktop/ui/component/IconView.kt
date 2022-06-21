package me.tomasan7.jecnadesktop.ui.component

import io.github.palexdev.materialfx.utils.SwingFXUtils
import javafx.beans.NamedArg
import javafx.css.*
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.paint.Color
import java.awt.image.BufferedImage
import java.awt.image.DataBufferInt

/**
 * An extension of [ImageView] class, designed to view icons.
 * This class has new style property `-jd-icon-color`, which changes the color of the viewed image at runtime.
 * The images are supposed to be single-color images, with possibly transparent pixels.
 * Is able to cache [Images][Image] for each color that is used, to save time recoloring the images.
 * This behaviour can be changed with `doCache` constructor parameter.
 *
 * @param image The icon's image.
 * @param doCache Whether to cache [Image] for each [Color] that is ever used. Caching faster but uses more memory, choose depending on the amount of different used colors and icon's size.
 */
class IconView(image: Image, private val doCache: Boolean = true) : ImageView(image)
{
    val colorProperty: StyleableObjectProperty<Color> = FACTORY.createStyleableColorProperty(
        /* styleable = */ this,
        /* propertyName = */ "color",
        /* cssProperty = */ "-jd-icon-color",
        /* function = */ { it.colorProperty },
        /* initialValue = */ Color.BLACK,
        /* inherits = */ true) as StyleableObjectProperty<Color>

    /**
     * Shorthand for `colorProperty.set(color) and colorProperty.get()`.
     */
    var color: Color
        get() = colorProperty.get()
        set(color) = colorProperty.set(color)

    private val imageCache: MutableMap<Color, Image> = HashMap()

    init
    {
        styleClass.add("icon-view")
        colorProperty.addListener { _, _, newValue: Color -> colorize(newValue) }

        /* Colorizing with the initial color. */
        colorize(color)
    }

    /**
     * Same as [.IconView], but with `doCache = false` as default.
     * Shorthand for `new IconView(url, false)`.
     *
     * @param url the string representing the URL from which to load the image.
     * @param doCache Whether to cache [Image] for each [Color] that is ever used.
     * Caching faster but uses more memory, choose depending on the amount of different used colors and icon's size.
     */
    constructor(@NamedArg("url") url: String?,
                @NamedArg(value = "doCache", defaultValue = "true") doCache: Boolean = true) : this(Image(url), doCache)

    private fun colorize(newColor: Color)
    {
        /*
		 *	TODO: Make the recoloring deal with the Image instances directly
		 *  instead of converting to BufferedImage and then back to Image; unnecessary computing.
		 */
        if (doCache && imageCache.containsKey(newColor))
        {
            imageProperty().set(imageCache[newColor])
            return
        }

        val oldBufferedImage = SwingFXUtils.fromFXImage(imageProperty().get(), null)
        recolorBufferedImage(oldBufferedImage, color)
        val fxImage = SwingFXUtils.toFXImage(oldBufferedImage, null)
        imageProperty().set(fxImage)

        imageCache[newColor] = fxImage
    }

    override fun getCssMetaData(): List<CssMetaData<out Styleable?, *>>
    {
        return FACTORY.cssMetaData
    }

    companion object
    {
        private val FACTORY = StyleablePropertyFactory<IconView>(getClassCssMetaData())

        /**
         * @return The CssMetaData associated with this class, which may include the
         * CssMetaData of its superclasses.
         */
        /* By JavaFX convention, this method should be provided, but unfortunately due to kotlin-java interpretability, it is not possible. */
        /*@JvmStatic
        fun getClassCssMetaData(): List<CssMetaData<out Styleable?, *>> = FACTORY.cssMetaData*/

        /**
         * Colors every non-transparent pixel of the image to the passed [Color], keeps the alpha values the same.
         * @param image The [Image] to recolor.
         * @param color The [Color] to recolor with.
         */
        private fun recolorBufferedImage(image: BufferedImage, color: Color)
        {
            val data = (image.raster.dataBuffer as DataBufferInt).data

            for (i in data.indices)
            {
                val pixel = data[i]
                if (pixel == 0) continue
                val red = (color.red * 255).toInt().toByte()
                val green = (color.green * 255).toInt().toByte()
                val blue = (color.blue * 255).toInt().toByte()
                val alpha = (pixel shr 24 and 0xFF).toByte()
                data[i] = alpha.toInt() shl 24 or (red.toInt() and 255 shl 16) or (green.toInt() and 255 shl 8) or (blue.toInt() and 255)
            }
        }
    }
}