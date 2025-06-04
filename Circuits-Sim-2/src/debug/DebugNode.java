package debug;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;

import nodes.Node;
import nodes.Point;
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
	public static ArrayList<Node> makeTestLayout(ArrayList<Node> nodes, HashMap<String, Point> inputs, HashMap<String, Point> outputs) {
		Switch switch1 = new Switch(100,100);
		Switch switch2 = new Switch(100,200);
		And and = new And(300,150);

		switch1.addToOutputs(makePoint(outputs).getUuid());
		switch2.addToOutputs(makePoint(outputs).getUuid());
		
		and.addToOutputs(makePoint(outputs).getUuid());
		and.addToInputs(makePoint(inputs).getUuid());
		and.addToInputs(makePoint(inputs).getUuid());

		outputs.get(switch1.getOutputUUIDs().get(0)).setConnectedUUID(and.getInputUUIDs().get(0));
		outputs.get(switch2.getOutputUUIDs().get(0)).setConnectedUUID(and.getInputUUIDs().get(1));
		
		nodes.add(switch1);
		nodes.add(switch2);
		nodes.add(and);
		
		
		//Shouldnt need these after hashmap
//		nodes.get(0).getOutputByIndex(0).setConnectedUUID(nodes.get(2).getInputByIndex(0).getUuid());
//		nodes.get(1).getOutputByIndex(0).setConnectedUUID(nodes.get(2).getInputByIndex(1).getUuid());
		
		return nodes;
	}
	
	public static Point makePoint(HashMap<String, Point> pointMap) {
		Point toAdd = new Point();
		pointMap.put(toAdd.getUuid(), toAdd);
		return toAdd;
	}
}
