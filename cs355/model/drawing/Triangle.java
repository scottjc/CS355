package cs355.model.drawing;

import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

/**
 * Add your triangle code here. You can add fields, but you cannot
 * change the ones that already exist. This includes the names!
 */
public class Triangle extends Shape {

	// The three points of the triangle.
	private Point2D.Double a;
	private Point2D.Double b;
	private Point2D.Double c;

	/**
	 * Basic constructor that sets all fields.
	 * @param color the color for the new shape.
	 * @param center the center of the new shape.
	 * @param a the first point, relative to the center.
	 * @param b the second point, relative to the center.
	 * @param c the third point, relative to the center.
	 */
	public Triangle(Color color, Point2D.Double center, Point2D.Double a,
					Point2D.Double b, Point2D.Double c)
	{

		// Initialize the superclass.
		super(color, center);

		// Set fields.
		this.a = a;
		this.b = b;
		this.c = c;
	}

	/**
	 * Getter for the first point.
	 * @return the first point as a Java point.
	 */
	public Point2D.Double getA() {
		return a;
	}

	/**
	 * Setter for the first point.
	 * @param a the new first point.
	 */
	public void setA(Point2D.Double a) {
		this.a = a;
	}

	/**
	 * Getter for the second point.
	 * @return the second point as a Java point.
	 */
	public Point2D.Double getB() {
		return b;
	}

	/**
	 * Setter for the second point.
	 * @param b the new second point.
	 */
	public void setB(Point2D.Double b) {
		this.b = b;
	}

	/**
	 * Getter for the third point.
	 * @return the third point as a Java point.
	 */
	public Point2D.Double getC() {
		return c;
	}

	/**
	 * Setter for the third point.
	 * @param c the new third point.
	 */
	public void setC(Point2D.Double c) {
		this.c = c;
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
		
		//get the transformed points for center and for this point-------------------------------------------------------------------------
		Point2D.Double newpt = worldToObj(pt);
		Point2D.Double newa = worldToObj(a);
		Point2D.Double newb = worldToObj(b);
		Point2D.Double newc = worldToObj(c);
		
		//throw new UnsupportedOperationException("Not supported yet.");
		Point2D.Double first = new Point2D.Double(newpt.getX() - newa.getX(), newpt.getY() - newa.getY());
		Point2D.Double firstTrans = perpendicular(new Point2D.Double(newb.getX() - newa.getX(), newb.getY() - newa.getY()));
		double firstNum = first.getX() * firstTrans.getX() + first.getY() * firstTrans.getY();
		//System.out.println("first num is " + firstNum);
		
		Point2D.Double second = new Point2D.Double(newpt.getX() - newb.getX(), newpt.getY() - newb.getY());
		Point2D.Double secondTrans = perpendicular(new Point2D.Double(newc.getX() - newb.getX(), newc.getY() - newb.getY()));
		double secondNum = second.getX() * secondTrans.getX() + second.getY() * secondTrans.getY();
		//System.out.println("second num is " + secondNum);
		
		Point2D.Double third = new Point2D.Double(newpt.getX() - newc.getX(), newpt.getY() - newc.getY());
		Point2D.Double thirdTrans = perpendicular(new Point2D.Double(newa.getX() - newc.getX(), newa.getY() - newc.getY()));
		double thirdNum = third.getX() * thirdTrans.getX() + third.getY() * thirdTrans.getY();
		//System.out.println("third num is " + thirdNum);
		
		if(firstNum > 0 && secondNum > 0 && thirdNum > 0) return true;
		else return false;
		
	}
	
	//my functions--------------------------------------------------------------------------------------------------------------
	public Point2D.Double worldToObj(Point2D.Double pt)
	{
		//do inverse transform
		AffineTransform worldToObj = new AffineTransform();
		worldToObj.rotate(-this.rotation);
		worldToObj.translate(-center.getX(), -center.getY());
		Point2D.Double obCoord = new Point2D.Double();
		worldToObj.transform(pt, obCoord);
		System.out.println(obCoord);
		return obCoord;
	}
	
	public Point2D.Double perpendicular(Point2D.Double pt)
	{
		double xx = pt.getX();
		double yy = pt.getY();
		yy = (yy * -1);
		return new Point2D.Double(yy, xx);
	}
	
	public Point2D.Double getCenter()
	{
		double centerX = (a.x + b.x + c.x) / 3;
		double centerY = (a.y + b.y + c.y) / 3;
		return new Point2D.Double(centerX, centerY);
	}

	public void translate(Point2D.Double firstPoint, Point2D.Double secondPoint)
	{
		//System.out.println("in triangle translate");
		double xdiff = secondPoint.getX() - firstPoint.getX();
		double ydiff = secondPoint.getY() - firstPoint.getY();
		Point2D.Double newa = new Point2D.Double(a.getX() + xdiff, a.getY() + ydiff);
		setA(newa);
		Point2D.Double newb = new Point2D.Double(b.getX() + xdiff, b.getY() + ydiff);
		setB(newb);
		Point2D.Double newc = new Point2D.Double(c.getX() + xdiff, c.getY() + ydiff);
		setC(newc);
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
