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

		/* Calculate area of triangle ABC */
		float A = area (newa.x, newa.y, newb.x, newb.y, newc.x, newc.y);

		/* Calculate area of triangle PBC */  
		float A1 = area (newpt.x, newpt.y, newb.x, newb.y, newc.x, newc.y);

		/* Calculate area of triangle PAC */  
		float A2 = area (newa.x, newa.y, newpt.x, newpt.y, newc.x, newc.y);

		/* Calculate area of triangle PAB */   
		float A3 = area (newa.x, newa.y, newb.x, newb.y, newpt.x, newpt.y);

		/* Check if sum of A1, A2 and A3 is same as A */
		return (A == A1 + A2 + A3);

	}

	public float area(double x1, double y1, double x2, double y2, double x3, double y3)
	{
	   return (float) Math.abs((x1*(y2-y3) + x2*(y3-y1)+ x3*(y1-y2))/2.0);
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
		//System.out.println(obCoord);
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
		Point2D.Double newpt = worldToObj(pt);
		Point2D.Double newcenter = worldToObj(center);
		Point2D.Double newa = worldToObj(a);
		Point2D.Double newb = worldToObj(b);
		Point2D.Double newc = worldToObj(c);
		
		
		//draw the circle plus the rotation.
		//System.out.println("in triangle clicked circle");
		//double yt = (double) t.getCenter().getY() - t.getHeight()/2 - 30;
		double minY1 = Math.min(newa.getY(), newb.getY());
		double realMin = Math.min(minY1, newc.getY());
		
		//Point2D.Double newPointt = new Point2D.Double(newcenter.getX()-10, realMin - 30);
		//System.out.println("circle center is " + newPointt.toString());
		
		//draw the circle plus the rotation.
		//System.out.println("drawing the halo");
		double y = (double) realMin - 30;
		Point2D.Double circleCenter = new Point2D.Double(newcenter.getX()-10, y);
		if((Math.abs(newpt.getX() - circleCenter.getX()) <= 40) && (Math.abs(newpt.getY() - circleCenter.getY()) <= 40)) return center;
		return null;
	}
}
