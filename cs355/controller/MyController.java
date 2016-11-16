package cs355.controller;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Vector;

import cs355.GUIFunctions;
import cs355.model.drawing.*;

import cs355.model.drawing.MyModel;
import cs355.model.scene.CS355Scene;
import cs355.model.scene.Instance;
import cs355.model.scene.Point3D;
import cs355.view.MyView;

public class MyController implements CS355Controller {
		
	//Modes
	//more stuff!!!!!!!!
	MyModel mm;
	MyView mv;
	
	//buttons!
	public enum Button
	{
		NONE, SHAPE, SELECT
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
	
	//stuff for lab 3
	int scrollSize;
	Boolean atMM;
	
	
	//Lab5-----------------------------------------------------------------------
	cs355.model.scene.CS355Scene scene;
	Boolean threedee;
	Point3D homePt;
	Double homeRot;
	private Random randomNumber = new Random();
	
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
		
		scrollSize = 512;
		atMM = false;
		
		//Lab5---------------------------
		 threedee = false;
		 scene = new CS355Scene();
		 homePt = null;
		 homeRot = 0.0;
	}

	//Lab5 stuff-----------------------------------------------------
	//my functions--------------------------------------------------------------
	public void setScene(cs355.model.scene.CS355Scene s)
	{
		scene = s;
	}
	
	public void setModel(MyModel m)
	{
		mm = m;
	}
	
	public void setView(MyView v)
	{
		mv = v;
	}

	
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
	public void zoomInButtonHit()//must change the viewScale here
	{
		//currShape = Shape.NONE;
		//currButton = Button.ZOOMIN;
		//reset();
		//System.out.println("in zoomIN");
		if(mv.get_viewScale()*2 >= 400)
		{
			mv.set_viewScale(400);
			GUIFunctions.setHScrollBarKnob(126);
			GUIFunctions.setVScrollBarKnob(126);
			scrollSize = 126;
			if(!atMM)
			{
				GUIFunctions.setHScrollBarPosit((int)(mv.get_originCoords().getX() + scrollSize/2));
				GUIFunctions.setVScrollBarPosit((int)(mv.get_originCoords().getY() + scrollSize/2));
			}
			//mv.set_originCoords(new Point2D.Double(value, mv.get_originCoords().getY()));
			//System.out.println(mv.get_originCoords());
			//System.out.println(mv.get_viewScale());
			atMM = true;
		}
		else
		{
			mv.set_viewScale(mv.get_viewScale() * 2);
			scrollSize = scrollSize / 2;
			GUIFunctions.setHScrollBarKnob(scrollSize);
			GUIFunctions.setVScrollBarKnob(scrollSize);
			GUIFunctions.setHScrollBarPosit((int)(mv.get_originCoords().getX()) + scrollSize/2);
			GUIFunctions.setVScrollBarPosit((int)(mv.get_originCoords().getY()) + scrollSize/2);
			//mv.set_originCoords(new Point2D.Double(value, mv.get_originCoords().getY()));
			System.out.println(mv.get_originCoords());
			//System.out.println(mv.get_viewScale());
			atMM = false;
		}
		GUIFunctions.setZoomText(mv.get_viewScale()/100);
		
		mm.forceCalls();
		GUIFunctions.refresh();
		return;
	}

	/**
	 * Called when the user hits the zoom out button.
	 */
	public void zoomOutButtonHit()
	{
		//currShape = Shape.NONE;
		//currButton = Button.ZOOMOUT;
		//reset();
		System.out.println("in zoomOut");
		if(mv.get_viewScale()/2 <= 25)
		{
			mv.set_viewScale(25);
			GUIFunctions.setHScrollBarPosit(0);
			GUIFunctions.setVScrollBarPosit(0);
			GUIFunctions.setHScrollBarKnob(2048);
			GUIFunctions.setVScrollBarKnob(2048);
			scrollSize = 2048;
			//mv.set_originCoords(new Point2D.Double(value, mv.get_originCoords().getY()));
			System.out.println(mv.get_originCoords());
			//System.out.println(mv.get_viewScale());
			atMM = true;
		}
		else
		{
			mv.set_viewScale(mv.get_viewScale() / 2);
			scrollSize = scrollSize * 2;
			GUIFunctions.setHScrollBarKnob(scrollSize);
			GUIFunctions.setVScrollBarKnob(scrollSize);
			GUIFunctions.setHScrollBarPosit((int)mv.get_originCoords().getX() - scrollSize/4);
			GUIFunctions.setVScrollBarPosit((int)mv.get_originCoords().getY() - scrollSize/4);
			//mv.set_originCoords(new Point2D.Double(value, mv.get_originCoords().getY()));
			System.out.println(mv.get_originCoords());
			//System.out.println(mv.get_viewScale());
			atMM = false;
			//put scale on screen
		}
		
		GUIFunctions.setZoomText(mv.get_viewScale()/100);
		mm.forceCalls();
		GUIFunctions.refresh();
		return;		
	}

	/**
	 * Called when the horizontal scrollbar position changes.
	 * @param value = the new position.
	 */
	public void hScrollbarChanged(int value)//give us the actual position. change origin
	{
		//System.out.println(" hScrollbarChanged " + value);
		//originCoord change
		GUIFunctions.setHScrollBarPosit(value);
		//GUIFunctions.setVScrollBarPosit(768);
		mv.set_originCoords(new Point2D.Double(value, mv.get_originCoords().getY()));
		//System.out.println(mv.get_originCoords());
		GUIFunctions.refresh();
		mm.forceCalls();
	}

	/**
	 * Called when the vertical scrollbar position changes.
	 * @param value = the new position.
	 */
	public void vScrollbarChanged(int value)
	{
		//System.out.println(" vScrollbarChanged " + value);
		//originCoord change
		//GUIFunctions.setHScrollBarPosit(768);
		GUIFunctions.setVScrollBarPosit(value);
		mv.set_originCoords(new Point2D.Double(mv.get_originCoords().getX(), value));
		//System.out.println(mv.get_originCoords());
		GUIFunctions.refresh();
		mm.forceCalls();
	}

	// 3D Model. -------------------------------------------------------------------------

	/**
	 * Called to load a scene from a file.
	 * @param file = the file containing the scene to load.
	 */
	public void openScene(File file)
	{
		//System.out.println("Open Scene button");
		scene.open(file);
		
		//iterate through all of the instances and assign a color to assign 
		Vector<Color> colors = new Vector<Color>();
		for(Instance i  : scene.instances())
		{
			Color currColor = new Color(randomNumber.nextFloat(),
				            randomNumber.nextFloat(), randomNumber.nextFloat());
			colors.add(currColor);
		}
		mv.setColor(colors);
		
		homePt = scene.getCameraPosition();
		homeRot = scene.getCameraRotation();
		setScene(scene);//put the points into the matrixes.
		mv.setScene(scene);
		GUIFunctions.refresh();
	}

	/**
	 * Called to toggle the 3D OpenGL display.
	 */
	public void toggle3DModelDisplay()
	{
		//System.out.println("toggle 3E Model Display button");
		threedee = !threedee;
		//renderer.setShow(threedee);
		mv.getRenderer().setShow(threedee);
		//stuff
		GUIFunctions.refresh();
	}

	/**
	 * Called when the user presses keys. This is used
	 * for navigating in the 3D world.
	 * @param iterator = the iterator over the keys.
	 */
	public void keyPressed(Iterator<Integer> iterator)
	{
		//System.out.println("keyPressed");
		if(!threedee) return;
		
		int i = iterator.next();
		//System.out.println(i);

		//a Move left
		//if(Keyboard.isKeyDown(Keyboard.KEY_A))
		if(i == 65)
		{
			//System.out.println("You are pressing A!");
			//change model
			//reshapeTrans((float)(translateMatrix[0] + Math.cos(rotateMatrix[0]*(Math.PI/180))), translateMatrix[1], (float)(translateMatrix[2] + Math.sin(rotateMatrix[0]*(Math.PI/180))));
			
			Point3D pt = new Point3D((double)scene.getCameraPosition().x - Math.cos(scene.getCameraRotation()*(Math.PI/180))
									 , (double)scene.getCameraPosition().y
									 , (double)scene.getCameraPosition().z - Math.sin(scene.getCameraRotation() *(Math.PI/180)));
			scene.setCameraPosition(pt);
			scene.setCameraRotation(scene.getCameraRotation());
			mv.setScene(scene);
			GUIFunctions.refresh();
		}

		//d Move right
		//else if(Keyboard.isKeyDown(Keyboard.KEY_D))
		else if(i == 68)
		{
			//System.out.println("You are pressing D!");
			//change model
			//reshapeTrans((float)(translateMatrix[0] - Math.cos(rotateMatrix[0]*(Math.PI/180))), translateMatrix[1], (float)(translateMatrix[2] - Math.sin(rotateMatrix[0]*(Math.PI/180))));
			
			Point3D pt = new Point3D((double)scene.getCameraPosition().x + Math.cos(scene.getCameraRotation()*(Math.PI/180))
									 , (double)scene.getCameraPosition().y
									 , (double)scene.getCameraPosition().z + Math.sin(scene.getCameraRotation()*(Math.PI/180)));
			scene.setCameraPosition(pt);
			scene.setCameraRotation(scene.getCameraRotation());
			mv.setScene(scene);
			GUIFunctions.refresh();
		}

		//w Move forward
		//else if(Keyboard.isKeyDown(Keyboard.KEY_W))
		else if(i == 87)
		{
			//System.out.println("You are pressing W!");
			//change model
			//reshapeTrans((float)(translateMatrix[0] - Math.cos(rotateMatrix[0]*(Math.PI/180))), translateMatrix[1], (float)(translateMatrix[2] + Math.sin(rotateMatrix[0]*(Math.PI/180))));
			
			Point3D pt = new Point3D((double)scene.getCameraPosition().x - Math.sin(scene.getCameraRotation()*(Math.PI/180))
									 , (double)scene.getCameraPosition().y
									 , (double)scene.getCameraPosition().z + Math.cos(scene.getCameraRotation()*(Math.PI/180)));
			scene.setCameraPosition(pt);
			mv.setScene(scene);
			GUIFunctions.refresh();
		}

		//s Move backward
		//else if(Keyboard.isKeyDown(Keyboard.KEY_S))
		else if(i == 83)
		{
			//System.out.println("You are pressing S!");
			//change model
			//reshapeTrans((float)(translateMatrix[0] + Math.cos(rotateMatrix[0]*(Math.PI/180))), translateMatrix[1], (float)(translateMatrix[2] - Math.sin(rotateMatrix[0]*(Math.PI/180))));
			Point3D pt = new Point3D((double)scene.getCameraPosition().x + Math.sin(scene.getCameraRotation()*(Math.PI/180))
									 , (double)scene.getCameraPosition().y
									 , (double)scene.getCameraPosition().z - Math.cos(scene.getCameraRotation()*(Math.PI/180)));
			scene.setCameraPosition(pt);
			mv.setScene(scene);
			GUIFunctions.refresh();
		}

		//q Turn left
		//else if(Keyboard.isKeyDown(Keyboard.KEY_Q))
		else if(i == 81)
		{
			//System.out.println("You are pressing Q!");
			//reshapeRotate(rotateMatrix[0] - 1, 0, 1, 0);
			
			scene.setCameraRotation(scene.getCameraRotation() + 2);
			mv.setScene(scene);
			GUIFunctions.refresh();
		}

		//e Turn right
		//else if(Keyboard.isKeyDown(Keyboard.KEY_E))
		else if(i == 69)
		{
			//System.out.println("You are pressing E!");
			//reshapeRotate(rotateMatrix[0] + 1, 0, 1, 0);
			
			scene.setCameraRotation(scene.getCameraRotation() - 2);
			mv.setScene(scene);
			GUIFunctions.refresh();
		}

		//r Move up
		//else if(Keyboard.isKeyDown(Keyboard.KEY_R))
		else if(i == 82)
		{
			//System.out.println("You are pressing R!");
			//reshapeTrans(translateMatrix[0], translateMatrix[1] - 1, translateMatrix[2]);
			Point3D pt = new Point3D((double)scene.getCameraPosition().x
									 , (double)scene.getCameraPosition().y + 1
									 , (double)scene.getCameraPosition().z);
			scene.setCameraPosition(pt);
			scene.setCameraRotation(scene.getCameraRotation());
			mv.setScene(scene);
			GUIFunctions.refresh();
			
		}

		//f Move down
		//else if(Keyboard.isKeyDown(Keyboard.KEY_F))
		else if(i == 70)
		{
			//System.out.println("You are pressing F!");
			//reshapeTrans(translateMatrix[0], translateMatrix[1] + 1, translateMatrix[2]);
			Point3D pt = new Point3D((double)scene.getCameraPosition().x
									 , (double)scene.getCameraPosition().y - 1
									 , (double)scene.getCameraPosition().z);
			scene.setCameraPosition(pt);
			scene.setCameraRotation(scene.getCameraRotation());
			mv.setScene(scene);
			GUIFunctions.refresh();
		}

		//h Return to the original “home” position and orientation
		//else if(Keyboard.isKeyDown(Keyboard.KEY_H))
		else if(i == 72)
		{
			//System.out.println("You are pressing H!");
			//reshapeRotate((float)homeRot,0,0,0);
			//reshapeTrans((float)homePt.x, (float)homePt.y, (float)homePt.z);
			//Point3D trans = new Point3D((double)translateMatrix[0], (double)translateMatrix[1], (double)translateMatrix[2]);
			//System.out.println(scene.getCameraPosition() + " " + homePt );
			scene.setCameraPosition(homePt);
			scene.setCameraRotation(homeRot);
			mv.setScene(scene);
			GUIFunctions.refresh();
		}
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
						firstPoint = vTw(firstPoint);//Lab3
						break;
						
					case SECONDCLICK:
						//System.out.println("second tri click");
						secondPoint = new Point2D.Double(e.getX(), e.getY());
						secondPoint = vTw(secondPoint);//Lab3
						break;
						
					case THIRDCLICK:
						//System.out.println("third tri click");
						//get er drawn
						thirdPoint = new Point2D.Double(e.getX(), e.getY());
						thirdPoint = vTw(thirdPoint);//Lab3
						
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
			firstPoint = vTw(firstPoint);//Lab3
		
			Line currLine = new Line(mm.currColor, firstPoint, firstPoint);
			mm.addShape(currLine);

			break;

		case SQUARE:

			currState = State.FIRSTCLICK;

			//System.out.println("first click square");
			firstPoint = new Point2D.Double(e.getX(), e.getY());
			firstPoint = vTw(firstPoint);//Lab3
			
			Square currSquare = new Square(mm.currColor, firstPoint, 3);
			mm.addShape(currSquare);
			break;

		case RECTANGLE:

			currState = State.FIRSTCLICK;

			//System.out.println("first click rect");
			firstPoint = new Point2D.Double(e.getX(), e.getY());
			firstPoint = vTw(firstPoint);//Lab3
			Rectangle currRec = new Rectangle(mm.currColor, firstPoint, 
					(firstPoint.getX() - firstPoint.getX()), 
					(firstPoint.getY() - firstPoint.getY()));
			mm.addShape(currRec);
			break;

		case CIRCLE:

			currState = State.FIRSTCLICK;

			//System.out.println("first click circle");
			firstPoint = new Point2D.Double(e.getX(), e.getY());
			firstPoint = vTw(firstPoint);//Lab3
			Circle currCircle = new Circle(mm.currColor,firstPoint, 0);
			mm.addShape(currCircle);
			break;

		case ELLIPSE:

			currState = State.FIRSTCLICK;

			//System.out.println("first click ellipse");
			firstPoint = new Point2D.Double(e.getX(), e.getY());
			firstPoint = vTw(firstPoint);//Lab3
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
				firstPoint = new Point2D.Double(e.getX(), e.getY());
				firstPoint = vTw(firstPoint);//Lab3
				
				//System.out.println("CONT we selected");//
				
				cs355.model.drawing.Shape tempClick = mm.clicked(firstPoint, 4 * (mv.get_viewScale()/100), mv.get_viewScale(), mv.get_originCoords());
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
				Point2D.Double pt = currDrawingShape.clickedCircle(firstPoint, mv.get_viewScale());
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
					
					Point2D.Double ptt = currDrawingShape.clickedCircle(firstPoint,mv.get_viewScale());
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
				firstPoint = new Point2D.Double(e.getX(), e.getY());
				firstPoint = vTw(firstPoint);//Lab3
				
				//System.out.println("CONT ELSE we selected");
								
				currDrawingShape = mm.clicked(firstPoint, 4, mv.get_viewScale(), mv.get_originCoords());
				if(currDrawingShape != null)
				{
					//firstPoint = new Point2D.Double(e.getX(), e.getY());
					//function to see if we clicked on the rotation circle/ start or endpoint
					Point2D.Double pt = currDrawingShape.clickedCircle(firstPoint, mv.get_viewScale());
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
				secondPoint = vTw(secondPoint);//Lab3
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
				secondPoint = vTw(secondPoint);//Lab3

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
				secondPoint = vTw(secondPoint);//Lab3

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
				secondPoint = vTw(secondPoint);//Lab3

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
				secondPoint = vTw(secondPoint);//Lab3

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
				secondPoint = vTw(secondPoint);//Lab3
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
				secondPoint = vTw(secondPoint);//Lab3
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
				secondPoint = vTw(secondPoint);//Lab3
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
				secondPoint = vTw(secondPoint);//Lab3
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
				secondPoint = vTw(secondPoint);//Lab3
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
							firstPoint = vTw(firstPoint);//Lab3
							Line currLine = new Line(mm.currColor, firstPoint, secondPoint);
							mm.setShape(mm.getIndexbyShape(currDrawingShape), currLine);
							mv.set_currShapeIndex(mm.getIndexbyShape(currLine));
							currDrawingShape = currLine;
						}
						else
						{
							secondPoint = new Point2D.Double(e.getX(), e.getY());
							secondPoint = vTw(secondPoint);//Lab3
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
						secondPoint = vTw(secondPoint);//Lab3
						
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
					secondPoint = vTw(secondPoint);//Lab3
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
	
	//Lab3
	Point2D.Double vTw(Point2D.Double pt)
	{
		AffineTransform vTw = new AffineTransform();
		vTw.concatenate(new AffineTransform(1,0,0,1, mv.get_originCoords().getX(), mv.get_originCoords().getY()));//T of scroll bars
		vTw.concatenate(new AffineTransform(1/(mv.get_viewScale()/100),0,0,1/(mv.get_viewScale()/100), 0, 0));//S-1 of view
		
		//apply to this shape
		Point2D.Double worldCoord = new Point2D.Double();
		vTw.transform(pt, worldCoord);
		//System.out.println(pt + " pTw " + worldCoord);
		return worldCoord;
	}
}