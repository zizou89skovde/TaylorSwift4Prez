package com.allan.imgproc.camera;

import java.util.ArrayList;

import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import com.allan.imgproc.opencv.ImageProcessObject;

public class AlgoritmApplier {
    private Mat                    mRgba;
    private Mat                    mIntermediateMat;
    private Mat                    mGray;
    ArrayList<ImageProcessObject> mAlgoritmList;
	public AlgoritmApplier(ArrayList<ImageProcessObject> mAlgoritmList) {
		this.mAlgoritmList = mAlgoritmList;
	}
	
	public void initialize(int width, int height){
		 mRgba = new Mat(height, width, CvType.CV_8UC4);
	     mIntermediateMat = new Mat(height, width, CvType.CV_8UC4);
	     mGray = new Mat(height, width, CvType.CV_8UC1);
	     for (ImageProcessObject o : mAlgoritmList) {
	    	 o.initialize(height, width);
	     }
	}
	
	public boolean hasParsedMetaData(){
		return false;
	}
	
	public void release(){
		
	}
	
	public Mat applyAlgoritm(CvCameraViewFrame inputFrame){
			mRgba = inputFrame.rgba();
			for (ImageProcessObject o : mAlgoritmList) {
				mRgba = o.performAlgoritm(mRgba,mGray,mIntermediateMat);
			}
	        return mRgba;
	}
}
