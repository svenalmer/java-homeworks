package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.oprpp1.math.Vector2D;
import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

public class DrawCommand implements Command {

	private double step;
	
	public DrawCommand(double step) {
		this.step = step;
	}
	
	@Override
	public void execute(Context ctx, Painter painter) {
		Vector2D currentPosition = ctx.getCurrentState().getPosition();
		Vector2D orientation = ctx.getCurrentState().getOrientation();
		
		Vector2D nextPosition = currentPosition.added(orientation.scaled(step));
		
		painter.drawLine(currentPosition.getX(), currentPosition.getY(), nextPosition.getX(), nextPosition.getY(), ctx.getCurrentState().getColor(), 1f);
		
		ctx.getCurrentState().setPosition(nextPosition);
	}
	
}
