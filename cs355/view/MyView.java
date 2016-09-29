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
		
		//change all of the shapes into drawing shapes
		//loop through all shapes and draw them!
		for(int i = mm.getShapes().size()-1; i > -1; i--)
		{
			//do Affine transform for the shape-----------------------------------------------------------------
			AffineTransform worldToObj = new AffineTransform();
			worldToObj.translate(-mm.getShapes().get(i).getCenter().getX(), -mm.getShapes().get(i).getCenter().getY());
			worldToObj.rotate(mm.getShapes().get(i).getRotation());
			worldToObj.translate(mm.getShapes().get(i).getCenter().getX(), mm.getShapes().get(i).getCenter().getY());
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
				Square s = ((Square) mm.getShapes().get(i));//.objToWorld(); //----------------------------------------------------
				
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
		
		switch(selectedShape)
		{
		case LINE:
			//System.out.println("Draw line outline");
			Line l =  (Line)outlineShape;
			Circle c = new Circle(outlineShape.getColor(), new Point2D.Double(l.getStart().getX() - 4, l.getStart().getY() - 4), 10);//color center radius
			DrawingCircle dccl = new DrawingCircle(c, g2d, outlineShape.getColor());
			dccl.drawOutline();
			c = new Circle(outlineShape.getColor(), new Point2D.Double(l.getEnd().getX() - 4, l.getEnd().getY() - 4), 10);//color center radius
			DrawingCircle dccll = new DrawingCircle(c, g2d, outlineShape.getColor());
			dccll.drawOutline();
			break;
			
		case TRIANGLE:
			//System.out.println("Draw triangle outline");
			DrawingTriangle dcc = new DrawingTriangle((Triangle)outlineShape, g2d, outlineShape.getColor());
			dcc.drawOutline();
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
			DrawingRectangle dcc2 = new DrawingRectangle((Rectangle)outlineShape, g2d, outlineShape.getColor());
			dcc2.drawOutline();
			
			//halo
			break;
			
		case SQUARE:
			System.out.println("Draw square outline");
			//need to apply objToWorld transformations
			//((Square) mm.getShapes().get(i)).objToWorld();
			Square s = ((Square)outlineShape);//.objToWorld();//--------------------------------------------------------------
			DrawingSquare dcc3 = new DrawingSquare(s, g2d, outlineShape.getColor());
			dcc3.drawOutline();
			
			//draw the circle plus the rotation.
			System.out.println("drawing the halo");
			double y = (double) s.getCenter().getY() - s.getSize()/2 - 30;
			Point2D.Double newPoint = new Point2D.Double(s.getCenter().getX()-10, y);
			System.out.println("circle center is " + newPoint.toString());
			c = new Circle(outlineShape.getColor(), newPoint, 20);//color center radius
			DrawingCircle dccsq = new DrawingCircle(c, g2d, outlineShape.getColor());
			dccsq.drawOutline();
			break;
			
		case ELLIPSE:
			//affine transform for showing this modified shape
			//System.out.println("Draw ellipse outline");
			DrawingEllipse dcc4 = new DrawingEllipse((Ellipse)outlineShape, g2d, outlineShape.getColor());
			dcc4.drawOutline();
			
			//halo
			break;
		}
		
		
	}
	
	//allows controller to force a refreshView
	public void forceRefreshView()
	{
		//System.out.println("in forceRefresh");
		refreshView(this.g2d);	
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

	//for drawing
	public void objToWorld(cs355.model.drawing.Shape s)
	{
		//do inverse transform
		AffineTransform worldToObj = new AffineTransform();
		worldToObj.translate(s.getCenter().getX(), s.getCenter().getY());
		worldToObj.rotate(s.getRotation());
		worldToObj.translate(-s.getCenter().getX(), -s.getCenter().getY());
		Point2D.Double obCoord = new Point2D.Double();
		worldToObj.transform(s.getCenter(), obCoord);
		System.out.println("new center is " + obCoord);
		s.setCenter(obCoord);
		return;// output;
	}

//	public void translate(Point2D.Double firstPoint, Point2D.Double secondPoint)
//	{
//		//System.out.println("in square translate");
//		double xdiff = secondPoint.getX() - firstPoint.getX();
//		double ydiff = secondPoint.getY() - firstPoint.getY();
//		Point2D.Double newCenter = new Point2D.Double(s.getCenter().getX() + xdiff, s.getCenter().getY() + ydiff);
//		s.setCenter(newCenter);
//	}
}
