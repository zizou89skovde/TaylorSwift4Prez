package com.allan.imgproc.felix;

import java.io.IOException;
import java.util.Vector;

import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDMatch;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Size;
import org.opencv.features2d.DMatch;
import org.opencv.features2d.DescriptorExtractor;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.FeatureDetector;
import org.opencv.features2d.Features2d;
import org.opencv.features2d.KeyPoint;
import org.opencv.imgproc.Imgproc;
import org.opencv.calib3d.Calib3d;

import com.allan.imgproc.camera.CameraActivity;
import com.allan.imgproc.opencv.FindFeatures;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import com.allan.imgproc.felix.CameraInstance;

public class FelixActivity<MatOf2dpointf> extends CameraActivity{
	private Mat                    mRgba;
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
	private Vector<Mat> 		   points4D;

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
		corre = new Mat(2*height, width, CvType.CV_8UC4);
	}

	int counter = 0;	
	@Override
	public Mat onCameraFrame(CvCameraViewFrame inputFrame) {
		counter++;	
		if( counter % 2 == 0){
			counter = 1;
			mRgba = inputFrame.rgba();
			
			camera_count++;

			if(camera_index<19)
				camera_index++;
			else
				camera_index = 0;

			Log.d("FELIX","Computing Matches...");

			long startTime = System.currentTimeMillis();
			mycameras[camera_index] = new CameraInstance(mRgba.clone(),cam_height,cam_width);
			mycameras[camera_index].ExtractDescriptors();

			if( mycameras[camera_index].mdescriptors.empty()){
				camera_index--;
				camera_count--;
				return mRgba;
			}


			if(camera_count<2)
				return mRgba;

			//Feature matching
			int last_camera_index;
			if(camera_index > 0)
				last_camera_index = camera_index-1;
			else
				last_camera_index = 19;	

			if(mycameras[last_camera_index].mkeyPoints.empty() || mycameras[camera_index].mkeyPoints.empty()){
				camera_index--;
				camera_count--;
				Log.d("FELIX","keypoints empty");
				return mRgba;
			}

			mmatcher.match(mycameras[camera_index].mdescriptors, mycameras[last_camera_index].mdescriptors, mmatches);
			if(mmatches.empty()){
				camera_index--;
				camera_count--;
				Log.d("FELIX","matches empty");
				return mRgba;
			}
			int cols = mycameras[camera_index].mkeyPoints.cols();
			int rows = mycameras[camera_index].mkeyPoints.rows();
			Log.d("FELIX","Cols: " + cols);
			Log.d("FELIX","Rows: " + rows);
			
			int colsPrev = mycameras[last_camera_index].mkeyPoints.cols();
			int rowsPrev = mycameras[last_camera_index].mkeyPoints.rows();
			Log.d("FELIX","Previous Cols: " + colsPrev);
			Log.d("FELIX","Previous Rows: " + rowsPrev);
			
			
			 if( Math.abs(cols - colsPrev) > 200 || Math.abs(rows - rowsPrev) > 200){
					camera_index--;
					camera_count--;
					Log.d("FELIX","abssar");
					return mRgba;
			 }
			Features2d.drawMatches( mycameras[camera_index].mRgba, mycameras[camera_index].mkeyPoints, mycameras[last_camera_index].mRgba, mycameras[last_camera_index].mkeyPoints,mmatches, corre);

			mycameras[last_camera_index].freememory();
			
			if(camera_count == 2)
			{
				//Find fundamental matrix and initialize first two cameras and 3D-points
				Mat points1 = new Mat(2,mmatches.width(),CvType.CV_64FC1);
				Mat points2 = new Mat(2,mmatches.width(),CvType.CV_64FC1);
				
				findPointPairsFromMatches(mycameras[camera_index].mkeyPoints,mycameras[last_camera_index].mkeyPoints,mmatches,points1,points2);
				MatOfPoint2f m1 = new MatOfPoint2f(points1);
				MatOfPoint2f m2 = new MatOfPoint2f(points2);
				Mat initF = Calib3d.findFundamentalMat(m1, m2);
			}
			else
			{
				//Use PnP to find new camera matrix and triangulate new 3D-points from new correspondancies
			}
			//Oliver
			Size size = mRgba.size();
			Imgproc.resize(corre, mRgba, size);

			long stopTime = System.currentTimeMillis();
			long deltaTime = stopTime -startTime;
			Log.d("FELIX","Computiation time: " + deltaTime);
			 System.gc();
			return mycameras[camera_index].mRgba;
		}
		return mRgba;

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
	
	private void findPointPairsFromMatches(MatOfKeyPoint mkeyPoints1,MatOfKeyPoint mkeyPoints2,MatOfDMatch matches1to2, Mat points1, Mat points2)
	{

		KeyPoint p1[] = mkeyPoints1.toArray();
		KeyPoint p2[] = mkeyPoints2.toArray();
		DMatch correspondaneindices[] = matches1to2.toArray();
		double x1,y1,x2,y2;
		
		for(int i = 0; i<matches1to2.width();i++)
		{
			points1.put(1, i, p1[correspondaneindices[i].queryIdx].pt.x);
			points1.put(2, i, p1[correspondaneindices[i].queryIdx].pt.y);
			
			points2.put(1, i, p1[correspondaneindices[i].trainIdx].pt.x);
			points2.put(2, i, p1[correspondaneindices[i].trainIdx].pt.y);
		}
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

