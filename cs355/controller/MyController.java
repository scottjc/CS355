package cs355.controller;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.io.File;
import java.util.Iterator;
import cs355.GUIFunctions;
import cs355.model.drawing.*;

import cs355.model.drawing.MyModel;
import cs355.view.MyView;

public class MyController implements CS355Controller {
	
	//Modes
	//more stuff!!!!!!!!
	MyModel mm;
	MyView mv;
	
	//buttons!
	public enum Button
	{
		NONE, SHAPE, SELECT, ZOOMIN, ZOOMOUT
	}
	public enum Shape
	{
		NONE, LINE, SQUARE, RECTANGLE,
		CIRCLE, ELLIPSE, TRIANGLE
	}
	public enum State
	{
		FIRSTCLICK, SECONDCLICK, THIRDCLICK, WAITING
	}
	public Button currButton;
	public cs355.model.drawing.Shape currDrawingShape; 
	public Shape currShape;
	public State currState;
	Point2D.Double firstPoint = new Point2D.Double();
	Point2D.Double secondPoint = new Point2D.Double();
	Point2D.Double thirdPoint = new Point2D.Double();
	Boolean weSelected;
	Boolean rotating;
	Boolean clickedStart;
	
	public MyController()
	{
		currShape = Shape.NONE;
		currState = State.WAITING;
		currButton = Button.NONE;
		//weDragging = false;
		currDrawingShape = null;
		weSelected = false;
		rotating = false;
		clickedStart = false;
	}
	
	public void setModel(MyModel m)
	{
		mm = m;
	}
	
	public void setView(MyView v)
	{
		mv = v;
	}

	// Color.
	
	/**
	 * Called when the user hits the color button.
	 * @param c = the new <i>drawing</i> color.
	 */
	public void colorButtonHit(Color c)
	{
		//System.out.println(c);
		if(c != null)
		{
			mm.currColor = c;
			GUIFunctions.changeSelectedColor(c); 
			if(currDrawingShape != null)
			{
				currDrawingShape.setColor(c);
				//so we can see the changes and stuff.
				mm.forceCalls();
			}
		}
		GUIFunctions.refresh();
	}

	// Shapes.

	/**
	 * Called when the user hits the line button.
	 */
	public void lineButtonHit()
	{
		//System.out.println("Line Button");
		currShape = Shape.LINE;
		reset();
		currButton = Button.SHAPE;
		GUIFunctions.refresh();
		mm.forceCalls();
	}

	/**
	 * Called when the user hits the square button.
	 */
	public void squareButtonHit()
	{
		//System.out.println("Square Button");
		currShape = Shape.SQUARE;
		reset();
		currButton = Button.SHAPE;
		GUIFunctions.refresh();
		mm.forceCalls();
	}

	/**
	 * Called when the user hits the rectangle button.
	 */
	public void rectangleButtonHit()
	{
		//System.out.println("rectangle Button");
		currShape = Shape.RECTANGLE;
		reset();
		currButton = Button.SHAPE;
		GUIFunctions.refresh();
		mm.forceCalls();
	}

	/**
	 * Called when the user hits the circle button.
	 */
	public void circleButtonHit()
	{
		//System.out.println("circle Button");
		currShape = Shape.CIRCLE;
		reset();
		currButton = Button.SHAPE;
		GUIFunctions.refresh();
		mm.forceCalls();
	}

	/**
	 * Called when the user hits the ellipse button.
	 */
	public void ellipseButtonHit()
	{
		//System.out.println("ellipse Button");
		currShape = Shape.ELLIPSE;
		reset();
		currButton = Button.SHAPE;
		GUIFunctions.refresh();
		mm.forceCalls();
	}

	/**
	 * Called when the user hits the triangle button.
	 */
	public void triangleButtonHit()
	{
		//System.out.println("triangle Button");
		currShape = Shape.TRIANGLE;
		reset();
		currButton = Button.SHAPE;
		GUIFunctions.refresh();
		mm.forceCalls();
	}

	/**
	 * Called when the user hits the select button.
	 */
	public void selectButtonHit()
	{
		currShape = Shape.NONE;
		currButton = Button.SELECT;
		reset();
		GUIFunctions.refresh();
		mm.forceCalls();
		
	}

	// Zooming.

	/**
	 * Called when the user hits the zoom in button.
	 */
	public void zoomInButtonHit()
	{
		currShape = Shape.NONE;
		currButton = Button.ZOOMIN;
		reset();
	}

	/**
	 * Called when the user hits the zoom out button.
	 */
	public void zoomOutButtonHit()
	{
		currShape = Shape.NONE;
		currButton = Button.ZOOMOUT;
		reset();
	}

	/**
	 * Called when the horizontal scrollbar position changes.
	 * @param value = the new position.
	 */
	public void hScrollbarChanged(int value)
	{
		
	}

	/**
	 * Called when the vertical scrollbar position changes.
	 * @param value = the new position.
	 */
	public void vScrollbarChanged(int value)
	{
		
	}

	// 3D Model.

	/**
	 * Called to load a scene from a file.
	 * @param file = the file containing the scene to load.
	 */
	public void openScene(File file)
	{
		
	}

	/**
	 * Called to toggle the 3D OpenGL display.
	 */
	public void toggle3DModelDisplay()
	{
		
	}

	/**
	 * Called when the user presses keys. This is used
	 * for navigating in the 3D world.
	 * @param iterator = the iterator over the keys.
	 */
	public void keyPressed(Iterator<Integer> iterator)
	{
		
	}

	// Image.

	/**
	 * Called to load a background image.
	 * @param file = the image file to load.
	 */
	public void openImage(File file)
	{
		//last lab
	}

	/**
	 * Called to save the background image.
	 * @param file = the file to save the image to.
	 */
	public void saveImage(File file)
	{
		//last lab
	}

	/**
	 * Called to toggle the background image display.
	 */
	public void toggleBackgroundDisplay()
	{
		
	}

	// File menu.

	/**
	 * Called to save a drawing.
	 * @param file = the file to save the drawing to.
	 */
	public void saveDrawing(File file)
	{
		mm.save(file);
	}

	/**
	 * Called to open a drawing.
	 * @param file = the file to open the drawing from.
	 */
	public void openDrawing(File file)
	{
		mm.open(file);
	}

	// Edit menu.

	/**
	 * Called to delete the currently selected shape.
	 */
	public void doDeleteShape()
	{
		//System.out.println("Delete?");
		if(currDrawingShape != null)
		{
			mm.deleteShape(mm.getIndexbyShape(currDrawingShape));
			currDrawingShape = null;
			currShape = Shape.NONE;
			currButton = Button.SELECT;
			reset();
			GUIFunctions.refresh();
			mm.forceCalls();
		}
	}

	// Image menu.

	/**
	 * Called to perform edge detection
	 * on the background image.
	 */
	public void doEdgeDetection()
	{
		
	}

	/**
	 * Called to perform sharpen
	 * on the background image.
	 */
	public void doSharpen()
	{
		
	}

	/**
	 * Called to perform median blur
	 * on the background image.
	 */
	public void doMedianBlur()
	{
		
	}

	/**
	 * Called to perform uniform blur
	 * on the background image.
	 */
	public void doUniformBlur()
	{
		
	}

	/**
	 * Called to change the background image to grayscale.
	 */
	public void doGrayscale()
	{
		
	}

	/**
	 * Called to change the contrast on the background image.
	 * @param contrastAmountNum = how much contrast to add.
	 */
	public void doChangeContrast(int contrastAmountNum)
	{
		
	}

	/**
	 * Called to change the brightness on the background image.
	 * @param brightnessAmountNum = how much brightness to add.
	 */
	public void doChangeBrightness(int brightnessAmountNum)
	{
		
	}

	// Object menu.

	/**
	 * Called to move the currently selected
	 * shape one slot forward.
	 */
	public void doMoveForward()
	{
		//System.out.println("doMoveForward?");
		if(currDrawingShape != null)
		{
			cs355.model.drawing.Shape temp = currDrawingShape;
			mm.moveForward(mm.getIndexbyShape(currDrawingShape));
			currDrawingShape = temp;
			mv.set_currShapeIndex((mm.getIndexbyShape(currDrawingShape)));
			GUIFunctions.refresh();
			mm.forceCalls();
		}
	}

	/**
	 * Called to move the currently selected
	 * shape one slot backward.
	 */
	public void doMoveBackward()
	{
		//System.out.println("doMoveBackward?");
		if(currDrawingShape != null)
		{
			cs355.model.drawing.Shape temp = currDrawingShape;
			mm.moveBackward(mm.getIndexbyShape(currDrawingShape));
			currDrawingShape = temp;
			mv.set_currShapeIndex((mm.getIndexbyShape(currDrawingShape)));
			GUIFunctions.refresh();
			mm.forceCalls();
		}
	}

	/**
	 * Called to move the currently
	 * selected shape to the front.
	 */
	public void doSendToFront()
	{
		//System.out.println("doMoveForward?");
		if(currDrawingShape != null)
		{
			cs355.model.drawing.Shape temp = currDrawingShape;
			mm.moveToFront(mm.getIndexbyShape(currDrawingShape));
			currDrawingShape = temp;
			mv.set_currShapeIndex((mm.getIndexbyShape(currDrawingShape)));
			GUIFunctions.refresh();
			mm.forceCalls();
		}
	}

	/**
	 * Called to move the currently
	 * selected shape to the back.
	 */
	public void doSendtoBack()
	{
		//System.out.println("doSendToBack?");
		if(currDrawingShape != null)
		{
			cs355.model.drawing.Shape temp = currDrawingShape;
			mm.movetoBack(mm.getIndexbyShape(currDrawingShape));
			currDrawingShape = temp;
			mv.set_currShapeIndex((mm.getIndexbyShape(currDrawingShape)));
			GUIFunctions.refresh();
			mm.forceCalls();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
		//increment click
		//System.out.println("incrementing");
		switch(currState)
		{
			case WAITING:
				currState = State.FIRSTCLICK;
				break;
				
			case FIRSTCLICK:
				currState = State.SECONDCLICK;
				break;
				
			case SECONDCLICK:
				currState = State.THIRDCLICK;
				break;
		}
			
		//condition of state
		//LINE, SQUARE, RECTANGLE,
		//CIRCLE, ELLIPSE, TRIANGLE
		//System.out.println("State decision");
		switch(currShape)
		{
			case LINE: 						
				break;
				
			case SQUARE:
				break;
				
			case RECTANGLE:
				break;
	
			case CIRCLE:
				break;
				
			case ELLIPSE:
				break;
				
			case TRIANGLE:
				switch(currState)
				{
					case FIRSTCLICK:
						//System.out.println("first tri click");
						firstPoint = new Point2D.Double(e.getX(), e.getY());
						break;
						
					case SECONDCLICK:
						//System.out.println("second tri click");
						secondPoint = new Point2D.Double(e.getX(), e.getY());
						break;
						
					case THIRDCLICK:
						//System.out.println("third tri click");
						//get er drawn
						thirdPoint = new Point2D.Double(e.getX(), e.getY());
						
						//add center point
						double cx = (firstPoint.getX() + secondPoint.getX() + thirdPoint.getX())/3;
						double cy = (firstPoint.getY() + secondPoint.getY() + thirdPoint.getY())/3;
								
						Point2D.Double centerPoint = new Point2D.Double(cx, cy);
						Triangle currTri = new Triangle(mm.currColor, centerPoint, firstPoint, secondPoint, thirdPoint);
						mm.addShape(currTri);
						reset();
						break;
				}
				break;
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub	
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub	
	}

	@Override
	public void mousePressed(MouseEvent e) {
		//System.out.println("pressed State decision");

		switch(currShape)
		{
		case LINE: 

			currState = State.FIRSTCLICK;

			//System.out.println("first click line");
			firstPoint = new Point2D.Double(e.getX(), e.getY());
		
			Line currLine = new Line(mm.currColor, firstPoint, firstPoint);
			mm.addShape(currLine);

			break;

		case SQUARE:

			currState = State.FIRSTCLICK;

			//System.out.println("first click square");
			firstPoint = new Point2D.Double(e.getX(), e.getY());
			
			Square currSquare = new Square(mm.currColor, firstPoint, 3);
			mm.addShape(currSquare);
			break;

		case RECTANGLE:

			currState = State.FIRSTCLICK;

			//System.out.println("first click rect");
			firstPoint = new Point2D.Double(e.getX(), e.getY());
			Rectangle currRec = new Rectangle(mm.currColor, firstPoint, 
					(firstPoint.getX() - firstPoint.getX()), 
					(firstPoint.getY() - firstPoint.getY()));
			mm.addShape(currRec);
			break;

		case CIRCLE:

			currState = State.FIRSTCLICK;

			//System.out.println("first click circle");
			firstPoint = new Point2D.Double(e.getX(), e.getY());
			Circle currCircle = new Circle(mm.currColor,firstPoint, 0);
			mm.addShape(currCircle);
			break;

		case ELLIPSE:

			currState = State.FIRSTCLICK;

			//System.out.println("first click ellipse");
			firstPoint = new Point2D.Double(e.getX(), e.getY());
			Ellipse currEllipse = new Ellipse(mm.currColor, firstPoint, 
					firstPoint.getX() - firstPoint.getX(),
					firstPoint.getY() - firstPoint.getY());
			mm.addShape(currEllipse);
			break;

		case TRIANGLE:
			break;	
		}
		
		switch(currButton)//we be clickin!
		{
		case SELECT:
			
			if(weSelected)
			{
				rotating = false;
				//System.out.println("we selected");
				firstPoint = new Point2D.Double(e.getX(), e.getY());
				cs355.model.drawing.Shape tempClick = mm.clicked(firstPoint, 4);
				if(currDrawingShape == null)
				{
					//System.out.println("must have deleted the shape");
					mv.set_currShapeIndex(-1);
					currDrawingShape = null;
					GUIFunctions.refresh();
					mm.forceCalls();
					weSelected = false;
					rotating = false;
					break;
				}
				Point2D.Double pt = currDrawingShape.clickedCircle(firstPoint);
				if(weSelected && pt != null)
				{
					//System.out.println("WESELECTED clicked in the circle");
					rotating = true;
					if(currDrawingShape instanceof Line)//lines have two, the others have 1
					{
						//System.out.println("WESLECTED pressed it's line circle");
						//distinguish between top or bottom
						Line l = (Line) currDrawingShape;
						if(pt == l.getStart()) clickedStart = true;
						firstPoint = l.getStart();
						secondPoint = l.getEnd();

					}
					else if(currDrawingShape instanceof Circle){}//nothing for circle
					else//all other shapes
					{
						//System.out.println("NOW WE GET TO PROCESS THE ROTATION OF THE OBJECT!!!");
						rotating = true;
					}
				}
				else if(tempClick != null)
				{
					//firstPoint = new Point2D.Double(e.getX(), e.getY());
					//function to see if we clicked on the rotation circle/ start or endpoint
					//System.out.println("#righthere");
					currDrawingShape = tempClick;
					mm.setShape(mm.getIndexbyShape(tempClick), currDrawingShape);
					mv.set_currShapeIndex(mm.getIndexbyShape(currDrawingShape));
					
					Point2D.Double ptt = currDrawingShape.clickedCircle(firstPoint);
					if(weSelected && pt != null)
					{
						//System.out.println("WESELECT clicked in the circle");
						rotating = true;
						if(currDrawingShape instanceof Line)//lines have two, the others have 1
						{
							//System.out.println("WESELECT pressed it's line circle");
							//distinguish between top or bottom
							Line l = (Line) currDrawingShape;
							if(ptt == l.getStart()) clickedStart = true;
							firstPoint = l.getStart();
							secondPoint = l.getEnd();
						}
					}


					//draw around it.
					mv.set_currShapeIndex(mm.getIndexbyShape(currDrawingShape));
					//currDrawingShape.setRotation(Math.PI/3);
					//System.out.println("WESELECT Got a shape " + mm.getIndexbyShape(currDrawingShape));
					GUIFunctions.changeSelectedColor(currDrawingShape.getColor());
					mm.currColor = currDrawingShape.getColor(); 
					GUIFunctions.refresh();
					mm.forceCalls();

					weSelected = true;
				}
				else
				{
					mv.set_currShapeIndex(-1);
					currDrawingShape = null;
					GUIFunctions.refresh();
					mm.forceCalls();
					weSelected = false;
					rotating = false;
				}

			}
			else
			{
				currDrawingShape = mm.clicked(new Point2D.Double(e.getX(), e.getY()), 4);
				if(currDrawingShape != null)
				{
					firstPoint = new Point2D.Double(e.getX(), e.getY());
					//function to see if we clicked on the rotation circle/ start or endpoint
					Point2D.Double pt = currDrawingShape.clickedCircle(firstPoint);
					if(weSelected && pt != null)
					{
						//System.out.println("clicked in the circle");
						rotating = true;
						if(currDrawingShape instanceof Line)//lines have two, the others have 1
						{
							//System.out.println("pressed it's line circle");
							//distinguish between top or bottom
							Line l = (Line) currDrawingShape;
							if(pt == l.getStart()) clickedStart = true;
							firstPoint = l.getStart();
							secondPoint = l.getEnd();

						}
					}
					//draw around it.
					mv.set_currShapeIndex(mm.getIndexbyShape(currDrawingShape));
					//currDrawingShape.setRotation(Math.PI/3);
					//System.out.println("Got a shape " + mm.getIndexbyShape(currDrawingShape));
					GUIFunctions.changeSelectedColor(currDrawingShape.getColor());
					mm.currColor = currDrawingShape.getColor(); 
					GUIFunctions.refresh();
					mm.forceCalls();

					weSelected = true;
				}
				else
				{
					mv.set_currShapeIndex(-1);
					GUIFunctions.refresh();
					mm.forceCalls();
					weSelected = false;
					rotating = false;
					currDrawingShape = null;
				}
			}
			break;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
		switch(currShape)
		{
		case LINE: 
			if(currState == State.FIRSTCLICK)
			{
				//System.out.println("released the line click");
				//get er drawn
				secondPoint = new Point2D.Double(e.getX(), e.getY());
				Line currLine = new Line(mm.currColor, firstPoint, secondPoint);
				mm.setShape(0, currLine);
				GUIFunctions.refresh();
				//mm.addShape(currLine);
				reset();
			}
			break;

		case SQUARE:
			
			if(currState == State.FIRSTCLICK)
			{
				//System.out.println("released the square click");
				//get er drawn
				secondPoint = new Point2D.Double(e.getX(), e.getY());

				double height = firstPoint.getY() - secondPoint.getY();
				double width = firstPoint.getX() - secondPoint.getX();
				double length = Math.min(Math.abs(height), Math.abs(width));

				//((Square) selectedShape).setLength(length);

				if (width < 0) 
				{
					if (height < 0) 
					{
						//System.out.println("Square use orig firstPoint");
						//center point
						double cx = firstPoint.getX() + length/2;
						double cy = firstPoint.getY() + length/2;
						Point2D.Double centerPoint = new Point2D.Double(cx, cy);
						
						Square currSquare = new Square(mm.currColor, centerPoint, length);
						mm.setShape(0, currSquare);
						GUIFunctions.refresh();
						//mm.addShape(currLine);
					} 
					else 
					{
						//System.out.println("Square q2");
						Point2D.Double tempPoint = new Point2D.Double(firstPoint.getX(),(firstPoint.getY() - length));
						//center point
						double cx = tempPoint.getX() + length/2;
						double cy = tempPoint.getY() + length/2;
						Point2D.Double centerPoint = new Point2D.Double(cx, cy);
						
						Square currSquare = new Square(mm.currColor, centerPoint, length);
						mm.setShape(0, currSquare);
						GUIFunctions.refresh();
						//mm.addShape(currLine);
					}
				} 
				else 
				{
					if (height < 0) 
					{
						//System.out.println("Square q3");
						Point2D.Double tempPoint = new Point2D.Double(firstPoint.getX() - length, firstPoint.getY());
						//center point
						double cx = tempPoint.getX() + length/2;
						double cy = tempPoint.getY() + length/2;
						Point2D.Double centerPoint = new Point2D.Double(cx, cy);
						
						Square currSquare = new Square(mm.currColor, centerPoint, length);
						mm.setShape(0, currSquare);
						GUIFunctions.refresh();
						//mm.addShape(currLine);
					} 
					else 
					{
						//System.out.println("Square q1");
						Point2D.Double tempPoint = new Point2D.Double(firstPoint.getX() - length, firstPoint.getY() - length);
						//center point
						double cx = tempPoint.getX() + length/2;
						double cy = tempPoint.getY() + length/2;
						Point2D.Double centerPoint = new Point2D.Double(cx, cy);
						
						Square currSquare = new Square(mm.currColor,centerPoint, length);
						mm.setShape(0, currSquare);
						GUIFunctions.refresh();
						//mm.addShape(currLine);
					}
				}
				reset();
			}
			break;

		case RECTANGLE:

			if(currState == State.FIRSTCLICK)
			{
				//System.out.println("release the rectangle click");
				//p2
				secondPoint = new Point2D.Double(e.getX(), e.getY());

				if(firstPoint.getX() < secondPoint.getX() && firstPoint.getY() < secondPoint.getY())
				{
					//TL = p1
					//System.out.println("TL = p1");
					//center point
					double cx = firstPoint.getX() + (secondPoint.getX() - firstPoint.getX())/2;
					double cy = firstPoint.getY() + (secondPoint.getY() - firstPoint.getY())/2;
					Point2D.Double centerPoint = new Point2D.Double(cx, cy);
					
					Rectangle currRec = new Rectangle(mm.currColor, centerPoint, 
							(secondPoint.getX() - firstPoint.getX()), 
							(secondPoint.getY() - firstPoint.getY()));
					mm.setShape(0, currRec);
					GUIFunctions.refresh();
					//mm.addShape(currLine);
				}
				else if(firstPoint.getX() > secondPoint.getX() && firstPoint.getY() > secondPoint.getY())
				{
					//TL = p2
					//System.out.println("TL = p2");
					double cx = secondPoint.getX() + (firstPoint.getX() - secondPoint.getX())/2;
					double cy = secondPoint.getY() + (firstPoint.getY() - secondPoint.getY())/2;
					Point2D.Double centerPoint = new Point2D.Double(cx, cy);
					
					Rectangle currRec = new Rectangle(mm.currColor, centerPoint, 
							(firstPoint.getX() - secondPoint.getX()), 
							(firstPoint.getY() - secondPoint.getY()));
					mm.setShape(0, currRec);
					GUIFunctions.refresh();
					//mm.addShape(currLine);
				}
				else if(firstPoint.getX() < secondPoint.getX() && firstPoint.getY() > secondPoint.getY())
				{
					//TL = p1's x p2's Y
					//System.out.println("TL = p1's x p2's Y");//here
					Point2D.Double tempPoint = new Point2D.Double(firstPoint.getX(), secondPoint.getY());
					//center point
					double cx = tempPoint.getX() + (secondPoint.getX() - firstPoint.getX())/2;
					double cy = tempPoint.getY() + (firstPoint.getY() - secondPoint.getY())/2;
					Point2D.Double centerPoint = new Point2D.Double(cx, cy);
					
					Rectangle currRec = new Rectangle(mm.currColor, centerPoint, 
							Math.abs(firstPoint.getX() - secondPoint.getX()), 
							Math.abs(firstPoint.getY() - secondPoint.getY()));
					mm.setShape(0, currRec);
					GUIFunctions.refresh();
					//mm.addShape(currLine);
				}
				else
				{
					//TL = p1's y p2's x
					//System.out.println("TL = p1's y p2's x default");
					Point2D.Double tempPoint = new Point2D.Double(secondPoint.getX(), firstPoint.getY());
					//center point
					double cx = tempPoint.getX() + (firstPoint.getX() - secondPoint.getX())/2;
					double cy = tempPoint.getY() + (secondPoint.getY() - firstPoint.getY())/2;
					Point2D.Double centerPoint = new Point2D.Double(cx, cy);
					
					Rectangle currRec = new Rectangle(mm.currColor, centerPoint, 
							Math.abs(secondPoint.getX() - firstPoint.getX()), 
							Math.abs(secondPoint.getY() - firstPoint.getY()));
					mm.setShape(0, currRec);
					GUIFunctions.refresh();
					//mm.addShape(currLine);
				}
				reset();
			}
			break;

		case CIRCLE:

			if(currState == State.FIRSTCLICK)
			{
				secondPoint = new Point2D.Double(e.getX(), e.getY());

				double height = firstPoint.getY() - secondPoint.getY();
				double width = firstPoint.getX() - secondPoint.getX();
				double length = Math.min(Math.abs(height), Math.abs(width));

				//((Square) selectedShape).setLength(length);

				if (width < 0) 
				{
					if (height < 0) 
					{
						//((Square) selectedShape).setUpperLeftCorner(origCorner);
						//System.out.println("Circle use orig firstPoint");
						Circle currCircle = new Circle(mm.currColor,firstPoint, length);
						mm.setShape(0, currCircle);
						GUIFunctions.refresh();
						//mm.addShape(currLine);
					} 
					else 
					{
						//System.out.println("Circle q2");
						Point2D.Double tempPoint = new Point2D.Double(firstPoint.getX(),(firstPoint.getY() - length));					
						Circle currCircle = new Circle(mm.currColor, tempPoint, length);
						mm.setShape(0, currCircle);
						GUIFunctions.refresh();
						//mm.addShape(currLine);
					}
				} 
				else 
				{
					if (height < 0) 
					{
						//System.out.println("Circle q3");
						Point2D.Double tempPoint = new Point2D.Double(firstPoint.getX() - length, firstPoint.getY());
						Circle currCircle = new Circle(mm.currColor, tempPoint, length);
						mm.setShape(0, currCircle);
						GUIFunctions.refresh();
						//mm.addShape(currLine);
					} 
					else 
					{
						//System.out.println("Circle q1");
						Point2D.Double tempPoint = new Point2D.Double(firstPoint.getX() - length, firstPoint.getY() - length);
						Circle currCircle = new Circle(mm.currColor, tempPoint, length);
						mm.setShape(0, currCircle);
						GUIFunctions.refresh();
						//mm.addShape(currLine);
					}
				}
				reset();
			}
			break;

		case ELLIPSE:
			if(currState == State.FIRSTCLICK)
			{	
				//System.out.println("release the ellipse click");
				//p2
				secondPoint = new Point2D.Double(e.getX(), e.getY());

				if(firstPoint.getX() < secondPoint.getX() && firstPoint.getY() < secondPoint.getY())
				{
					//TL = p1
					//System.out.println("TL = p1");
					Ellipse currEllipse = new Ellipse(mm.currColor, firstPoint, 
							secondPoint.getX() - firstPoint.getX(),
							secondPoint.getY() - firstPoint.getY());
					mm.setShape(0, currEllipse);
					GUIFunctions.refresh();
					//mm.addShape(currLine);
				}
				else if(firstPoint.getX() > secondPoint.getX() && firstPoint.getY() > secondPoint.getY())
				{
					//TL = p2
					//System.out.println("TL = p2");
					Ellipse currEllipse = new Ellipse(mm.currColor, secondPoint, 
							(firstPoint.getX() - secondPoint.getX()), 
							(firstPoint.getY() - secondPoint.getY()));
					mm.setShape(0, currEllipse);
					GUIFunctions.refresh();
					//mm.addShape(currLine);
				}
				else if(firstPoint.getX() < secondPoint.getX() && firstPoint.getY() > secondPoint.getY())
				{
					//TL = p1's x p2's Y
					//System.out.println("TL = p1's x p2's Y");//here
					Point2D.Double tempPoint = new Point2D.Double(firstPoint.getX(), secondPoint.getY());
					//center point
					double cx = tempPoint.getX() + (firstPoint.getX() - secondPoint.getX())/2;
					double cy = tempPoint.getY() + (firstPoint.getX() - secondPoint.getX())/2;
					Point2D.Double centerPoint = new Point2D.Double(cx, cy);
					
					Ellipse currEllipse = new Ellipse(mm.currColor, tempPoint, 
							Math.abs(firstPoint.getX() - secondPoint.getX()), 
							Math.abs(firstPoint.getY() - secondPoint.getY()));
					mm.setShape(0, currEllipse);
					GUIFunctions.refresh();
					//mm.addShape(currLine);
				}
				else
				{
					//TL = p1's y p2's x
					//System.out.println("TL = p1's y p2's x default");
					Point2D.Double tempPoint = new Point2D.Double(secondPoint.getX(), firstPoint.getY());
					Ellipse currEllipse = new Ellipse(mm.currColor, tempPoint, 
							Math.abs(secondPoint.getX() - firstPoint.getX()), 
							Math.abs(secondPoint.getY() - firstPoint.getY()));
					mm.setShape(0, currEllipse);
					GUIFunctions.refresh();
					//mm.addShape(currLine);
				}
				reset();
			}
			break;

		case TRIANGLE:
			break;
		}
		
		switch(currButton)
		{
			case SELECT:
			{
				//System.out.println("releasing the shape.");// size is " + mm.getShapesListSize());
				//rotating = false;
				clickedStart = false;
				break;
			}
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// animations
		//draw it just as normal, but then you must select it and then erase it while it's still in this mode.
		//System.out.println("dragged State decision");
		switch(currShape)
		{
		case LINE: 
			if(currState == State.FIRSTCLICK)
			{
				//System.out.println("dragged the line click");
				//erase the top shape
				//if(weDragging == true) mm.deleteShape(0);
				//get er drawn
				secondPoint = new Point2D.Double(e.getX(), e.getY());
				Line currLine = new Line(mm.currColor, firstPoint, secondPoint);
				//mm.addShape(currLine);
				//currDrawingShape = currLine;
				mm.setShape(0, currLine);
				GUIFunctions.refresh();
				
				//currentDrawingShape
				//weDragging = true;
			}
			break;

		case SQUARE:

			if(currState == State.FIRSTCLICK)
			{
				//System.out.println("dragged the square click");
	
				//get er drawn
				secondPoint = new Point2D.Double(e.getX(), e.getY());
				double height = firstPoint.getY() - secondPoint.getY();
				double width = firstPoint.getX() - secondPoint.getX();
				double length = Math.min(Math.abs(height), Math.abs(width));

				if (width < 0) 
				{
					if (height < 0) 
					{
						//System.out.println("Square use orig firstPoint");
						//center point
						double cx = firstPoint.getX() + length/2;
						double cy = firstPoint.getY() + length/2;
						Point2D.Double centerPoint = new Point2D.Double(cx, cy);
						
						Square currSquare = new Square(mm.currColor,centerPoint, length);
						mm.setShape(0, currSquare);
						GUIFunctions.refresh();
						//mm.addShape(currLine);
					} 
					else 
					{
						//System.out.println("Square q2");
						Point2D.Double tempPoint = new Point2D.Double(firstPoint.getX(),(firstPoint.getY() - length));
						//center point
						double cx = tempPoint.getX() + length/2;
						double cy = tempPoint.getY() + length/2;
						Point2D.Double centerPoint = new Point2D.Double(cx, cy);
						
						Square currSquare = new Square(mm.currColor, centerPoint, length);
						mm.setShape(0, currSquare);
						GUIFunctions.refresh();
						//mm.addShape(currLine);
					}
				} 
				else 
				{
					if (height < 0) 
					{
						//System.out.println("Square q3");
						Point2D.Double tempPoint = new Point2D.Double(firstPoint.getX() - length, firstPoint.getY());
						//center point
						double cx = tempPoint.getX() + length/2;
						double cy = tempPoint.getY() + length/2;
						Point2D.Double centerPoint = new Point2D.Double(cx, cy);
						
						Square currSquare = new Square(mm.currColor, centerPoint, length);
						mm.setShape(0, currSquare);
						GUIFunctions.refresh();
						//mm.addShape(currLine);
					} 
					else 
					{
						//System.out.println("Square q1");
						Point2D.Double tempPoint = new Point2D.Double(firstPoint.getX() - length, firstPoint.getY() - length);
						//center point
						double cx = tempPoint.getX() + length/2;
						double cy = tempPoint.getY() + length/2;
						Point2D.Double centerPoint = new Point2D.Double(cx, cy);
						
						Square currSquare = new Square(mm.currColor, centerPoint, length);
						mm.setShape(0, currSquare);
						GUIFunctions.refresh();
						//mm.addShape(currLine);
					}
				}
				//weDragging = true;
			}
			break;

		case RECTANGLE:

			if(currState == State.FIRSTCLICK)
			{
				////System.out.println("release the rectangle click");
				//p2
				secondPoint = new Point2D.Double(e.getX(), e.getY());
				//erase the top shape
				//if(weDragging == true) mm.deleteShape(0);

				if(firstPoint.getX() < secondPoint.getX() && firstPoint.getY() < secondPoint.getY())
				{
					//TL = p1
					//System.out.println("TL = p1");
					//center point
					double cx = firstPoint.getX() + (secondPoint.getX() - firstPoint.getX())/2;
					double cy = firstPoint.getY() + (secondPoint.getY() - firstPoint.getY())/2;
					Point2D.Double centerPoint = new Point2D.Double(cx, cy);
					
					Rectangle currRec = new Rectangle(mm.currColor, centerPoint, 
							(secondPoint.getX() - firstPoint.getX()), 
							(secondPoint.getY() - firstPoint.getY()));
					mm.setShape(0, currRec);
					GUIFunctions.refresh();
					//mm.addShape(currLine);
				}
				else if(firstPoint.getX() > secondPoint.getX() && firstPoint.getY() > secondPoint.getY())
				{
					//TL = p2
					//System.out.println("TL = p2");
					//center point
					double cx = secondPoint.getX() + (firstPoint.getX() - secondPoint.getX())/2;
					double cy = secondPoint.getY() + (firstPoint.getY() - secondPoint.getY())/2;
					Point2D.Double centerPoint = new Point2D.Double(cx, cy);
					
					Rectangle currRec = new Rectangle(mm.currColor, centerPoint, 
							(firstPoint.getX() - secondPoint.getX()), 
							(firstPoint.getY() - secondPoint.getY()));
					mm.setShape(0, currRec);
					GUIFunctions.refresh();
					//mm.addShape(currLine);
				}
				else if(firstPoint.getX() < secondPoint.getX() && firstPoint.getY() > secondPoint.getY())
				{
					//TL = p1's x p2's Y
					//System.out.println("TL = p1's x p2's Y");//here
					Point2D.Double tempPoint = new Point2D.Double(firstPoint.getX(), secondPoint.getY());
					//center point
					double cx = tempPoint.getX() + (secondPoint.getX() - firstPoint.getX())/2;
					double cy = tempPoint.getY() + (firstPoint.getY() - secondPoint.getY())/2;
					Point2D.Double centerPoint = new Point2D.Double(cx, cy);
					
					Rectangle currRec = new Rectangle(mm.currColor, centerPoint, 
							Math.abs(firstPoint.getX() - secondPoint.getX()), 
							Math.abs(firstPoint.getY() - secondPoint.getY()));
					mm.setShape(0, currRec);
					GUIFunctions.refresh();
					//mm.addShape(currLine);
				}
				else//(firstPoint.getX() > secondPoint.getX() && firstPoint.getY() < secondPoint.getY())
				{
					//TL = p1's y p2's x
					//System.out.println("TL = p1's y p2's x default");
					Point2D.Double tempPoint = new Point2D.Double(secondPoint.getX(), firstPoint.getY());
					//center point
					double cx = tempPoint.getX() + (firstPoint.getX() - secondPoint.getX())/2;
					double cy = tempPoint.getY() + (secondPoint.getY() - firstPoint.getY())/2;
					Point2D.Double centerPoint = new Point2D.Double(cx, cy);
					
					Rectangle currRec = new Rectangle(mm.currColor, centerPoint, 
							Math.abs(secondPoint.getX() - firstPoint.getX()), 
							Math.abs(secondPoint.getY() - firstPoint.getY()));
					mm.setShape(0, currRec);
					GUIFunctions.refresh();
					//mm.addShape(currLine);
				}
				//weDragging = true;
			}
			break;

		case CIRCLE:

			if(currState == State.FIRSTCLICK)
			{
				//System.out.println("dragged the circle click");
				
				//get er drawn
				secondPoint = new Point2D.Double(e.getX(), e.getY());
				double height = firstPoint.getY() - secondPoint.getY();
				double width = firstPoint.getX() - secondPoint.getX();
				double length = Math.min(Math.abs(height), Math.abs(width));

				if (width < 0) 
				{
					if (height < 0) 
					{
						//System.out.println("circle use orig firstPoint");
						Circle currCircle = new Circle(mm.currColor,firstPoint, length);
						mm.setShape(0, currCircle);
						GUIFunctions.refresh();
						//mm.addShape(currLine);
					} 
					else 
					{
						//System.out.println("Circle q2");
						Point2D.Double tempPoint = new Point2D.Double(firstPoint.getX(),(firstPoint.getY() - length));
						Circle currCircle = new Circle(mm.currColor, tempPoint, length);
						mm.setShape(0, currCircle);
						GUIFunctions.refresh();
						//mm.addShape(currLine);
					}
				} 
				else 
				{
					if (height < 0) 
					{
						//System.out.println("Circle q3");
						Point2D.Double tempPoint = new Point2D.Double(firstPoint.getX() - length, firstPoint.getY());
						Circle currCircle = new Circle(mm.currColor, tempPoint, length);
						mm.setShape(0, currCircle);
						GUIFunctions.refresh();
						//mm.addShape(currLine);
					} 
					else 
					{
						//System.out.println("Circle q1");
						Point2D.Double tempPoint = new Point2D.Double(firstPoint.getX() - length, firstPoint.getY() - length);
						Circle currCircle = new Circle(mm.currColor, tempPoint, length);
						mm.setShape(0, currCircle);
						GUIFunctions.refresh();
						//mm.addShape(currLine);
					}
				}
				//weDragging = true;
			}
			break;

		case ELLIPSE:
			if(currState == State.FIRSTCLICK)
			{	
				//System.out.println("dragged the ellipse click");
				//p2
				secondPoint = new Point2D.Double(e.getX(), e.getY());
				//erase the top shape
				//if(weDragging == true) mm.deleteShape(0);

				if(firstPoint.getX() < secondPoint.getX() && firstPoint.getY() < secondPoint.getY())
				{
					//TL = p1
					//System.out.println("TL = p1");
					Ellipse currEllipse = new Ellipse(mm.currColor, firstPoint, 
							secondPoint.getX() - firstPoint.getX(),
							secondPoint.getY() - firstPoint.getY());
					mm.setShape(0, currEllipse);
					GUIFunctions.refresh();
					//mm.addShape(currLine);
				}
				else if(firstPoint.getX() > secondPoint.getX() && firstPoint.getY() > secondPoint.getY())
				{
					//TL = p2
					//System.out.println("TL = p2");
					Ellipse currEllipse = new Ellipse(mm.currColor, secondPoint, 
							(firstPoint.getX() - secondPoint.getX()), 
							(firstPoint.getY() - secondPoint.getY()));
					mm.setShape(0, currEllipse);
					GUIFunctions.refresh();
					//mm.addShape(currLine);
				}
				else if(firstPoint.getX() < secondPoint.getX() && firstPoint.getY() > secondPoint.getY())
				{
					//TL = p1's x p2's Y
					//System.out.println("TL = p1's x p2's Y");//here
					Point2D.Double tempPoint = new Point2D.Double(firstPoint.getX(), secondPoint.getY());
					Ellipse currEllipse = new Ellipse(mm.currColor, tempPoint, 
							Math.abs(firstPoint.getX() - secondPoint.getX()), 
							Math.abs(firstPoint.getY() - secondPoint.getY()));
					mm.setShape(0, currEllipse);
					GUIFunctions.refresh();
					//mm.addShape(currLine);
				}
				else
				{
					//TL = p1's y p2's x
					//System.out.println("TL = p1's y p2's x default");
					Point2D.Double tempPoint = new Point2D.Double(secondPoint.getX(), firstPoint.getY());
					Ellipse currEllipse = new Ellipse(mm.currColor, tempPoint, 
							Math.abs(secondPoint.getX() - firstPoint.getX()), 
							Math.abs(secondPoint.getY() - firstPoint.getY()));
					mm.setShape(0, currEllipse);
					GUIFunctions.refresh();
					//mm.addShape(currLine);
				}
				//weDragging = true;
			}
			break;

		case TRIANGLE:
			break;
		}
		
		switch(currButton)
		{
		case SELECT:			

			if(currDrawingShape != null)
			{
				//System.out.println("moving");
				if(rotating == true)
				{
					//System.out.println("rotating");
					if(currDrawingShape instanceof Line)//lines have two, the others have 1
					{
						//System.out.println("it's line circle");
						//System.out.println("dragged the end of line");

						if(clickedStart)
						{
							firstPoint = new Point2D.Double(e.getX(), e.getY());
							Line currLine = new Line(mm.currColor, firstPoint, secondPoint);
							mm.setShape(mm.getIndexbyShape(currDrawingShape), currLine);
							mv.set_currShapeIndex(mm.getIndexbyShape(currLine));
							currDrawingShape = currLine;
						}
						else
						{
							secondPoint = new Point2D.Double(e.getX(), e.getY());
							Line currLine = new Line(mm.currColor, firstPoint, secondPoint);
							mm.setShape(mm.getIndexbyShape(currDrawingShape), currLine);
							mv.set_currShapeIndex(mm.getIndexbyShape(currLine));
							currDrawingShape = currLine;
						}
						//mm.addShape(currLine);
						//mm.setShape(mm.getIndexbyShape(currDrawingShape), currLine);
						//mv.set_currShapeIndex(mm.getIndexbyShape(currLine));
						//currDrawingShape = currLine;
						GUIFunctions.refresh();
					}
					else if(currDrawingShape instanceof Circle){}//lines have two, the others have 1
					else
					{
						//System.out.println("We are rotating now!");
						secondPoint = new Point2D.Double(e.getX(), e.getY());
						
						Double rotAngle = Math.atan2(secondPoint.getX() - currDrawingShape.getCenter().getX(), 
								-secondPoint.getY() + currDrawingShape.getCenter().getY());
						// and to make it count 0-360
						if (rotAngle < 0) rotAngle += 2 * Math.PI;
						//System.out.println("Angle is " + rotAngle); 
						currDrawingShape.setRotation(rotAngle);
						
						mm.setShape(mm.getIndexbyShape(currDrawingShape), currDrawingShape);
						mv.set_currShapeIndex(mm.getIndexbyShape(currDrawingShape));
						//currDrawingShape = temp;
						GUIFunctions.refresh();
					}
				}
				else
				{
					//System.out.println("Setting the shape and such");
					secondPoint = new Point2D.Double(e.getX(), e.getY());
					currDrawingShape.translate(firstPoint, secondPoint);
					mm.setShape(mm.getIndexbyShape(currDrawingShape), currDrawingShape);
					//for moving again
					firstPoint = secondPoint;
					GUIFunctions.refresh();
				}
			}
			break;
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	//my functions--------------------------------------------------------------------------------------------------------------------
	public void reset()
	{
		firstPoint = new Point2D.Double();
		secondPoint = new Point2D.Double();
		thirdPoint = new Point2D.Double();
		currState = State.WAITING;
		currDrawingShape = null;
		mv.set_currShapeIndex(-1);
		rotating = false;
	}
}