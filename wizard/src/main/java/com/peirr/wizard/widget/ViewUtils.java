package com.peirr.wizard.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.TypedValue;

public class ViewUtils {

    public static int getColor(Context context,int colorAttr) {
        TypedValue typedValue = new TypedValue();
        TypedArray a = context.obtainStyledAttributes(typedValue.data, new int[] { colorAttr });
        int color = a.getColor(0, 0);
        a.recycle();
        return color;
    }
}
