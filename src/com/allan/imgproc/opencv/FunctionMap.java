package com.allan.imgproc.opencv;

import java.util.HashMap;

public class FunctionMap {
	HashMap<String, ImageProcessObject> mObjectList = new HashMap<String, ImageProcessObject>();
	
	public FunctionMap() {
		ImageProcessObject newObject = new Canny();
		mObjectList.put(newObject.getLabel(),newObject);
		newObject = new Gray();
		mObjectList.put(newObject.getLabel(),newObject);
		newObject = new FindFeatures();
		mObjectList.put(newObject.getLabel(),newObject);
		newObject = new MatchFeatures();
		mObjectList.put(newObject.getLabel(),newObject);
	}
	
	public HashMap<String, ImageProcessObject> getFunctionMap(){
		return mObjectList;
	}
	
	public ImageProcessObject getObject(String s){
		return mObjectList.get(s);
	}

}
