package root;

import java.awt.Color;

public class Colors {
	public static Color bgColor = new Color(0x121212);
	public static Color shadowColor = new Color(0,0,0,80);
	public static Color connectionColor = new Color(200,0,50,80);

	public static Color pointIn = new Color(180,180,180);
	public static Color pointHoveredIn = new Color(230,230,230);
	public static Color textColor = new Color(255,255,255,100);

	public static Color switchOn = new Color(100, 180, 255);
	public static Color switchOff = new Color(60, 90, 110);
	public static Color and = new Color(173,100,255);
	
	public static Color nodeBaseColor = new Color(0x000000);
	public static Color nodeBorderColor = new Color(100,100,100, 30);
	
	public static Color nodeSlateBlue     = new Color(0x4B5D67); // cool blue-grey
    public static Color nodeDustyLilac    = new Color(0x7E6C88); // faded violet
    public static Color nodeAshRose       = new Color(0xB49791); // warm rose-brown
    public static Color nodeOliveSlate    = new Color(0x78866B); // desaturated green-olive
    public static Color nodeCoal          = new Color(0x2E2F2F); // deep grey-black
    public static Color nodeFadedIndigo   = new Color(0x4C5C68); // steel blue
    public static Color nodeSoftRust      = new Color(0xA25C58); // earthy red
    public static Color nodeWashedTeal    = new Color(0x5F8171); // muted teal
    public static Color nodeIronViolet    = new Color(0x6A5C7C); // soft purple
    public static Color nodeWarmTaupe     = new Color(0x8B7E74); // neutral beige
    public static Color nodeCementGrey    = new Color(0x6F7378); // mid-industrial grey
    public static Color nodeFadedCopper   = new Color(0xB08968); // desaturated brown-orange
    public static Color nodeMossGrey      = new Color(0x7C8C6C); // quiet olive green
    public static Color nodeDullAzure     = new Color(0x628CA5); // steel sky blue
    public static Color nodeSmokyPlum     = new Color(0x7D6E83); // neutral violet
    public static Color nodeSteelframe    = new Color(0x4A6670); // industrial blue
    public static Color nodeClayOrange    = new Color(0xBA7F58); // baked clay
    public static Color nodeOchre         = new Color(0xC1A76E); // muted yellow ochre
    public static Color nodeDesertGrey    = new Color(0xA4978E); // warm desaturated grey
    public static Color nodeCharcoalGreen = new Color(0x5D6B57); // earthy green-black
	
	/**
     * Returns a gradient color array based on a base color.
     * @param base Base color (e.g., a node color)
     * @return Color array of [0] lighter (top), [1] darker (bottom)
     */
    public static Color[] getGradientColors(Color base) {
        return getGradientColors(base, 0.15f, 0.2f); // default factors
    }

    /**
     * Customizable version with brightness factors.
     * @param base Base color
     * @param lightFactor How much to lighten the top color (0–1)
     * @param darkFactor How much to darken the bottom color (0–1)
     * @return Color array: [0] = top gradient color, [1] = bottom
     */
    public static Color[] getGradientColors(Color base, float lightFactor, float darkFactor) {
        lightFactor = clamp(lightFactor, 0f, 1f);
        darkFactor = clamp(darkFactor, 0f, 1f);

        int rLight = (int) (base.getRed() + (255 - base.getRed()) * lightFactor);
        int gLight = (int) (base.getGreen() + (255 - base.getGreen()) * lightFactor);
        int bLight = (int) (base.getBlue() + (255 - base.getBlue()) * lightFactor);

        int rDark = (int) (base.getRed() * (1 - darkFactor));
        int gDark = (int) (base.getGreen() * (1 - darkFactor));
        int bDark = (int) (base.getBlue() * (1 - darkFactor));

        Color lighter = new Color(rLight, gLight, bLight);
        Color darker = new Color(rDark, gDark, bDark);

        return new Color[]{lighter, darker};
    }

    private static float clamp(float val, float min, float max) {
        return Math.max(min, Math.min(max, val));
    }
    
    public static Color blend(Color a, Color b, float bias) {
        // Clamp bias to [0, 1]
        bias = Math.max(0f, Math.min(1f, bias));

        int r = (int) (a.getRed() * (1 - bias) + b.getRed() * bias);
        int g = (int) (a.getGreen() * (1 - bias) + b.getGreen() * bias);
        int bVal = (int) (a.getBlue() * (1 - bias) + b.getBlue() * bias);
        int alpha = (int) (a.getAlpha() * (1 - bias) + b.getAlpha() * bias);

        return new Color(r, g, bVal, alpha);
    }
    public static Color[] interpolateColors(Color start, Color end, int steps) {
        Color[] result = new Color[steps];

        for (int i = 0; i < steps; i++) {
            float t = (float) i / (steps - 1); // from 0.0 to 1.0

            int r = (int) (start.getRed()   * (1 - t) + end.getRed()   * t);
            int g = (int) (start.getGreen() * (1 - t) + end.getGreen() * t);
            int b = (int) (start.getBlue()  * (1 - t) + end.getBlue()  * t);
            int a = (int) (start.getAlpha() * (1 - t) + end.getAlpha() * t);

            result[i] = new Color(r, g, b, a);
        }
        
        System.out.println(result.length);

        return result;
    }
}
