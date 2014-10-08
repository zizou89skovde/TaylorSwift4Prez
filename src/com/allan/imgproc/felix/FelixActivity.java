package com.allan.imgproc.felix;

import java.io.IOException;
import java.util.Vector;

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
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import com.allan.imgproc.felix.CameraInstance;

public class FelixActivity extends CameraActivity{
	private Mat                    mRgba;
	private Mat                    mIntermediateMat;
	private Mat                    mGray;
	private CameraInstance[] 	   mycameras = new CameraInstance[20];
	private int 				   camera_index;
	private int					   camera_count;
	private Vector<Double>	       mythreeDPoints;
	
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
		//dispatchTakePictureIntent();
		return true; // ifall debuggit testa basts
	};

	/*
	private Mat findFeatures(CvCameraViewFrame inputFrame){
		
		Imgproc.cvtColor(mRgba, mGray, Imgproc.COLOR_RGB2GRAY);
		FeatureDetector featureDetector = FeatureDetector.create(FeatureDetector.FAST);
		MatOfKeyPoint keyPoints = new MatOfKeyPoint();
		featureDetector.detect(mGray, keyPoints);
		Features2d.drawKeypoints(mGray, keyPoints, mRgba);	
		return mRgba;
		
	}

	static final int REQUEST_IMAGE_CAPTURE = 1;
	
	private void dispatchTakePictureIntent() {
	    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
	        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
	    }
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
	    	Bundle extras = data.getExtras();
	    	mIntermediateMat = (Mat) extras.get("data");
			Toast.makeText(getApplicationContext(),"CAPTURED BIATCH", Toast.LENGTH_SHORT).show();
			Log.d("FELIX","CAPTURED BIATCHeeeessss");
	    }
	}
	*/
	
	public double[] projectionError() {
		
		int output_size = 0;
		for(int i = 0;i<camera_count;i++)
		{
			output_size += mycameras[i].points.size();
		}
		
		double[] output_vector = new double[2*output_size];
		
		for(int i = 0;i<camera_count;i++)
		{
			output_vector[2*i]=1;
			output_vector[2*i+1]=1;
		}
		
		return output_vector;
	}
	
	}
	
