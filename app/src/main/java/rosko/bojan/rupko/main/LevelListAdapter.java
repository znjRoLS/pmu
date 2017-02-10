package rosko.bojan.rupko.main;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.List;
import java.util.Locale;

import rosko.bojan.rupko.imageview.Level;
import rosko.bojan.rupko.R;
import rosko.bojan.rupko.preferences.GameConfiguration;
import rosko.bojan.rupko.statistics.StatsDbHelper;

/**
 * Created by rols on 1/21/17.
 */

public class LevelListAdapter extends ArrayAdapter<Level> {

    StatsDbHelper dbHelper;
    Context context;

    public void LevelListAdapterConstructor(Context context) {
        dbHelper = new StatsDbHelper(context);
        this.context = context;
    }

    public LevelListAdapter(Context context, int resource) {
        super(context, resource);
        LevelListAdapterConstructor(context);
    }

    public LevelListAdapter(Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
        LevelListAdapterConstructor(context);
    }

    public LevelListAdapter(Context context, int resource, Level[] objects) {
        super(context, resource, objects);
        LevelListAdapterConstructor(context);
    }

    public LevelListAdapter(Context context, int resource, int textViewResourceId, Level[] objects) {
        super(context, resource, textViewResourceId, objects);
        LevelListAdapterConstructor(context);
    }

    public LevelListAdapter(Context context, int resource, List<Level> objects) {
        super(context, resource, objects);
        LevelListAdapterConstructor(context);
    }

    public LevelListAdapter(Context context, int resource, int textViewResourceId, List<Level> objects) {
        super(context, resource, textViewResourceId, objects);
        LevelListAdapterConstructor(context);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        if (view == null) {
            LayoutInflater layoutInflater;
            layoutInflater = LayoutInflater.from(getContext());
            view = layoutInflater.inflate(R.layout.row_level, null);
        }

        Level level = getItem(position);

        if (level != null) {
            TextView levelName = (TextView) view.findViewById(R.id.levelNameTextView);
            TextView bestScore = (TextView) view.findViewById(R.id.bestScoreTextView);

            levelName.setText(level.getName());

            Log.e("levellistview", level.getName());

            long time = dbHelper.getBestStatsForLevel(level.getName());
            if (time == 0) {
                bestScore.setText("No high scores");
            }
            else {
                long minutes = time / 60000;
                long seconds = time / 1000 % 60;
                long millis = time % 1000;

                String timeStr = String.format(Locale.getDefault(), "%02d:%02d:%03d", minutes, seconds, millis);
                bestScore.setText(timeStr);
            }

            ImageView imageView = (ImageView) view.findViewById(R.id.thumbnailImageView);

            File internalDir = context.getFilesDir();
            File file = new File(internalDir, level.getThumbnailName());

            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath() ,new BitmapFactory.Options());
            imageView.setImageBitmap(bitmap);
        }
        return view;
    }
}
