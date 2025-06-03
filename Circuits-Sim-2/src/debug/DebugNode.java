package debug;

import java.awt.Color;
import java.util.ArrayList;

import nodes.Node;
import nodes.types.And;
import nodes.types.Switch;

public class DebugNode {
	public static ArrayList<Node> makeDebugNode(ArrayList<Node> nodes) {
		Node toAdd = new Node(400,400,120,160, new Color(100,200,10), "Test Node", "debug");
		nodes.add(toAdd);
		return nodes;
	}
	public static ArrayList<Node> makeTestSwitch(ArrayList<Node> nodes) {
		Switch toAdd = new Switch(100,100);
		nodes.add(toAdd);
		return nodes;
	}
	public static ArrayList<Node> makeTestLayout(ArrayList<Node> nodes) {
		nodes.add(new Switch(100,100));
		nodes.add(new Switch(100,200));
		nodes.add(new And(300,150));

		nodes.get(0).getOutputByIndex(0).setConnectedUUID(nodes.get(2).getInputByIndex(0).getUuid());
		nodes.get(1).getOutputByIndex(0).setConnectedUUID(nodes.get(2).getInputByIndex(1).getUuid());
		return nodes;
	}
	
}
