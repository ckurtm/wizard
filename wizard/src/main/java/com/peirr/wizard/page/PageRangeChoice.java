package com.peirr.wizard.page;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.peirr.wizard.ChoiceClickListener;
import com.peirr.wizard.ChoiceItem;
import com.coresthetics.wizard.R;
import com.peirr.wizard.page.base.BasePage;
import com.warkiz.widget.IndicatorSeekBar;
import com.warkiz.widget.IndicatorStayLayout;
import com.warkiz.widget.IndicatorType;
import com.warkiz.widget.OnSeekChangeListener;
import com.warkiz.widget.SeekParams;
import com.warkiz.widget.TickMarkType;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

public class PageRangeChoice extends BasePage implements ChoiceClickListener {

    private static final String TAG = "PageSingleChoice";

    private boolean choiceSelected;

    public static PageRangeChoice newInstance(int position, ChoiceItem[] choices, String key, int titleRes) {
        Bundle args = new Bundle();
        PageRangeChoice fragment = new PageRangeChoice();
        args.putInt(ARG_POSITION, position);
        args.putParcelableArray(ARG_CHOICES, choices);
        args.putString(ARG_KEY, key);
        args.putInt(ARG_TITLE, titleRes);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.wizard_range_choice, container, false);
        TextView title = view.findViewById(R.id.choice_title);
        IndicatorStayLayout layout = view.findViewById(R.id.choice_range);


        final Bundle args = getArguments();
        if (args != null) {
            title.setText(args.getInt(ARG_TITLE));
            key = args.getString(ARG_KEY);
            ChoiceItem[] choices = (ChoiceItem[]) args.getParcelableArray(ARG_CHOICES);
            if (choices != null) {
                String[] values = new String[choices.length];
                for (int i = 0; i < choices.length; i++) {
                    values[i] = choices[i].getValue();
                }

                int color = ContextCompat.getColor(requireActivity(), R.color.colorAccent);
                IndicatorSeekBar seekBar = IndicatorSeekBar
                        .with(requireActivity())
                        .max(choices.length)
                        .min(0)
                        .progress(choices.length / 2)
                        .tickCount(6)
                        .showTickMarksType(TickMarkType.NONE)
                        .showTickTexts(true)
                        .indicatorColor(color)
                        .indicatorTextColor(ContextCompat.getColor(requireActivity(), android.R.color.white))
                        .showIndicatorType(IndicatorType.RECTANGLE)
                        .thumbColorStateList(ContextCompat.getColorStateList(requireActivity(), R.color.colorAccent))
                        .thumbSize(18)
                        .thumbColor(color)
                        .trackProgressColor(color)
                        .tickTextsColor(ContextCompat.getColor(requireActivity(), android.R.color.white))
                        .trackProgressSize(4)
                        .trackBackgroundSize(2)
                        .build();

                layout.attachTo(seekBar);
                seekBar.setOnSeekChangeListener(new OnSeekChangeListener() {
                    @Override
                    public void onSeeking(SeekParams seekParams) {
                        if (seekParams.fromUser) {
                            choiceSelected = true;
                            bundle.putString(key, choices[seekParams.thumbPosition].getValue());
                        }
                    }

                    @Override
                    public void onStartTrackingTouch(IndicatorSeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(IndicatorSeekBar seekBar) {

                    }
                });
            }
        }
        return view;
    }

    @Override
    public boolean isAutoNext() {
        return false;
    }


    @Override
    public boolean isValidData() {
        return choiceSelected;
    }

    @Override
    public void onSelectedChoice(ChoiceItem item) {
        choiceSelected = true;
        bundle.putString(key, item.getValue());
        onDataChanged(bundle);
    }
}