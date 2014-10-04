package com.allan.imgproc.opencv;

import org.opencv.core.Mat;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.features2d.FeatureDetector;
import org.opencv.features2d.Features2d;
import org.opencv.imgproc.Imgproc;

public class FindFeatures extends ImageProcessObject{

	public FindFeatures() {
		mLabel = "Find features";
	
	}
	
	
	public Mat performAlgoritm(Mat Rgba,Mat Gray,Mat IntermediateMat) {
		  Imgproc.cvtColor(Rgba, Gray, Imgproc.COLOR_RGB2GRAY);
		  FeatureDetector featureDetector = FeatureDetector.create(FeatureDetector.FAST);
		  MatOfKeyPoint keyPoints = new MatOfKeyPoint();
		  featureDetector.detect(Gray, keyPoints);
		  Features2d.drawKeypoints(Gray, keyPoints, Rgba);		
        return Rgba;
	};
	

}
