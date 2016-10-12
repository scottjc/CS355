package cs355.model.drawing;

import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

/**
 * Add your square code here. You can add fields, but you cannot
 * change the ones that already exist. This includes the names!
 */
public class Square extends Shape {

	// The size of this Square.
	private double size;

	/**
	 * Basic constructor that sets all fields.
	 * @param color the color for the new shape.
	 * @param center the center of the new shape.
	 * @param size the size of the new shape.
	 */
	public Square(Color color, Point2D.Double center, double size) {

		// Initialize the superclass.
		super(color, center);

		// Set the field.
		this.size = size;
		//System.out.println("ssss" + center);
	}

	/**
	 * Getter for this Square's size.
	 * @return the size as a double.
	 */
	public double getSize() {
		return size;
	}

	/**
	 * Setter for this Square's size.
	 * @param size the new size.
	 */
	public void setSize(double size) {
		this.size = size;
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
		//get the transformed points for center and for this point
		Point2D.Double newpt = worldToObj(pt);
		Point2D.Double newcenter = worldToObj(center);
		
		//throw new UnsupportedOperationException("Not supported yet.");
		if((Math.abs(newpt.getX() - newcenter.getX()) <= size/2) && (Math.abs(newpt.getY() - newcenter.getY()) <= size/2)) return true;
		else return false;
	}
	
	//my functions------------------------------------------------------------------
	public Point2D.Double worldToObj(Point2D.Double pt)
	{
		//put into a matrix
		//System.out.println("In wto Square");
		AffineTransform transAndRot = new AffineTransform(Math.cos(this.rotation),Math.sin(-this.rotation),Math.sin(this.rotation),Math.cos(this.rotation),-center.getX(),-center.getY());//the first 6 00,10,01,11,02,12
		//AffineTransform concat = translation.concatenate(T);//correct order?
		Point2D.Double obCoord = new Point2D.Double();
		transAndRot.transform(pt, obCoord);
		return obCoord;
	}
	
	public void translate(Point2D.Double firstPoint, Point2D.Double secondPoint)
	{
		//System.out.println("in square translate");
		double xdiff = secondPoint.getX() - firstPoint.getX();
		double ydiff = secondPoint.getY() - firstPoint.getY();
		Point2D.Double newCenter = new Point2D.Double(center.getX() + xdiff, center.getY() + ydiff);
		setCenter(newCenter);
	}
	
	public Point2D.Double clickedCircle(Point2D.Double pt, double viewScale)
	{	
		Point2D.Double newpt = worldToObj(pt);
		Point2D.Double newcenter = worldToObj(center);
		
		//draw the circle plus the rotation.
		//System.out.println("drawing the halo");
		double y = (double) newcenter.getY() - getSize()/2 - 30;
		Point2D.Double circleCenter = new Point2D.Double(newcenter.getX()-10, y);
		//System.out.println("newpt is " + newpt.toString());
		//System.out.println("circleCenter is " + circleCenter.toString());
		if((Math.abs(newpt.getX() - circleCenter.getX()) <= 40) && (Math.abs(newpt.getY() - circleCenter.getY()) <= 40)) return center;
		return null;
	}
}
