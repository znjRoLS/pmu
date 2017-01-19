package rosko.bojan.rupko;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by rols on 1/19/17.
 */

public class Logger {

    public static void throwError(Context context, String msg) {
        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
        toast.show();
    }
}
