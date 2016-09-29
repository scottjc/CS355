package cs355.model.drawing;

import java.awt.Color;
import java.awt.Graphics2D;

public class DrawingLine {

	Line line;
	Graphics2D g2d;
	Color currColor;
	public DrawingLine(Line l, Graphics2D g, Color c)
	{
		line = l;
		g2d = g;
		currColor = c;
	}
	
	public void draw()
	{
		g2d.setColor(currColor);
		g2d.drawLine((int)line.getStart().getX(), (int)line.getStart().getY(), (int)line.getEnd().getX(), (int)line.getEnd().getY());
	}
}
