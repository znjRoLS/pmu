package rosko.bojan.rupko.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
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
    public int BALL_COLOR;

    public float HOLE_RADIUS_RATIO;
    public float BALL_RADIUS_RATIO;

    public float BALL_TRACTION;
    public float BALL_BOUNCE;
    public float GRAVITY_MAGNITUDE;
    public int GAME_UPDATE_RATE;

    public String LEVEL_SUFIX;

    public float FILTER_ALFA;

    public String BITMAP_SUFIX;
    public Bitmap.CompressFormat BITMAP_FORMAT;
    public int BITMAP_SIZE;
    public int BITMAP_COMPRESS_FACTOR;

    public int AUDIO_BALL_BOUNCE;

    public final static GameConfiguration defaultConfiguration;
    public static GameConfiguration currentConfiguration;

    private GameConfiguration(){};

    static {
        defaultConfiguration = new GameConfiguration();

        defaultConfiguration.HOLE_RADIUS_RATIO = 0.07f;
        defaultConfiguration.BALL_RADIUS_RATIO = 0.8f;
        defaultConfiguration.START_HOLE_COLOR = Color.parseColor("#ff00ff00");
        defaultConfiguration.HOLE_COLOR = Color.BLUE;
        defaultConfiguration.END_HOLE_COLOR = Color.DKGRAY;
        defaultConfiguration.WALL_COLOR = Color.parseColor("#8B4513");
        defaultConfiguration.DRAGGABLE_COLOR = Color.parseColor("#998B4513");
        defaultConfiguration.BALL_COLOR = Color.GRAY;

        defaultConfiguration.BALL_BOUNCE = 0.7f;
        defaultConfiguration.BALL_TRACTION = 0.001f;
        defaultConfiguration.GRAVITY_MAGNITUDE = 0.03f;
        defaultConfiguration.GAME_UPDATE_RATE = 60;

        defaultConfiguration.FILTER_ALFA = 1f;

        defaultConfiguration.LEVEL_SUFIX = ".level";

        defaultConfiguration.BITMAP_SUFIX = ".jpg";
        defaultConfiguration.BITMAP_FORMAT = Bitmap.CompressFormat.JPEG;
        defaultConfiguration.BITMAP_SIZE = 300;
        defaultConfiguration.BITMAP_COMPRESS_FACTOR = 10;

        defaultConfiguration.AUDIO_BALL_BOUNCE = R.raw.c;

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
        currentConfiguration.GRAVITY_MAGNITUDE = sharedPreferences.getFloat(
                context.getString(R.string.preference_gravity_magnitude),
                defaultConfiguration.GRAVITY_MAGNITUDE);
        currentConfiguration.GAME_UPDATE_RATE = sharedPreferences.getInt(
                context.getString(R.string.preference_game_update_rate),
                defaultConfiguration.GAME_UPDATE_RATE);

        currentConfiguration.HOLE_RADIUS_RATIO = sharedPreferences.getFloat(
                context.getString(R.string.preference_hole_radius_ratio),
                defaultConfiguration.HOLE_RADIUS_RATIO);
        currentConfiguration.BALL_RADIUS_RATIO = sharedPreferences.getFloat(
                context.getString(R.string.preference_ball_radius_ratio),
                defaultConfiguration.BALL_RADIUS_RATIO);

        currentConfiguration.START_HOLE_COLOR = sharedPreferences.getInt(
                context.getString(R.string.preference_start_hole_color),
                defaultConfiguration.START_HOLE_COLOR
        );

        Log.e("reading pref", "color is " + currentConfiguration.START_HOLE_COLOR);
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
