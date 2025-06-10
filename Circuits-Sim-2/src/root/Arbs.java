package root;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.Paint;
import java.awt.Stroke;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

public class Arbs {
	public static int pointDiam = 16;
	public static int pointVertPadding = 15;
	public static int fontHorizPadding = 16;
	public static int shadowOffset = 5;
	
	public static float nodeArcFactor = .3f;
	public static int nodeStroke = 4;
	public static int pointStroke = 2;
	public static int connectionThickness = 2;
	
	public static int calcNodeHeight(int maxNodes) {
		return 2 * pointVertPadding + maxNodes * pointDiam + ((maxNodes - 1) * (pointVertPadding / 2));
	}
	
	public static void drawBezier(Graphics2D g, int x1, int y1, int x2, int y2, Color color) {
        float offset = Math.abs(x2 - x1) * 0.5f;

        CubicCurve2D.Float curve = new CubicCurve2D.Float();
        curve.setCurve(
            x1, y1,
            x1 + offset, y1,
            x2 - offset, y2,
            x2, y2
        );

        Stroke originalStroke = g.getStroke();
        g.setColor(color);
        g.setStroke(new BasicStroke(connectionThickness, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g.draw(curve);
        g.setStroke(originalStroke); // restore stroke
    }
	public static void drawBezierGlow(Graphics2D g, int x1, int y1, int x2, int y2, Color innerColor, Color outerColor, float maxThickness, int steps) {
	    float offset = Math.abs(x2 - x1) * 0.5f;

	    CubicCurve2D.Float curve = new CubicCurve2D.Float();
	    curve.setCurve(
	        x1, y1,
	        x1 + offset, y1,
	        x2 - offset, y2,
	        x2, y2
	    );

	    Stroke originalStroke = g.getStroke();
	    Paint originalPaint = g.getPaint();

	    // Generate gradient colors from center (solid) to edge (faded)
	    Color[] gradientColors = Colors.interpolateColors(innerColor, Colors.blend(innerColor, outerColor, .3f), steps);

	    for (int i = 0; i < steps; i++) {
	        float thickness = maxThickness * (1 - (float)i / steps); // decreasing thickness

	        g.setColor(gradientColors[i]);
	        g.setStroke(new BasicStroke(thickness, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
	        g.draw(curve);
	    }
	    g.setColor(outerColor);
	    g.setStroke(new BasicStroke(connectionThickness));
	    g.draw(curve);

	    g.setStroke(originalStroke);
	    g.setPaint(originalPaint);
	}
}
