//package CS355.LWJGL;


//You might notice a lot of imports here.
//You are probably wondering why I didn't just import org.lwjgl.opengl.GL11.*
//Well, I did it as a hint to you.
//OpenGL has a lot of commands, and it can be kind of intimidating.
//This is a list of all the commands I used when I implemented my project.
//Therefore, if a command appears in this list, you probably need it.
//If it doesn't appear in this list, you probably don't.
//Of course, your milage may vary. Don't feel restricted by this list of imports.
import org.lwjgl.input.Keyboard;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex3d;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.util.glu.GLU.gluPerspective;

import java.util.Iterator;

/**
 * @author Scott Christensen
 */
public class StudentLWJGLController implements CS355LWJGLController 
{
	public float[] rotateMatrix;
	public float[] translateMatrix;
	
	//This is a model of a house.
	//It has a single method that returns an iterator full of Line3Ds.
	//A "Line3D" is a wrapper class around two Point2Ds.
	//It should all be fairly intuitive if you look at those classes.
	//If not, I apologize.
	private WireFrame model = new HouseModel();

	//This method is called to "resize" the viewport to match the screen.
	//When you first start, have it be in perspective mode.
	@Override
	public void resizeGL() 
	{
		//System.out.println("resizeGL");
		//intitialize the rotate matrix, and translate matrix
		reshapeRotate(0, 0, 0, 0);
		reshapeTrans(0, -5, -15);
		
		//projection
		//set ortho or pers
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();//clear
		float f = (float) (4.0/3.0);
		gluPerspective(90,f,1,1000);//fov, aspect nearplane farclippingplane
	}

	@Override
	public void update() 
	{

	}

	//This is called every frame, and should be responsible for keyboard updates.
	//An example keyboard event is captured below.
	//The "Keyboard" static class should contain everything you need to finish
	// this up.
	@Override
	public void updateKeyboard() 
	{
		//a Move left
		if(Keyboard.isKeyDown(Keyboard.KEY_A)) 
		{
			//System.out.println("You are pressing A!");
			//reshapeTrans(translateMatrix[0] + 1, translateMatrix[1], translateMatrix[2]);
			reshapeTrans((float)(translateMatrix[0] + Math.cos(rotateMatrix[0]*(Math.PI/180))), translateMatrix[1], (float)(translateMatrix[2] + Math.sin(rotateMatrix[0]*(Math.PI/180))));
		}
		
		//d Move right
		else if(Keyboard.isKeyDown(Keyboard.KEY_D)) 
		{
			//System.out.println("You are pressing D!");
			//reshapeTrans(translateMatrix[0] - 1, translateMatrix[1], translateMatrix[2]);
			reshapeTrans((float)(translateMatrix[0] - Math.cos(rotateMatrix[0]*(Math.PI/180))), translateMatrix[1], (float)(translateMatrix[2] - Math.sin(rotateMatrix[0]*(Math.PI/180))));
		}
		
		//w Move forward
		else if(Keyboard.isKeyDown(Keyboard.KEY_W)) 
		{
			//System.out.println("You are pressing W!");
			reshapeTrans((float)(translateMatrix[0] - Math.sin(rotateMatrix[0]*(Math.PI/180))), translateMatrix[1], (float)(translateMatrix[2] + Math.cos(rotateMatrix[0]*(Math.PI/180))));//1
		}
		
		//s Move backward
		else if(Keyboard.isKeyDown(Keyboard.KEY_S)) 
		{
			//System.out.println("You are pressing S!");
			reshapeTrans((float)(translateMatrix[0] + Math.sin(rotateMatrix[0]*(Math.PI/180))), translateMatrix[1], (float)(translateMatrix[2] - Math.cos(rotateMatrix[0]*(Math.PI/180))));//1
		}
		
		//q Turn left
		else if(Keyboard.isKeyDown(Keyboard.KEY_Q)) 
		{
			//System.out.println("You are pressing Q!");
			reshapeRotate(rotateMatrix[0] - 1, 0, 1, 0);
		}
		
		//e Turn right
		else if(Keyboard.isKeyDown(Keyboard.KEY_E)) 
		{
			//System.out.println("You are pressing E!");
			reshapeRotate(rotateMatrix[0] + 1 , 0, 1, 0);
		}
		
		//r Move up
		else if(Keyboard.isKeyDown(Keyboard.KEY_R)) 
		{
			//System.out.println("You are pressing R!");
			reshapeTrans(translateMatrix[0], translateMatrix[1] - 1, translateMatrix[2]);
		}
		
		//f Move down
		else if(Keyboard.isKeyDown(Keyboard.KEY_F)) 
		{
			//System.out.println("You are pressing F!");
			reshapeTrans(translateMatrix[0], translateMatrix[1] + 1, translateMatrix[2]);
		}
		
		//h Return to the original “home” position and orientation
		else if(Keyboard.isKeyDown(Keyboard.KEY_H)) 
		{
			//System.out.println("You are pressing H!");
			
			reshapeRotate(0, 0, 0, 0);
			reshapeTrans(0, -5, -15);
		}
		
		//o Switch to orthographic projection
		else if(Keyboard.isKeyDown(Keyboard.KEY_O)) 
		{
			//System.out.println("You are pressing O!");
			//glOrtho(0.0, 1.0, 0.0, 1.0, -1.0, 1.0);
			glMatrixMode(GL_PROJECTION);
			glLoadIdentity();//clear
			glOrtho(-5,5,-5,5,1,1000);
		} 
		
		//p Switch to perspective projection
		else if(Keyboard.isKeyDown(Keyboard.KEY_P)) 
		{
			//System.out.println("You are pressing P!");
			glMatrixMode(GL_PROJECTION);
			glLoadIdentity();//clear
			float f = (float) (4.0/3.0);
			gluPerspective(90,f,1,1000);//fov, aspect nearplane farclippingplane
			
		}

	}

	//This method is the one that actually draws to the screen.
	@Override
	public void render() 
	{
		//System.out.println("render");
		//This clears the screen.
		glClear(GL_COLOR_BUFFER_BIT);
		
		
		//model view
		glMatrixMode(GL_MODELVIEW);//use this stack	 GL_PROJECTION
		glLoadIdentity();//clear
		//translation beignn read R-1T-1TR
		//O->W
		//glRotatef(90,0,1,0);TAKEN CARE OF!!!
		//glTranslatef(0,-5,-10);TAKEN CARE OF!!!
		//W->C
		glRotatef(rotateMatrix[0], rotateMatrix[1], rotateMatrix[2], rotateMatrix[3]);//-1
		glTranslatef(translateMatrix[0], translateMatrix[1], translateMatrix[2]);//-1

		//Do your drawing here.
		 glBegin(GL_LINES);
		 HouseModel hm = new HouseModel();
		 Iterator<Line3D> it = hm.getLines();//our stores lines to actual opengl lines
		 while(it.hasNext())
		 {
			 //System.out.println("here");
			 Line3D l = it.next();
			 Point3D p = l.start;
			 Point3D pe = l.end;
			 glVertex3d(p.x, p.y, p.z);//line between here and here
			 glVertex3d(pe.x, pe.y, pe.z);
		 }
		 
		//EXTRA CREDIT!!! CALL IT A NUMBER OF TIMES!!!
		 Iterator<Line3D> itt = hm.getLines();//our stores lines to actual opengl lines
		 glColor3f(0.0f,50.0f,50.0f);
		 while(itt.hasNext())
		 {
			 //System.out.println("here");
			 Line3D l = itt.next();
			 Point3D p = l.start;
			 Point3D pe = l.end;
			 glVertex3d(p.x-15, p.y, p.z);//line between here and here
			 glVertex3d(pe.x-15, pe.y, pe.z);
		 }
		 
		 
		 Iterator<Line3D> ittt = hm.getLines();//our stores lines to actual opengl lines
		 glColor3f(50.0f,50.0f,0.0f);
		 while(ittt.hasNext())
		 {
			 //System.out.println("here");
			 Line3D l = ittt.next();
			 Point3D p = l.start;
			 Point3D pe = l.end;
			 glVertex3d(p.x-30, p.y, p.z);//line between here and here
			 glVertex3d(pe.x-30, pe.y, pe.z);
		 }
		 
		 Iterator<Line3D> itttt = hm.getLines();//our stores lines to actual opengl lines
		 glColor3f(0.0f,50.0f,0.0f);
		 while(itttt.hasNext())
		 {
			 //System.out.println("here");
			 Line3D l = itttt.next();
			 Point3D p = l.start;
			 Point3D pe = l.end;
			 glVertex3d(p.x+15, p.y, p.z);//line between here and here
			 glVertex3d(pe.x+15, pe.y, pe.z);
		 }
		 
		 Iterator<Line3D> ittttt = hm.getLines();//our stores lines to actual opengl lines
		 glColor3f(50.0f,0.0f,50.0f);
		 while(ittttt.hasNext())
		 {
			 //System.out.println("here");
			 Line3D l = ittttt.next();
			 Point3D p = l.start;
			 Point3D pe = l.end;
			 glVertex3d(p.x+30, p.y, p.z);//line between here and here
			 glVertex3d(pe.x+30, pe.y, pe.z);
		 }
		 glColor3f(50.0f,50.0f,50.0f);

		 glEnd();
		 
		 
		//glBegin();
		//draw house
		//glEnd();
		//glFlush();//doit, not just store to buffer
		//glOrtho(0.0, 1.0, 0.0, 1.0, -1.0, 1.0);
		//gluPerspective(5,5,5,5);
	}
	// glOrtho(), specifies the coordinate system OpenGL assumes 
	//		as it draws the final image and how the image gets mapped to the screen.
	//		glOrtho(0.0, 1.0, 0.0, 1.0, -1.0, 1.0);
	
	//my functions--------------------------------------------------------------
	public void reshapeRotate(float r, float x, float y, float z)
	{
		rotateMatrix = new float[4];
		rotateMatrix[0] = r;
		rotateMatrix[1] = x;
		rotateMatrix[2] = y;
		rotateMatrix[3] = z;
	}
	public void reshapeTrans(float x, float y, float z)
	{
		translateMatrix = new float[3];
		translateMatrix[0] = x;
		translateMatrix[1] = y;
		translateMatrix[2] = z;
	}

}
