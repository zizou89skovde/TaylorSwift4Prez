package com.allan.imgproc.opencv;

import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.objdetect.Objdetect;


public class ObjectDetection extends ImageProcessObject {

	public ObjectDetection() {
		mLabel = "Object Detection";
	}
	
	@Override
	public Mat performAlgoritm(Mat Rgba, Mat Gray, Mat mIntermediateMat) {
		Objdetect mNewObjdetect = new Objdetect();
		CascadeClassifier mCC = new CascadeClassifier();
		Imgproc.cvtColor(Rgba, Gray, Imgproc.COLOR_RGB2GRAY);
		MatOfRect mObjects = new MatOfRect();
	
		 Imgproc.cvtColor(Gray, Rgba, Imgproc.COLOR_GRAY2RGBA, 4);
		 return Rgba;
	}
}
