package cs355.model.drawing;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;

public class DrawingEllipse {

	Ellipse elli;
	Graphics2D g2d;
	Color currColor;
	public DrawingEllipse(Ellipse e, Graphics2D g, Color col)
	{
		elli = e;
		g2d = g;
		currColor = col;
	}
	
	public void draw()
	{
		g2d.setColor(currColor);
		//changed this stuff below-----------------------------------------------------------------------------------------------
		g2d.fillOval((int) elli.getCenter().getX() -  (int) elli.getWidth()/2, (int) elli.getCenter().getY() - (int) elli.getHeight()/2, (int) elli.getWidth(), (int) elli.getHeight());
	}
	
	public void drawOutline()
	{
		//draw the box
		g2d.setColor(new Color(255, 196, 0));
		float thickness = 2;
		Stroke oldStroke = g2d.getStroke();
		g2d.setStroke(new BasicStroke(thickness));
		g2d.drawRect((int) elli.getCenter().getX() -  (int) elli.getWidth()/2, (int) elli.getCenter().getY() - (int) elli.getHeight()/2, (int) elli.getWidth(), (int) elli.getHeight());	
		g2d.setStroke(oldStroke);
		
		//draw the circle
		//g2d.drawOval((int)squ.getCenter().getX()-5, (int)squ.getCenter().getY() - 30,  20, 20);
	}
}
