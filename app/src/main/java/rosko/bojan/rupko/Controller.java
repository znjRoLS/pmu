package rosko.bojan.rupko;

/**
 * Created by rols on 1/12/17.
 */

public class Controller {

    public interface ViewInterface {
        void updateView();
    }

    protected ViewInterface view;

    public Controller(ViewInterface view) {
        this.view = view;
    }

}
