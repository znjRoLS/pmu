package rosko.bojan.rupko.preferences;

import android.content.Context;
import android.content.res.TypedArray;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.NumberPicker;

import rosko.bojan.rupko.R;

/**
 * Created by rols on 2/20/17.
 */

public class SoundPickerPreference extends DialogPreference {

    private NumberPicker picker;
    private int value;
    private int index;
    private String[] arrayItems;
    private int[] soundStreamIDs;
    SoundPool soundPool;

    public SoundPickerPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public SoundPickerPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SoundPickerPreference(Context context, AttributeSet attrs) {
        super(context, attrs);

        for (int i = 0; i < attrs.getAttributeCount(); i++) {
            String attr = attrs.getAttributeName(i);
            String value = attrs.getAttributeValue(i);
            if (attr.equalsIgnoreCase("array")) {
                int arrayid = context.getResources().getIdentifier(value, "array",
                        context.getPackageName());
                arrayItems = context.getResources().getStringArray(arrayid);
            }
        }
    }

    public SoundPickerPreference(Context context) {
        super(context);
    }

    @Override
    protected View onCreateDialogView() {
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;

        picker = new NumberPicker(getContext());
        picker.setLayoutParams(layoutParams);

        soundStreamIDs = new int[arrayItems.length];

        soundPool = (new SoundPool.Builder()).setMaxStreams(3).build();
        for (int i = 0 ;i < arrayItems.length; i ++ ) {
            String res = arrayItems[i];
            soundStreamIDs[i] = soundPool.load(getContext(), getResourceId(res), 0);
        }

        picker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                String value = arrayItems[i1];
                soundPool.play(soundStreamIDs[i1], 1.0f, 1.0f, 0, 0, 1.0f);
            }
        });

        FrameLayout dialogView = new FrameLayout(getContext());
        dialogView.addView(picker);

        return dialogView;
    }

    @Override
    protected void onBindDialogView(View view) {
        super.onBindDialogView(view);

        picker.setMinValue(0);
        picker.setMaxValue(arrayItems.length - 1);
        picker.setWrapSelectorWheel(false);
        picker.setDisplayedValues(arrayItems);
        picker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        picker.setValue(getValue());
    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {
        if (positiveResult) {
            picker.clearFocus();
            String resName = arrayItems[picker.getValue()];
//            Log.e("presisitng", resName);
            int newValue = getResourceId(resName);
//            Log.e("presisitng", "val " + newValue);
            if (callChangeListener(newValue)) {
                setValue(newValue);
            }
        }
    }

    @Override
    protected Object onGetDefaultValue(TypedArray a, int index) {
        String value = a.getString(index);
        return getResourceId(value);
    }

    @Override
    protected void onSetInitialValue(boolean restorePersistedValue, Object defaultValue) {
        setValue(restorePersistedValue ? getPersistedInt(R.raw.c) : (Integer) defaultValue);
    }

    public void setValue(int value) {
        this.value = value;
        Log.e("persisting", "persis " + value + " res b " + R.raw.b);
        persistInt(this.value);
        for (int i = 0; i < arrayItems.length; i ++ ) {
            String res = arrayItems[i];
            if (getResourceId(res) == value) {
                this.index = i;
            }
        }
    }

    public int getValue() {
        return this.index;
    }

    private int getResourceId(String resName) {
        return getContext().getResources().getIdentifier(resName, "raw", GameConfiguration.ROOT_PACKAGE_NAME);
    }
}
