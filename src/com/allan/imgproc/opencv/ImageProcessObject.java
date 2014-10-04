package com.allan.imgproc.opencv;

import java.util.HashMap;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;

import android.util.Log;

public class ImageProcessObject {
	String mLabel = "Base Class Label";
	int id = -1;
	public String getLabel() {
		return mLabel;
	}
	
	public void initialize(int h,int w){
		
	}
	
	public int getId(){
		return id;
	}
	
	
	public String getIdAsString(){
		return String.valueOf(id);
	}

	public Mat performAlgoritm(Mat Rgba, Mat Gray, Mat IntermediateMat) {
		return Rgba;
		
	}
	
	protected void printMatInfo(Mat mat){
		Size size = mat.size();
		Log.d("Mat info","Type: " + CvType.typeToString(mat.type()));
		Log.d("Mat info","Width: " + size.width);
		Log.d("Mat info","Height: " + size.height); 
	}
	
	

}
