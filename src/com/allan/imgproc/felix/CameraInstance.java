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
	
	public CameraInstance(Mat in_mRgba)
	{
		mRgba = in_mRgba;
		Mat mGray = new Mat();
		Imgproc.cvtColor(mRgba, mGray, Imgproc.COLOR_RGB2GRAY);
		FeatureDetector featureDetector = FeatureDetector.create(FeatureDetector.FAST);
		featureDetector.detect(mGray, keyPoints);
		Features2d.drawKeypoints(mGray, keyPoints, mRgba);

		DescriptorExtractor ext = DescriptorExtractor.create(DescriptorExtractor.SIFT);
		ext.compute(mRgba,keyPoints,descriptor);
	}
	//Camera translation
	public double[] t = new double[3]; 
	//Camera Rotation
	public double alpha,beta,gamma;
	
	MatOfKeyPoint keyPoints;
	Mat mRgba, descriptor;
	
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
