package hr.fer.zemris.lsystems.impl;

import java.awt.Color;

import hr.fer.oprpp1.math.Vector2D;

public class TurtleState {

	private Vector2D position;
	private Vector2D orientation;
	private Color color;
	private double length;
	
	public TurtleState(Vector2D position, Vector2D orientation, Color color, double length) {
		this.position = position;
		this.orientation = orientation;
		this.color = color;
		this.length = length;
	}
	
	public TurtleState copy() {
		return new TurtleState(position, orientation, color, length);
	}
	
	public Vector2D getPosition() {
		return position;
	}

	public void setPosition(Vector2D position) {
		this.position = position;
	}

	public Vector2D getOrientation() {
		return orientation;
	}

	public void setOrientation(Vector2D orientation) {
		this.orientation = orientation;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public double getLength() {
		return length;
	}

	public void setLength(double length) {
		this.length = length;
	}

}
