package nodes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import inputs.Keyboard;
import inputs.Mouse;
import main.Camera;
import root.Arbs;
import root.Colors;
import root.Fonts;

public class Node {
	private int x,y,w,h;
	private Color color;
	private String name, type, uuid;
	private ArrayList<Point> inputs = new ArrayList<>();
	private ArrayList<Point> outputs = new ArrayList<>();
	private ArrayList<String> inputUUIDs = new ArrayList<>();
	private ArrayList<String> outputUUIDs = new ArrayList<>();
	private Font rubik;
	
	public Node(int x, int y, int w, int h, Color color, String name, String type) {
		this(x,y,w,h,color,name,type,UUID.randomUUID().toString());
	}
	public Node(int x, int y, int w, int h, Color color, String name, String type, String uuid) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.color = color;
		this.name = name;
		this.type = type;
		this.uuid = uuid;
	}

	public int getX() { return x; }
	public void setX(int x) { this.x = x; }
	public int getY() { return y; }
	public void setY(int y) { this.y = y; }
	public int getW() { return w; }
	public void setW(int w) { this.w = w; }
	public int getH() { return h; }
	public void setH(int h) { this.h = h; }
	public Color getColor() { return color; }
	public void setColor(Color color) { this.color = color; }
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	public String getType() { return type; }
	public void setType(String type) { this.type = type; }
	public String getUuid() { return uuid; }
	public void setUuid(String uuid) { this.uuid = uuid; }
	public void addToInputs(String toAdd) {inputUUIDs.add(toAdd);}
	public void addToOutputs(String toAdd) {outputUUIDs.add(toAdd);}
	public ArrayList<String> getOutputUUIDs() {return this.outputUUIDs;}
	public ArrayList<String> getInputUUIDs() {return this.inputUUIDs;}
	
	public void render(Graphics2D g, Camera camera, HashMap<String, Point> inputs, HashMap<String, Point> outputs) {
		
		int[] corner = this.getTopLeftCorner();
		int arc = this.getArc(0.3f);
		g.setFont(Fonts.rubik);
		
		this.w = (Arbs.fontHorizPadding * 2) + Fonts.getTextLength(g, this.name);
		pointPositions(camera, inputs, outputs);
		
		g.setColor(this.color);
		g.fillRoundRect(corner[0] - camera.getX(), corner[1] - camera.getY(), this.w, this.h, arc, arc);
		
		g.setStroke(new BasicStroke(3));
		g.setColor(Colors.borderColor);
		g.drawRoundRect(corner[0] - camera.getX(), corner[1] - camera.getY(), this.w, this.h, arc, arc);
		
		drawPoints(g, inputs, outputs);
		
		g.setColor(Colors.borderColor);
		g.drawString(this.name, corner[0] + Arbs.fontHorizPadding - camera.getX(), corner[1] + g.getFontMetrics(g.getFont()).getHeight() / 4 + this.h / 2 - camera.getY());
	}
	
	public void drawPoints(Graphics2D g, HashMap<String, Point> inputs, HashMap<String, Point> outputs) {
		//Outputs
		for (int i = 0; i < this.outputUUIDs.size(); i++) {
			outputs.get(this.outputUUIDs.get(i)).draw(g);
		}
		for (int i = 0; i < this.inputUUIDs.size(); i++) {
			inputs.get(this.inputUUIDs.get(i)).draw(g);
		}
	}
	
	public int[] getTopLeftCorner() {
		int[] corner = new int[2];
		
		corner[0] = this.x - (this.w / 2);
		corner[1] = this.y - (this.h / 2);
		
		return corner;
	}
	
	public int getArc(float factor) {
		return (int) (Math.min(this.w,  this.h) * factor);
	}

	public Point getInputByIndex(int index) {return this.inputs.get(index);}
	public Point getOutputByIndex(int index) {return this.outputs.get(index);}

	public Point getInputByUUID(String uuid) {
		Point toOut = null;
		
		for (Point point :  inputs) {
			if (point.getUuid().equals(uuid)) toOut = point;
		}
		
		if (toOut == null) {
//			System.out.println("Point <" + uuid + "> was not found.");
		}
		return toOut;
	}
	public Point getOutputByUUID(String uuid) {
		Point toOut = null;
		
		for (Point point :  outputs) {
			if (point.getUuid().equals(uuid)) toOut = point;
		}
		
		if (toOut == null) {
//			System.out.println("Point <" + uuid + "> was not found.");
		}
		return toOut;
	}
	
	public boolean getHovering(Mouse mouse, Camera camera) {
		boolean hovering = false;
		
		int[] corner = this.getTopLeftCorner();
		
		boolean inLeft = mouse.getX() >= corner[0] - camera.getX();
		boolean inRight = mouse.getX() <= corner[0] + this.w - camera.getX();
		boolean inTop = mouse.getY() >= corner[1] - camera.getY();
		boolean inBottom = mouse.getY() <= corner[1] + this.h - camera.getY();
		
		if (inLeft && inRight && inTop && inBottom) hovering = true;
		
		return hovering;
	}
	
	public void update(Keyboard keys, Mouse mouse, Camera camera) {
	}
	
	public void pointPositions(Camera camera, HashMap<String, Point> inputs, HashMap<String, Point> outputs) {
		int[] corner = this.getTopLeftCorner();
		
		for (int i = 0; i < this.outputUUIDs.size(); i++) {
			int toX = corner[0] + this.w - camera.getX();
			int toY = corner[1] + 2 * Arbs.pointVertPadding + (i * Arbs.pointVertPadding) + (i * Arbs.pointDiam - (Arbs.pointDiam / 2)) - camera.getY();
			outputs.get(this.outputUUIDs.get(i)).setPosition(toX, toY);
			
		}
		
		for (int i = 0; i < this.inputUUIDs.size(); i++) {
			int toX = corner[0] - camera.getX();
			int toY = corner[1] + 2 *  Arbs.pointVertPadding + (i * Arbs.pointVertPadding) + (i * Arbs.pointDiam - (Arbs.pointDiam / 2)) - camera.getY();
			inputs.get(this.inputUUIDs.get(i)).setPosition(toX, toY);
			
		}
	}
	
	public void drawConnections(Graphics2D g, ArrayList<Node> nodes, HashMap<String, Point> inputs, HashMap<String, Point> outputs) {
		g.setColor(this.color);
		g.setStroke(new BasicStroke(2));
		
		for (int i = 0; i < this.outputUUIDs.size(); i++) {
			Point output = outputs.get(this.outputUUIDs.get(i));
			Point input = inputs.get(output.getConnectedUUID());
			if (input != null) {
				int[] startPoint = {output.getX(), output.getY()};
				int[] endPoint = {input.getX(), input.getY()};
				
				g.drawLine(startPoint[0], startPoint[1], endPoint[0], endPoint[1]);
			}
		}
	}
}
