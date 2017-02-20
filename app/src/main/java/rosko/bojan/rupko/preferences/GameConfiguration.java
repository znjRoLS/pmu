package rosko.bojan.rupko.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.util.Log;

import rosko.bojan.rupko.R;

/**
 * Created by rols on 1/19/17.
 */

public class GameConfiguration implements Cloneable{

    public final static String ROOT_PACKAGE_NAME = "rosko.bojan.rupko";

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
    public float BALL_BOUNCE_THRESHOLD;

    public String LEVEL_SUFIX;

    public float FILTER_ALFA;

    public String BITMAP_SUFIX;
    public Bitmap.CompressFormat BITMAP_FORMAT;
    public int BITMAP_SIZE;
    public int BITMAP_COMPRESS_FACTOR;

    public int AUDIO_BALL_BOUNCE;
    public int AUDIO_BALL_HOLE;

    private static GameConfiguration defaultConfiguration = null;
    public static GameConfiguration currentConfiguration;

    private GameConfiguration(){};

    private static void init(Context context) {

        Resources resources = context.getResources();

        defaultConfiguration = new GameConfiguration();

        defaultConfiguration.BALL_BOUNCE =
                Float.parseFloat(resources.getString(R.string.preference_default_ball_bounce));
        defaultConfiguration.BALL_TRACTION =
                Float.parseFloat(resources.getString(R.string.preference_default_ball_traction));
        defaultConfiguration.GRAVITY_MAGNITUDE =
                Float.parseFloat(resources.getString(R.string.preference_default_gravity_magnitude));
        defaultConfiguration.GAME_UPDATE_RATE =
                Integer.parseInt(resources.getString(R.string.preference_default_game_update_rate));
        defaultConfiguration.BALL_BOUNCE_THRESHOLD =
                Float.parseFloat(resources.getString(R.string.preference_default_ball_bounce_threshold));

        defaultConfiguration.HOLE_RADIUS_RATIO =
                Float.parseFloat(resources.getString(R.string.preference_default_hole_radius_ratio));
        defaultConfiguration.BALL_RADIUS_RATIO =
                Float.parseFloat(resources.getString(R.string.preference_default_ball_radius_ratio));
        defaultConfiguration.START_HOLE_COLOR =
                Color.parseColor(resources.getString(R.string.preference_default_start_hole_color));
        defaultConfiguration.HOLE_COLOR =
                Color.parseColor(resources.getString(R.string.preference_default_hole_color));
        defaultConfiguration.END_HOLE_COLOR =
                Color.parseColor(resources.getString(R.string.preference_default_end_hole_color));
        defaultConfiguration.WALL_COLOR =
                Color.parseColor(resources.getString(R.string.preference_default_wall_color));
        defaultConfiguration.DRAGGABLE_COLOR =
                Color.parseColor(resources.getString(R.string.preference_default_draggable_color));
        defaultConfiguration.BALL_COLOR =
                Color.parseColor(resources.getString(R.string.preference_default_ball_color));

        defaultConfiguration.FILTER_ALFA =
                Float.parseFloat(resources.getString(R.string.preference_default_filter_alfa));

        defaultConfiguration.LEVEL_SUFIX = resources.getString(R.string.preference_default_level_sufix);
        defaultConfiguration.BITMAP_SUFIX = resources.getString(R.string.preference_default_bitmap_sufix);
        defaultConfiguration.BITMAP_SIZE =
                Integer.parseInt(resources.getString(R.string.preference_default_bitmap_size));
        defaultConfiguration.BITMAP_COMPRESS_FACTOR =
                Integer.parseInt(resources.getString(R.string.preference_default_bitmap_compress_factor));

        defaultConfiguration.BITMAP_FORMAT = Bitmap.CompressFormat.JPEG;
        defaultConfiguration.AUDIO_BALL_BOUNCE = context.getResources().getIdentifier(
                resources.getString(R.string.preference_default_sound_bounce),
                "raw",
                ROOT_PACKAGE_NAME
        );
        defaultConfiguration.AUDIO_BALL_HOLE = context.getResources().getIdentifier(
                resources.getString(R.string.preference_default_sound_hole),
                "raw",
                ROOT_PACKAGE_NAME
        );

        copyDefaultPreferences();
    }

    public static void fillCurrentConfiguration(Context context) {

        if (defaultConfiguration == null) {
            init(context);
        }

        copyDefaultPreferences();

//        SharedPreferences sharedPreferences = context.getSharedPreferences(
//                context.getString(R.string.app_prefix) +
//                        context.getString(R.string.preference_file_key),
//                Context.MODE_PRIVATE
//        );

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        currentConfiguration.BALL_TRACTION = sharedPreferences.getFloat(
                context.getString(R.string.preference_ball_traction),
                defaultConfiguration.BALL_TRACTION
        );
        currentConfiguration.BALL_BOUNCE = sharedPreferences.getFloat(
                context.getString(R.string.preference_ball_bounce),
                defaultConfiguration.BALL_BOUNCE
        );
        currentConfiguration.GRAVITY_MAGNITUDE = sharedPreferences.getFloat(
                context.getString(R.string.preference_gravity_magnitude),
                defaultConfiguration.GRAVITY_MAGNITUDE
        );
        currentConfiguration.GAME_UPDATE_RATE = sharedPreferences.getInt(
                context.getString(R.string.preference_game_update_rate),
                defaultConfiguration.GAME_UPDATE_RATE
        );
        currentConfiguration.BALL_BOUNCE_THRESHOLD = sharedPreferences.getFloat(
                context.getString(R.string.preference_ball_bounce_threshold),
                defaultConfiguration.BALL_BOUNCE_THRESHOLD
        );

        currentConfiguration.HOLE_RADIUS_RATIO = sharedPreferences.getFloat(
                context.getString(R.string.preference_hole_radius_ratio),
                defaultConfiguration.HOLE_RADIUS_RATIO
        );
        currentConfiguration.BALL_RADIUS_RATIO = sharedPreferences.getFloat(
                context.getString(R.string.preference_ball_radius_ratio),
                defaultConfiguration.BALL_RADIUS_RATIO
        );
        currentConfiguration.START_HOLE_COLOR = sharedPreferences.getInt(
                context.getString(R.string.preference_start_hole_color),
                defaultConfiguration.START_HOLE_COLOR
        );
        currentConfiguration.HOLE_COLOR = sharedPreferences.getInt(
                context.getString(R.string.preference_hole_color),
                defaultConfiguration.HOLE_COLOR
        );
        currentConfiguration.END_HOLE_COLOR = sharedPreferences.getInt(
                context.getString(R.string.preference_end_hole_color),
                defaultConfiguration.END_HOLE_COLOR
        );
        currentConfiguration.WALL_COLOR = sharedPreferences.getInt(
                context.getString(R.string.preference_wall_color),
                defaultConfiguration.WALL_COLOR
        );
        currentConfiguration.DRAGGABLE_COLOR = sharedPreferences.getInt(
                context.getString(R.string.preference_draggable_color),
                defaultConfiguration.DRAGGABLE_COLOR
        );
        currentConfiguration.BALL_COLOR = sharedPreferences.getInt(
                context.getString(R.string.preference_ball_color),
                defaultConfiguration.BALL_COLOR
        );

        currentConfiguration.FILTER_ALFA = sharedPreferences.getFloat(
                context.getString(R.string.preference_filter_alfa),
                defaultConfiguration.FILTER_ALFA
        );
        currentConfiguration.LEVEL_SUFIX = sharedPreferences.getString(
                context.getString(R.string.preference_level_sufix),
                defaultConfiguration.LEVEL_SUFIX
        );
        currentConfiguration.BITMAP_SUFIX = sharedPreferences.getString(
                context.getString(R.string.preference_bitmap_sufix),
                defaultConfiguration.BITMAP_SUFIX
        );
        currentConfiguration.BITMAP_SIZE = sharedPreferences.getInt(
                context.getString(R.string.preference_bitmap_size),
                defaultConfiguration.BITMAP_SIZE
        );
        currentConfiguration.BITMAP_COMPRESS_FACTOR = sharedPreferences.getInt(
                context.getString(R.string.preference_bitmap_compress_factor),
                defaultConfiguration.BITMAP_COMPRESS_FACTOR
        );

        currentConfiguration.AUDIO_BALL_BOUNCE = sharedPreferences.getInt(
                context.getString(R.string.preference_sound_bounce),
                defaultConfiguration.AUDIO_BALL_BOUNCE
        );
        currentConfiguration.AUDIO_BALL_HOLE = sharedPreferences.getInt(
                context.getString(R.string.preference_sound_hole),
                defaultConfiguration.AUDIO_BALL_HOLE
        );

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
