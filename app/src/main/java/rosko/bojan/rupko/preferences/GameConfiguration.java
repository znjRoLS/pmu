package rosko.bojan.rupko.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.util.Log;

import rosko.bojan.rupko.R;

/**
 * Created by rols on 1/19/17.
 */

public class GameConfiguration implements Cloneable{

    public int START_HOLE_COLOR;
    public int END_HOLE_COLOR;
    public int HOLE_COLOR;
    public int WALL_COLOR;
    public int DRAGGABLE_COLOR;

    public float HOLE_RADIUS_PERCENTAGE;

    public float BALL_TRACTION;
    public float BALL_BOUNCE;

    public String LEVEL_SUFIX;

    public final static GameConfiguration defaultConfiguration;
    public static GameConfiguration currentConfiguration;

    static {
        defaultConfiguration = new GameConfiguration();

        defaultConfiguration.HOLE_RADIUS_PERCENTAGE = 0.07f;
        defaultConfiguration.START_HOLE_COLOR = Color.GREEN;
        defaultConfiguration.HOLE_COLOR = Color.BLUE;
        defaultConfiguration.END_HOLE_COLOR = Color.DKGRAY;
        defaultConfiguration.WALL_COLOR = Color.parseColor("#8B4513");
        defaultConfiguration.DRAGGABLE_COLOR = Color.parseColor("#998B4513");

        defaultConfiguration.BALL_BOUNCE = 0.7f;
        defaultConfiguration.BALL_TRACTION = 0.07f;

        defaultConfiguration.LEVEL_SUFIX = ".level";

        copyDefaultPreferences();
    }

    public static void fillCurrentConfiguration(Context context) {
        copyDefaultPreferences();

//        SharedPreferences sharedPreferences = context.getSharedPreferences(
//                context.getString(R.string.app_prefix) +
//                        context.getString(R.string.preference_file_key),
//                Context.MODE_PRIVATE
//        );

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        currentConfiguration.BALL_TRACTION = sharedPreferences.getFloat(
                context.getString(R.string.preference_ball_traction),
                defaultConfiguration.BALL_TRACTION);
        currentConfiguration.BALL_BOUNCE = sharedPreferences.getFloat(
                context.getString(R.string.preference_ball_bounce),
                defaultConfiguration.BALL_BOUNCE);
    }

    public static void restoreDefaultPreferences(Context context) {

        copyDefaultPreferences();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putFloat(context.getString(R.string.preference_ball_bounce), currentConfiguration.BALL_BOUNCE);
        editor.putFloat(context.getString(R.string.preference_ball_traction), currentConfiguration.BALL_TRACTION);

        editor.apply();
    }

    private static void copyDefaultPreferences() {
        try {
            currentConfiguration = (GameConfiguration)defaultConfiguration.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }

}
