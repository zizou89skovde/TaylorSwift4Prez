package com.allan.imgproc.felix;

import org.opencv.core.Mat;

public interface ImageTaskListener {
	
	
	// 
	void onCompleteComputation(Mat result);
}
