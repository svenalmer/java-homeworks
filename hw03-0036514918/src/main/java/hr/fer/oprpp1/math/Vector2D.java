package hr.fer.oprpp1.math;

public class Vector2D {
	private double x;
	private double y;
	
	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public void add(Vector2D offset) {
		x = x + offset.x;
		y = y + offset.y;
	}
	
	public Vector2D added(Vector2D offset) {
		return new Vector2D(this.x + offset.x, this.y + offset.y);
	}
	
	public void rotate(double angle) {
		double x2 = Math.cos(angle) * x - Math.sin(angle) * y;
		double y2 = Math.sin(angle) * x + Math.cos(angle) * y;
		x = x2;
		y = y2;
	}
	
	public Vector2D rotated(double angle) {
		return new Vector2D(Math.cos(angle) * x - Math.sin(angle) * y, Math.sin(angle) * x + Math.cos(angle) * y);
	}
	
	public void scale(double scaler) {
		x = x * scaler;
		y = y * scaler;
	}
	
	public Vector2D scaled(double scaler) {
		return new Vector2D(x * scaler, y * scaler);
	}
	
	public Vector2D copy() {
		return new Vector2D(x, y);
	}
}
