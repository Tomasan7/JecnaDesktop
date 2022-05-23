package me.tomasan7.jecnadesktop.ui.component;

import io.github.palexdev.materialfx.utils.SwingFXUtils;
import javafx.beans.NamedArg;
import javafx.css.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * An extension of {@link ImageView} class, designed to view icons.
 * This class has new style property {@code -jd-icon-color}, which changes the color of the viewed image at runtime.
 * The images are supposed to be single-color images, with possibly transparent pixels.
 * Is able to cache {@link Image Images} for each color that is used, to save time recoloring the images.
 * This behaviour can be changed with {@code doCache} constructor parameter.
 */
public class IconView extends ImageView
{
	private static final StyleablePropertyFactory<IconView> FACTORY = new StyleablePropertyFactory<>(ImageView.getClassCssMetaData());

	private final StyleableObjectProperty<Color> colorProperty = (StyleableObjectProperty<Color>)
			FACTORY.createStyleableColorProperty(this,
												 "color",
												 "-jd-icon-color",
												 IconView::colorPropertyProperty,
												 Color.BLACK,
												 true);

	private final Map<Color, Image> imageCache = new HashMap<>();

	/**
	 * Whether to cache {@link Image} for each {@link Color} that is ever used.
	 * Caching faster but uses more memory, choose depending on the amount of different used colors and icon's size.
	 */
	private final boolean doCache;

	/**
	 * @param image The icon's image.
	 * @param doCache Whether to cache {@link Image} for each {@link Color} that is ever used.
	 *                   Caching faster but uses more memory, choose depending on the amount of different used colors and icon's size.
	 */
	public IconView (Image image, boolean doCache)
	{
		super(image);
		this.doCache = doCache;

		getStyleClass().add("icon-view");

		colorProperty.addListener((__, oldValue, newValue) -> colorize(newValue));

		/* Colorizing with the initial color. */
		colorize(colorPropertyProperty().getValue());
	}

	/**
	 * @param url the string representing the URL from which to load the image.
	 * @param doCache Whether to cache {@link Image} for each {@link Color} that is ever used.
	 *                   Caching faster but uses more memory, choose depending on the amount of different used colors and icon's size.
	 */
	public IconView (@NamedArg("url") String url, @NamedArg(value = "doCache", defaultValue = "true") boolean doCache)
	{
		this(new Image(url), doCache);
	}

	/**
	 * Same as {@link #IconView(Image, boolean)}, but with {@code doCache = false} as default.
	 * Shorthand for {@code new IconView(image, false)}.
	 */
	public IconView (Image image)
	{
		this(image, true);
	}


	/**
	 * Same as {@link #IconView(String, boolean)}, but with {@code doCache = false} as default.
	 * Shorthand for {@code new IconView(url, false)}.
	 */
	public IconView (@NamedArg("url") String url)
	{
		this(url, true);
	}

	private void colorize (Color newColor)
	{
		/*
		 *	TODO: Make the recoloring deal with the Image instances directly
		 *  instead of converting to BufferedImage and then back to Image; unnecessary computing.
		 */

		if (doCache && imageCache.containsKey(newColor))
		{
			imageProperty().set(imageCache.get(newColor));
			return;
		}

		BufferedImage oldBufferedImage = SwingFXUtils.fromFXImage(imageProperty().get(), null);
		recolorBufferedImage(oldBufferedImage, getColor());
		WritableImage fxImage = SwingFXUtils.toFXImage(oldBufferedImage, null);
		imageProperty().set(fxImage);

		imageCache.put(newColor, fxImage);
	}

	public StyleableProperty<Color> colorPropertyProperty ()
	{
		return colorProperty;
	}

	public boolean getDoCache ()
	{
		return doCache;
	}

	public Color getColor ()
	{
		return colorProperty.get();
	}

	public void setColor (Color color)
	{
		colorProperty.set(color);
	}

	@Override
	public List<CssMetaData<? extends Styleable, ?>> getCssMetaData ()
	{
		return FACTORY.getCssMetaData();
	}

	/**
	 * @return The CssMetaData associated with this class, which may include the
	 * CssMetaData of its superclasses.
	 */
	public static List<CssMetaData<? extends Styleable, ?>> getClassCssMetaData ()
	{
		return FACTORY.getCssMetaData();
	}

	/**
	 * Colors every non-transparent pixel of the image to the passed {@link Color}, keeps the alpha values the same.
	 * @param image The {@link Image} to recolor.
	 * @param color The {@link Color} to recolor with.
	 */
	private static void recolorBufferedImage (BufferedImage image, Color color)
	{
		int[] data = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

		for (int i = 0; i < data.length; i++)
		{
			int pixel = data[i];
			if (pixel == 0)
				continue;

			byte red = (byte) (color.getRed() * 255);
			byte green = (byte) (color.getGreen() * 255);
			byte blue = (byte) (color.getBlue() * 255);

			byte alpha = (byte) ((pixel >> 24) & 0xFF);

			data[i] = (alpha << 24) | ((red & 255) << 16) | ((green & 255) << 8) | (blue & 255);
		}
	}
}
