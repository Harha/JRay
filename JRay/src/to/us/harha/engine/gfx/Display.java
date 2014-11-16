package to.us.harha.engine.gfx;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Arrays;

import to.us.harha.engine.util.math.Vec3f;

public class Display {

	private int				m_width;
	private int				m_height;
	private int[]			m_pixels;
	private BufferedImage	m_image;

	public Display(int width, int height) {
		m_width = width;
		m_height = height;
		m_image = new BufferedImage(m_width, m_height, BufferedImage.TYPE_INT_RGB);
		m_pixels = ((DataBufferInt) m_image.getRaster().getDataBuffer()).getData();
	}

	public void clear() {
		Arrays.fill(m_pixels, 0x000000);
	}

	public void drawPixelVec3f(int x, int y, Vec3f v) {
		if (x < 0 || x > m_width || y < 0 || y > m_height)
			return;

		// Calculate the hexadecimal color from the vector parameters
		long red = (long) (v.x * 255.0f);
		long green = (long) (v.y * 255.0f);
		long blue = (long) (v.z * 255.0f);
		long hex_value = ((red << 16) | (green << 8) | blue);

		m_pixels[x + y * m_width] = (int) hex_value;
	}

	public void drawPixelInt(int x, int y, int color) {
		if (x < 0 || x >= m_width || y < 0 || y >= m_height)
			return;
		m_pixels[x + y * m_width] = color;
	}

	/**
	 * @return the m_width
	 */
	public int get_width() {
		return m_width;
	}

	/**
	 * @return the m_height
	 */
	public int get_height() {
		return m_height;
	}

	/**
	 * @return the m_pixels
	 */
	public int[] get_pixels() {
		return m_pixels;
	}

	public int get_pixel(int i) {
		return m_pixels[i];
	}

	/**
	 * @return the m_image
	 */
	public BufferedImage get_image() {
		return m_image;
	}

}
