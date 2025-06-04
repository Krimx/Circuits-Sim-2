package nodes;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.UUID;

import root.Arbs;
import root.Colors;

public class Point {
	private String uuid;
	private boolean on;
	private String connectedUUID;
	private int x,y;
	
	public Point() {
		this(UUID.randomUUID().toString());
	}
	
	public Point(String uuid) {
		this.uuid = uuid;
		this.on = false;
		this.connectedUUID = "";
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
		int[] corner = {this.x - (Arbs.pointDiam / 2), this.y - (Arbs.pointDiam / 2)};
		
		g.setColor(Colors.pointIn);
		g.fillOval(corner[0], corner[1], Arbs.pointDiam, Arbs.pointDiam);
		
		g.setColor(Colors.borderColor);
		g.setStroke(new BasicStroke(3));
		g.drawOval(corner[0], corner[1], Arbs.pointDiam, Arbs.pointDiam);
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
