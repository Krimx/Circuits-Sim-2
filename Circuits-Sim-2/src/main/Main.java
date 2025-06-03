package main;

import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import debug.DebugNode;
import inputs.Keyboard;
import inputs.Mouse;
import nodes.Node;
import root.Colors;
import root.Fonts;

class Screen extends JPanel {
	public void paintComponent(Graphics g) {
//		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		//Background
		g2.setColor(Colors.bgColor);
		g2.fillRect(0, 0, Main.scrW,  Main.scrH);
		
		for (int i = 0; i < Main.nodes.size(); i++) {
			Main.nodes.get(i).drawConnections(g2, Main.nodes);
		}
		for (int i = 0; i < Main.nodes.size(); i++) {
			Main.nodes.get(i).render(g2, Main.camera);
		}
	}
}

public class Main {
	
	//Technical fields
	public static boolean running = true, initialized = true;
	public static int tps = 60;
	
	//Needed classes for rendering
	public static JFrame frame = new JFrame();
	public static Container cont = frame.getContentPane();
	public static Screen scr = new Screen();
	public static Camera camera = new Camera(0,0);
	
	//Needed classes for inputs
	public static Keyboard keys = new Keyboard();
	public static Mouse mouse = new Mouse();
	
	//Rendering-related fields
	public static int scrW = 800, scrH = 800, fps = 60;;
	public static float aspectRatio = (float) scrW / (float) scrH;
	
	public static ArrayList<Node> nodes = new ArrayList<>();
	
	public static Node heldNode = null;
	public static int[] heldNodeOffset = {0,0};

	public static void main(String[] args) throws Exception {
		init();
		startMainThread();
		startRenderThread();
	}

	public static void init() {
		frame.setSize(scrW, scrH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		cont.add(scr);
		
		frame.addKeyListener(keys);
		frame.addMouseListener(mouse);
		frame.addMouseMotionListener(mouse);
		frame.addMouseWheelListener(mouse);
		mouse.setYOffset(-30);
		frame.setVisible(true);
		
		nodes = DebugNode.makeTestLayout(nodes);
	}
	
	public static void startMainThread() throws Exception {
		new Thread(() -> {
			long lastTime = System.nanoTime(); //Init time var
		    final double ns = 1000000000.0 / tps; //Control ticks per second
		    double delta = 0;
		    while(running) { //Game loop boolean
		        long now = System.nanoTime(); //Far for now time
		        delta += (now - lastTime) / ns; //While in running loop, wait until two time vars are equal
		        lastTime = now; //Reset
		        while(delta >= 1) { //Wait until time is less than one
		        	updateSystem();
		        	mainLoop();
		            delta--; //Remove
	            }
	        } 

		    System.exit(0); //Once done with game loop, shut everything down
		}, "MainThread").start();
	}
	public static void startRenderThread() {
	    new Thread(() -> {
	        long frameDelay = 1000000000 / fps;
	        long lastTime = System.nanoTime();

	        while (running) {
	            long now = System.nanoTime();
	            if (now - lastTime >= frameDelay) {
	                scr.repaint();
	                lastTime = now;
	            }

	            // Optional: yield to save CPU
	            try {
	                Thread.sleep(1);
	            } catch (InterruptedException e) {}
	        }
	    }, "RenderThread").start();
	}
	
	public static void updateSystem() {
	}
	
	public static void mainLoop() {
		if (keys.ESCAPETYPED()) {
			running = false;
		}
		
		for (Node node : nodes) {
			node.update(keys, mouse, camera);
		}
		
		grabbingNodes();
		
	}
	
	public static void grabbingNodes() {
		if (heldNode == null) {
			if (mouse.LEFTCLICKED()) {
				for (Node node : nodes) {
					if (node.getHovering(mouse, camera)) {
						heldNode = node;
						heldNodeOffset[0] = mouse.getX() - node.getX();
						heldNodeOffset[1] = mouse.getY() - node.getY();
					}
				}
			}
		}
		else {
			heldNode.setX(mouse.getX() - heldNodeOffset[0]);
			heldNode.setY(mouse.getY() - heldNodeOffset[1]);
			if (mouse.LEFTRELEASED()) heldNode = null;
		}
	}
}
