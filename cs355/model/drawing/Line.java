package cs355.model.drawing;

import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

/**
 * Add your line code here. You can add fields, but you cannot
 * change the ones that already exist. This includes the names!
 */
public class Line extends Shape {

	// The ending point of the line.
	private Point2D.Double end;
	
	//my variables
	private Point2D.Double start;

	/**
	 * Basic constructor that sets all fields.
	 * @param color the color for the new shape.
	 * @param start the starting point.
	 * @param end the ending point.
	 */
	public Line(Color color, Point2D.Double start, Point2D.Double end) {

		// Initialize the superclass.
		super(color, start);

		// Set the field.
		this.start = start;
		this.end = end;
		
		center = start;
	}

	/**
	 * Getter for this Line's ending point.
	 * @return the ending point as a Java point.
	 */
	public Point2D.Double getEnd() {
		return end;
	}

	/**
	 * Setter for this Line's ending point.
	 * @param end the new ending point for the Line.
	 */
	public void setEnd(Point2D.Double end) {
		this.end = end;
	}

	/**
	 * Add your code to do an intersection test
	 * here. You <i>will</i> need the tolerance.
	 * @param pt = the point to test against.
	 * @param tolerance = the allowable tolerance.
	 * @return true if pt is in the shape,
	 *		   false otherwise.
	 */
	@Override
	public boolean pointInShape(Point2D.Double pt, double tolerance) {
		
		//get the transformed points for center and for this point-------------------------------------------------------
		Point2D.Double newpt = worldToObj(pt);
		Point2D.Double newstart = worldToObj(start);
		Point2D.Double newend = worldToObj(end);
		
		//throw new UnsupportedOperationException("Not supported yet.");
		double top = Math.abs((newend.getX() - newstart.getX()) * (newstart.getY() - newpt.getY()) - (newstart.getX() - newpt.getX()) * (newend.getY() - newstart.getY()));
		double bottom = Math.sqrt(Math.pow(newend.getX() - newstart.getX(), 2) + Math.pow(newend.getY() - newstart.getY(), 2));
		//System.out.println(top + " " + end + " " + top/bottom);
		if((top / bottom) <= tolerance && ((newend.getX() - newstart.getX())*(newpt.getX() - newstart.getX()) + (newend.getY() - newend.getX())*(pt.getY() - newstart.getY()) >= 0)
				&& ((newstart.getX() - newend.getX())*(newpt.getX() - newend.getX()) + (newstart.getY() - newstart.getX())*(pt.getY() - newend.getY()) >= 0)) 
		{
			//System.out.println(top + " " + bottom + " " + top/bottom);
			return true;
		}
		else return false;

//		 double slope = (newend.y-newstart.y)/(newend.x-newstart.x);
//         double y = slope * newpt.x + newstart.y;
//
//         if((y <= newpt.y + tolerance && y >= newpt.y-tolerance) && (newpt.x >= newstart.x && newpt.x <= newend.x)) {
//             return true;
//         }
//         return false;
		
	}
	
	//my functions--------------------------------------------------------------------
	public Point2D.Double getStart() {
		return this.start;
	}
	
	public void setStart(Point2D.Double pt) {
		 this.start = pt;
	}

	public Point2D.Double worldToObj(Point2D.Double pt)
	{
		//do inverse transform
		AffineTransform worldToObj = new AffineTransform();
		//worldToObj.rotate(-this.rotation);
		worldToObj.translate(-start.getX(), -start.getY());
		Point2D.Double obCoord = new Point2D.Double();
		worldToObj.transform(pt, obCoord);
		//System.out.println(obCoord);
		return obCoord;
	}
	
	public void translate(Point2D.Double firstPoint, Point2D.Double secondPoint)
	{
		//System.out.println("in line translate");
		double xdiff = secondPoint.getX() - firstPoint.getX();
		double ydiff = secondPoint.getY() - firstPoint.getY();
		Point2D.Double newStart = new Point2D.Double(start.getX() + xdiff, start.getY() + ydiff);
		setStart(newStart);
		Point2D.Double newEnd = new Point2D.Double(end.getX() + xdiff, end.getY() + ydiff);
		setEnd(newEnd);
	}
	
	public Point2D.Double clickedCircle(Point2D.Double pt)
	{
		Point2D.Double newpt = worldToObj(pt);
		Point2D.Double newstart = worldToObj(start);
		Point2D.Double newend = worldToObj(end);
		
		if(Math.sqrt(Math.pow((newpt.getX() - newstart.getX()),2) + Math.pow((newpt.getY() - newstart.getY()),2)) < 10/2) return start;
		if(Math.sqrt(Math.pow((newpt.getX() - newend.getX()),2) + Math.pow((newpt.getY() - newend.getY()),2)) < 10/2) return end;
		else return null;
	}

}
