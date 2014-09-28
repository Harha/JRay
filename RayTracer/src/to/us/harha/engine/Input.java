package to.us.harha.engine;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Input implements KeyListener {

	public boolean	esc		= false;
	public boolean	up		= false;
	public boolean	down	= false;
	public boolean	left	= false;
	public boolean	right	= false;
	public boolean	w		= false;
	public boolean	s		= false;
	public boolean	a		= false;
	public boolean	d		= false;
	public boolean	r		= false;
	public boolean	f		= false;

	public void keyPressed(KeyEvent ke) {
		if (ke.getKeyCode() == KeyEvent.VK_ESCAPE) {
			esc = true;
		}
		if (ke.getKeyCode() == KeyEvent.VK_UP) {
			up = true;
		} else if (ke.getKeyCode() == KeyEvent.VK_DOWN) {
			down = true;
		}
		if (ke.getKeyCode() == KeyEvent.VK_LEFT) {
			left = true;
		} else if (ke.getKeyCode() == KeyEvent.VK_RIGHT) {
			right = true;
		}
		if (ke.getKeyCode() == KeyEvent.VK_W) {
			w = true;
		} else if (ke.getKeyCode() == KeyEvent.VK_S) {
			s = true;
		}
		if (ke.getKeyCode() == KeyEvent.VK_A) {
			a = true;
		} else if (ke.getKeyCode() == KeyEvent.VK_D) {
			d = true;
		}
		if (ke.getKeyCode() == KeyEvent.VK_R) {
			r = true;
		} else if (ke.getKeyCode() == KeyEvent.VK_F) {
			f = true;
		}
	}

	public void keyReleased(KeyEvent ke) {
		if (ke.getKeyCode() == KeyEvent.VK_ESCAPE) {
			esc = false;
		}
		if (ke.getKeyCode() == KeyEvent.VK_UP) {
			up = false;
		} else if (ke.getKeyCode() == KeyEvent.VK_DOWN) {
			down = false;
		}
		if (ke.getKeyCode() == KeyEvent.VK_LEFT) {
			left = false;
		} else if (ke.getKeyCode() == KeyEvent.VK_RIGHT) {
			right = false;
		}
		if (ke.getKeyCode() == KeyEvent.VK_W) {
			w = false;
		} else if (ke.getKeyCode() == KeyEvent.VK_S) {
			s = false;
		}
		if (ke.getKeyCode() == KeyEvent.VK_A) {
			a = false;
		} else if (ke.getKeyCode() == KeyEvent.VK_D) {
			d = false;
		}
		if (ke.getKeyCode() == KeyEvent.VK_R) {
			r = false;
		} else if (ke.getKeyCode() == KeyEvent.VK_F) {
			f = false;
		}
	}

	public void keyTyped(KeyEvent ke) {

	}

}
