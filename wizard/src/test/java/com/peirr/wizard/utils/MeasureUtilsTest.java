package com.peirr.wizard.utils;

import  org.junit.Assert;

import org.junit.Test;

public class MeasureUtilsTest {
    @Test
    public void lbs2Kg() {
        double kgs = MeasureUtils.lbs2Kg(194.00694274902344);
        Assert.assertEquals(88, Math.round(kgs));
    }

    @Test
    public void kg2Lbs() {
        double lbs = MeasureUtils.kg2Lbs(88);
        Assert.assertEquals(194, Math.round(lbs));
    }

    @Test
    public void ft2cm() {
        double cm = MeasureUtils.ft2cm(5, 10);
        Assert.assertEquals(178.4, cm,0.01);
    }

    @Test
    public void cm2ft() {
        int[] res = MeasureUtils.cm2ft(177.7);
        Assert.assertEquals(5, res[0]);
        Assert.assertEquals(10, res[1]);
    }
}