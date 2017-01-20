package rosko.bojan.rupko.preferences;

import android.content.Context;
import android.content.res.TypedArray;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.NumberPicker;
import android.widget.SeekBar;

import java.text.AttributedCharacterIterator;
import java.util.ArrayList;

/**
 * Created by rols on 1/20/17.
 */

public class NumberPickerPreference extends DialogPreference {

    private NumberPicker picker;
    private float value;
    private int index;
    private String[] arrayItems;

    public NumberPickerPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public NumberPickerPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public NumberPickerPreference(Context context, AttributeSet attrs) {
        super(context, attrs);

        for (int i = 0; i < attrs.getAttributeCount(); i ++) {
            String attr = attrs.getAttributeName(i);
            String value = attrs.getAttributeValue(i);
            if (attr.equalsIgnoreCase("array")) {
                int arrayid = context.getResources().getIdentifier(value, "array",
                        context.getPackageName());
                arrayItems = context.getResources().getStringArray(arrayid);
            }
        }
    }

    public NumberPickerPreference(Context context) {
        super(context);
    }

    @Override
    protected View onCreateDialogView() {
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;

        picker = new NumberPicker(getContext());
        picker.setLayoutParams(layoutParams);

        FrameLayout dialogView = new FrameLayout(getContext());
        dialogView.addView(picker);

        return dialogView;
    }

    @Override
    protected void onBindDialogView(View view) {
        super.onBindDialogView(view);

        picker.setMinValue(0);
        picker.setMaxValue(arrayItems.length-1);
        picker.setWrapSelectorWheel(false);
        picker.setDisplayedValues(arrayItems);
        picker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        picker.setValue(getValue());
    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {
        if (positiveResult) {
            picker.clearFocus();
            float newValue = Float.parseFloat(arrayItems[picker.getValue()]);
            if (callChangeListener(newValue)) {
                setValue(newValue);
            }
        }
    }

    @Override
    protected Object onGetDefaultValue(TypedArray a, int index) {
        return a.getFloat(index, 0.0f);
    }

    @Override
    protected void onSetInitialValue(boolean restorePersistedValue, Object defaultValue) {
        setValue(restorePersistedValue ? getPersistedFloat(0.0f) : (Float) defaultValue);
    }

    public void setValue(float value) {
        this.value = value;
        persistFloat(this.value);
    }

    public int getValue() {
        return this.index;
    }
}