package com.allan.imgproc.camera;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2;

import com.allan.imgproc.R;
import com.allan.imgproc.opencv.ImageProcessObject;
import com.allan.imgproc.xml.XMLHandler;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.Toast;

public class CameraActivity extends Activity implements CvCameraViewListener2 {
	private static final String TAG = "CAMERA";
	private CameraBridgeViewBase mOpenCvCameraView;
	private boolean              mIsJavaCamera = true;

	AlgoritmApplier mAlgoritmApplier;

	private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
		@Override
		public void onManagerConnected(int status) {
			switch (status) {
			case LoaderCallbackInterface.SUCCESS:
			{
				//                    Log.i(TAG, "OpenCV loaded successfully");
				mOpenCvCameraView.enableView();
			} break;
			default:
			{
				super.onManagerConnected(status);
			} break;
			}
		}
	};

	public CameraActivity() throws IOException {

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		setContentView(R.layout.camera_menu);

		loadConfiguration();
		if (mIsJavaCamera)
			mOpenCvCameraView = (CameraBridgeViewBase) findViewById(R.id.camera_surface_view);

		mOpenCvCameraView.setVisibility(SurfaceView.VISIBLE);
		mOpenCvCameraView.setCvCameraViewListener(this);
	}


	private void loadConfiguration(){
		String dirPath 	= XMLHandler.getWorkingDir(this);
		String fileName = XMLHandler.getWorkingFileName();

		String filePath = dirPath + fileName;
		File file = new File(filePath);
		if(file.exists()){
			FileInputStream fIn;
			try {
				fIn = new FileInputStream(filePath);
				ArrayList<ImageProcessObject> tempList = XMLHandler.readConfigFile(fIn);
				for (ImageProcessObject imageProcessObject : tempList) {
					Toast.makeText(getApplicationContext(),"Item read from file: " +  imageProcessObject.getLabel(), Toast.LENGTH_SHORT).show();

				}
				mAlgoritmApplier = new AlgoritmApplier(tempList); 
			}catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}else{
			mAlgoritmApplier = new AlgoritmApplier(new ArrayList<ImageProcessObject>()); 
		}  
	}
	@Override
	public void onPause()
	{
		super.onPause();
		if (mOpenCvCameraView != null)
			mOpenCvCameraView.disableView();
	}

	@Override
	public void onResume()
	{
		super.onResume();
		OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_3, this, mLoaderCallback);
	}

	public void onDestroy() {
		super.onDestroy();
		if (mOpenCvCameraView != null)
			mOpenCvCameraView.disableView();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return true;
	}

	public void onCameraViewStarted(int width, int height) {
		mAlgoritmApplier.initialize(width, height);
	}

	public void onCameraViewStopped() {
		mAlgoritmApplier.release();
	}

	public Mat onCameraFrame(CvCameraViewFrame inputFrame) {     
		return mAlgoritmApplier.applyAlgoritm(inputFrame);
	}
}
