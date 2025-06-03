package root;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.io.InputStream;

public class Fonts {
	public static Font rubik;

	static {
		try {
			InputStream is = Fonts.class.getResourceAsStream("/fonts/Rubik-Regular.ttf");
			if (is == null) throw new RuntimeException("Font not found at path");

			rubik = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(24f); // Adjust size as needed
		} catch (FontFormatException | java.io.IOException e) {
			e.printStackTrace();
			rubik = new Font("SansSerif", Font.PLAIN, 24); // fallback font
		}
	}
	
	public static int getTextLength(Graphics2D g, String text) {
		return g.getFontMetrics().stringWidth(text);
	}
}
