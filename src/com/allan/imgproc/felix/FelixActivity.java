package com.allan.imgproc.felix;

import java.io.IOException;

import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.features2d.FeatureDetector;
import org.opencv.features2d.Features2d;
import org.opencv.imgproc.Imgproc;

import com.allan.imgproc.camera.CameraActivity;
import com.allan.imgproc.opencv.FindFeatures;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class FelixActivity extends CameraActivity{
	private Mat                    mRgba;
	private Mat                    mIntermediateMat;
	private Mat                    mGray;
	public FelixActivity() throws IOException {
		super();		
	}

	@Override
	public void onCameraViewStarted(int width, int height) {
		super.onCameraViewStarted(width, height);
		mRgba = new Mat(height, width, CvType.CV_8UC4);
		mIntermediateMat = new Mat(height, width, CvType.CV_8UC4);
		mGray = new Mat(height, width, CvType.CV_8UC1);
	}
	@Override
	public Mat onCameraFrame(CvCameraViewFrame inputFrame) {
		mRgba = inputFrame.rgba();
		Imgproc.cvtColor(mRgba, mGray, Imgproc.COLOR_RGB2GRAY);
		FeatureDetector featureDetector = FeatureDetector.create(FeatureDetector.FAST);
		MatOfKeyPoint keyPoints = new MatOfKeyPoint();
		featureDetector.detect(mGray, keyPoints);
		Features2d.drawKeypoints(mGray, keyPoints, mRgba);	
		return mRgba;

	}
	
	public boolean onTouchEvent(android.view.MotionEvent event) {
		Toast.makeText(getApplicationContext(),"CAPTURE MOFOCKA", Toast.LENGTH_SHORT).show();
		Log.d("FELIX","CAPTURE MOFOCKAasdass");
		return true; // ifall debuggit testa basts
	};

	Mat findFeatures(CvCameraViewFrame inputFrame){
		
		Imgproc.cvtColor(mRgba, mGray, Imgproc.COLOR_RGB2GRAY);
		FeatureDetector featureDetector = FeatureDetector.create(FeatureDetector.FAST);
		MatOfKeyPoint keyPoints = new MatOfKeyPoint();
		featureDetector.detect(mGray, keyPoints);
		Features2d.drawKeypoints(mGray, keyPoints, mRgba);	
		return mRgba;
		
	}
}
