package to.us.harha.engine.gfx;

import to.us.harha.engine.math.MathUtils;

public class RGB {

	// Color red component
	public float	r;
	// Color green component
	public float	g;
	// Color blue component
	public float	b;
	// Color value as a hexadecimal
	public long		value;

	/*
	 * Main RGB constructor
	 */
	public RGB(float r, float g, float b) {
		this.r = r;
		this.g = g;
		this.b = b;
		absoluteValue();
		updateRGB();
	}

	/*
	 * Blank RGB constructor
	 */
	public RGB() {
		this.r = 0.0f;
		this.g = 0.0f;
		this.b = 0.0f;
		absoluteValue();
		updateRGB();
	}

	public void add(RGB c) {
		r += c.r;
		g += c.g;
		b += c.b;
		absoluteValue();
		updateRGB();
	}

	public RGB addR(RGB c) {
		return new RGB(r + c.r, g + c.g, b + c.b);
	}

	public RGB addF(float f) {
		return new RGB(r + f, g + f, b + f);
	}

	public void _add(float f) {
		r += f;
		g += f;
		b += f;
		absoluteValue();
		updateRGB();
	}

	public void sub(RGB c) {
		r -= c.r;
		g -= c.g;
		b -= c.b;
		absoluteValue();
		updateRGB();
	}

	public RGB subR(RGB c) {
		return new RGB(r - c.r, g - c.g, b - c.b);
	}

	public RGB _subF(float f) {
		return new RGB(r - f, g - f, b - f);
	}

	public void subF(float f) {
		r -= f;
		g -= f;
		b -= f;
		absoluteValue();
		updateRGB();
	}

	public void scale(float f) {
		r *= f;
		g *= f;
		b *= f;
		absoluteValue();
		updateRGB();
	}

	public void scale(RGB c) {
		r *= c.r;
		g *= c.g;
		b *= c.b;
		absoluteValue();
		updateRGB();
	}

	public RGB _scale(RGB c) {
		return new RGB(r * c.r, g * c.g, b * c.b);
	}

	public RGB scaleR(float f) {
		return new RGB(r * f, g * f, b * f);
	}

	public void updateRGB() {
		long red = (long) (r * 255.0f);
		long green = (long) (g * 255.0f);
		long blue = (long) (b * 255.0f);
		value = ((red << 16) | (green << 8) | blue);
	}

	public void absoluteValue() {
		r = MathUtils.clampf(r, 0.0f, 1.0f);
		g = MathUtils.clampf(g, 0.0f, 1.0f);
		b = MathUtils.clampf(b, 0.0f, 1.0f);
	}

}
