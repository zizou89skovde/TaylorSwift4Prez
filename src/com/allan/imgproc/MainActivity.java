package com.allan.imgproc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.allan.imgproc.R;
import com.allan.imgproc.camera.CameraActivity;
import com.allan.imgproc.design.DesignActivity;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends ListActivity {


	private static final String ITEM_IMAGE = "item_image";
	private static final String ITEM_TITLE = "item_title";
	private static final String ITEM_SUBTITLE = "item_subtitle";	

	public MainActivity() {

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_menu);
		setTitle(R.string.main_title);

		final List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		final SparseArray<Class<? extends Activity>> activityMapping = new SparseArray<Class<? extends Activity>>();	
		int i = 0;

		{
			final Map<String, Object> item = new HashMap<String, Object>();
			item.put(ITEM_IMAGE, R.drawable.cam);
			item.put(ITEM_TITLE, getText(R.string.camera_title));
			item.put(ITEM_SUBTITLE, getText(R.string.camera_subtitle));
			data.add(item);
			activityMapping.put(i++, CameraActivity.class);			
		}

		{
			final Map<String, Object> item = new HashMap<String, Object>();
			item.put(ITEM_IMAGE, R.drawable.back);
			item.put(ITEM_TITLE, getText(R.string.design_title));
			item.put(ITEM_SUBTITLE, getText(R.string.design_subtitle));
			
			data.add(item);
			activityMapping.put(i++, DesignActivity.class);			
		}

		final SimpleAdapter dataAdapter = new SimpleAdapter(this, data, R.layout.main_menu_item, new String[] {ITEM_IMAGE, ITEM_TITLE, ITEM_SUBTITLE}, new int[] {R.id.Image, R.id.Title, R.id.SubTitle});
		setListAdapter(dataAdapter);	

		getListView().setOnItemClickListener(new OnItemClickListener() 
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) 
			{
				final Class<? extends Activity> activityToLaunch = activityMapping.get(position);

				if (activityToLaunch != null)
				{
					final Intent launchIntent = new Intent(MainActivity.this, activityToLaunch);
					startActivity(launchIntent);
				}				
			}
		});
	}



}
