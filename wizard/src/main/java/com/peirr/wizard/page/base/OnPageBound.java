package com.peirr.wizard.page.base;

import android.os.Bundle;

/**
 * Created by Kurt on 2017/11/29.
 */

public interface OnPageBound<T> {
    void onDataChanged(Bundle bundle, int position);
    T getData();
}