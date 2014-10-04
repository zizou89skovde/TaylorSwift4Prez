package com.allan.imgproc.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;

import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import com.allan.imgproc.opencv.FunctionMap;
import com.allan.imgproc.opencv.ImageProcessObject;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.util.Xml;




public class XMLHandler {

	public static void writeConfigFile(FileOutputStream fOut, ArrayList<ImageProcessObject> mAlgoritmList) throws FileNotFoundException, SAXException{
		XmlSerializer xmlSerializer;
		xmlSerializer = Xml.newSerializer();
		StringWriter writer = new StringWriter();
		try {
			xmlSerializer.setOutput(writer);
			xmlSerializer.startDocument("UTF-8", true);
			
			for(ImageProcessObject o : mAlgoritmList){
				xmlSerializer.startTag("","ITEM");
				xmlSerializer.startTag("",o.getLabel().replaceAll(" ", "_"));
				xmlSerializer.text("A_TOPIC");
				xmlSerializer.endTag("",o.getLabel().replaceAll(" ", "_"));
				xmlSerializer.endTag("","ITEM");
			}
			
			xmlSerializer.endDocument();
			writer.toString();
			fOut.write(writer.toString().getBytes());
			fOut.close();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static ArrayList<ImageProcessObject> readConfigFile(FileInputStream fIn) throws IOException{

		XmlPullParser parser = Xml.newPullParser(); 
		ArrayList<ImageProcessObject> algoritmList = new ArrayList<ImageProcessObject>();
		try {
			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			FunctionMap functionMap = new FunctionMap();

			parser.setInput(fIn, null);
			int eventType = parser.getEventType();
			while (eventType != XmlPullParser.END_DOCUMENT){
				String tag = null;
				switch (eventType){
				case XmlPullParser.START_DOCUMENT:

					break;
				case XmlPullParser.START_TAG:
					tag = parser.getName();
					if (tag.equals("ITEM")) {
						while (parser.next() != XmlPullParser.END_TAG) {
							if (parser.getEventType() != XmlPullParser.START_TAG) {
								continue;
							}
							String itemTag = parser.getName();
							ImageProcessObject newObject = functionMap.getObject(itemTag.replaceAll("_", " "));
							if(newObject != null)
								algoritmList.add(newObject);
						}
					}
					break;
				case XmlPullParser.END_TAG:
					tag = parser.getName();

				}
				eventType = parser.next();
			}

		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return algoritmList;
	}

	public static File getStorageDir(Context context,String filename) {
		File file = new File(context.getExternalFilesDir(null), filename);
		if (!file.mkdirs()) {
			Log.e("ERROR", "Directory not created");
		}
		return file;
	}

	public static boolean isExternalStorageWritable() {
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			return true;
		}
		return false;
	}

	public static String getWorkingFileName(){
		return  "current_config.xml";
	}
	
	public static String getWorkingDir(Context context){
		String packageName = context.getPackageName();
		return Environment.getExternalStorageDirectory().getAbsolutePath()+ "/Android/data/" + packageName + "/files/";
	}
}
