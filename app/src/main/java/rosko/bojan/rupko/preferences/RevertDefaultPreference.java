package rosko.bojan.rupko.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.DialogPreference;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.NumberPicker;

/**
 * Created by rols on 2/11/17.
 */

public class RevertDefaultPreference extends Preference {

    public RevertDefaultPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public RevertDefaultPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public RevertDefaultPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RevertDefaultPreference(Context context) {
        super(context);
    }

//    @Override
//    protected View onCreateDialogView() {
//
//        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getContext());
//
//        SharedPreferences.Editor editor = settings.edit();
//        editor.clear();
//        editor.commit();
//        PreferenceManager.setDefaultValues(RevertDefaultPreference.this, R.xml.preferences, true);
//
//        return null;
//    }
}
