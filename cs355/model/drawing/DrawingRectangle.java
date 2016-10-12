package cs355.model.drawing;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.lang.reflect.Array;

public class DrawingRectangle {

	Rectangle rec;
	Graphics2D g2d;
	Color currColor;
	public DrawingRectangle(Rectangle r, Graphics2D g, Color c)
	{
		rec = r;
		g2d = g;
		currColor = c;
	}
	
	public void draw()
	{
		g2d.setColor(currColor);
		//draw that puppy!
		//p1x  p1y   p2x    p2y
		g2d.fillRect((int)rec.getCenter().getX() - (int)(rec.getWidth()/2), (int)rec.getCenter().getY() - (int)(rec.getHeight()/2), 
				(int)rec.getWidth(), (int)rec.getHeight());
	}
	
	public void drawOutline(double viewScale)
	{
		//draw the box
		g2d.setColor(new Color(255, 196, 0));
		float thickness = (float) (2.5 * (100/viewScale));
		Stroke oldStroke = g2d.getStroke();
		g2d.setStroke(new BasicStroke(thickness));
		g2d.drawRect((int)rec.getCenter().getX() - (int)(rec.getWidth()/2), (int)rec.getCenter().getY() - (int)(rec.getHeight()/2), 
				(int)rec.getWidth(), (int)rec.getHeight());	
		g2d.setStroke(oldStroke);
		
		//draw the circle
		//g2d.drawOval((int)squ.getCenter().getX()-5, (int)squ.getCenter().getY() - 30,  20, 20);
	}
}
