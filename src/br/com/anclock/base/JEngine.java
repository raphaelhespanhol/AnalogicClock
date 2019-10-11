package br.com.anclock.base;
import java.awt.Graphics2D;


public interface JEngine extends Constants {

	void update();
	void paint(Graphics2D g2);
	
}
