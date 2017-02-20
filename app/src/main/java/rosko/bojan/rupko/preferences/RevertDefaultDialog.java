package rosko.bojan.rupko.preferences;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import rosko.bojan.rupko.R;
import rosko.bojan.rupko.newlevel.SaveDialog;

/**
 * Created by rols on 2/20/17.
 */

public class RevertDefaultDialog extends DialogFragment {

    public interface DialogActionListener {
        void onDialogPositiveAction(String name);
    }

    SaveDialog.DialogActionListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = (SaveDialog.DialogActionListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(context.toString()
                    + " must implement NoticeDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View content = inflater.inflate(R.layout.dialog_save, null);
        builder.setView(content);
        builder
                .setTitle("Type a level name ")
                .setPositiveButton("Save",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                EditText editText = (EditText) getDialog().findViewById(R.id.levelNameEditText);
                                listener.onDialogPositiveAction(editText.getText().toString());
                            }
                        }
                )
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.dismiss();
                            }
                        }
                );

        String levelName = getArguments().getString(LEVEL_NAME_EXTRA_NAME);
        if (levelName != null) {
            ((EditText) content.findViewById(R.id.levelNameEditText)).setText(levelName);
        }

        return builder.create();
    }
}
