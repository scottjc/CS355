package cs355.solution;

import java.awt.Color;

import cs355.GUIFunctions;
import cs355.controller.MyController;
import cs355.model.drawing.MyModel;
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
		
		//Lab 3 stuff--------------------------------------------------------------------------------------------------------
		GUIFunctions.setHScrollBarKnob(512);
		GUIFunctions.setVScrollBarKnob(512);
		GUIFunctions.setHScrollBarPosit(768);
		GUIFunctions.setVScrollBarPosit(768);
		GUIFunctions.refresh();
	}
}
