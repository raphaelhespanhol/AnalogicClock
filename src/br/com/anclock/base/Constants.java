package br.com.anclock.base;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.DisplayMode;
import java.awt.Font;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Stroke;
import java.awt.Transparency;
import java.awt.image.BufferedImage;


public interface Constants {

	double					FPS				= 30;
	double					PERIOD			= 1000.0 / FPS;
	BestRenderingHints		R_HINTS			= BestRenderingHints.getInstance();
	GraphicsEnvironment		G_ENV			= GraphicsEnvironment.getLocalGraphicsEnvironment();
	GraphicsDevice			G_DEV			= G_ENV.getDefaultScreenDevice();
	DisplayMode				D_MODE			= G_DEV.getDisplayMode();
	GraphicsConfiguration	G_CONF			= G_DEV.getDefaultConfiguration();
	int						WINDOW_HEIGHT	= (int)(0.4 * D_MODE.getHeight());
	int						WINDOW_WIDTH	= WINDOW_HEIGHT;
	BufferedImage			BUFFER_IMG		= G_CONF.createCompatibleImage(WINDOW_WIDTH, WINDOW_HEIGHT, Transparency.OPAQUE);
	Color					BASE_COLOR		= new Color(0xFFF800);
	Color					DARKER_COLOR	= new Color(0x606300);
	Color					HIGHLIGHT_COLOR	= new Color(0x00C0FF);
	Stroke					THICK_STROKE	= new BasicStroke((float)(0.0075 * WINDOW_HEIGHT), BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER, 10.0f, null, 0.0f);
	Stroke					THIN_STROKE		= new BasicStroke((float)(0.004 * WINDOW_HEIGHT), BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER, 10.0f, null, 0.0f);
	Font					FONT			= new Font("Arial", Font.BOLD, (int)(0.055 * WINDOW_HEIGHT));
	int						POS_X_CENTER	= WINDOW_WIDTH / 2;
	int						POS_Y_CENTER	= WINDOW_HEIGHT / 2;
	
}
