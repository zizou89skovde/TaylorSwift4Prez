package com.allan.imgproc.oliver;

import com.allan.imgproc.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.renderscript.RenderScript;
import android.renderscript.Allocation;


public class RenderScriptTest {
	private ScriptC_process mScript; 
	private  RenderScript mRenderScript;
	private  Allocation mInAllocation;
	private Allocation mOutAllocation;
	Bitmap mInBitmap;
	Bitmap mOutBitmap;
	public RenderScriptTest() {
		
	}

	public  void runScript(Context context)
	{
		mRenderScript = RenderScript.create(context);
		mScript =  new ScriptC_process(mRenderScript);
		mInBitmap = loadBitmap(R.drawable.edin, context);
		mOutBitmap = Bitmap.createBitmap(mInBitmap.getWidth(), mInBitmap.getHeight(), mInBitmap.getConfig());
		 mInAllocation = Allocation.createFromBitmap(mRenderScript, mInBitmap,
                 Allocation.MipmapControl.MIPMAP_NONE,
                 Allocation.USAGE_SCRIPT);
		mOutAllocation =Allocation.createFromBitmap(mRenderScript, mOutBitmap,
                Allocation.MipmapControl.MIPMAP_NONE,
                Allocation.USAGE_SCRIPT);
		
		mScript.forEach_root(mInAllocation, mOutAllocation);
		
		mOutAllocation.copyTo(mOutBitmap);
		
	}
	
	private Bitmap loadBitmap(int resource,Context context) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap b = BitmapFactory.decodeResource(context.getResources(), resource, options);
        Bitmap b2 = Bitmap.createBitmap(b.getWidth(), b.getHeight(), b.getConfig());
        Canvas c = new Canvas(b2);
        c.drawBitmap(b, 0, 0, null);
        b.recycle();
        return b2;
    }
	 public Bitmap getResult(){
		 return mOutBitmap;
	 }
	

}
