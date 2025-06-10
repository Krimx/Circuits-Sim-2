package nodes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.UUID;

import root.Arbs;
import root.Colors;

public class Point {
	private String uuid;
	private boolean on;
	private String connectedUUID;
	private int x,y;
	private boolean hovering;
	
	public Point() {
		this(UUID.randomUUID().toString());
	}
	
	public Point(String uuid) {
		this.uuid = uuid;
		this.on = false;
		this.connectedUUID = "";
		this.hovering = false;
	}
	
	public Point(HashMap<String, Point> pointMap) {
		this(UUID.randomUUID().toString());
		pointMap.put(this.uuid, this);
	}
	
	public Point(String uuid, HashMap<String, Point> pointMap) {
		this(uuid);
		pointMap.put(this.uuid, this);
	}
	
	public void draw(Graphics2D g) {
	    int radius = Arbs.pointDiam / 2;
	    int centerX = this.x;
	    int centerY = this.y;
	    int drawX = centerX - radius;
	    int drawY = centerY - radius;

	    // Choose base color depending on hover state
	    Color base = this.hovering ? Colors.pointHoveredIn : Colors.pointIn;

	    // Define radial gradient
	    float[] dist = {.7f, 1f};
	    Color[] colors = {
	        base, // center
	        base.darker()    // edge
	    };

	    RadialGradientPaint gradient = new RadialGradientPaint(
	        new Point2D.Float(centerX, centerY),
	        radius,
	        dist,
	        colors
	    );

	    // Fill with gradient
	    g.setPaint(gradient);
	    g.fillOval(drawX, drawY, Arbs.pointDiam, Arbs.pointDiam);

	    // Draw border
	    g.setColor(Colors.nodeBorderColor);
	    g.setStroke(new BasicStroke(Arbs.pointStroke));
	    g.drawOval(drawX, drawY, Arbs.pointDiam, Arbs.pointDiam);
	}

	public String getUuid() { return uuid; }
	public void setUuid(String uuid) { this.uuid = uuid; }
	public boolean isOn() { return on; }
	public void setOn(boolean on) { this.on = on; }
	public String getConnectedUUID() { return connectedUUID; }
	public void setConnectedUUID(String connectedUUID) { this.connectedUUID = connectedUUID; }
	public int getX() { return x; }
	public void setX(int x) { this.x = x; }
	public int getY() { return y; }
	public void setY(int y) { this.y = y; }
	public boolean isHovering() {
		return hovering;
	}

	public void setHovering(boolean hovering) {
		this.hovering = hovering;
	}

	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public static Point makePoint(HashMap<String, Point> pointMap) {
		Point toAdd = new Point();
		pointMap.put(toAdd.getUuid(), toAdd);
		return toAdd;
	}
}
