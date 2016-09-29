package cs355.model.drawing;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import cs355.GUIFunctions;
import cs355.JsonShape;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/** FROM THE PARENT CLASS
 * This is the abstract base class for the model.
 * Make sure your model implements and extends
 * this. Also <b>MAKE SURE YOU STORE SHAPES IN
 * BACK-TO-FRONT ORDER!</b> That means that the
 * shape in the very back should be at index 0.
 */
public class MyModel extends CS355Drawing {

	// This is used to write out shapes to JSON.
	// The call to registerTypeAdapter() is essential
	// for allowing us to distinguish between shapes.
	// Also, we want pretty indenting.
	private static final Gson gson = new GsonBuilder().setPrettyPrinting()
			.registerTypeAdapter(Shape.class, new JsonShape()).create();
	private List<Shape> shapesList = new ArrayList<Shape>();
	
	public Color currColor;

	/**
	 * Get a shape at a certain index.
	 * @param index = the index of the desired shape.
	 * @return the shape at the provided index.
	 */
	public Shape getShape(int index)
	{
		return (Shape) shapesList.get(index);
	}

	// Adding and deleting.

	/**
	 * Add a shape to the <b>FRONT</b> of the list.
	 * @param s = the shape to add.
	 * @return the index of the shape.
	 */
	public int addShape(Shape s)
	{
		shapesList.add(0, s); 
		setChanged();
		notifyObservers();
		return 0;//ok?
	}

	/**
	 * Delete the shape at a certain index.
	 * @param index = the index of the shape to delete.
	 */
	public void deleteShape(int index)
	{
		if(shapesList.isEmpty() == false)
		{
			shapesList.remove(index);
		}
		setChanged();
		notifyObservers();
	}

	// Moving commands.

	/**
	 * Move the shape at a certain index to the front of the list.
	 * @param index = the index of the shape to move to the front.
	 */
	public void moveToFront(int index)
	{
		if(index > 0)
		{
			Shape toFront = shapesList.get(index);
			for(int i = index; i > 0; i--)
			{
				//System.out.println(i);
				shapesList.set(i, shapesList.get(i - 1));	
			}
			//System.out.println("here");
			shapesList.set(0, toFront);
			forceCalls();
		}
	}

	/**
	 * Move the shape at a certain index to the back of the list.
	 * @param index = the index of the shape to move to the back.
	 */
	public void movetoBack(int index)
	{
		if(index < shapesList.size() - 1)
		{
			Shape toBack = shapesList.get(index);
			for(int i = index; i < shapesList.size() - 1; i++)
			{
				//System.out.println(i);
				shapesList.set(i, shapesList.get(i + 1));	
			}
			//System.out.println("here");
			shapesList.set(shapesList.size() - 1, toBack);
			forceCalls();
		}
			
	}

	/**
	 * Move the shape at a certain index forward one slot.
	 * @param index = the index of the shape to move forward.
	 */
	public void moveForward(int index)
	{
		if(index > 0)
		{
			Shape temp = shapesList.get(index - 1);
			shapesList.set(index - 1, shapesList.get(index));
			shapesList.set(index, temp);
			forceCalls();
		}
	}

	/**
	 * Move the shape at a certain index backward one slot.
	 * @param index = the index of the shape to move backward.
	 */
	public void moveBackward(int index)
	{
		if(index < shapesList.size() - 1)
		{
			Shape temp = shapesList.get(index + 1);
			shapesList.set(index + 1, shapesList.get(index));
			shapesList.set(index, temp);
			forceCalls();
		}
	}

	// Whole list operations.

	/**
	 * Get the list of the shapes in this model.
	 * @return the list of shapes.
	 */
	public List<Shape> getShapes()
	{
		return shapesList;
	}

	/**
	 * Get the reversed list of the shapes in this model.
	 * This is for doing click tests (front first).
	 * @return the reversed list of shapes.
	 */
	public List<Shape> getShapesReversed()
	{
		List<Shape> temp = new ArrayList<Shape>();
		for(int i = 0; i < shapesList.size(); i++)
		{
			temp.add(0, shapesList.get(i));//ok?
		}
		return temp;
	}

	/**
	 * Sets the list of shapes in this model.
	 * This should overwrite the current list.
	 * @param shapes = the new list of shapes
	 *				   for the model.
	 */
	public void setShapes(List<Shape> shapes)
	{
		shapesList = shapes;
	}

	// Implemented methods.

	/**
	 * Opens a drawing from a Json file and populate
	 * this drawing with the shapes in that file.
	 * @param f = the handle of the file to open.
	 * @return true if successful, false otherwise.
	 */
	public boolean open(File f) {

		// Make a blank list.
		List<Shape> shapes = null;

		try {
			// Read the entire file in. I hate partial I/O.
			byte[] b = Files.readAllBytes(f.toPath());

			// Validation.
			if (b == null) {
				throw new IOException("Unable to read drawing");
			}

			// Convert it to text.
			String data = new String(b, StandardCharsets.UTF_8);

			// Use Gson to convert the text to a list of Shapes.
			Shape[] list = gson.fromJson(data, Shape[].class);
			shapes = new ArrayList<>(Arrays.asList(list));
		}
		catch (IOException | JsonSyntaxException ex) {
			Logger.getLogger(CS355Drawing.class.getName()).log(Level.SEVERE, null, ex);
			return false;
		}

		// Set the new shape list.
		setShapes(shapes);
		GUIFunctions.refresh();
		return true;
	}

	/**
	 * Save out this drawing's list of shapes to a
	 * Json file.
	 * @param f = the file to save to.
	 * @return true if successful, false otherwise.
	 */
	public boolean save(File f) {
		try (FileOutputStream fos = new FileOutputStream(f)) {

			// Get the list from the concrete class.
			List<Shape> data = getShapes();

			// Convert the List to an array.
			Shape[] shapes = new Shape[data.size()];
			data.toArray(shapes);

			// Convert to JSON text and write it out.
			String json = gson.toJson(shapes, Shape[].class);
			fos.write(json.getBytes());
		}
		catch (IOException ex) {
			Logger.getLogger(CS355Drawing.class.getName()).log(Level.SEVERE, null, ex);
			return false;
		}

		return true;
	}
	
	//my functions------------------------------------------------------------------------------------------------
	/**
	 * Sets the  shapes at given index.
	 * @param index = position of point
	 * @param shapey = the new shape
	 */
	public void setShape(int index, Shape shapey)
	{
		shapesList.set(index, shapey);
		setChanged();
		notifyObservers();
		//System.out.println( "center is " + shapey.getCenter());
	}
	
	/**
	 * Sees if we are currently clicking on a shape
	 */
	public Shape clicked(Point2D.Double pt, double tolerance)//reference ok??????????????????????????????????????????????????????????
	{
		for(int i = 0; i < shapesList.size(); i++)//ok for clicking?------------------------------------------------------------------------
		{
			if(shapesList.get(i).pointInShape(pt, tolerance))
			{
				//do something about it!
				//System.out.println( "You clicked on" + shapesList.get(i).toString());
				return shapesList.get(i);
			}
		}
		return null;
	}
	
	//so we can change the view of shape colors, position, rotation, etc.
	public void forceCalls()
	{
		setChanged();
		notifyObservers();
	}

	public int getIndexbyShape(cs355.model.drawing.Shape shapy) {
		for(int i = 0; i < shapesList.size(); i++)//ok for clicking?------------------------------------------------------------------------
		{
			if(shapesList.get(i) == shapy)
			{
				//System.out.println( "Found it!");
				return i;
			}
		}
		return -1;
	}
	
	public int getShapesListSize()
	{
		return shapesList.size();
	}
}
