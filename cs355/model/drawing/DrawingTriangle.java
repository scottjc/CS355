package cs355.model.drawing;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.lang.reflect.Array;

public class DrawingTriangle {

	Triangle tri;
	Graphics2D g2d;
	Color currColor;
	public DrawingTriangle(Triangle t, Graphics2D g, Color c)
	{
		tri = t;
		g2d = g;
		currColor = c;
	}
	
	public void draw()
	{
		g2d.setColor(currColor);
		int[] x = new int[3];
		int[] y = new int[3];
		

		x[0] = (int) tri.getA().getX();
		y[0] = (int) tri.getA().getY();
		x[1] = (int) tri.getB().getX();
		y[1] = (int) tri.getB().getY();
		x[2] = (int) tri.getC().getX();
		y[2] = (int) tri.getC().getY();

		
		g2d.fillPolygon(x, y, 3);
	}
	
	public void drawOutline(double viewScale)
	{
		//draw the box
		g2d.setColor(new Color(255, 196, 0));
		float thickness = (float) (2.5 * (100/viewScale));
		Stroke oldStroke = g2d.getStroke();
		g2d.setStroke(new BasicStroke(thickness));

		int[] x = new int[3];
		int[] y = new int[3];
		

		x[0] = (int) tri.getA().getX();
		y[0] = (int) tri.getA().getY();
		x[1] = (int) tri.getB().getX();
		y[1] = (int) tri.getB().getY();
		x[2] = (int) tri.getC().getX();
		y[2] = (int) tri.getC().getY();

		
		g2d.drawPolygon(x, y, 3);
		g2d.setStroke(oldStroke);
		
		//draw the circle
		//g2d.drawOval((int)squ.getCenter().getX()-5, (int)squ.getCenter().getY() - 30,  20, 20);
	}
}
