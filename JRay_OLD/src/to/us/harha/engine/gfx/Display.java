package to.us.harha.engine.gfx;

import java.util.Arrays;

public class Display {

	// Integers
	private int		m_width;
	private int		m_height;

	// Arrays
	private int[]	m_pixels;

	public Display(int width, int height) {
		m_width = width;
		m_height = height;
		m_pixels = new int[m_width * m_height];
	}

	public void clear() {
		Arrays.fill(m_pixels, 0x00000000);
	}

	public void drawPixel(int x, int y, RGB color) {
		int index = x + y * m_width;
		if (x >= 0 && x < m_width && y >= 0 && y < m_height) {
			m_pixels[index] = (int) color.value;
		} else {
			return;
		}
	}

	public int getPixel(int i) {
		return m_pixels[i];
	}

	public int getWidth() {
		return m_width;
	}

	public int getHeight() {
		return m_height;
	}

}
