package com.peirr.wizard.page;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.coresthetics.wizard.R;
import com.peirr.wizard.page.base.BasePage;
import com.peirr.wizard.ChoiceItem;
import com.peirr.wizard.widget.ChoiceButton;
import com.peirr.wizard.widget.ChoiceGroup;

public class PageOption2 extends BasePage {

    private ChoiceGroup optionGroup;

    public static PageOption2 newInstance(int position, ChoiceItem[] choices, String key, int titleRes) {
        Bundle args = new Bundle();
        PageOption2 fragment = new PageOption2();
        args.putInt(ARG_POSITION, position);
        args.putParcelableArray(ARG_CHOICES, choices);
        args.putString(ARG_KEY, key);
        args.putInt(ARG_TITLE, titleRes);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.wizard_option_2, container, false);
        optionGroup = view.findViewById(R.id.choice_group);
        TextView title = view.findViewById(R.id.choice_title);
        ChoiceButton option1 = view.findViewById(R.id.choice_1);
        ChoiceButton option2 = view.findViewById(R.id.choice_2);

        final Bundle args = getArguments();
        if (args != null) {
            title.setText(args.getInt(ARG_TITLE));
            key = args.getString(ARG_KEY);
            ChoiceItem[] choices = (ChoiceItem[]) args.getParcelableArray(ARG_CHOICES);
            if (choices != null && choices.length >= 2) {
                option1.setText(getString(choices[0].getTitle()));
                option1.setIcon(choices[0].getIconRes());
                option1.setTag(choices[0]);

                option2.setText(getString(choices[1].getTitle()));
                option2.setIcon(choices[1].getIconRes());
                option2.setTag(choices[1]);
            }
        }

        optionGroup.setOnCheckedChangeListener((group, button, checked, id) -> {
            ChoiceButton cb = optionGroup.findViewById(id);
            ChoiceItem item = (ChoiceItem) cb.getTag();
            Bundle bundle = new Bundle();
            bundle.putString(key, item.getValue());
            onDataChanged(bundle);
        });
        return view;
    }


    @Override
    public boolean isAutoNext() {
        return true;
    }


    @Override
    public boolean isValidData() {
        return optionGroup.getCheckedViewId() != View.NO_ID && optionGroup.getCheckedViewId() != 0;
    }
}
