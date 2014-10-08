package com.allan.imgproc.oliver;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.Toast;

public class SensorData implements SensorEventListener{
	private SensorManager mSensorManager;
	private Sensor mSensor;
	private String mLabel;
	private float[] mSensorValue;
	private int mSensorAccuracy;
	/*
	 * This function should be called from an activity's 
	 * "onCreate" routine. A list of demanded sensor types
	 * are passed as arguement.
	 */
	public SensorData(Context context,int sensorType){
		mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);		
		mSensor = selectSensor(sensorType);
		mSensorValue = new float[3];
		if(mSensor !=null){
			mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
		}else{
			Toast.makeText(context,"Sensor type: " + sensorType + " not found", Toast.LENGTH_SHORT).show();
		}
		
	}
	
	
	public float[] getSensorValue(){
		if(mSensor.getType() == Sensor.TYPE_ROTATION_VECTOR){
			 float[] R = new float[9];
			 SensorManager.getRotationMatrixFromVector(R,mSensorValue);
			 float[] orientation = new float[3];
			 SensorManager.getOrientation(R, orientation);
			 orientation[0] = (float) Math.toDegrees(orientation[0]);
			 orientation[1] = (float) Math.toDegrees(orientation[1]);
			 orientation[2] = (float) Math.toDegrees(orientation[2]);
			 return orientation;
			 
		}
		return mSensorValue.clone();
	}
	
	public String getSensorInfo(){
		String info = "Sensor Type:" + mSensor.getName() + "\n" + "Sensor Vendor:" +  mSensor.getVendor(); 	
		return  info;
	}

	private Sensor selectSensor(int sensorType){
		Sensor selectedSensor = mSensorManager.getDefaultSensor(sensorType);
		return selectedSensor;
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		mSensorValue = new float[event.values.length];
		mSensorValue = event.values.clone();
	
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		mSensorAccuracy = accuracy;
		
	}
	
	public void onPause() {
		mSensorManager.unregisterListener(this);
		
	}

	public void onResume() {
		mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
	}


}
