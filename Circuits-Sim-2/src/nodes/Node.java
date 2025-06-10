package nodes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
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
		int arc = this.getArc(Arbs.nodeArcFactor);
		g.setFont(Fonts.rubik);
		
		this.w = (Arbs.fontHorizPadding * 2) + Fonts.getTextLength(g, this.name);
		pointPositions(camera, inputs, outputs);
		
		drawShadow(g,camera, corner, arc);
		
		//Draw points
		drawPoints(g, inputs, outputs);
		
		//Draw node bg
//		renderStandardBg(g,camera,corner,arc);
		renderGradBg(g,camera,corner,arc);
		
		//Draw node border
		g.setStroke(new BasicStroke(Arbs.nodeStroke));
		g.setColor(Colors.nodeBorderColor);
		g.drawRoundRect(corner[0] - camera.getX(), corner[1] - camera.getY(), this.w, this.h, arc, arc);
		
		
		
		//Render node name
		g.setColor(Colors.textColor);
		g.drawString(this.name, corner[0] + Arbs.fontHorizPadding - camera.getX(), corner[1] + g.getFontMetrics(g.getFont()).getHeight() / 4 + this.h / 2 - camera.getY());
	}
	
	public void renderStandardBg(Graphics2D g, Camera camera, int[] corner, int arc) {
		g.setColor(this.color);
		g.fillRoundRect(corner[0] - camera.getX(), corner[1] - camera.getY(), this.w, this.h, arc, arc);
	}
	
	public void drawShadow(Graphics2D g, Camera camera, int[] corner, int arc) {
		int drawX = corner[0] - camera.getX();
		int drawY = corner[1] - camera.getY();
		
		g.setColor(Colors.shadowColor);
		g.fillRoundRect(drawX + Arbs.shadowOffset, drawY + Arbs.shadowOffset, this.w, this.h, arc, arc);
	}
	
	public void renderGradBg(Graphics2D g, Camera camera, int[] corner, int arc) {
		int drawX = corner[0] - camera.getX();
		int drawY = corner[1] - camera.getY();
		
		Color[] gradColors = Colors.getGradientColors(this.color);
		
		GradientPaint bgGradient = new GradientPaint(
			    drawX, drawY, Colors.blend(Colors.nodeBaseColor, this.color, 0.4f),     // Top color
			    drawX, drawY + (int) (this.h / 1), Colors.blend(Colors.nodeBaseColor, this.color, 0.2f)  // Bottom color
			);
			g.setPaint(bgGradient);
			g.fillRoundRect(drawX, drawY, this.w, this.h, arc, arc);
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

	public String getInputByIndex(int index) {return this.inputUUIDs.get(index);}
	public String getOutputByIndex(int index) {return this.outputUUIDs.get(index);}

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
	
	public void update(Keyboard keys, Mouse mouse, Camera camera, HashMap<String, Point> inputs, HashMap<String, Point> outputs) {
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
			int toY = corner[1] - camera.getY() + (i * Arbs.pointDiam) + (i * Arbs.pointVertPadding) + (int) (1.5 * Arbs.pointVertPadding);
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
				
//				g.drawLine(startPoint[0], startPoint[1], endPoint[0], endPoint[1]);
				Arbs.drawBezierGlow(g, startPoint[0], startPoint[1], endPoint[0], endPoint[1], Colors.bgColor, this.color, 16f, 8);
			}
		}
	}
	
	public Point isHoveringOverPoint(Mouse mouse, Camera camera, HashMap<String, Point> inputs, HashMap<String, Point> outputs) {
		Point toOut = null;
		
		for (int i = 0; i < this.inputUUIDs.size(); i++) {
			Point point = inputs.get(this.inputUUIDs.get(i));
			
			int[] coords = {mouse.getX() - camera.getX(), mouse.getY() - camera.getY(), point.getX() - camera.getX(), point.getY() - camera.getY()};
			
			double distance = Math.sqrt(Math.pow(coords[0] - coords[2], 2) + Math.pow(coords[1] - coords[3], 2));
			
			if (distance <= (double) (Arbs.pointDiam / 2)) {
				toOut = point;
			}
		}
		for (int i = 0; i < this.outputUUIDs.size(); i++) {
			Point point = outputs.get(this.outputUUIDs.get(i));
			
			int[] coords = {mouse.getX() - camera.getX(), mouse.getY() - camera.getY(), point.getX() - camera.getX(), point.getY() - camera.getY()};
			
			double distance = Math.sqrt(Math.pow(coords[0] - coords[2], 2) + Math.pow(coords[1] - coords[3], 2));
			
			if (distance <= (double) (Arbs.pointDiam / 2)) {
				toOut = point;
			}
		}
		
		return toOut;
	}
	
	public String isHoveringOverPointUUID(Mouse mouse, Camera camera, HashMap<String, Point> inputs, HashMap<String, Point> outputs) {
		String toOut = "";
		
		for (int i = 0; i < this.inputUUIDs.size(); i++) {
			Point point = inputs.get(this.inputUUIDs.get(i));
			
			int[] coords = {mouse.getX() - camera.getX(), mouse.getY() - camera.getY(), point.getX() - camera.getX(), point.getY() - camera.getY()};
			
			double distance = Math.sqrt(Math.pow(coords[0] - coords[2], 2) + Math.pow(coords[1] - coords[3], 2));
			
			if (distance <= (double) (Arbs.pointDiam / 2)) {
				toOut = point.getUuid();
			}
		}
		for (int i = 0; i < this.outputUUIDs.size(); i++) {
			Point point = outputs.get(this.outputUUIDs.get(i));
			
			int[] coords = {mouse.getX() - camera.getX(), mouse.getY() - camera.getY(), point.getX() - camera.getX(), point.getY() - camera.getY()};
			
			double distance = Math.sqrt(Math.pow(coords[0] - coords[2], 2) + Math.pow(coords[1] - coords[3], 2));
			
			if (distance <= (double) (Arbs.pointDiam / 2)) {
				toOut = point.getUuid();
			}
		}
		
		return toOut;
	}
}
