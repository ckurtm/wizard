package com.peirr.wizard.utils;

import android.content.Context;

import com.coresthetics.wizard.R;


/**
 * Created by Kurt on 2017/12/22.
 */

public final class MeasureUtils {

    public static String getWeightUnits(Context context, boolean metric) {
        return context.getString(metric ? R.string.measure_weight_metric : R.string.measure_weight_imperial);
    }

    public static String getHeightUnits(Context context, boolean metric) {
        return context.getString(metric ? R.string.measure_height_metric : R.string.measure_height_imperial);
    }

    public static double lbs2Kg(double pounds) {
        return pounds * 0.453592f;
    }

    public static float kg2Lbs(float kgs) {
        return kgs / 0.453592f;
    }

    public static double ft2cm(double feet, double inches) {
        return Math.ceil(feet * 30.48) + (inches * 2.54);
    }

    public static int[] cm2ft(double cm) {
        int feet, inches;
        feet = (int) Math.floor((cm / 2.54) / 12);
        inches = (int) Math.ceil((cm / 2.54) - (feet * 12));
        return new int[]{feet, inches};
    }
}
