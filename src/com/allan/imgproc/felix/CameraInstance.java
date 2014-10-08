package com.allan.imgproc.felix;

import java.util.Vector;

import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.features2d.FeatureDetector;
import org.opencv.features2d.Features2d;
import org.opencv.imgproc.Imgproc;
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
		
	}
}
