package rosko.bojan.rupko.newlevel;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import rosko.bojan.rupko.R;
import rosko.bojan.rupko.imageview.Hole;

/**
 * Created by rols on 1/19/17.
 */

public class NewElementDialog extends DialogFragment {

    public interface ListDialogListener {
        void onDialogItemClick(Hole.Type itemType);
    }

    ListDialogListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = (ListDialogListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(context.toString()
                    + " must implement NoticeDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        int title = getArguments().getInt("title");

        return new AlertDialog.Builder(getActivity())
//                .setIcon(R.drawable.alert_dialog_icon)
                .setTitle("Pick an element")
                .setItems(R.array.new_level_new_items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // The 'which' argument contains the index position
                        // of the selected item
                        switch(which) {
                            case 0:
                                listener.onDialogItemClick(Hole.Type.START);
                                break;
                            case 1:
                                listener.onDialogItemClick(Hole.Type.HOLE);
                                break;
                            case 2:
                                listener.onDialogItemClick(Hole.Type.END);
                                break;
                        }
                    }
                })
                .create();
    }
}
