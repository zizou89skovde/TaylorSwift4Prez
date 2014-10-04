package com.allan.imgproc.opencv;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;


public class Gray extends ImageProcessObject {

	public Gray() {
		mLabel = "To Grayscale";
	}
	
	@Override
	public Mat performAlgoritm(Mat Rgba, Mat Gray, Mat mIntermediateMat) {
		 Imgproc.cvtColor(Rgba, Gray, Imgproc.COLOR_RGB2GRAY);
		 Imgproc.cvtColor(Gray, Rgba, Imgproc.COLOR_GRAY2RGBA, 4);
		 return Rgba;
	}
}
