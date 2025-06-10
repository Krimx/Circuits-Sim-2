package nodes.types;

import java.util.HashMap;

import inputs.Keyboard;
import inputs.Mouse;
import main.Camera;
import nodes.Node;
import nodes.Point;
import root.Arbs;
import root.Colors;

public class And extends Node {
	public And(int x, int y, int w, int h, String name, String type) {
		super(x, y, w, h, Colors.nodeDustyLilac, name, type);
	}

	public And(int x, int y) {
		this(x, y, 100, Arbs.calcNodeHeight(2), "And", "and");
	}
	
	@Override
	public void update(Keyboard keys, Mouse mouse, Camera camera, HashMap<String, Point> inputs, HashMap<String, Point> outputs) {
		super.update(keys, mouse, camera, inputs, outputs);
	}
}
