package cs355.model.image;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Collections;
import java.util.Vector;

public class MyImage extends CS355Image {

	public Boolean drawMe;
	//orig img
	public float sharpMask[][];
	
	float[][][]hsbImg;
	float[][][]rgbImg;
	
	public MyImage()
	{	
		super();
		//System.out.println("in myImage");
		drawMe = false;
		hsbImg = null;
		rgbImg = null;
		
		sharpMask  = new float[3][3];
		sharpMask[0][0] = 0;
		sharpMask[1][0] = -1;
		sharpMask[2][0] = 0;
		sharpMask[0][1] = -1;
		sharpMask[1][1] = 6;
		sharpMask[2][1] = -1;
		sharpMask[0][2] = 0;
		sharpMask[1][2] = -1;
		sharpMask[2][2] = 0;
	}


	/**
	 * Called from the ViewRefresher to get a BufferedImage for drawing.
	 * I would suggest that you make this as efficient as possible, or
	 * maybe you could try to only call it when necessary.
	 * @return the BufferedImage that will be drawn to the screen.
	 */
	public BufferedImage getImage() 
	{
		//System.out.println("in getImage");
		//System.out.println(getWidth() + " " + getHeight());
		
		if(getHeight() == 0 && getWidth() == 0)
		{
			//System.out.println("Don't exist yet!");
			return null;
		}
			
		//new bI
		BufferedImage bi = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
		//get raster
		//WritableRaster raster = bi.getRaster();

		//loop through
		for (int y = 0; y < getHeight(); y++) 
		{
			for (int x = 0; x < getWidth(); x++) 
			{
				Color c = new Color(getRed(x,y), getGreen(x,y), getBlue(x,y));
				bi.setRGB(x, y, c.getRGB()); //bitwise concatination
			}
		}
		//bi.setData(raster);
		return bi;
	}

	/**
	 * Called from the controller to do edge detection.
	 */
	public void edgeDetection() 
	{
		//grayscale();
		makeHSBIMG();
		
		//System.out.println("in edgeDetection");
		//loop
		for (int y = 1; y < hsbImg[0].length-1; y++) 
		{
			for (int x = 1; x < hsbImg.length-1; x++) 
			{
				//hsb DONE!
				//System.out.println(x + " " + y);
				
				//xder
				float resultX = 0;
				resultX += -1 * hsbImg[x-1][y-1][2];
				//resultX += 0 * hsbImg[x][y-1][2];
				resultX += 1 * hsbImg[x+1][y-1][2];
				resultX += -2 * hsbImg[x-1][y][2];
				//resultX += 0 * hsbImg[x][y][2];
				resultX += 2 * hsbImg[x+1][y][2];
				resultX += -1 * hsbImg[x-1][y+1][2];
				//resultX += 0 * hsbImg[x][y+1][2];
				resultX += 1 * hsbImg[x+1][y+1][2];
				//System.out.println("resultX " + resultX);
				resultX = resultX/8;

				//yder
				float resultY = 0;
				resultY += -1 * hsbImg[x-1][y-1][2];
				resultY += -2 * hsbImg[x][y-1][2];
				resultY += -1 * hsbImg[x+1][y-1][2];
				//resultY += 0 * hsbImg[x-1][y][2];
				//resultY += 0 * hsbImg[x][y][2];
				//resultY += 0 * hsbImg[x+1][y][2];
				resultY += 1 * hsbImg[x-1][y+1][2];
				resultY += 2 * hsbImg[x][y+1][2];
				resultY += 1 * hsbImg[x+1][y+1][2];
				resultY = resultY/8;
		    	
				//x2 + y2
				resultX = (float) Math.pow(resultX, 2);
				resultY = (float) Math.pow(resultY, 2);
				//if(resultX < 0) System.out.println("x " + resultX);
				//if(resultY < 0) System.out.println("y " + resultY);
				float resultB = (float) Math.sqrt(resultX + resultY);
				
				//b to 255, to rgb
//		    	int rgb = Color.HSBtoRGB(hsbImg[x][y][0], hsbImg[x][y][1], resultB);
//				int newVal = rgb&0xFF;
//				if(newVal < 0) newVal = 0;
//				if(newVal > 255) newVal = 255;
				//Y = (X-A)/(B-A) * (D-C) + C
				int newVal = (int)(resultB * 255);
				if(newVal < 0) newVal = 0;
				if(newVal > 255) newVal = 255;
				
				setRed(x-1,y-1,newVal);
				setGreen(x-1,y-1,newVal);
				setBlue(x-1,y-1,newVal);
			}
		}
	}

	/**
	 * Called from the controller to do a sharpen operation.
	 */
	public void sharpen() 
	{
		makeRGBIMG();

		//System.out.println("in uniformBlur");
		//loop
		for (int y = 1; y < rgbImg[0].length-1; y++) 
		{
			for (int x = 1; x < rgbImg.length-1; x++) 
			{
				float r = 0;
				float g = 0;
				float b = 0;

				//System.out.println(x + " " + y);
				r += sharpMask[0][0] * rgbImg[x-1][y-1][0];
				r += sharpMask[1][0] * rgbImg[x][y-1][0];
				r += sharpMask[2][0] * rgbImg[x+1][y-1][0];
				r += sharpMask[0][1] * rgbImg[x-1][y][0];
				r += sharpMask[1][1] * rgbImg[x][y][0];
				r += sharpMask[2][1] * rgbImg[x+1][y][0];
				r += sharpMask[0][2] * rgbImg[x-1][y+1][0];
				r += sharpMask[1][2] * rgbImg[x][y+1][0];
				r += sharpMask[2][2] * rgbImg[x+1][y+1][0];
				//System.out.println("resultX " + resultX);
				r = r/2;
				if(r > 255)  r = (float) 255;
				if(r < 0) r = (float) 0;
				setRed(x-1,y-1,Math.round(r));
				
				
				g += sharpMask[0][0] * rgbImg[x-1][y-1][1];
				g += sharpMask[1][0] * rgbImg[x][y-1][1];
				g += sharpMask[2][0] * rgbImg[x+1][y-1][1];
				g += sharpMask[0][1] * rgbImg[x-1][y][1];
				g += sharpMask[1][1] * rgbImg[x][y][1];
				g += sharpMask[2][1] * rgbImg[x+1][y][1];
				g += sharpMask[0][2] * rgbImg[x-1][y+1][1];
				g += sharpMask[1][2] * rgbImg[x][y+1][1];
				g += sharpMask[2][2] * rgbImg[x+1][y+1][1];
				//System.out.println("resultX " + resultX);
				g = g/2;
				if(g > 255)  g = (float) 255;
				if(g < 0) g = (float) 0;
				setGreen(x-1,y-1,Math.round(g));
				
				
				b += sharpMask[0][0] * rgbImg[x-1][y-1][2];
				b += sharpMask[1][0] * rgbImg[x][y-1][2];
				b += sharpMask[2][0] * rgbImg[x+1][y-1][2];
				b += sharpMask[0][1] * rgbImg[x-1][y][2];
				b += sharpMask[1][1] * rgbImg[x][y][2];
				b += sharpMask[2][1] * rgbImg[x+1][y][2];
				b += sharpMask[0][2] * rgbImg[x-1][y+1][2];
				b += sharpMask[1][2] * rgbImg[x][y+1][2];
				b += sharpMask[2][2] * rgbImg[x+1][y+1][2];
				//System.out.println("resultX " + resultX);
				b = b/2;
				if(b > 255)  b = (float) 255;
				if(b < 0) b = (float) 0;
				setBlue(x-1,y-1,Math.round(b));
			}
		}
	}

	/**
	 * Called from the controller to do color median blur.
	 */
	public void medianBlur() 
	{
		makeRGBIMG();

		//System.out.println("in medianBlur");
		//loop
		for (int y = 1; y < rgbImg[0].length-1; y++) 
		{
			for (int x = 1; x < rgbImg.length-1; x++) 
			{
				Vector<Float> r = new Vector<Float>();
				Vector<Float> g = new Vector<Float>();
				Vector<Float> b = new Vector<Float>();

				//System.out.println(x + " " + y);
				r.add(rgbImg[x-1][y-1][0]);
				r.add(rgbImg[x][y-1][0]);
				r.add(rgbImg[x+1][y-1][0]);
				r.add(rgbImg[x-1][y][0]);
				r.add(rgbImg[x][y][0]);
				r.add(rgbImg[x+1][y][0]);
				r.add(rgbImg[x-1][y+1][0]);
				r.add(rgbImg[x][y+1][0]);
				r.add(rgbImg[x+1][y+1][0]);
				
				Collections.sort(r);
				Float rr =  r.get(4);
				if(rr > 255)  rr = (float) 255;
				if(rr < 0) rr = (float) 0;
				setRed(x-1,y-1,Math.round(rr));


				//System.out.println(x + " " + y);
				g.add(rgbImg[x-1][y-1][1]);
				g.add(rgbImg[x][y-1][1]);
				g.add(rgbImg[x+1][y-1][1]);
				g.add(rgbImg[x-1][y][1]);
				g.add(rgbImg[x][y][1]);
				g.add(rgbImg[x+1][y][1]);
				g.add(rgbImg[x-1][y+1][1]);
				g.add(rgbImg[x][y+1][1]);
				g.add(rgbImg[x+1][y+1][1]);
				
				Collections.sort(g);
				Float gg =  g.get(4);
				if(gg > 255)  gg = (float) 255;
				if(gg < 0) gg = (float) 0;
				setGreen(x-1,y-1,Math.round(gg));
				
				//System.out.println(x + " " + y);
				b.add(rgbImg[x-1][y-1][2]);
				b.add(rgbImg[x][y-1][2]);
				b.add(rgbImg[x+1][y-1][2]);
				b.add(rgbImg[x-1][y][2]);
				b.add(rgbImg[x][y][2]);
				b.add(rgbImg[x+1][y][2]);
				b.add(rgbImg[x-1][y+1][2]);
				b.add(rgbImg[x][y+1][2]);
				b.add(rgbImg[x+1][y+1][2]);
				
				Collections.sort(b);
				Float bb =  b.get(4);
				if(bb > 255)  bb = (float) 255;
				if(bb < 0) bb = (float) 0;
				setBlue(x-1,y-1,Math.round(bb));
				
			}
		}

	}

	/**
	 * Called from the controller to do uniform blur.
	 */
	public void uniformBlur() 
	{
		makeRGBIMG();

		//System.out.println("in uniformBlur");
		//loop
		for (int y = 1; y < rgbImg[0].length-1; y++) 
		{
			for (int x = 1; x < rgbImg.length-1; x++) 
			{
				float r = 0;
				float g = 0;
				float b = 0;

				//System.out.println(x + " " + y);
				r += rgbImg[x-1][y-1][0];
				r += rgbImg[x][y-1][0];
				r += rgbImg[x+1][y-1][0];
				r += rgbImg[x-1][y][0];
				r += rgbImg[x][y][0];
				r += rgbImg[x+1][y][0];
				r += rgbImg[x-1][y+1][0];
				r += rgbImg[x][y+1][0];
				r += rgbImg[x+1][y+1][0];
				r = r/9;
				if(r > 255)  r = 255;
				if(r < 0) r = 0;
				setRed(x-1,y-1,(int)r);

				g += rgbImg[x-1][y-1][1];
				g += rgbImg[x][y-1][1];
				g += rgbImg[x+1][y-1][1];
				g += rgbImg[x-1][y][1];
				g += rgbImg[x][y][1];
				g += rgbImg[x+1][y][1];
				g += rgbImg[x-1][y+1][1];
				g += rgbImg[x][y+1][1];
				g += rgbImg[x+1][y+1][1];
				g = g/9;
				if(g > 255)  g = 255;
				if(g < 0) g = 0;
				setGreen(x-1,y-1,(int)g);

				b += rgbImg[x-1][y-1][2];
				b += rgbImg[x][y-1][2];
				b += rgbImg[x+1][y-1][2];
				b += rgbImg[x-1][y][2];
				b += rgbImg[x][y][2];
				b += rgbImg[x+1][y][2];
				b += rgbImg[x-1][y+1][2];
				b += rgbImg[x][y+1][2];
				b += rgbImg[x+1][y+1][2];
				b = b/9;
				if(b > 255)  b = 255;
				if(b < 0) b = 0;
				setBlue(x-1,y-1,(int)b);
			}
		}
	}

	/**
	 * Called from the controller to make the image grayscale.
	 */
	public void grayscale() 
	{
		//loop
		for (int y = 0; y < getHeight(); y++) 
		{
			for (int x = 0; x < getWidth(); x++) 
			{
				//hsb
				float[] hsb = RGBtoHSB(x, y);

				//operation
				hsb[1] = 0;

				//rgb and set
				HSBtoRGB(x, y, hsb);
			}
		}
	}

	/**
	 * Called from the controller to change the contrast.
	 * @param amount the amount of contrast to add (could be negative).
	 */
	public void contrast(int amount) 
	{
		//loop
		for (int y = 0; y < getHeight(); y++) 
		{
			for (int x = 0; x < getWidth(); x++) 
			{
				//hsb
				float[] hsb = RGBtoHSB(x, y);

				//System.out.println(amount);
				//operation
				float s = (float)((Math.pow((((float)amount + 100f)/100f),4f) * ((float)hsb[2] - 0.5f)) + 0.5f);
				hsb[2] = s;
				if(hsb[2] > 1) hsb[2] = 1;
		    	if(hsb[2] < 0) hsb[2] = 0;
		    	
				//rgb and set
				HSBtoRGB(x, y, hsb);
			}
		}
	}

	/**
	 * Called from the controller to change the brightness.
	 * @param amount the amount of brightness to add (could be negative).
	 */
	public void brightness(int amount) 
	{
		//loop
		for (int y = 0; y < getHeight(); y++) 
		{
		    for (int x = 0; x < getWidth(); x++) 
		    {
		    	//hsb
		    	float[] hsb = RGBtoHSB(x, y);
		    	
		    	//System.out.println(amount);
		    	//operation
		    	hsb[2] += hsb[2] * (amount/100.0); //0-1
		    	if(hsb[2] > 1) hsb[2] = 1;
		    	if(hsb[2] < 0) hsb[2] = 0;
		    	
		    	//rgb and set
		    	HSBtoRGB(x, y, hsb);
		    }
		}
	}
	
	
	//my functions-------------------------------------------------------------------------------
	public float[] RGBtoHSB(int x, int y)
	{
		return Color.RGBtoHSB(getRed(x,y), getGreen(x,y), getBlue(x,y), null);
	}
	
	public void HSBtoRGB(int x, int y, float[] hsb)
	{
		// Convert back to RGB.
		int rgb = Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]);
		int[] in = new int[3];
		in[0] = (rgb>>16)&0xFF;
		in[1] = (rgb>>8)&0xFF;
		in[2] = rgb&0xFF;
		
		// Set the pixel.
		setPixel(x, y, in);
	}
	

	void makeHSBIMG()
	{
		//System.out.println("in makeHSBIMG");
		hsbImg = new float[getWidth()+2][getHeight()+2][3];
		// Convert each pixel.
		for (int y = 0; y < getHeight()+2; ++y) 
		{
			for (int x = 0; x < getWidth()+2; ++x) 
			{
				//System.out.println(x + " " + y);
				//assign the hsb	
				if(x == 0 || y == 0 || x == getWidth()+1|| y == getHeight()+1)
				{
					//System.out.println(0);
					hsbImg[x][y][0] = 0;
					hsbImg[x][y][1] = 0;
					hsbImg[x][y][2] = 0;
				}
				else
				{
					float[] c = RGBtoHSB(x-1,y-1);
					hsbImg[x][y][0] = c[0];
					hsbImg[x][y][1] = c[1];
					hsbImg[x][y][2] = c[2];
				}
			}
		}
	}
	
	void makeRGBIMG()
	{
		//System.out.println("in makeRGBIMG");
		rgbImg = new float[getWidth()+2][getHeight()+2][3];
		// Convert each pixel.
		for (int y = 0; y < getHeight()+2; ++y) 
		{
			for (int x = 0; x < getWidth()+2; ++x) 
			{
				//System.out.println(x + " " + y);
				//assign the hsb	
				if(x == 0 || y == 0 || x == getWidth()+1|| y == getHeight()+1)
				{
					//System.out.println(0);
					rgbImg[x][y][0] = 0;
					rgbImg[x][y][1] = 0;
					rgbImg[x][y][2] = 0;
				}
				else
				{
					rgbImg[x][y][0] = getRed(x-1,y-1);
					rgbImg[x][y][1] = getGreen(x-1,y-1);
					rgbImg[x][y][2] = getBlue(x-1,y-1);
				}
			}
		}
	}

}

