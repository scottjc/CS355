package cs355.solution;

import java.awt.Color;

import cs355.GUIFunctions;
import cs355.controller.MyController;
import cs355.model.drawing.MyModel;
import cs355.model.image.MyImage;
import cs355.model.scene.Point3D;
import cs355.view.MyView;

/**
 * This is the main class. The program starts here.
 * Make you add code below to initialize your model,
 * view, and controller and give them to the app.
 */
public class CS355 {

	/**
	 * This is where it starts.
	 * @param args = the command line arguments
	 */
	public static void main(String[] args) {

		// Fill in the parameters below with your controller and view.
		MyModel mm = new MyModel();
		MyController mc = new MyController();
		mc.setModel(mm);
		MyView mv = new MyView();
		mc.setView(mv);
		mv.setModel(mm);
		mm.addObserver(mv);
		mm.currColor = Color.WHITE;
		GUIFunctions.createCS355Frame(mc, mv);
		
		
		//Lab5 stuff
		cs355.model.scene.Render3D renderer = new cs355.model.scene.Render3D();
		cs355.model.scene.CS355Scene scene = new cs355.model.scene.CS355Scene();
		mc.setScene(scene);
		mv.setScene(scene);
		mv.setRenderer(renderer);
		
		//Lab6 stuff -----------------------------------------------
		cs355.model.image.MyImage imagey = new MyImage();
		mc.setImagey(imagey);
		mv.setImagey(imagey);
		
		//Lab 3 stuff
		GUIFunctions.setHScrollBarKnob(512);
		GUIFunctions.setVScrollBarKnob(512);
		GUIFunctions.setHScrollBarPosit(768);
		GUIFunctions.setVScrollBarPosit(768);
		GUIFunctions.refresh();
	}
}
