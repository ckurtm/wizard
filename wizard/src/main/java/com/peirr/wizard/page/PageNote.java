package com.peirr.wizard.page;

import android.os.Bundle;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.coresthetics.wizard.R;
import com.peirr.wizard.page.base.BasePage;

public class PageNote extends BasePage {

    public static PageNote newInstance(int position,int titleRes) {
        Bundle args = new Bundle();
        PageNote fragment = new PageNote();
        args.putInt(ARG_POSITION, position);
        args.putInt(ARG_TITLE, titleRes);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.wizard_note, container, false);
        TextView title = view.findViewById(R.id.note_title);
        final Bundle args = getArguments();
        if (args != null) {
            title.setText(args.getInt(ARG_TITLE));
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        onDataChanged(getPageData());
    }

    @Override
    public boolean isValidData() {
        return true;
    }
}