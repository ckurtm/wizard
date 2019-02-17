package com.peirr.wizard.page;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.coresthetics.wizard.R;
import com.peirr.wizard.page.base.BasePage;

import androidx.fragment.app.Fragment;


public class PageTextEdit extends BasePage {
    private EditText editView;
    private int inputType = InputType.TYPE_CLASS_TEXT;
    public static Fragment newInstance(int position, int inputType, String key, int qstnRes, int hintResource) {
        Bundle args = new Bundle();
        Fragment fragment = new PageTextEdit();
        args.putInt(ARG_POSITION, position);
        args.putInt(ARG_INPUT_TYPE, inputType);
        args.putString(ARG_KEY, key);
        args.putInt(ARG_HINT, hintResource);
        args.putInt(ARG_QUESTION, qstnRes);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.wizard_text_edit, container, false);
        TextView questionView = view.findViewById(R.id.edit_title);
        editView = view.findViewById(R.id.edit_value);

        final Bundle args = getArguments();

        if (args != null) {
            questionView.setText(args.getInt(ARG_QUESTION));
            inputType = args.getInt(ARG_INPUT_TYPE);
            editView.setInputType(inputType);

            key = args.getString(ARG_KEY);

            editView.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (!TextUtils.isEmpty(s)) {
                        if (inputType == InputType.TYPE_CLASS_NUMBER) {
                            bundle.putInt(key, Integer.valueOf(s.toString()));
                        } else if (inputType == (InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL)) {
                            bundle.putFloat(key, Float.valueOf(s.toString()));
                        }
                        onDataChanged(bundle);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }
        return view;
    }

    @Override
    public boolean isValidData() {
        return !TextUtils.isEmpty(editView.getText());
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        setupUnits();
    }

    void setupUnits() {
        if (getWizardData() == null) {
            return;
        }
//        boolean metric = getWizardData().isMetric();
//        if (key.equals(Asses.QSTN_7)) {
//            editView.setHint(MeasureUtils.getHeightUnits(getActivity(), metric));
//        } else if (key.equals(Asses.QSTN_6)) {
//            editView.setHint(MeasureUtils.getWeightUnits(getActivity(), metric));
//        } else if (getArguments() != null) {
//            editView.setHint(getArguments().getInt(ARG_HINT));
//        }
    }
}
