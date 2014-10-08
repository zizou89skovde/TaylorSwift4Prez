package com.allan.imgproc.oliver;

import java.util.ArrayList;

import com.allan.imgproc.R;

import android.app.Activity;
import android.hardware.Sensor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class OliverActivity extends Activity{
	ArrayList<SensorData> mSensors;
	int mCurrentIndex = 0;
	int mSensorTypeList[];
	public OliverActivity() {
	 
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sensor_menu);
		mSensors = new ArrayList<SensorData>();
		mSensors.add(new SensorData(this,Sensor.TYPE_MAGNETIC_FIELD));
		mSensors.add(new SensorData(this,Sensor.TYPE_ROTATION_VECTOR));
		mSensors.add(new SensorData(this,Sensor.TYPE_GRAVITY));
		setTexts();
		setButtons();
	
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		for (SensorData s: mSensors)
			s.onResume();
	}
	
	/*
	 * Set layout texts. 
	 * Text boxes will contain info about the selected sensors and 
	 * their current values.
	 */
	private void setTexts(){
		TextView sensor1  	  = (TextView) findViewById(R.id.sensor_text_1);
		TextView sensor2  	  = (TextView) findViewById(R.id.sensor_text_2);
		TextView sensor3  = (TextView) findViewById(R.id.sensor_text_3);
		
		String sensorInfo = mSensors.get(mCurrentIndex).getSensorInfo();
		sensorInfo += "\n Values:";
		float[] value =mSensors.get(mCurrentIndex).getSensorValue();
		for (int i = 0; i < value.length; i++) {
			sensorInfo += "\n" + value[i];
		}
		sensor1.setText(sensorInfo);
	
		sensorInfo = mSensors.get(mCurrentIndex+1).getSensorInfo();
		sensorInfo += "\n Values:";
		value = mSensors.get(mCurrentIndex+1).getSensorValue();
		for (int i = 0; i < value.length; i++) {
			sensorInfo += "\n" + value[i];
		}
		sensor2.setText(sensorInfo);
		
		sensorInfo = mSensors.get(mCurrentIndex+2).getSensorInfo();
		sensorInfo += "\n Values:";
		value = mSensors.get(mCurrentIndex+2).getSensorValue();
		for (int i = 0; i < value.length; i++) {
			sensorInfo += "\n" + value[i];
		}
		sensor3.setText(sensorInfo);
		
	}
	
	private void setButtons(){
		Button buttonNext  = (Button) findViewById(R.id.ButtonNext);
		buttonNext.setText("Update Sensors");
		buttonNext.setOnClickListener(new OnClickListener() {	
			@Override
			public void onClick(View v) {
//				if(mCurrentIndex < mSensorTypeList.length -1){
//					mCurrentIndex++;
					setTexts();
//				}

			}
		});
		
		// Knapparna nedan används ej
		Button buttonPrev  = (Button) findViewById(R.id.ButtonPrev);
		buttonPrev.setVisibility(Button.INVISIBLE);
		buttonPrev.setOnClickListener(new OnClickListener() {	
			@Override
			public void onClick(View v) {
//				if(mCurrentIndex > 0){
//					mCurrentIndex--;
//					setTexts();
//				}

			}
		});

		Button buttonCheck  = (Button) findViewById(R.id.ButtonCheck);
		buttonCheck.setVisibility(Button.INVISIBLE);
		buttonCheck.setOnClickListener(new OnClickListener() {	
			@Override
			public void onClick(View v) {
//				setTexts();

			}
		});

	}
	
	
}
