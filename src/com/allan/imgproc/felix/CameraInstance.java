package com.allan.imgproc.felix;

import java.util.Vector;
import org.opencv.core.Mat;
import com.allan.imgproc.felix.IntrestPoint;

public class CameraInstance {
	//Camera translation
	public double[] t = new double[3]; 
	//Camera Rotation
	public double alpha,beta,gamma;
	
	public Vector<IntrestPoint> points; 
	
	public Mat rotationMatrix()
	{
		//Mat X = zeros(3,3);
		return null;
	}
}
