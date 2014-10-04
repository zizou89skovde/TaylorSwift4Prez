package com.allan.imgproc.opencv;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDMatch;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.core.Size;
import org.opencv.features2d.FeatureDetector;
import org.opencv.features2d.Features2d;
import org.opencv.imgproc.Imgproc;

public class MatchFeatures extends ImageProcessObject{
	MatOfKeyPoint mPrevKeyPoints;
	Mat IntermediateMat;
	Mat mPrevImage;
	boolean firstRun = true;
	public MatchFeatures() {
		mLabel = "Match Features";

	}
	@Override
	public void initialize(int h, int w) {
		mPrevImage = new Mat(h, w, CvType.CV_8UC3);
		IntermediateMat = new Mat(h, w, CvType.CV_8UC3);
		mPrevKeyPoints = new MatOfKeyPoint();
	}


	public Mat performAlgoritm(Mat Rgba,Mat Gray,Mat IntermediateMat) {
		Imgproc.cvtColor(Rgba, Gray, Imgproc.COLOR_RGB2GRAY);		
		FeatureDetector featureDetector = FeatureDetector.create(FeatureDetector.FAST);
		MatOfKeyPoint keyPoints = new MatOfKeyPoint();
		featureDetector.detect(Gray, keyPoints);
		if(firstRun){
			mPrevKeyPoints = keyPoints;
			mPrevImage 	   = Gray.clone();
			firstRun = false;
		}else{
			MatOfDMatch matchingKeyPoints = new MatOfDMatch();
			Mat fittMat = Gray.clone();
			Features2d.drawMatches(Gray, keyPoints, mPrevImage, mPrevKeyPoints, matchingKeyPoints, fittMat);		
			mPrevKeyPoints = keyPoints;
			mPrevImage 	   = IntermediateMat.clone();
			 Imgproc.cvtColor(Gray, Rgba, Imgproc.COLOR_GRAY2RGBA, 4);
			Imgproc.resize(fittMat, Rgba, Rgba.size(), 0,0, Imgproc.INTER_LINEAR);
		
		}
		return Rgba;
	};


}
