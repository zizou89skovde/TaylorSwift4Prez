package com.allan.imgproc.felix;

import java.io.IOException;
import java.util.Vector;

import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDMatch;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.features2d.DescriptorExtractor;
import org.opencv.features2d.DescriptorMatcher;
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
	private Mat                    mRgba,mRbaold;
	private Mat                    mGray;
	private CameraInstance[] 	   mycameras = new CameraInstance[20];
	private int 				   camera_index;
	private int					   camera_count;
	private int					   get_camera;
	private int 				   cam_height;
	private int					   cam_width;
	private MatOfKeyPoint 		   keyPoints;
	private DescriptorMatcher	   mmatcher;
	private MatOfDMatch 		   mmatches;
	private Mat 			       corre;
	public FelixActivity() throws IOException {
		super();	
	}

	@Override
	public void onCameraViewStarted(int width, int height) {
		super.onCameraViewStarted(width, height);
		cam_height = height;
		cam_width = width;
		mRgba = new Mat(height, width, CvType.CV_8UC4);
		mGray = new Mat(height, width, CvType.CV_8UC1);
		mmatcher = DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE_HAMMING);
		mmatches = new MatOfDMatch();
		corre = new Mat(2*height, 2*width, CvType.CV_8UC4);
	}
	
	@Override
	public Mat onCameraFrame(CvCameraViewFrame inputFrame) {
		mRgba = inputFrame.rgba();
		
		if(camera_count<20)
			camera_count++;
		
		if(camera_index<19)
			camera_index++;
		else
			camera_index = 0;
	
		//Toast.makeText(getApplicationContext(),"balle", Toast.LENGTH_SHORT).show();
		//Log.d("FELIX","asdg");
		mycameras[camera_index] = new CameraInstance(mRgba,cam_height,cam_width);
		mycameras[camera_index].ExtractDescriptors();
		if(camera_count<2)
			return mRgba;
		
		//Feature matching
		int last_camera_index;
		if(camera_index > 0)
			last_camera_index = camera_index-1;
		else
			last_camera_index = 19;	
		
		if(camera_count>2)
		{
			mmatcher.match(mycameras[camera_index].mdescriptors, mycameras[last_camera_index].mdescriptors, mmatches);
			Mat fittMat = mGray.clone();
			Features2d.drawMatches(mycameras[last_camera_index].mRgba, mycameras[last_camera_index].mkeyPoints, mycameras[camera_index].mRgba, mycameras[camera_index].mkeyPoints, mmatches, fittMat);
			Imgproc.cvtColor(mGray, mRgba, Imgproc.COLOR_GRAY2RGBA, 4);
			Imgproc.resize(fittMat, mRgba, mRgba.size(), 0,0, Imgproc.INTER_LINEAR);
			mRbaold = mRgba;
			return mRbaold;
		}
		return mRbaold;
		
	}
	

	public boolean onTouchEvent(android.view.MotionEvent event) {
		Toast.makeText(getApplicationContext(),"CAPTURE MOFOCKA", Toast.LENGTH_SHORT).show();
		Log.d("FELIX","CAPTURE MOFOCKAasdass");
		get_camera = 1;
		//dispatchTakePictureIntent();
		return true; // ifall debuggit testa basts
	};

	
	public void computeMatches()
	{
		if(camera_count<20)
			camera_count++;
		
		if(camera_index<19)
			camera_index++;
		else
			camera_index = 0;
	
		//Toast.makeText(getApplicationContext(),"balle", Toast.LENGTH_SHORT).show();
		//Log.d("FELIX","asdg");
		mycameras[camera_index] = new CameraInstance(mRgba,cam_height,cam_width);
		mycameras[camera_index].ExtractDescriptors();
		if(camera_count<2)
			return;
		
		//Feature matching
		int last_camera_index;
		if(camera_index > 0)
			last_camera_index = camera_index-1;
		else
			last_camera_index = 19;	
		
		mmatcher.match(mycameras[camera_index].mdescriptors, mycameras[last_camera_index].mdescriptors, mmatches);
		
		
		
		//Features2d.drawMatches(mycameras[camera_index].mRgba, mycameras[camera_index].mkeyPoints, mycameras[last_camera_index].mRgba, mycameras[last_camera_index].mkeyPoints, mmatches, mycameras[camera_index].mGray);
		Features2d.drawMatches(mycameras[last_camera_index].mRgba, mycameras[last_camera_index].mkeyPoints, mycameras[camera_index].mRgba, mycameras[camera_index].mkeyPoints, mmatches, corre);
	} 
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
	

	
	}
	
