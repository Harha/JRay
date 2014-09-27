package to.us.harha.engine.gfx;

import to.us.harha.engine.math.MathUtils;

public class RGBA {

	public float	r;
	public float	g;
	public float	b;
	public float	a;

	public long		value;

	public RGBA(float r, float g, float b, float a) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
		absoluteValue();
		updateRGBA();
	}

	public RGBA() {
		this.r = 0.0f;
		this.g = 0.0f;
		this.b = 0.0f;
		this.a = 0.0f;
		absoluteValue();
		updateRGBA();
	}

	public void add(RGBA c) {
		r += c.r;
		g += c.g;
		b += c.b;
		a += c.a;
		absoluteValue();
		updateRGBA();
	}

	public RGBA addR(RGBA c) {
		return new RGBA(r + c.r, g + c.g, b + c.b, a + c.a);
	}

	public RGBA addF(float f) {
		return new RGBA(r + f, g + f, b + f, a + f);
	}

	public void _add(float f) {
		r += f;
		g += f;
		b += f;
		a += f;
		absoluteValue();
		updateRGBA();
	}

	public void sub(RGBA c) {
		r -= c.r;
		g -= c.g;
		b -= c.b;
		a -= c.a;
		absoluteValue();
		updateRGBA();
	}

	public RGBA subR(RGBA c) {
		return new RGBA(r - c.r, g - c.g, b - c.b, a - c.a);
	}

	public void subF(float f) {
		r -= f;
		g -= f;
		b -= f;
		a -= f;
		absoluteValue();
		updateRGBA();
	}

	public void scale(float f) {
		r *= f;
		g *= f;
		b *= f;
		a *= f;
		absoluteValue();
		updateRGBA();
	}

	public void scale(RGBA c) {
		r *= c.r;
		g *= c.g;
		b *= c.b;
		a *= c.a;
		absoluteValue();
		updateRGBA();
	}

	public RGBA _scale(RGBA c) {
		return new RGBA(r * c.r, g * c.g, b * c.b, a * c.a);
	}

	public RGBA scaleR(float f) {
		return new RGBA(r * f, g * f, b * f, a * f);
	}

	public void updateRGBA() {
		long red = (long) (r * 255.0f);
		long green = (long) (g * 255.0f);
		long blue = (long) (b * 255.0f);
		long alpha = (long) (a * 255.0f);
		value = ((alpha << 24) | (red << 16) | (green << 8) | blue);
	}

	public void absoluteValue() {
		r = MathUtils.clampf(r, 0.0f, 1.0f);
		g = MathUtils.clampf(g, 0.0f, 1.0f);
		b = MathUtils.clampf(b, 0.0f, 1.0f);
		a = MathUtils.clampf(a, 0.0f, 1.0f);
	}

}
