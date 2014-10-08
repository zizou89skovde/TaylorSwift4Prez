package com.allan.imgproc.oliver;

import com.allan.imgproc.R;
import android.content.Context;
import android.renderscript.RenderScript;
import android.renderscript.Allocation;


public class RenderScriptTest {
	ScriptC_process mScript; 
	RenderScript mRenderScript;
	public RenderScriptTest(Context context) {
		mRenderScript = RenderScript.create(context);
		mScript =  new ScriptC_process(mRenderScript);
	}

	private void load()
	{

	}


}
