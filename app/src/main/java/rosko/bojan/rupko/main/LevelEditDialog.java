package rosko.bojan.rupko.main;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import rosko.bojan.rupko.R;
import rosko.bojan.rupko.imageview.Hole;
import rosko.bojan.rupko.newlevel.NewElementDialog;

/**
 * Created by rols on 1/22/17.
 */

public class LevelEditDialog extends DialogFragment {

    public interface ListDialogListener {
        void onDialogEditClick(String levelName);
        void onDialogDeleteClick(String levelName);
    }

    LevelEditDialog.ListDialogListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);



        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = (LevelEditDialog.ListDialogListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(context.toString()
                    + " must implement NoticeDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        int title = getArguments().getInt("title");

        Bundle bundle = getArguments();
        final String levelName = bundle.getString(MainActivity.DIALOG_BUNDLE_LEVEL_NAME,"");

        return new AlertDialog.Builder(getActivity())
//                .setIcon(R.drawable.alert_dialog_icon)
                .setTitle("Do you want to edit or remove level" + levelName + " ?")
                .setItems(R.array.edit_remove_dialog_items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // The 'which' argument contains the index position
                        // of the selected item
                        switch(which) {
                            case 0: // edit
                                listener.onDialogEditClick(levelName);
                                break;
                            case 1: // remove
                                listener.onDialogDeleteClick(levelName);
                                break;
                        }
                    }
                })
                .create();
    }
}
