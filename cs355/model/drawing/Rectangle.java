package cs355.model.drawing;

import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

/**
 * Add your rectangle code here. You can add fields, but you cannot
 * change the ones that already exist. This includes the names!
 */
public class Rectangle extends Shape {

	// The width of this shape.
	private double width;

	// The height of this shape.
	private double height;

	/**
	 * Basic constructor that sets all fields.
	 * @param color the color for the new shape.
	 * @param center the center of the new shape.
	 * @param width the width of the new shape.
	 * @param height the height of the new shape.
	 */
	public Rectangle(Color color, Point2D.Double center, double width, double height) {

		// Initialize the superclass.
		super(color, center);

		// Set fields.
		this.width = width;
		this.height = height;
	}

	/**
	 * Getter for this shape's width.
	 * @return this shape's width as a double.
	 */
	public double getWidth() {
		return width;
	}

	/**
	 * Setter for this shape's width.
	 * @param width the new width.
	 */
	public void setWidth(double width) {
		this.width = width;
	}

	/**
	 * Getter for this shape's height.
	 * @return this shape's height as a double.
	 */
	public double getHeight() {
		return height;
	}

	/**
	 * Setter for this shape's height.
	 * @param height the new height.
	 */
	public void setHeight(double height) {
		this.height = height;
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
		//throw new UnsupportedOperationException("Not supported yet.");
		//get the transformed points for center and for this point-------------------------------------------------------
		Point2D.Double newpt = worldToObj(pt);
		Point2D.Double newcenter = worldToObj(center);
		
		
		if((Math.abs(newpt.getX() - newcenter.getX()) <= width/2) && (Math.abs(newpt.getY() - newcenter.getY()) <= height/2)) return true;
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
		//System.out.println("in rectangle translate");
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
