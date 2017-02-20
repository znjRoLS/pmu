package rosko.bojan.rupko.game;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import rosko.bojan.rupko.R;
import rosko.bojan.rupko.newlevel.SaveDialog;

/**
 * Created by rols on 2/3/17.
 */

public class ScoreDialog extends DialogFragment{

    public static final String DIALOG_TIME_EXTRA_NAME = "rosko.bojan.rupko.dialog_time_extra_name";

    public interface DialogActionListener {
        void onDialogPositiveAction(String name);
        void onDialogNegativeAction();
    }

    ScoreDialog.DialogActionListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (ScoreDialog.DialogActionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement NoticeDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View content = inflater.inflate(R.layout.dialog_score, null);
        builder.setView(content);
        builder
                .setTitle("Type your name ")
                .setPositiveButton("Save",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                EditText editText = (EditText) getDialog().findViewById(R.id.userScoreEditText);
                                listener.onDialogPositiveAction(editText.getText().toString());
                            }
                        }
                )
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.dismiss();
                                listener.onDialogNegativeAction();
                            }
                        }
                );

        String time = getArguments().getString(DIALOG_TIME_EXTRA_NAME);
        ((TextView) content.findViewById(R.id.yourTimeTextView)).setText("Your time: " + time);

        return builder.create();
    }
}
