package cs355.view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImageOp;

import cs355.model.drawing.*;//our shape objects
import java.util.Observable;
import java.util.Vector;

import cs355.GUIFunctions;

/**                                                             
 * This is the interface for the view. Make
 * sure your view implements this interface.
 */
public class MyView implements ViewRefresher {

	MyModel mm;
	Graphics2D g2d;
	int currShapeIndex;
	public enum Shape//for helping us see what kind of shape outline we'd like to draw
	{
		NONE, LINE, SQUARE, RECTANGLE,
		CIRCLE, ELLIPSE, TRIANGLE
	}
	Shape selectedShape;
	cs355.model.drawing.Shape outlineShape;
	
	double viewScale;//for Lab3
	Point2D.Double originCoords;
	
	//Lab5
	cs355.model.scene.Render3D renderer;
	cs355.model.scene.CS355Scene scene;
	Vector<Color> colors = new Vector<Color>();
	
	//Lab6
	cs355.model.image.MyImage imagey;
	Boolean drawMe;
	
	public MyView()
	{
		currShapeIndex = -1;
		selectedShape = Shape.NONE;
		outlineShape = null;
		
		set_viewScale(100);
		set_originCoords(new Point2D.Double(768,768));
		
		//Lab6------------------------------------------------
		drawMe = false;
	}
	
	//Lab6 stuff-------------------------------------------------
	public void setDrawMe(Boolean b)
	{
		drawMe = b;
	}
	public void setImagey(cs355.model.image.MyImage i)
	{
		imagey = i;
	}
	
	//Lab5 stuff
	public void setRenderer(cs355.model.scene.Render3D r)
	{
		renderer = r;
	
	}
	public void setScene(cs355.model.scene.CS355Scene s)
	{
		scene = s;
	}
	public void setColor(Vector<Color> c)
	{
		this.colors = c;
	}
	
	//Not Lab 5
	public cs355.model.scene.Render3D getRenderer()
	{
		return renderer;
	}
	
	public void setModel(MyModel m)
	{
		mm = m;
	}
	/**
	 * Called when the view needs to be redrawn.
	 * @param g2d = the Graphics2D object to draw with.
	 * 
	 */
	public void refreshView(Graphics2D g2d)
	{
		this.g2d = g2d;
		selectedShape = Shape.NONE;
		outlineShape = null;
		
		//FIRST we must do this
		//Lab6 stuff-------------------------------------
		if(imagey.drawMe)
		{
				//System.out.println("it's true");
				//originCoords
				AffineTransform oTvv = new AffineTransform();
				oTvv.concatenate(new AffineTransform(viewScale/100,0,0,viewScale/100, 0, 0));//S of view
//				int AspectRatio = 1;
//				if(imagey.getWidth() != 0 && imagey.getHeight() != 0) AspectRatio = imagey.getWidth() / imagey.getHeight();
				oTvv.concatenate(new AffineTransform(1,0,0,1,
						1024 - imagey.getWidth()/2 - originCoords.getX(), 1024 - imagey.getHeight()/2 - originCoords.getY()));//T-1 of scroll bar
				g2d.setTransform(oTvv);//concat instead
				//draw the image
				g2d.drawImage(imagey.getImage(), 0, 0, null);
		}
		
		//change all of the shapes into drawing shapes
		//loop through all shapes and draw them!
		for(int i = mm.getShapes().size()-1; i > -1; i--)
		{
			//put into a matrix
			//System.out.println("refresh view");

			AffineTransform oTv = new AffineTransform();
			oTv.concatenate(new AffineTransform(viewScale/100,0,0,viewScale/100, 0, 0));//S of view
			oTv.concatenate(new AffineTransform(1,0,0,1, -originCoords.getX(), -originCoords.getY()));//T-1 or scroll bar
			oTv.concatenate(new AffineTransform(1,0,0,1, mm.getShapes().get(i).getCenter().getX(), mm.getShapes().get(i).getCenter().getY()));//T of shape center
			oTv.concatenate(new AffineTransform(Math.cos(mm.getShapes().get(i).getRotation()),Math.sin(mm.getShapes().get(i).getRotation()),
					Math.sin(-mm.getShapes().get(i).getRotation()),Math.cos(mm.getShapes().get(i).getRotation()),0,0));//the first 6 00,10,01,11,02,12) R of shape
			oTv.concatenate(new AffineTransform(1,0,0,1, -mm.getShapes().get(i).getCenter().getX(),-mm.getShapes().get(i).getCenter().getY()));//T of shape center
			
			g2d.setTransform(oTv);//concat instead
			
			if(mm.getShapes().get(i) instanceof Line)
			{
				DrawingLine dc = new DrawingLine((Line)mm.getShapes().get(i), g2d, mm.getShapes().get(i).getColor());
				//if(i == currShapeIndex) //add to dc
				dc.draw();
				
				if(i == currShapeIndex)
				{
					//System.out.println("Draw line outline");
					//Square box = new Square();
					//DrawingSquare dcc = new DrawingSquare(box, g2d, box.getColor());
					selectedShape = Shape.LINE;
					outlineShape = mm.getShapes().get(i);
				}
			}
			if(mm.getShapes().get(i) instanceof Triangle)
			{
				DrawingTriangle dc = new DrawingTriangle((Triangle)mm.getShapes().get(i), g2d, mm.getShapes().get(i).getColor());
				dc.draw();
				
				if(i == currShapeIndex)
				{
					selectedShape = Shape.TRIANGLE;
					outlineShape = mm.getShapes().get(i);
				}
			}
			if(mm.getShapes().get(i) instanceof Circle)
			{
				DrawingCircle dc = new DrawingCircle((Circle)mm.getShapes().get(i), g2d, mm.getShapes().get(i).getColor());
				dc.draw();
				
				if(i == currShapeIndex)
				{
					selectedShape = Shape.CIRCLE;
					outlineShape = mm.getShapes().get(i);
				}
			}
			if(mm.getShapes().get(i) instanceof Rectangle)
			{
				DrawingRectangle dc = new DrawingRectangle((Rectangle)mm.getShapes().get(i), g2d, mm.getShapes().get(i).getColor());
				dc.draw();

				if(i == currShapeIndex)
				{
					selectedShape = Shape.RECTANGLE;
					outlineShape = mm.getShapes().get(i);
				}
			}
			if(mm.getShapes().get(i) instanceof Square)
			{	
				//need to apply objToWorld transformations
				//((Square) mm.getShapes().get(i)).objToWorld();
				Square s = ((Square) mm.getShapes().get(i));
				
				DrawingSquare dc = new DrawingSquare(s, g2d, mm.getShapes().get(i).getColor());
				dc.draw();
				
				if(i == currShapeIndex)
				{
					selectedShape = Shape.SQUARE;
					outlineShape = mm.getShapes().get(i);
				}
			}
			if(mm.getShapes().get(i) instanceof Ellipse)
			{
				DrawingEllipse dc = new DrawingEllipse((Ellipse)mm.getShapes().get(i), g2d, mm.getShapes().get(i).getColor());
				dc.draw();
				
				if(i == currShapeIndex)
				{
					selectedShape = Shape.ELLIPSE;
					outlineShape = mm.getShapes().get(i);
				}
			}
		}
		
		if(outlineShape != null)
		{		
			//Object To View transformation
			AffineTransform oTv = new AffineTransform();
			oTv.concatenate(new AffineTransform(viewScale/100,0,0,viewScale/100, 0, 0));//S of view
			oTv.concatenate(new AffineTransform(1,0,0,1, -originCoords.getX(), -originCoords.getY()));//T-1 or scroll bar
			oTv.concatenate(new AffineTransform(1,0,0,1, outlineShape.getCenter().getX(), outlineShape.getCenter().getY()));//T of shape center
			oTv.concatenate(new AffineTransform(Math.cos(outlineShape.getRotation()),Math.sin(outlineShape.getRotation()),
					Math.sin(-outlineShape.getRotation()),Math.cos(outlineShape.getRotation()),0,0));//the first 6 00,10,01,11,02,12) R of shape
			oTv.concatenate(new AffineTransform(1,0,0,1, -outlineShape.getCenter().getX(),-outlineShape.getCenter().getY()));//T of shape center
			
			g2d.setTransform(oTv);//concat instead
		}

		switch(selectedShape)
		{
		case LINE:
			//System.out.println("Draw line outline");
			Line l =  (Line)outlineShape;
			Circle c = new Circle(outlineShape.getColor(), new Point2D.Double(l.getStart().getX() - 4, l.getStart().getY() - 4), 10);//color center radius
			DrawingCircle dccl = new DrawingCircle(c, g2d, outlineShape.getColor());
			dccl.drawOutline(viewScale);
			Circle c1 = new Circle(outlineShape.getColor(), new Point2D.Double(l.getEnd().getX() - 4, l.getEnd().getY() - 4), 10);//color center radius
			DrawingCircle dccll = new DrawingCircle(c1, g2d, outlineShape.getColor());
			dccll.drawOutline(viewScale);
			break;
			
		case TRIANGLE:
			//System.out.println("Draw trianlge outline");
			Triangle t = ((Triangle)outlineShape);
			DrawingTriangle dcct = new DrawingTriangle((Triangle)outlineShape, g2d, outlineShape.getColor());
			dcct.drawOutline(viewScale);
			
			//draw the circle plus the rotation.
			//System.out.println("drawing the halo");
			//double yt = (double) t.getCenter().getY() - t.getHeight()/2 - 30;
			double minY1 = Math.min(t.getA().getY(), t.getB().getY());
			double realMin = Math.min(minY1, t.getC().getY());
			
			Point2D.Double newPointt = new Point2D.Double(t.getCenter().getX()-10, realMin - 30);
			//System.out.println("circle center is " + newPointt.toString());
			Circle cc = new Circle(outlineShape.getColor(), newPointt, 20);//color center radius
			DrawingCircle dccre = new DrawingCircle(cc, g2d, outlineShape.getColor());
			dccre.drawOutline(viewScale);
			
			break;
			
		case CIRCLE:
			//System.out.println("Draw circle outline");
			DrawingCircle dcc1 = new DrawingCircle((Circle)outlineShape, g2d, outlineShape.getColor());
			dcc1.drawOutline(viewScale);
			break;
			
		case RECTANGLE:
			//System.out.println("Draw rectangle outline");
			Rectangle r = ((Rectangle)outlineShape);
			DrawingRectangle dccree = new DrawingRectangle((Rectangle)outlineShape, g2d, outlineShape.getColor());
			dccree.drawOutline(viewScale);
			
			//draw the circle plus the rotation.
			//System.out.println("drawing the halo");
			double yr = (double) r.getCenter().getY() - r.getHeight()/2 - 30;
			Point2D.Double newPointrr = new Point2D.Double(r.getCenter().getX()-10, yr);
			//System.out.println("circle center is " + newPointrr.toString());
			Circle ccc = new Circle(outlineShape.getColor(), newPointrr, 20);//color center radius
			DrawingCircle dccc = new DrawingCircle(ccc, g2d, outlineShape.getColor());
			dccc.drawOutline(viewScale);
			break;
			
		case SQUARE:
			//System.out.println("Draw square outline");
			//need to apply objToWorld transformations
			Square s = ((Square)outlineShape);
			DrawingSquare dcc3 = new DrawingSquare(s, g2d, outlineShape.getColor());
			dcc3.drawOutline(viewScale);
			
			//draw the circle plus the rotation.
			//System.out.println("drawing the halo");
			double y = (double) s.getCenter().getY() - s.getSize()/2 - 30;
			Point2D.Double newPoint = new Point2D.Double(s.getCenter().getX()-10, y);
			//System.out.println("circle center is " + newPoint.toString());
			Circle cccc = new Circle(outlineShape.getColor(), newPoint, 20);//color center radius
			DrawingCircle dccsq = new DrawingCircle(cccc, g2d, outlineShape.getColor());
			dccsq.drawOutline(viewScale);
			break;
			
		case ELLIPSE:
			//System.out.println("Draw rectangle outline");
			Ellipse e = ((Ellipse)outlineShape);//.objToWorld();
			DrawingEllipse dcce = new DrawingEllipse((Ellipse)outlineShape, g2d, outlineShape.getColor());
			dcce.drawOutline(viewScale);
			
			//draw the circle plus the rotation.
			//System.out.println("drawing the halo");
			double ye = (double) e.getCenter().getY() - e.getHeight()/2 - 30;
			Point2D.Double newPointe = new Point2D.Double(e.getCenter().getX()-10, ye);
			//System.out.println("circle center is " + newPointe.toString());
			Circle ccccc = new Circle(outlineShape.getColor(), newPointe, 20);//color center radius
			DrawingCircle dccel = new DrawingCircle(ccccc, g2d, outlineShape.getColor());
			dccel.drawOutline(viewScale);
			break;
		}
		
		//Lab5 stuff
		if(renderer.getShow())
		{
			//System.out.println("it's true");
			//stuff about it
			//Object To View transformation
			AffineTransform oTvv = new AffineTransform();
			oTvv.concatenate(new AffineTransform(viewScale/100,0,0,viewScale/100, 0, 0));//S of view
			oTvv.concatenate(new AffineTransform(1,0,0,1, -originCoords.getX(), -originCoords.getY()));//T-1 or scroll bar
			g2d.setTransform(oTvv);//concat instead
			renderer.process(scene, g2d, colors);
		}
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		GUIFunctions.refresh();
	}
	
	//my functions------------------------------------------------------------
	public void set_currShapeIndex(int in)
	{
		this.currShapeIndex = in;
	}

	public void set_viewScale(double i) 
	{
		this.viewScale = i;
		
	}

	public void set_originCoords(java.awt.geom.Point2D.Double double1) 
	{
		originCoords = double1;
		
	}

	public double get_viewScale() 
	{
		return viewScale;
	}
	
	public Point2D.Double get_originCoords() 
	{
		return originCoords;
	}
}
