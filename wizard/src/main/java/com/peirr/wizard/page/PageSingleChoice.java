package com.peirr.wizard.page;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.peirr.wizard.ChoiceItem;
import com.coresthetics.wizard.R;
import com.peirr.wizard.page.base.BasePage;
import com.peirr.wizard.ChoiceClickListener;
import com.peirr.wizard.ChoiceItemAdapter;

import java.util.Arrays;

public class PageSingleChoice extends BasePage implements ChoiceClickListener {

    private static final String TAG = "PageSingleChoice";

    private boolean choiceSelected;
    private ChoiceItemAdapter adapter;

    public static PageSingleChoice newInstance(int position, ChoiceItem[] choices, String key, int titleRes) {
        Bundle args = new Bundle();
        PageSingleChoice fragment = new PageSingleChoice();
        args.putInt(ARG_POSITION, position);
        args.putParcelableArray(ARG_CHOICES, choices);
        args.putString(ARG_KEY, key);
        args.putInt(ARG_TITLE, titleRes);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.wizard_single_choice, container, false);
        RecyclerView recycler = view.findViewById(R.id.choices);
        TextView title = view.findViewById(R.id.choice_title);
        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false));

        final Bundle args = getArguments();
        if (args != null) {
            title.setText(args.getInt(ARG_TITLE));
            key = args.getString(ARG_KEY);
            ChoiceItem[] choices = (ChoiceItem[]) args.getParcelableArray(ARG_CHOICES);
            if (choices != null) {
                adapter = new ChoiceItemAdapter(Arrays.asList(choices));
                adapter.setListener(this);
                recycler.setAdapter(adapter);
            }
        }
        return view;
    }

    @Override
    public boolean isAutoNext() {
        return true;
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