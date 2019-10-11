package br.com.anclock;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.Transparency;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.Calendar;
import java.util.GregorianCalendar;

import br.com.anclock.base.JEngine;


public class ClockEngine implements JEngine {

	private double radius = 0.5 * (double)WINDOW_HEIGHT;
	private double radiusDecrement = 0.05 * radius;
	
	private BufferedImage background;
	private BufferedImage secondsPointer;
	private BufferedImage minutesPointer;
	private BufferedImage hoursPointer;
	
	private double hourAngle;
	private double minuteAngle;
	private double secondAngle;
	private int centralCircleDiameter;
	
	public ClockEngine() {
		createBackground();
		secondsPointer = createPointer(radius - 5.5 * radiusDecrement);
		minutesPointer = createPointer(radius - 7.75 * radiusDecrement);
		hoursPointer = createPointer(radius - 10 * radiusDecrement);
		centralCircleDiameter = (int)(2.0 * radiusDecrement);
	}
	
	private void createBackground() {
		background = G_CONF.createCompatibleImage(WINDOW_WIDTH, WINDOW_HEIGHT, Transparency.TRANSLUCENT);
		Graphics2D g2 = background.createGraphics();
		g2.setRenderingHints(R_HINTS);
		g2.setColor(BASE_COLOR);
		g2.setFont(FONT);
		
		double radius = this.radius - radiusDecrement;
		double diameter = 2 * radius;
		
		Stroke stroke = g2.getStroke();
		g2.setStroke(THICK_STROKE);
		int posX = (int)(((double)WINDOW_WIDTH - diameter) / 2.0);
		int posY = (int)(((double)WINDOW_HEIGHT - diameter) / 2.0);
		g2.drawOval(posX, posY, (int)diameter, (int)diameter);
		g2.setStroke(stroke);
		
		g2.setColor(HIGHLIGHT_COLOR);
		String[] numbers = new String[]{"03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "01", "02"};
		radius = this.radius - 3.5 * radiusDecrement;
		for(double angle = 0, i = 0; angle < 360; angle += 360.0 / (double)numbers.length, i++) {
			posX = (int)(POS_X_CENTER + radius * Math.cos(Math.toRadians(angle)));
			posY = (int)(POS_Y_CENTER + radius * Math.sin(Math.toRadians(angle)));
			
			String number = numbers[(int)i];
			posX -= g2.getFontMetrics().stringWidth(number) / 2;
			posY += g2.getFontMetrics().getAscent() / 2;
			
			g2.drawString(number, posX, posY);
		}
		
		g2.setColor(DARKER_COLOR);
		g2.setStroke(THIN_STROKE);
		int outerDiameter = (int)(2 * (this.radius - 7 * radiusDecrement));
		int circles = 7;
		int diameterDecrement = (int)(3 * this.radiusDecrement);
		
		double outerRadius = outerDiameter / 2;
		double innerRadius = (outerDiameter - ((circles - 1) * diameterDecrement)) / 2;
		for(double angle = 0; angle < 360; angle += 360.0 / 60.0) {
			int posX1 = (int)(POS_X_CENTER + outerRadius * Math.cos(Math.toRadians(angle)));
			int posY1 = (int)(POS_Y_CENTER + outerRadius * Math.sin(Math.toRadians(angle)));
			int posX2 = (int)(POS_X_CENTER + innerRadius * Math.cos(Math.toRadians(angle)));
			int posY2 = (int)(POS_Y_CENTER + innerRadius * Math.sin(Math.toRadians(angle)));
			g2.drawLine(posX1, posY1, posX2, posY2);
		}
		
		int i = 0;
		for(diameter = outerDiameter; i < circles; diameter -= diameterDecrement, i++) {
			posX = (int)((WINDOW_WIDTH - diameter) / 2);
			posY = (int)((WINDOW_HEIGHT - diameter) / 2);
			g2.drawOval(posX, posY, (int)diameter, (int)diameter);
		}
		g2.setStroke(stroke);
		
		g2.setStroke(THICK_STROKE);
		innerRadius = outerRadius;
		outerRadius = outerRadius + radiusDecrement;
		for(double angle = 0; angle < 360; angle += 360.0 / 60.0) {
			g2.setColor(angle % 15 == 0 ? HIGHLIGHT_COLOR : BASE_COLOR);
			int posX1 = (int)(POS_X_CENTER + outerRadius * Math.cos(Math.toRadians(angle)));
			int posY1 = (int)(POS_Y_CENTER + outerRadius * Math.sin(Math.toRadians(angle)));
			int posX2 = (int)(POS_X_CENTER + innerRadius * Math.cos(Math.toRadians(angle)));
			int posY2 = (int)(POS_Y_CENTER + innerRadius * Math.sin(Math.toRadians(angle)));
			g2.drawLine(posX1, posY1, posX2, posY2);
		}
		g2.setStroke(stroke);
		
		g2.dispose();
	}

	private BufferedImage createPointer(double radius) {
		double triangleWidth = 1.5 * radiusDecrement;
		double triangleHeight = 2 * radiusDecrement;
		
		BufferedImage pointer = G_CONF.createCompatibleImage((int)triangleWidth, (int)radius, Transparency.TRANSLUCENT);
		Graphics2D g2 = pointer.createGraphics();
		g2.setRenderingHints(R_HINTS);
		g2.setColor(BASE_COLOR);
		
		Stroke stroke = g2.getStroke();
		g2.setStroke(THICK_STROKE);
		g2.drawLine((int)(triangleWidth / 2.0), (int)(triangleHeight / 2.0), (int)(triangleWidth / 2.0), (int)(radius));
		g2.setStroke(stroke);
		
		g2.fillPolygon(new int[]{0, (int)triangleWidth, (int)(triangleWidth / 2.0)}, new int[]{(int)triangleHeight, (int)triangleHeight, 0}, 3);
		
		g2.dispose();
		return pointer;
	}
	
	@Override
	public void update() {
		Calendar calendar = new GregorianCalendar();
		
		double hour			= calendar.get(Calendar.HOUR);
		double minute		= calendar.get(Calendar.MINUTE);
		double second		= calendar.get(Calendar.SECOND);
		double millisecond	= calendar.get(Calendar.MILLISECOND);
		
		double dayHours			= 12;
		double dayMinutes		= dayHours * 60;
		double daySeconds		= dayMinutes * 60;
		double dayMilliseconds	= daySeconds * 1000;
		
		double dayHoursPerc =
				(hour / dayHours) +
				(minute / dayMinutes) +
				(second / daySeconds) +
				(millisecond / dayMilliseconds);
		
		double hourMinutes		= 60;
		double hourSeconds		= hourMinutes * 60;
		double hourMilliseconds	= hourSeconds * 1000;
		
		double hourMinutesPerc =
				(minute / hourMinutes) +
				(second / hourSeconds) +
				(millisecond / hourMilliseconds);
		
		double minuteSeconds		= 60;
		double minuteMilliseconds	= minuteSeconds * 1000;
		
		double minuteSecondsPerc =
				(second / minuteSeconds) +
				(millisecond / minuteMilliseconds);
		
		hourAngle	= Math.toRadians(dayHoursPerc * 360.0);
		minuteAngle	= Math.toRadians(hourMinutesPerc * 360.0);
		secondAngle	= Math.toRadians(minuteSecondsPerc * 360.0);
	}

	@Override
	public void paint(Graphics2D g2) {
		g2.setColor(BASE_COLOR);
		AffineTransform transform = g2.getTransform();
		
		g2.drawImage(background, 0, 0, null);
		
		AffineTransform rotation = new AffineTransform();
		rotation.rotate(secondAngle, POS_X_CENTER, POS_Y_CENTER);
		g2.setTransform(rotation);
		g2.drawImage(secondsPointer, POS_X_CENTER - secondsPointer.getWidth() / 2, POS_Y_CENTER - secondsPointer.getHeight(), null);
		g2.setTransform(transform);
		
		rotation = new AffineTransform();
		rotation.rotate(minuteAngle, POS_X_CENTER, POS_Y_CENTER);
		g2.setTransform(rotation);
		g2.drawImage(minutesPointer, POS_X_CENTER - minutesPointer.getWidth() / 2, POS_Y_CENTER - minutesPointer.getHeight(), null);
		g2.setTransform(transform);
		
		rotation = new AffineTransform();
		rotation.rotate(hourAngle, POS_X_CENTER, POS_Y_CENTER);
		g2.setTransform(rotation);
		g2.drawImage(hoursPointer, POS_X_CENTER - hoursPointer.getWidth() / 2, POS_Y_CENTER - hoursPointer.getHeight(), null);
		g2.setTransform(transform);
		
		int posX = (WINDOW_WIDTH - centralCircleDiameter) / 2;
		int posY = (WINDOW_HEIGHT - centralCircleDiameter) / 2;
		g2.fillOval(posX, posY, centralCircleDiameter, centralCircleDiameter);
	}

}
