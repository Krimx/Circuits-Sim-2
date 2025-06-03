package root;

public class Arbs {
	public static int pointDiam = 20;
	public static int pointVertPadding = 20;
	public static int fontHorizPadding = 16;
	
	public static int calcNodeHeight(int maxNodes) {
		return 2 * pointVertPadding + maxNodes * pointDiam + ((maxNodes - 1) * (pointVertPadding / 2));
	}
}
