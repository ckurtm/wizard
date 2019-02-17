package com.peirr.wizard.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.coresthetics.wizard.R;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;


public class ChoiceButton extends ConstraintLayout implements ChoiceCheckable {


    public enum ChoiceButtonType {HORIZONTAL_1, HORIZONTAL_2, VERTICAL}


    private TextView textView, subtextView;
    private ImageView iconView;

    private String text;
    private String subtext;
    private Drawable drawable;
    private int textColor = View.NO_ID;
    private int subtextColor = textColor;
    private int pressedTextColor;

    private Drawable initBackgroundDrawable;
    private OnClickListener clickListener;
    private OnTouchListener touchListener;
    private boolean checked;
    private ArrayList<OnCheckedChangeListener> checkedChangeListeners = new ArrayList<>();

    private ChoiceButtonType buttonType = ChoiceButtonType.HORIZONTAL_2;

    private static final ChoiceButtonType[] buttonTypes = {
            ChoiceButtonType.HORIZONTAL_1,
            ChoiceButtonType.HORIZONTAL_2,
            ChoiceButtonType.VERTICAL
    };

    //================================================================================
    // Constructors
    //================================================================================

    public ChoiceButton(Context context) {
        super(context);
        pressedTextColor = ViewUtils.getColor(context, R.attr.colorAccent);
        setupView();
    }

    public ChoiceButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        parseAttributes(attrs, 0, R.style.ChoiceButtonStyle);
        setupView();

    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    public ChoiceButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        parseAttributes(attrs, defStyleAttr, R.style.ChoiceButtonStyle);
        setupView();
    }

//    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//    public ChoiceButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//        parseAttributes(attrs,defStyleAttr, defStyleRes);
//        setupView();
//    }

    //================================================================================
    // Init & inflate methods
    //================================================================================

    private void parseAttributes(AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.ChoiceButton, defStyleAttr, defStyleRes);
        try {
            text = a.getString(R.styleable.ChoiceButton_cb_text);
            subtext = a.getString(R.styleable.ChoiceButton_cb_subtext);
            int resId = a.getResourceId(R.styleable.ChoiceButton_cb_icon, View.NO_ID);
            checked = a.getBoolean(R.styleable.ChoiceButton_cb_selected, false);
            if (resId != View.NO_ID) {
                drawable = AppCompatResources.getDrawable(getContext(), resId);
            }
            final int index = a.getInt(R.styleable.ChoiceButton_cb_type, -1);

            if (index >= 0) {
                buttonType = buttonTypes[index];
            }
            textColor = a.getColor(R.styleable.ChoiceButton_cb_text_color, Color.WHITE);
            subtextColor = a.getColor(R.styleable.ChoiceButton_cb_subtext_color, textColor);
            pressedTextColor = a.getColor(R.styleable.ChoiceButton_cb_pressed_color,
                    ViewUtils.getColor(getContext(), R.attr.colorAccent));
        } finally {
            a.recycle();
        }
    }

    public void setData(int text, int subtext, int d) {
        Context c = getContext();
        if (text != View.NO_ID && text != 0) {
            this.text = c.getString(text);
        }
        if (subtext != View.NO_ID && subtext != 0) {
            this.subtext = c.getString(subtext);
        }
        if (d != View.NO_ID && d != 0) {
            this.drawable = ContextCompat.getDrawable(c, d);
        }
        bindView();
    }


    public void setText(String txt) {
        this.text = txt;
        textView.setText(txt);
    }

    public void setSubText(String txt) {
        this.subtext = txt;
        subtextView.setText(txt);
    }

    public void setIcon(int drawableResource) {
        drawable = AppCompatResources.getDrawable(getContext(), drawableResource);
        iconView.setImageDrawable(drawable);
    }

    public void setData(String text, String subtext, int d) {
        Context c = getContext();
        this.text = text;
        this.subtext = subtext;
        if (d != View.NO_ID && d != 0) {
            this.drawable = ContextCompat.getDrawable(c, d);
        }
        bindView();
    }

    // Template method
    private void setupView() {
        inflateView();
        bindView();
        setCustomTouchListener();
        if (checked) {
            setCheckedState();
        } else {
            setNormalState();
        }
    }

    protected void inflateView() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        switch (buttonType) {
            case HORIZONTAL_1:
                inflater.inflate(R.layout.widget_cb_h1, this, true);
                break;
            case HORIZONTAL_2:
                inflater.inflate(R.layout.widget_cb_h2, this, true);
                break;
            case VERTICAL:
                inflater.inflate(R.layout.widget_cb_v, this, true);
                break;
        }
        textView = findViewById(R.id.choice_text);
        subtextView = findViewById(R.id.choice_subtext);
        iconView = findViewById(R.id.choice_icon);
        initBackgroundDrawable = getBackground();
        if (textColor == View.NO_ID) {
            textColor = subtextColor = textView.getCurrentTextColor();
        }
    }

    protected void bindView() {
        if (subtextColor != View.NO_ID) {
            subtextView.setTextColor(subtextColor);
        }

        if (textColor != View.NO_ID) {
            textView.setTextColor(textColor);
        }

        if (drawable != null) {
            iconView.setImageDrawable(drawable);
        }
        iconView.setColorFilter(textColor, PorterDuff.Mode.SRC_ATOP);
        subtextView.setText(subtext);
        textView.setText(text);
    }


    //================================================================================
    // Overriding default behavior
    //================================================================================

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        clickListener = l;
    }

    protected void setCustomTouchListener() {
        super.setOnTouchListener(new TouchListener());
    }

    @Override
    public void setOnTouchListener(OnTouchListener onTouchListener) {
        touchListener = onTouchListener;
    }

    public OnTouchListener getOnTouchListener() {
        return touchListener;
    }

    private void onTouchDown(MotionEvent motionEvent) {
        setChecked(true);
    }

    private void onTouchUp(MotionEvent motionEvent) {
        // Handle user defined click listeners
        if (clickListener != null) {
            clickListener.onClick(this);
        }
    }
    //================================================================================
    // Public methods
    //================================================================================

    public void setCheckedState() {
//        setBackgroundResource(R.drawable.bg_choice_btn_pressed);
        textView.setTextColor(pressedTextColor);
        subtextView.setTextColor(pressedTextColor);
        iconView.setColorFilter(pressedTextColor, PorterDuff.Mode.SRC_ATOP);
    }

    public void setNormalState() {
        setBackground(initBackgroundDrawable);
        textView.setTextColor(textColor);
        subtextView.setTextColor(subtextColor);
        iconView.setColorFilter(textColor, PorterDuff.Mode.SRC_ATOP);
    }

    public String getValue() {
        return text;
    }

    public void setValue(String value) {
        text = value;
    }

    public String getUnit() {
        return subtext;
    }

    public void setUnit(String unit) {
        subtext = unit;
    }

    //================================================================================
    // Checkable implementation
    //================================================================================

    @Override
    public void setChecked(boolean checked) {
        if (this.checked != checked) {
            this.checked = checked;
            if (!checkedChangeListeners.isEmpty()) {
                for (int i = 0; i < checkedChangeListeners.size(); i++) {
                    checkedChangeListeners.get(i).onCheckedChanged(this, this.checked);
                }
            }
            if (this.checked) {
                setCheckedState();
            } else {
                setNormalState();
            }
        }
    }

    @Override
    public boolean isChecked() {
        return checked;
    }

    @Override
    public void toggle() {
        setChecked(!checked);
    }

    @Override
    public void addOnCheckChangeListener(OnCheckedChangeListener onCheckedChangeListener) {
        checkedChangeListeners.add(onCheckedChangeListener);
    }

    @Override
    public void removeOnCheckChangeListener(OnCheckedChangeListener onCheckedChangeListener) {
        checkedChangeListeners.remove(onCheckedChangeListener);
    }

    //================================================================================
    // Inner classes
    //================================================================================
    private final class TouchListener implements OnTouchListener {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    onTouchDown(event);
                    break;
                case MotionEvent.ACTION_UP:
                    onTouchUp(event);
                    break;
            }
            if (touchListener != null) {
                touchListener.onTouch(v, event);
            }
            return true;
        }
    }
}
