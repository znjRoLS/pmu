package rosko.bojan.rupko.preferences;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.preference.EditTextPreference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.margaritov.preference.colorpicker.AlphaPatternDrawable;

/**
 * Created by rols on 1/20/17.
 */

public class FloatEditTextPreference extends EditTextPreference {
    public FloatEditTextPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public FloatEditTextPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public FloatEditTextPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FloatEditTextPreference(Context context) {
        super(context);
    }

    @Override
    protected String getPersistedString(String defaultReturnValue) {
        return String.valueOf(getPersistedFloat(0));
    }

    @Override
    protected boolean persistString(String value) {
        return persistFloat(Float.valueOf(value));
    }

    private View mView;

    @Override
    protected void onBindView(View view) {
        super.onBindView(view);
        mView = view;
        setPreviewFloat();
    }

    private void setPreviewFloat() {
        if (mView == null) return;
        TextView iView = new TextView(getContext());
        LinearLayout widgetFrameView = ((LinearLayout) mView.findViewById(android.R.id.widget_frame));
        if (widgetFrameView == null) return;
        widgetFrameView.setVisibility(View.VISIBLE);
        widgetFrameView.setPadding(
                widgetFrameView.getPaddingLeft(),
                widgetFrameView.getPaddingTop(),
                20,
                widgetFrameView.getPaddingBottom()
        );
        // remove already create preview image
        int count = widgetFrameView.getChildCount();
        if (count > 0) {
            widgetFrameView.removeViews(0, count);
        }
        widgetFrameView.addView(iView);
        widgetFrameView.setMinimumWidth(0);
        iView.setText(getPersistedString(""));
    }
}
