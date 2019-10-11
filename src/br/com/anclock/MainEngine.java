package br.com.anclock;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import br.com.anclock.base.JEngine;


public class MainEngine extends JPanel implements JEngine {

	private static final long serialVersionUID = 1L;

	private List<JEngine> engines = new ArrayList<JEngine>();
	
	public MainEngine() {
		engines.add(new ClockEngine());
	}
	
	@Override
	public void update() {
		for(JEngine engine : engines) {
			engine.update();
		}
	}

	public void paintBuffer() {
		Graphics2D g2 = BUFFER_IMG.createGraphics();
		g2.setRenderingHints(R_HINTS);
		paint(g2);
		g2.dispose();
	}
	
	@Override
	public void paint(Graphics2D g2) {
		g2.setColor(Color.BLACK); // clearing screen
		g2.fillRect(-1, -1, WINDOW_WIDTH + 2, WINDOW_HEIGHT + 2);
		for(JEngine engine : engines) {
			engine.paint(g2);
		}
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.drawImage(BUFFER_IMG, 0, 0, null);
		g.dispose();
	}
	
}
