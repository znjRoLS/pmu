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
/*
- da ne moz mnogo se sedi van ekrana
- da ne ide lopta brze preko starta
-? animacija kad se upada u rupu
-? horizontal view
- da ne izlazi ono govno kad stavljas rupu
- da se menjaju sve preference
- da mog se vrate na default sve preference
- sredi listvju za levele
- sredi listvju za statistike
-? texture
 */