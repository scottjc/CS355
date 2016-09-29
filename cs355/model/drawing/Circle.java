package cs355.model.drawing;

import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

/**
 * Add your circle code here. You can add fields, but you cannot
 * change the ones that already exist. This includes the names!
 */
public class Circle extends Shape {

	// The radius.
	private double radius;

	/**
	 * Basic constructor that sets all fields.
	 * @param color the color for the new shape.
	 * @param center the center of the new shape.
	 * @param radius the radius of the new shape.
	 */
	public Circle(Color color, Point2D.Double center, double radius) {

		// Initialize the superclass.
		super(color, new Point2D.Double((double) (center.getX() + radius/2) ,(double) (center.getY() + radius/2)));

		this.center = new Point2D.Double((double) (center.getX() + radius/2) ,(double) (center.getY() + radius/2));
		
		// Set the field.
		this.radius = radius;
	}

	/**
	 * Getter for this Circle's radius.
	 * @return the radius of this Circle as a double.
	 */
	public double getRadius() {
		return radius;
	}

	/**
	 * Setter for this Circle's radius.
	 * @param radius the new radius of this Circle.
	 */
	public void setRadius(double radius) {
		this.radius = radius;
	}

	/**
	 * Add your code to do an intersection test
	 * here. You shouldn't need the tolerance.
	 * @param pt = the point to test against.
	 * @param tolerance = the allowable tolerance.
	 * @return true if pt is in the shape,
	 *		   false otherwise.
	 */
	@Override
	public boolean pointInShape(Point2D.Double pt, double tolerance) {
		//get the transformed points for center and for this point-------------------------------------------------------
		Point2D.Double newpt = worldToObj(pt);
		Point2D.Double newcenter = worldToObj(center);
		
		//throw new UnsupportedOperationException("Not supported yet.");
		if(Math.sqrt(Math.pow((newpt.getX() - newcenter.getX()),2) + Math.pow((newpt.getY() - newcenter.getY()),2)) < radius/2) return true;
		else return false;
	}
	
	//my functions------------------------------------------------------------------
	public Point2D.Double worldToObj(Point2D.Double pt)
	{
		//do inverse transform
		AffineTransform worldToObj = new AffineTransform();
		worldToObj.rotate(-this.rotation);
		worldToObj.translate(-center.getX(), -center.getY());
		Point2D.Double obCoord = new Point2D.Double();
		worldToObj.transform(pt, obCoord);
		//System.out.println(obCoord);
		return obCoord;
	}

	public void translate(Point2D.Double firstPoint, Point2D.Double secondPoint)
	{
		//System.out.println("in circle translate");
		double xdiff = secondPoint.getX() - firstPoint.getX();
		double ydiff = secondPoint.getY() - firstPoint.getY();
		Point2D.Double newCenter = new Point2D.Double(center.getX() + xdiff, center.getY() + ydiff);
		setCenter(newCenter);
	}
	
	public Point2D.Double clickedCircle(Point2D.Double pt)
	{
//		Point2D.Double newpt = worldToObj(pt);
//		Point2D.Double newstart = worldToObj(start);
//		Point2D.Double newend = worldToObj(end);
//		
//		if(Math.sqrt(Math.pow((newpt.getX() - newstart.getX()),2) + Math.pow((newpt.getY() - newstart.getY()),2)) < 10/2) return start;
//		if(Math.sqrt(Math.pow((newpt.getX() - newend.getX()),2) + Math.pow((newpt.getY() - newend.getY()),2)) < 10/2) return end;
//		else return null;
		return null;
	}
}
