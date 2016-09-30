package cs355.view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

import cs355.model.drawing.*;//our shape objects
import java.util.Observable;

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
	
	public MyView()
	{
		currShapeIndex = -1;
		selectedShape = Shape.NONE;
		outlineShape = null;
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
		//System.out.println("refreshView");
		this.g2d = g2d;
		selectedShape = Shape.NONE;
		outlineShape = null;
		
		//change all of the shapes into drawing shapes
		//loop through all shapes and draw them!
		for(int i = mm.getShapes().size()-1; i > -1; i--)
		{
			AffineTransform worldToObj = new AffineTransform();
			worldToObj.translate(mm.getShapes().get(i).getCenter().getX(), mm.getShapes().get(i).getCenter().getY());
			worldToObj.rotate(mm.getShapes().get(i).getRotation());
			worldToObj.translate(-mm.getShapes().get(i).getCenter().getX(), -mm.getShapes().get(i).getCenter().getY());
			g2d.setTransform(worldToObj);
			//instantiate
			
			if(mm.getShapes().get(i) instanceof Line)
			{
				DrawingLine dc = new DrawingLine((Line)mm.getShapes().get(i), g2d, mm.getShapes().get(i).getColor());
				//if(i == currShapeIndex) //add to dc
				dc.draw();
				
				if(i == currShapeIndex)
				{
					//affine transform for showing this modified shape
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
			AffineTransform worldToObj2 = new AffineTransform();
			worldToObj2.translate(outlineShape.getCenter().getX(), outlineShape.getCenter().getY());
			worldToObj2.rotate(outlineShape.getRotation());
			worldToObj2.translate(-outlineShape.getCenter().getX(), -outlineShape.getCenter().getY());
			g2d.setTransform(worldToObj2);
		}

		switch(selectedShape)
		{
		case LINE:
			//System.out.println("Draw line outline");
			Line l =  (Line)outlineShape;
			Circle c = new Circle(outlineShape.getColor(), new Point2D.Double(l.getStart().getX() - 4, l.getStart().getY() - 4), 10);//color center radius
			DrawingCircle dccl = new DrawingCircle(c, g2d, outlineShape.getColor());
			dccl.drawOutline();
			Circle c1 = new Circle(outlineShape.getColor(), new Point2D.Double(l.getEnd().getX() - 4, l.getEnd().getY() - 4), 10);//color center radius
			DrawingCircle dccll = new DrawingCircle(c1, g2d, outlineShape.getColor());
			dccll.drawOutline();
			break;
			
		case TRIANGLE:
			//affine transform for showing this modified shape
			//System.out.println("Draw trianlge outline");
			Triangle t = ((Triangle)outlineShape);
			DrawingTriangle dcct = new DrawingTriangle((Triangle)outlineShape, g2d, outlineShape.getColor());
			dcct.drawOutline();
			
			//draw the circle plus the rotation.
			//System.out.println("drawing the halo");
			//double yt = (double) t.getCenter().getY() - t.getHeight()/2 - 30;
			double minY1 = Math.min(t.getA().getY(), t.getB().getY());
			double realMin = Math.min(minY1, t.getC().getY());
			
			Point2D.Double newPointt = new Point2D.Double(t.getCenter().getX()-10, realMin - 30);
			//System.out.println("circle center is " + newPointt.toString());
			Circle cc = new Circle(outlineShape.getColor(), newPointt, 20);//color center radius
			DrawingCircle dccre = new DrawingCircle(cc, g2d, outlineShape.getColor());
			dccre.drawOutline();
			
			break;
			
		case CIRCLE:
			//affine transform for showing this modified shape
			//System.out.println("Draw circle outline");
			DrawingCircle dcc1 = new DrawingCircle((Circle)outlineShape, g2d, outlineShape.getColor());
			dcc1.drawOutline();
			break;
			
		case RECTANGLE:
			//affine transform for showing this modified shape
			//System.out.println("Draw rectangle outline");
			Rectangle r = ((Rectangle)outlineShape);
			DrawingRectangle dccree = new DrawingRectangle((Rectangle)outlineShape, g2d, outlineShape.getColor());
			dccree.drawOutline();
			
			//draw the circle plus the rotation.
			//System.out.println("drawing the halo");
			double yr = (double) r.getCenter().getY() - r.getHeight()/2 - 30;
			Point2D.Double newPointrr = new Point2D.Double(r.getCenter().getX()-10, yr);
			//System.out.println("circle center is " + newPointrr.toString());
			Circle ccc = new Circle(outlineShape.getColor(), newPointrr, 20);//color center radius
			DrawingCircle dccc = new DrawingCircle(ccc, g2d, outlineShape.getColor());
			dccc.drawOutline();
			break;
			
		case SQUARE:
			//System.out.println("Draw square outline");
			//need to apply objToWorld transformations
			Square s = ((Square)outlineShape);
			DrawingSquare dcc3 = new DrawingSquare(s, g2d, outlineShape.getColor());
			dcc3.drawOutline();
			
			//draw the circle plus the rotation.
			//System.out.println("drawing the halo");
			double y = (double) s.getCenter().getY() - s.getSize()/2 - 30;
			Point2D.Double newPoint = new Point2D.Double(s.getCenter().getX()-10, y);
			//System.out.println("circle center is " + newPoint.toString());
			Circle cccc = new Circle(outlineShape.getColor(), newPoint, 20);//color center radius
			DrawingCircle dccsq = new DrawingCircle(cccc, g2d, outlineShape.getColor());
			dccsq.drawOutline();
			break;
			
		case ELLIPSE:

			//affine transform for showing this modified shape
			//System.out.println("Draw rectangle outline");
			Ellipse e = ((Ellipse)outlineShape);//.objToWorld();
			DrawingEllipse dcce = new DrawingEllipse((Ellipse)outlineShape, g2d, outlineShape.getColor());
			dcce.drawOutline();
			
			//draw the circle plus the rotation.
			//System.out.println("drawing the halo");
			double ye = (double) e.getCenter().getY() - e.getHeight()/2 - 30;
			Point2D.Double newPointe = new Point2D.Double(e.getCenter().getX()-10, ye);
			//System.out.println("circle center is " + newPointe.toString());
			Circle ccccc = new Circle(outlineShape.getColor(), newPointe, 20);//color center radius
			DrawingCircle dccel = new DrawingCircle(ccccc, g2d, outlineShape.getColor());
			dccel.drawOutline();
			break;
		}
		
		
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		GUIFunctions.refresh();
	}
	
	//my functions---------------------------------------
	public void set_currShapeIndex(int in)
	{
		this.currShapeIndex = in;
	}
}
