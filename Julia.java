//=================================================================//
//Author: Austen Barker                                            //
//Date: 10/1/2016                                                  //
//Description: outputs an image that is a graphical representation // 
//of a julia set. The program does this by taking in 6 numbers and //
//then passing them into the function for the julia set, the number//
//of iterations then determines the rgb color value of each pixel  //
//of the image. The image is then given a file name based on the   //
//input then written using ImageIO.write().                        //                
//=================================================================//

import java.util.*;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.imageio.stream.ImageOutputStream;

public class Julia{
    static double c_real, c_imag, x_min, x_max, y_min, y_max;//initializes input variables
    static String fname;

    public static void main(String[] args){
	Scanner keyboard = new Scanner(System.in); //creates a scanner
	System.out.println("Input: C(real) C(imag) Xmin Xmax Ymin Ymax fname");//asks for input
	c_real = keyboard.nextDouble();//assigns input to variables
	c_imag = keyboard.nextDouble();
	x_min = keyboard.nextDouble();
	x_max = keyboard.nextDouble();
	y_min = keyboard.nextDouble();
	y_max = keyboard.nextDouble();
	fname = keyboard.next();//filename defined
	BufferedImage outImage = generateSetImage(x_max, x_min, y_max, y_min, c_real, c_imag);//runs the method to create the julia set image
	try{//outputs the buffered image into a jpg file
	File outputFile = new File(fname+".jpg");
	ImageIO.write(outImage, "jpg", outputFile);
	}
	catch(IOException e){}//catches the exception
    }
    //generates a buffered image for the julia set
    public static BufferedImage generateSetImage(double xmax, double xmin, double ymax, double ymin, double creal, double cimag){
	BufferedImage  outImage = new BufferedImage( 1024, 1024, BufferedImage.TYPE_INT_RGB );//creates the BufferedImage
	ColorMap colors = ColorMap.getJet(256);
	for(int i=0; i<1024; i++){//loops through the x coordinates
	    for(int j=0; j<1024; j++){//loops through the y coordinates
		Complex c = new Complex(creal, cimag);//creates the c complex number
		Complex z = new Complex(((xmin + i*(xmax-xmin)/1024)), (ymin + j*(ymax-ymin)/1024));//creates the z complex number (varies with the x and y coordinates
		int count = 0;//creates a counter for the number of iterations
		while (Complex.magnitude(z)<2){//iterates the julia set quadratic function
		    z = Complex.add(Complex.squared(z), c);//the function itself
		    count++;
		}
		outImage.setRGB(i, j, getHSBColor(count, colors));//sets the rgb color value for that pixel based upon the number of iterations
	    }
	}
	return outImage;//returns the buffered image
    }
    //hsb color output
    public static int getHSBColor(int idx, ColorMap colors)
    {
        //return Color.getHSBColor((float)(idx%256/256.0), 1.0f, 1.0f).getRGB();//gets a TYPE_INT_RGB value from the number of iterations
        //double value = (double)(idx/256.0);
	//double rgb = colors.getColor(value);
	//return rgb.getRGB();
	if(idx<=255){
            return colors.getColor(idx);
	}else{
	    return colors.getColor(255);
	}
    }
    //defines an object Complex that represents a complex number of the format a+ib where a is the real term and b is imaginary
    static class Complex{
	//fields
        double real, imag;
	//constructors
	Complex(){
	}
	Complex(double re, double im){
	    real = re;
	    imag = im;
	}
	//adds two complex numbers called by typing Complex.add(c1, c2)
	static Complex add(Complex c1, Complex c2){
	    return new Complex((c1.real+c2.real), (c1.imag+c2.imag));
	}
	//multiplies two complex numbers called by typing Complex.multiply(c1, c2)
	static Complex multiply(Complex c1, Complex c2){
	    return new Complex(((c1.real*c2.real)-(c1.imag*c2.imag)), ((c1.real*c2.imag)+(c2.real*c1.imag)));
	}
	//finds the maginitude of a complex number
	static double magnitude(Complex c1){
	    return Math.sqrt(Math.pow(c1.real, 2)+Math.pow(c1.imag, 2));
	}
	//finds the complex number when a complex number is squared
	static Complex squared(Complex c1){
	    return new Complex(((c1.real*c1.real)-(c1.imag*c1.imag)),((c1.real*c1.imag)+(c1.real*c1.imag)));
	}

    }


}
