package br.com.anclock.base;
import java.awt.RenderingHints;

/**
 * @see Constants
 */
public class BestRenderingHints extends RenderingHints {

	public static final BestRenderingHints instance = new BestRenderingHints();
	
	private BestRenderingHints() {
		super(null);
		put(KEY_ALPHA_INTERPOLATION,	VALUE_ALPHA_INTERPOLATION_QUALITY);
		put(KEY_ANTIALIASING,			VALUE_ANTIALIAS_ON);
		put(KEY_COLOR_RENDERING,		VALUE_COLOR_RENDER_QUALITY);
		put(KEY_DITHERING,				VALUE_DITHER_DISABLE);
		put(KEY_FRACTIONALMETRICS,		VALUE_FRACTIONALMETRICS_ON);
		put(KEY_INTERPOLATION,			VALUE_INTERPOLATION_BICUBIC);
		put(KEY_RENDERING,				VALUE_RENDER_QUALITY);
		put(KEY_STROKE_CONTROL,			VALUE_STROKE_NORMALIZE);
		put(KEY_TEXT_ANTIALIASING,		VALUE_TEXT_ANTIALIAS_ON);
	}

	public static BestRenderingHints getInstance() {
		return instance;
	}

}
