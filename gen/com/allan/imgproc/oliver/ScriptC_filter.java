/*
 * Copyright (C) 2011-2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/*
 * This file is auto-generated. DO NOT MODIFY!
 * The source Renderscript file: C:\\Users\\Datorn\\Desktop\\AndroidProjekt\\ImgProc\\TaylorSwift4Prez\\src\\filter.rs
 */
package com.allan.imgproc.oliver;

import android.renderscript.*;
import android.content.res.Resources;

/**
 * @hide
 */
public class ScriptC_filter extends ScriptC {
    private static final String __rs_resource_name = "filter";
    // Constructor
    public  ScriptC_filter(RenderScript rs) {
        this(rs,
             rs.getApplicationContext().getResources(),
             rs.getApplicationContext().getResources().getIdentifier(
                 __rs_resource_name, "raw",
                 rs.getApplicationContext().getPackageName()));
    }

    public  ScriptC_filter(RenderScript rs, Resources resources, int id) {
        super(rs, resources, id);
        __U16 = Element.U16(rs);
    }

    private Element __U16;
    private final static int mExportVarIdx_gIn = 0;
    private Allocation mExportVar_gIn;
    public void bind_gIn(Allocation v) {
        mExportVar_gIn = v;
        if (v == null) bindAllocation(null, mExportVarIdx_gIn);
        else bindAllocation(v, mExportVarIdx_gIn);
    }

    public Allocation get_gIn() {
        return mExportVar_gIn;
    }

    private final static int mExportVarIdx_gOut = 1;
    private Allocation mExportVar_gOut;
    public void bind_gOut(Allocation v) {
        mExportVar_gOut = v;
        if (v == null) bindAllocation(null, mExportVarIdx_gOut);
        else bindAllocation(v, mExportVarIdx_gOut);
    }

    public Allocation get_gOut() {
        return mExportVar_gOut;
    }

    private final static int mExportForEachIdx_root = 0;
    public Script.KernelID getKernelID_root() {
        return createKernelID(mExportForEachIdx_root, 1, null, null);
    }

    public void forEach_root(Allocation ain) {
        forEach_root(ain, null);
    }

    public void forEach_root(Allocation ain, Script.LaunchOptions sc) {
        // check ain
        if (!ain.getType().getElement().isCompatible(__U16)) {
            throw new RSRuntimeException("Type mismatch with U16!");
        }
        forEach(mExportForEachIdx_root, ain, null, null, sc);
    }

}

