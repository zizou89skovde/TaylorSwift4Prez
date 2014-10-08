package com.allan.imgproc.design;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.xml.sax.SAXException;

import com.allan.imgproc.R;
import com.allan.imgproc.opencv.FunctionMap;
import com.allan.imgproc.opencv.ImageProcessObject;
import com.allan.imgproc.xml.XMLHandler;

import android.app.ExpandableListActivity;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.TextView;
import android.widget.Toast;



public class DesignActivity extends ExpandableListActivity {
	ExpandableListAdapter mListAdapter;
	ExpandableListView mExpandableListView;
	List<String> mListHeader;
	HashMap<String, List<String>> mListData;
	HashMap<String, ImageProcessObject> mObjectMap;
	ArrayList<ImageProcessObject> mAlgoritmList = new ArrayList<ImageProcessObject>();

	int currentIndex;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.design_menu);
		setTitle(R.string.design_title);
		currentIndex = -1;
		mExpandableListView = (ExpandableListView) findViewById(android.R.id.list);

		instertListData();

		final ExpandableListAdapter listAdapter = new ExpandableListAdapter(this, mListHeader, mListData) ;
		setListAdapter(listAdapter);

		setListListeners();
		setButtons();
		setTexts();
	}

	private void setListListeners(){

		mExpandableListView.setOnGroupClickListener(new OnGroupClickListener() {

			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {
				return false;
			}
		});

		mExpandableListView.setOnGroupExpandListener(new OnGroupExpandListener() {
			public void onGroupExpand(int groupPosition) {

			}
		});

		mExpandableListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {

			@Override
			public void onGroupCollapse(int groupPosition) {

			}
		});

		mExpandableListView.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				String item =  mListData.get(mListHeader.get(groupPosition)).get(childPosition);
				ImageProcessObject mNewObject = mObjectMap.get(item);
				addNewItem(mNewObject);

				return false;
			}
		});
	}
	
	private void addNewItem(ImageProcessObject mNewObject){
		if(currentIndex == mAlgoritmList.size() -1  || mAlgoritmList.size() == 0){
			mAlgoritmList.add(mNewObject);
			currentIndex++;
		}else{
			mAlgoritmList.remove(currentIndex);
			mAlgoritmList.add(currentIndex, mNewObject);
		}
//		Toast.makeText(getApplicationContext(),"Selected Item: " + item, Toast.LENGTH_SHORT).show();
		setTexts();
	}

	private void instertListData() {
		mListHeader 	= new ArrayList<String>();
		mListData 		= new HashMap<String, List<String>>();


		// Adding items to the Function drop down menu
		FunctionMap mHelper = new FunctionMap();
		mObjectMap  	= mHelper.getFunctionMap();
		mListHeader.add("Item List");
		
		List<String> itemList = new ArrayList<String>();
		for ( ImageProcessObject o : mObjectMap.values()) {
			itemList.add(o.getLabel());
		}
		mListData.put(mListHeader.get(0), itemList);
		/*List<String> editOptions = new ArrayList<String>();
		editOptions.add("")
		mListHeader.add("Edit Items");
		mListData.put(mListHeader.get(1), editOptions);*/

	}

	private void setButtons(){


		Button buttonNext  = (Button) findViewById(R.id.ButtonNext);
		buttonNext.setOnClickListener(new OnClickListener() {	
			@Override
			public void onClick(View v) {
				if(currentIndex < mAlgoritmList.size() -1){
					currentIndex++;
					setTexts();
				}

			}
		});
		Button buttonPrev  = (Button) findViewById(R.id.ButtonPrev);
		buttonPrev.setOnClickListener(new OnClickListener() {	
			@Override
			public void onClick(View v) {
				if(currentIndex > 0){
					currentIndex--;
					setTexts();
				}

			}
		});

		Button buttonCheck  = (Button) findViewById(R.id.ButtonCheck);
		buttonCheck.setOnClickListener(new OnClickListener() {	
			@Override
			public void onClick(View v) {
				WriteWorkingFile();


			}
		});

	}

	@Override
	protected void onDestroy() {
		WriteWorkingFile();
		super.onDestroy();
	}

	private void WriteWorkingFile(){


		String dirPath 	= XMLHandler.getWorkingDir(this);
		String fileName = XMLHandler.getWorkingFileName();
		try {
			boolean exists = (new File(dirPath)).exists();
			if (!exists) {
				new File(dirPath).mkdirs();
			}

			String filePath = dirPath + fileName;
			File file = new File(filePath);
			if(file.exists()){
				file.delete();
				Toast.makeText(getApplicationContext(),"Old File deleted", Toast.LENGTH_SHORT).show();
			}
			FileOutputStream fOut = new FileOutputStream(filePath,true);
			XMLHandler.writeConfigFile(fOut,mAlgoritmList);

			file = new File(filePath);
			if(file.exists()){

				FileInputStream fIn = new FileInputStream(filePath);
				Toast.makeText(getApplicationContext(),"New file created, size " +  fIn.available(), Toast.LENGTH_SHORT).show();
				ArrayList<ImageProcessObject> testList = XMLHandler.readConfigFile(fIn);
				for (ImageProcessObject imageProcessObject : testList) {
					Toast.makeText(getApplicationContext(),"New Item stored to file: " +  imageProcessObject.getLabel(), Toast.LENGTH_SHORT).show();
				}
			}


		} catch (IOException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
	}

	private void setTexts(){
		TextView textNext  	  = (TextView) findViewById(R.id.DesignTextNext);
		TextView textPrev  	  = (TextView) findViewById(R.id.DesignTextPrev);
		TextView textCurrent  = (TextView) findViewById(R.id.DesignTextCurrent);
		if(mAlgoritmList.size() == 0){
			textNext.setText("Output");
			textCurrent.setText("Empty Slot");
			textPrev.setText("Input");

		}else{

			if(currentIndex >= mAlgoritmList.size() - 1)
				textNext.setText("Output");
			else
				textNext.setText(mAlgoritmList.get(currentIndex + 1).getLabel());

			if(currentIndex == 0)
				textPrev.setText("Input");
			else
				textPrev.setText(mAlgoritmList.get(currentIndex - 1).getLabel());

			textCurrent.setText(mAlgoritmList.get(currentIndex).getLabel());
		}
	}


}
