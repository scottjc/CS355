package cs355.model.scene;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;
import java.util.Vector;


public class Render3D 
{
	Boolean show;
	private Random randomNumber = new Random();
	
	public Render3D()
	{
		show = false;
	}
	
	public void setShow(Boolean f)
	{
		show = f;
	}
	
	public Boolean getShow()
	{
		return show;
	}

	public void process(cs355.model.scene.CS355Scene scene, Graphics2D g2d, Vector<Color> colors)//return an Affine Transform object
	{
		//System.out.println("in process");
		//Do your drawing here.
		//glBegin(GL_LINES);
		//HouseModel hm = new HouseModel();
		//Iterator<Line3D> it = hm.getLines();//our stores lines to actual opengl lines
		//while(it.hasNext())

		//W->C defined here, but used later!------------------------------------------------------------------------------
		//		AffineTransform wTc = new AffineTransform();
		//		wTc.concatenate(new AffineTransform(1,0,0,1, -originCoords.getX(), -originCoords.getY()));//T-1
		//		wTc.concatenate(new AffineTransform(Math.cos(mm.getShapes().get(i).getRotation()),Math.sin(mm.getShapes().get(i).getRotation()),
		//				Math.sin(-mm.getShapes().get(i).getRotation()),Math.cos(mm.getShapes().get(i).getRotation()),0,0));//the first 6 00,10,01,11,02,12) R-1

		//cw -
		double[][] RR = makeIdentity();
		// y  x
		RR[0][0] = Math.cos(scene.getCameraRotation()* (Math.PI/180));
		RR[0][2] = Math.sin(scene.getCameraRotation() * (Math.PI/180));
		RR[2][0] = -Math.sin(scene.getCameraRotation() * (Math.PI/180));
		RR[2][2] = Math.cos(scene.getCameraRotation() * (Math.PI/180));
		//RR[3][3] = 0;
		//System.out.println("RR");
		//printMatrix(RR);
		
		double[][] TT = makeIdentity();
		// y  x
		TT[0][3] = -scene.getCameraPosition().x;
		TT[1][3] = -scene.getCameraPosition().y;
		TT[2][3] = -scene.getCameraPosition().z;
		//System.out.println("TT");
		//printMatrix(TT);

		//II*RR.TT
		//printMatrix(IR);
		double[][]wTcam = mmMult(RR,TT);
		//System.out.println("wTcam");
		//printMatrix(wTcam);
		
		//projection?!?!?!---------------------------------------------------------------------------------------------
		double[][]wTcamera = wTcam;
//		double[][]projectionMatrix = makeIdentity();
//		projectionMatrix[3][3] = 0;
//		projectionMatrix[3][2] = 1;
//		System.out.println("wTcamera");
//		double[][]wTcamera = mmMult(projectionMatrix,wTcam);
//		printMatrix(wTcamera);
		
		//define the clip matrix here
		//         y  x
		double[][] clipMatrix = new double[4][4];
		clipMatrix[0][0] = (double)(1/Math.tan((90 *(Math.PI/180))/2));//in radians
		clipMatrix[0][1] = 0;
		clipMatrix[0][2] = 0;
		clipMatrix[0][3] = 0;
		
		clipMatrix[1][0] = 0;
		clipMatrix[1][1] = (double)(1/Math.tan((90 *(Math.PI/180))/2));
		clipMatrix[1][2] = 0;
		clipMatrix[1][3] = 0;
		
		clipMatrix[2][0] = 0;
		clipMatrix[2][1] = 0;
		clipMatrix[2][2] = (double)(1000.1/999.9);//f = 1000 n = 0.1
		clipMatrix[2][3] = (double)((-2 * 0.1 * 1000)/999.9);
		
		clipMatrix[3][0] = 0;
		clipMatrix[3][1] = 0;
		clipMatrix[3][2] = 1;
		clipMatrix[3][3] = 0;
		
		//System.out.println("clipMatrix");
		//printMatrix(clipMatrix);
		
		//cTv matrix defined here 512
		//		   y  x
		double[][] viewMatrix = new double[3][3];
		viewMatrix[0][0] = 1024;// 2048/2
		viewMatrix[0][1] = 0;
		viewMatrix[0][2] = 1024;
		viewMatrix[1][0] = 0;
		viewMatrix[1][1] = -1024;
		viewMatrix[1][2] = 1024;
		viewMatrix[2][0] = 0;
		viewMatrix[2][1] = 0;
		viewMatrix[2][2] = 1;
		//printMatrix(viewMatrix);
		
		//System.out.println("before wTclip");
		double[][] wTclip = mmMult(clipMatrix, wTcamera);
		//System.out.println("wTclip");
		//printMatrix(wTclip);
		
		int counter = 0;
		for(Instance s : scene.instances())
		{
			//colors
			g2d.setColor(colors.get(counter));
			counter++;
			
			//Trip to Trippy Town
			//Color currColor = new Color(randomNumber.nextFloat(),
		    //        randomNumber.nextFloat(), randomNumber.nextFloat());
			//g2d.setColor(currColor);
		
			//System.out.println("instance");

			//O->W
			//		AffineTransform oTw = new AffineTransform();
			//		oTw.concatenate(new AffineTransform(1,0,0,1, -scene.getCameraPosition().x, -scene.getCameraPosition().y));//T-1
			//		oTw.concatenate(new AffineTransform(Math.cos(mm.getShapes().get(i).getRotation()),Math.sin(mm.getShapes().get(i).getRotation()),
			//				Math.sin(-mm.getShapes().get(i).getRotation()),Math.cos(mm.getShapes().get(i).getRotation()),0,0));//the first 6 00,10,01,11,02,12) R-1
			
			double[][] T = makeIdentity();
			//y  x
			T[0][3] = s.getPosition().x;
			T[1][3] = s.getPosition().y;
			T[2][3] = s.getPosition().z;
			//System.out.println("T");
			//printMatrix(T);
			
			//ccw+
			double[][] R = makeIdentity();
			//y  x
			R[0][0] = Math.cos(s.getRotAngle() * (Math.PI/180));
			R[0][2] = Math.sin(s.getRotAngle() * (Math.PI/180));
			R[2][0] = Math.sin(s.getRotAngle() * (Math.PI/180));
			R[2][2] = Math.cos(s.getRotAngle() * (Math.PI/180));
			//R[3][3] = 0;
			//System.out.println("R");
			//printMatrix(R);

			
			//I*T.R_V
			//System.out.println("before others");
			double[][]oTw = mmMult(T,R);
			//System.out.println("oTw");
			//printMatrix(oTw);
			
			//all of them
			double[][] oTclip = mmMult(wTclip,oTw);
			//System.out.println("oTclip");
			//printMatrix(oTclip);
			
			//clip and w->c
			for(cs355.model.scene.Line3D L : s.getModel().getLines())
			{
				//Apply transformation to this line
				double[] line = new double[4];
				line[0] = L.start.x;
				line[1] = L.start.y;
				line[2] = L.start.z;
				line[3] =  1;
				double[] pt1 = mvMult(oTclip, line);
				//double w1 = pt1[3];
				//System.out.println(line[0] + " "  + line[1] + " " + line[2] + " " + line[3]);
				//System.out.println(pt1[0] + " " +  pt1[1] + " " + pt1[2] + " "+ pt1[3]);
				
				line[0] = L.end.x;
				line[1] = L.end.y;
				line[2] = L.end.z;
				line[3] =  1;
				double[] pt2 = mvMult(oTclip, line);
				//double w2 = pt2[3];
				//System.out.println(line[0] + " "  + line[1] + " " + line[2] + " " + line[3]);
				//System.out.println(pt2[0] + " " +  pt2[1] + " " + pt2[2] + " "+ pt2[3]);
				
				//do Clip test of it
				//System.out.println(clipTest(pt1) + " " + clipTest(pt2));
				if(clipTest(pt1, pt2))
				{
					//System.out.println("drawing now");
					//If pass, / by W then multiply by screen matrix
					//apply screen coords
					//viewmatrix
					double[] in1 = new double[3];
					in1[0] = pt1[0]/pt1[3];//x1
					in1[1] = pt1[1]/pt1[3];//y1
					in1[2] = 1;
					
					double[] results1 = mvMult(viewMatrix, in1);
					
					double[] in2 = new double[3];
					in2[0] = pt2[0]/pt2[3];//x2
					in2[1] = pt2[1]/pt2[3];//y2
					in2[2] = 1;
					
					double[] results2 = mvMult(viewMatrix, in2);
					
					//System.out.println("drawing now " + (int)results1[0]  + " " +(int)results1[1] + " " + (int)results2[0]
							//+ " " + (int)results2[1]);
					g2d.drawLine((int)results1[0], (int)results1[1], (int)results2[0], (int)results2[1]);
				}
			}	
		}

		return;
	}
	
	public Boolean clipTest(double[] pt1, double[] pt2)
	{
		double w1 = pt1[3];
		double w2 = pt2[3];
		
//		if((-w1 < pt1[2] && pt1[2] < w1 && -w2 < pt2[2] && pt2[2]< w2)//z
//		&& //either pt1 or pt2's x  and y is in here
//		(((-w1 < pt1[0] && pt1[0] < w1) && (-w1 < pt1[1] && pt1[1] < w1)) || ((-w2 < pt2[0] && pt2[0] < w2) && (-w2 < pt2[1] && pt2[1]< w2)))) return true;

		Boolean z1 = (-w1 < pt1[2]) && (pt1[2] < w1);
		Boolean z2 = (-w2 < pt2[2]) && (pt2[2] < w2);
		Boolean zs = z1 && z2;
		Boolean x1 = (-w1 < pt1[0]) && (pt1[0] < w1);
		Boolean y1 = (-w1 < pt1[1]) && (pt1[1] < w1);
		Boolean xy1 = x1 && y1;
		Boolean x2 = (-w2 < pt2[0]) && (pt2[0] < w2);
		Boolean y2 = (-w2 < pt2[1]) && (pt2[1] < w2);
		Boolean xy2 = x2 && y2;	
		Boolean xys = xy1 || xy2;
		return zs && xys;
		//return false;
	}

	public double[][] makeIdentity()
	{
		double[][] identity = new double[4][4];
		for(int i = 0; i < 4; i++)
		{
			for(int j = 0; j < 4; j++)
			{
				if(i == j) identity[i][j] = 1;
				else  identity[i][j] = 0;
			}
		}
		//printMatrix(identity);
		return identity;
	}
	
	public double[][] mmMult(double[][] a, double[][] b)//3x3 or 4x4
	{
		double[][] c = new double[a.length][b.length];
		//y  x
		c[0][0] = a[0][0]*b[0][0] + a[0][1]*b[1][0] + a[0][2]*b[2][0] + a[0][3]*b[3][0];
		c[0][1] = a[0][0]*b[0][1] + a[0][1]*b[1][1] + a[0][2]*b[2][1] + a[0][3]*b[3][1];
		c[0][2] = a[0][0]*b[0][2] + a[0][1]*b[1][2] + a[0][2]*b[2][2] + a[0][3]*b[3][2];
		c[0][3] = a[0][0]*b[0][3] + a[0][1]*b[1][3] + a[0][2]*b[2][3] + a[0][3]*b[3][3];
		
		c[1][0] = a[1][0]*b[0][0] + a[1][1]*b[1][0] + a[1][2]*b[2][0] + a[1][3]*b[3][0];
		c[1][1] = a[1][0]*b[0][1] + a[1][1]*b[1][1] + a[1][2]*b[2][1] + a[1][3]*b[3][1];
		c[1][2] = a[1][0]*b[0][2] + a[1][1]*b[1][2] + a[1][2]*b[2][2] + a[1][3]*b[3][2];
		c[1][3] = a[1][0]*b[0][3] + a[1][1]*b[1][3] + a[1][2]*b[2][3] + a[1][3]*b[3][3];
		
		c[2][0] = a[2][0]*b[0][0] + a[2][1]*b[1][0] + a[2][2]*b[2][0] + a[2][3]*b[3][0];
		c[2][1] = a[2][0]*b[0][1] + a[2][1]*b[1][1] + a[2][2]*b[2][1] + a[2][3]*b[3][1];
		c[2][2] = a[2][0]*b[0][2] + a[2][1]*b[1][2] + a[2][2]*b[2][2] + a[2][3]*b[3][2];
		c[2][3] = a[2][0]*b[0][3] + a[2][1]*b[1][3] + a[2][2]*b[2][3] + a[2][3]*b[3][3];
		
		c[3][0] = a[3][0]*b[0][0] + a[3][1]*b[1][0] + a[3][2]*b[2][0] + a[3][3]*b[3][0];
		c[3][1] = a[3][0]*b[0][1] + a[3][1]*b[1][1] + a[3][2]*b[2][1] + a[3][3]*b[3][1];
		c[3][2] = a[3][0]*b[0][2] + a[3][1]*b[1][2] + a[3][2]*b[2][2] + a[3][3]*b[3][2];
		c[3][3] = a[3][0]*b[0][3] + a[3][1]*b[1][3] + a[3][2]*b[2][3] + a[3][3]*b[3][3];
		return c;
	}
	
	public double[] mvMult(double[][] A, double[] B)//3x3 or 4x4
	{
		double[] C = new double[A.length];
		for (int i = 0; i < A.length; i++)
		{
			for (int k = 0; k < B.length; k++)
			{
				C[i] += A[i][k] * B[k];
			}
		}
		return C;
	}
	
	public void printMatrix(double[][]A)
	{
		for (int i = 0; i < A.length; i++)//y
		{
			for (int k = 0; k < A.length; k++)//x
			{
				System.out.print(A[i][k] + " ");
			}
			System.out.println("");
		}
	}
}

