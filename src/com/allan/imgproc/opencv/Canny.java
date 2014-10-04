package com.allan.imgproc.opencv;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;


public class Canny extends ImageProcessObject{

	public Canny() {
		mLabel = "Canny Edge Detection";
	}
	
	public Mat performAlgoritm(Mat Rgba,Mat Gray,Mat IntermediateMat) {
		Imgproc.cvtColor(Rgba, Gray, Imgproc.COLOR_RGB2GRAY);
		Imgproc.Canny(Gray, IntermediateMat, 80, 100);
        Imgproc.cvtColor(IntermediateMat, Rgba, Imgproc.COLOR_GRAY2RGBA, 4);
        return Rgba;
	};
}
