package com.allan.imgproc.felix;

import java.util.Vector;

import org.opencv.core.CvType;
import org.opencv.core.Mat;

import org.opencv.core.MatOfKeyPoint;
import org.opencv.features2d.DescriptorExtractor;
import org.opencv.features2d.FeatureDetector;
import org.opencv.features2d.Features2d;
import org.opencv.imgproc.Imgproc;

import com.allan.imgproc.felix.IntrestPoint;

public class CameraInstance {
	public double[] t = new double[3]; 
	//Camera Rotation
	public double alpha,beta,gamma;
	
	MatOfKeyPoint mkeyPoints;
	Mat mRgba, mdescriptors, mGray;
	
	public CameraInstance(Mat in_mRgba,int width, int height)
	{
		mRgba = new Mat(height, width, CvType.CV_8UC4);
		mGray = new Mat(height, width, CvType.CV_8UC1);
		mRgba = in_mRgba;
		mdescriptors = new Mat();
	}
	//Camera translation

	public void ExtractDescriptors()
	{
		MatOfKeyPoint keyPoints = new MatOfKeyPoint();
			
		Imgproc.cvtColor(mRgba, mGray, Imgproc.COLOR_RGB2GRAY);
	  	FeatureDetector featureDetector = FeatureDetector.create(FeatureDetector.ORB);
	  	
	  	featureDetector.detect(mGray, keyPoints);
	  	mkeyPoints = keyPoints;
	  	//Features2d.drawKeypoints(mGray, keyPoints, mRgba);	
		DescriptorExtractor ext = DescriptorExtractor.create(DescriptorExtractor.BRIEF);
		ext.compute(mRgba,keyPoints,mdescriptors);
		
	}
	
	public Mat rotationMatrix()
	{

		Mat mX = new Mat(3,3,CvType.CV_64FC1);
		Mat mY = new Mat(3,3,CvType.CV_64FC1);
		Mat mZ = new Mat(3,3,CvType.CV_64FC1);
		
		mX.put(2,2,Math.cos(alpha));
		mX.put(3,1,Math.sin(alpha));
		mX.put(2,3,-Math.sin(alpha));
		mX.put(3,3,Math.cos(alpha));
		mX.put(1,1,1);
		
		mY.put(1,1,Math.cos(alpha));
		mY.put(3,1,-Math.sin(alpha));
		mY.put(1,3,Math.sin(alpha));
		mY.put(3,3,Math.cos(alpha));
		mY.put(2,2,1);
		
		mX.put(1,1,Math.cos(alpha));
		mX.put(2,1,-Math.sin(alpha));
		mX.put(1,2,Math.sin(alpha));
		mX.put(2,2,Math.cos(alpha));
		mX.put(3, 3, 1);
		
		Mat tmp = mY.mul(mZ);
		Mat tot = mX.mul(tmp);
		
		return tot;
	}
	
	public Mat cameraMatrix()
	{
		Mat C = new Mat(3,4,CvType.CV_64FC1);
		Mat R = rotationMatrix();
		Mat Rt = R.t();
		
		C.put(1,1,1);
		C.put(2,2,1);
		C.put(3,3,1);
		C.put(1,4,t[0]);
		C.put(2,4,t[1]);
		C.put(3,4,t[2]);
		
		Mat Cres = Rt.mul(C);
		
		return Cres;
	}
}
