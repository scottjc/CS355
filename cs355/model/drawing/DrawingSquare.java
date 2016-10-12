package cs355.model.drawing;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.lang.reflect.Array;

public class DrawingSquare {

	Square squ;
	Graphics2D g2d;
	Color currColor;
	public DrawingSquare(Square r, Graphics2D g, Color c)
	{
		squ = r;
		g2d = g;
		currColor = c;
	}
	
	public void draw()
	{
		g2d.setColor(currColor);
		//draw that puppy!
		//p1x  p1y   p2x    p2y
		g2d.fillRect((int)squ.getCenter().getX() - (int)(squ.getSize()/2), (int)squ.getCenter().getY() - (int)(squ.getSize()/2),
				(int)squ.getSize(), (int)squ.getSize());
	}

	public void drawOutline(double viewScale)
	{
		//draw the box
		g2d.setColor(new Color(255, 196, 0));
		float thickness = (float) (2.5 * (100/viewScale));
		Stroke oldStroke = g2d.getStroke();
		g2d.setStroke(new BasicStroke(thickness));
		g2d.drawRect((int)squ.getCenter().getX() - (int)(squ.getSize()/2), (int)squ.getCenter().getY() - (int)(squ.getSize()/2),
				(int)squ.getSize(), (int)squ.getSize());	
		g2d.setStroke(oldStroke);
	}
}
