package cs355.model.drawing;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;

public class DrawingCircle {

	Circle cir;
	Graphics2D g2d;
	Color currColor;
	public DrawingCircle(Circle c, Graphics2D g, Color col)
	{
		cir = c;
		g2d = g;
		currColor = col;
	}
	
	public void draw()
	{
		g2d.setColor(currColor);
		g2d.fillOval((int) (cir.getCenter().getX() - cir.getRadius()/2), (int) (cir.getCenter().getY() - cir.getRadius()/2), (int) cir.getRadius(), (int) cir.getRadius());
	}
	
	public void drawOutline()
	{
		//draw the box
		g2d.setColor(new Color(255, 196, 0));
		float thickness = 2;
		Stroke oldStroke = g2d.getStroke();
		g2d.setStroke(new BasicStroke(thickness));
		g2d.drawOval((int) (cir.getCenter().getX() - cir.getRadius()/2), (int) (cir.getCenter().getY() - cir.getRadius()/2), (int) cir.getRadius(), (int) cir.getRadius());	
		g2d.setStroke(oldStroke);
	}
}
