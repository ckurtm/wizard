package com.peirr.wizard.page.base;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;

/**
 * Created by Kurt on 2017/11/29.
 */

public abstract class BasePage<T> extends Fragment {

    private OnPageBound<T> binder;
    protected int position;
    protected boolean valueSet;
    protected String key;
    protected Bundle bundle = new Bundle();
    public static final String ARG_POSITION = "frag_position";
    public static final String ARG_VALUE = "frag_value";
    public static final String ARG_VALUE2 = "frag_value_2";
    public static final String ARG_CHOICES = "choices";
    public static final String ARG_TITLE = "frag_title";
    public static final String ARG_KEY = "frag_key";
    public static final String ARG_HINT = "frag_hint";
    public static final String ARG_INPUT_TYPE = "frag_input_type";
    public static final String ARG_QUESTION = "frag_qstn";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            position = getArguments().getInt(ARG_POSITION);
        }
    }

    public void onDataChanged(Bundle bundle) {
        if (binder != null) {
            binder.onDataChanged(bundle, position);
        }
    }

    public Bundle getPageData() {
        return bundle;
    }

    public boolean isAutoNext() {
        return false;
    }

    public T getWizardData() {
        if (binder == null) {
            return null;
        }
        return binder.getData();
    }

    void attachBinder(){
        Context context = getActivity();
        if (context instanceof OnPageBound) {
            binder = (OnPageBound<T>) context;
        } else if (getParentFragment() != null && getParentFragment() instanceof OnPageBound) {
            binder = (OnPageBound<T>) getParentFragment();
        } else {
            throw new RuntimeException(context.toString() + " must implement OnPageBound");
        }
    }

    public void detachBinder(){
        binder = null;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        attachBinder();
    }

    @Override
    public void onDetach() {
        super.onDetach();
       detachBinder();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        detachBinder();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isResumed()) {
            onDataChanged(getPageData());
        }
    }

    public abstract boolean isValidData();

    public static class OnBoardingTextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }
}
