package com.peirr.wizard.page;

import android.os.Bundle;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.coresthetics.wizard.R;
import com.peirr.wizard.page.base.BasePage;
import com.peirr.wizard.ChoiceItem;
import com.peirr.wizard.widget.ChoiceButton;
import com.peirr.wizard.widget.ChoiceGroup;


public class PageSingleFixedChoice extends BasePage {

    private ChoiceGroup choiceGroup;

    public static PageSingleFixedChoice newInstance(int position, ChoiceItem[] choices, String key, int titleRes) {
        Bundle args = new Bundle();
        PageSingleFixedChoice fragment = new PageSingleFixedChoice();
        args.putInt(ARG_POSITION, position);
        args.putParcelableArray(ARG_CHOICES, choices);
        args.putString(ARG_KEY, key);
        args.putInt(ARG_TITLE, titleRes);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.wizard_single_fixed_choice, container, false);
        choiceGroup = view.findViewById(R.id.choice_group);
        TextView title = view.findViewById(R.id.choice_title);

        final Bundle args = getArguments();
        if (args != null) {
            title.setText(args.getInt(ARG_TITLE));
            key = args.getString(ARG_KEY);
            ChoiceItem[] choices = (ChoiceItem[]) args.getParcelableArray(ARG_CHOICES);
            if (choices != null) {
                for (ChoiceItem item : choices) {
                    choiceGroup.addView(createButton(item));
                }
            }
        }

        choiceGroup.setOnCheckedChangeListener((group, button, checked, id) -> {
            ChoiceButton cb = choiceGroup.findViewById(id);
            ChoiceItem item = (ChoiceItem) cb.getTag();
            Bundle bundle = new Bundle();
            bundle.putString(key, item.getValue());
            onDataChanged(bundle);
        });
        return view;
    }

    @Override
    public boolean isValidData() {
        return choiceGroup.getCheckedViewId() != View.NO_ID && choiceGroup.getCheckedViewId() != 0;
    }

    private ChoiceButton createButton(ChoiceItem item) {
        ChoiceButton button = new ChoiceButton(getActivity());
        button.setData(item.getTitle(),item.getFooter(), item.getIconRes());
        button.setTag(item);
        button.setClickable(true);
        return button;
    }
}