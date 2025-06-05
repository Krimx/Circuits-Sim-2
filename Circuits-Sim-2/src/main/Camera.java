package main;

public class Camera {
	private int x,y;
	private float zoom;

	public Camera(int x, int y) {
		this.x = x;
		this.y = y;
		this.zoom = 1;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public float getZoom() {
		return zoom;
	}

	public void setZoom(float zoom) {
		this.zoom = zoom;
	}
}
