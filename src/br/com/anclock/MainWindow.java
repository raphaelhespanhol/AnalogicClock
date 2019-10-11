package br.com.anclock;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import br.com.anclock.base.Constants;


public class MainWindow extends JFrame implements Runnable, Constants {

	private static final long serialVersionUID = 1L;

	private volatile boolean running;
	
	private MainEngine mainEngine;
	
	public MainWindow() {
		super("Analogic Clock");
		init();
		setVisible(true);
		new Thread(this).start();
	}
	
	private void init() {
		Container container = getContentPane();
		mainEngine = new MainEngine();
		mainEngine.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
		container.add(mainEngine);
		pack();
		setResizable(false);
		setLocationRelativeTo(null);
		setFocusable(true);
		// Adding Listeners
		addFocusListener(new FocusAdapter(){
			@Override
			public void focusLost(FocusEvent e) {
				MainWindow.this.requestFocus();
			}
		});
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter(){
			@Override
			public void windowClosing(WindowEvent e) {
				stop();
			}
		});
		addKeyListener(new KeyAdapter(){
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					stop();
				}
			}
		});
	}
	
	@Override
	public void run() {
		running = true;
		update();
		paintBuffer();
		while(running) {
			double initialTime = System.nanoTime();
			repaint();
			sleep(initialTime);
			update();
			paintBuffer();
		}
		setVisible(false);
		dispose();
		System.exit(0);
	}
	
	private void update() {
		mainEngine.update();
	}

	private void paintBuffer() {
		mainEngine.paintBuffer();
	}
	
	private void sleep(double initialTime) {
		long sleepTime = (long)(PERIOD - ((double)System.nanoTime() - initialTime) / 1000000.0);
		if(sleepTime > 0) {
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {}
		} else {
			Thread.yield();
		}
	}
	
	private void stop() {
		running = false;
	}

	public static void main(String[] args) {
		new MainWindow();
	}
	
}
