package nodes.types;

import java.util.HashMap;

import inputs.Keyboard;
import inputs.Mouse;
import main.Camera;
import nodes.Node;
import nodes.Point;
import root.Arbs;
import root.Colors;

public class Switch extends Node {
	private boolean on;

	public Switch(int x, int y, int w, int h, String name, String type) {
		super(x, y, w, h, Colors.switchOff, name, type);
		this.on = false;
	}

	public Switch(int x, int y) {
		this(x, y, 100, Arbs.calcNodeHeight(1), "Switch", "switch");
	}
	
	@Override
	public void update(Keyboard keys, Mouse mouse, Camera camera, HashMap<String, Point> inputs, HashMap<String, Point> outputs) {
		super.update(keys, mouse, camera, inputs, outputs);
		if (super.getHovering(mouse, camera)) {
			if (keys.SPACETYPED()) {
				this.on = !this.on;
			}
		}
		
		if (this.on) super.setColor(Colors.switchOn);
		else super.setColor(Colors.switchOff);
	}
}
