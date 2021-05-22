package hr.fer.zemris.lsystems.impl.commands;

import java.awt.Color;

import hr.fer.oprpp1.math.Vector2D;
import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

public class SkipCommand implements Command {
	
	private double step;
	
	public SkipCommand(double step) {
		this.step = step;
	}

	@Override
	public void execute(Context ctx, Painter painter) {
		Vector2D currentPosition = ctx.getCurrentState().getPosition();
		Vector2D orientation = ctx.getCurrentState().getOrientation();
		
		Vector2D nextPosition = currentPosition.added(orientation.scaled(step));
		
		painter.drawLine(currentPosition.getX(), currentPosition.getY(), nextPosition.getX(), nextPosition.getY(), Color.WHITE, 1f);
		
		ctx.getCurrentState().setPosition(nextPosition);
	}
	
	
}
