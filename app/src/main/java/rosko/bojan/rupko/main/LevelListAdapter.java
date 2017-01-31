package rosko.bojan.rupko.main;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import rosko.bojan.rupko.Level;
import rosko.bojan.rupko.R;

/**
 * Created by rols on 1/21/17.
 */

public class LevelListAdapter extends ArrayAdapter<Level> {


    public LevelListAdapter(Context context, int resource) {
        super(context, resource);
    }

    public LevelListAdapter(Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
    }

    public LevelListAdapter(Context context, int resource, Level[] objects) {
        super(context, resource, objects);
    }

    public LevelListAdapter(Context context, int resource, int textViewResourceId, Level[] objects) {
        super(context, resource, textViewResourceId, objects);
    }

    public LevelListAdapter(Context context, int resource, List<Level> objects) {
        super(context, resource, objects);
    }

    public LevelListAdapter(Context context, int resource, int textViewResourceId, List<Level> objects) {
        super(context, resource, textViewResourceId, objects);
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

            levelName.setText(level.getName());
        }

        return view;
    }
}
