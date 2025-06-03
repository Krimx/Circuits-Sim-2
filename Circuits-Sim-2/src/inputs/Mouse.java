package inputs;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class Mouse implements MouseListener, MouseMotionListener, MouseWheelListener {
	private boolean left, right, middle;
	private boolean canLeft = true, canRight = true, canMiddle = true;
	private boolean canLeft2 = true, canRight2 = true, canMiddle2 = true;
	private int x,y, lastX = 0, lastY = 0;
	private int scroll, scrollDifference = 0;
	private int dX = 0, dY = 0;
	private int xOffset = 0, yOffset = 0;
	private boolean verticalScroll = true;
	
	public Mouse() {
		this.xOffset = 0;
		this.yOffset = 0;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		int key = e.getButton();

		if (key == MouseEvent.BUTTON1) {
			left = true;
		
		}
		else left = false;
		
		if (key == MouseEvent.BUTTON2) {
			middle = true;
		}
		
		if (key == MouseEvent.BUTTON3) {
			right = true;
		}
		
		x = e.getX();
		y = e.getY();
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		int key = e.getButton();

		if (key == MouseEvent.BUTTON1) {
			left = false;
			canLeft = true;
			canLeft2 = true;
		}
		if (key == MouseEvent.BUTTON2) {
			middle = false;
			canMiddle = true;
			canMiddle2 = true;
		}
		if (key == MouseEvent.BUTTON3) {
			right = false;
			canRight = true;
			canRight2 = true;
		}
		
		x = e.getX();
		y = e.getY();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		x = e.getX();
		y = e.getY();
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		x = e.getX();
		y = e.getY();
		
	}
	
	public void mouseDragged(MouseEvent e) {
		x = e.getX();
		y = e.getY();
		
		dX = e.getX() - lastX;
		dY = e.getY() - lastY;
		
		lastX = e.getX();
		lastY = e.getY();
	}

    public void mouseMoved(MouseEvent e) {
    	x = e.getX();
		y = e.getY();
		
		dX = e.getX() - lastX;
		dY = e.getY() - lastY;
		
		lastX = e.getX();
		lastY = e.getY();
    }
    
    @Override
	public void mouseWheelMoved(MouseWheelEvent e) {
    	scroll -= e.getWheelRotation();
    	scrollDifference = -e.getWheelRotation();
    	
    	if (e.isShiftDown()) this.verticalScroll = false;
    	else this.verticalScroll = true;
	}
	
	public int getX() {return x + xOffset;}
	public int getY() {return y + yOffset;}
	public boolean getIsVerticalScroll() {return this.verticalScroll;}

	public int[] getDelta() {
		int[] toOut = {dX,dY};
		dX = 0;
		dY = 0;
		return toOut;
	}

	public boolean LEFT() {return left;}
	public boolean RIGHT() {return right;}
	public boolean MIDDLE() {return middle;}
	
	public boolean LEFTCLICKED() {
		if (left && canLeft) {
			canLeft = false;
			return true;
		}
		else {
			return false;
		}
	}
	public boolean RIGHTCLICKED() {
		if (right && canRight) {
			canRight = false;
			return true;
		}
		else {
			return false;
		}
	}
	public boolean MIDDLECLICKED() {
		if (middle && canMiddle) {
			canMiddle = false;
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean LEFTRELEASED() {
		if (!left && canLeft2) {
			canLeft2 = false;
			return true;
		}
		else return false;
	}
	public boolean MIDDLERELEASED() {
		if (!middle && canMiddle2) {
			canMiddle2 = false;
			return true;
		}
		else return false;
	}
	public boolean RIGHTRELEASED() {
		if (!right && canRight2) {
			canRight2 = false;
			return true;
		}
		else return false;
	}
	
	public int getScrollAmount() {
		return scroll;
	}
	public int getScrollDifference() {
		int toOut = scrollDifference;
		return toOut;
	}
	
	


	public void setXOffset(int offset) {xOffset = offset;}
	public void setYOffset(int offset) {yOffset = offset;}
	public void setScrollDifference(int in) {this.scrollDifference = in;}
}