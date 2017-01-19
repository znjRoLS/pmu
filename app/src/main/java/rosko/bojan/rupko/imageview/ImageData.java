package rosko.bojan.rupko.imageview;

import android.graphics.Point;
import android.graphics.PointF;

import rosko.bojan.rupko.Level;
import rosko.bojan.rupko.newlevel.Hole;

/**
 * Created by rols on 1/15/17.
 */

public class ImageData {

    private Level level;

    public ImageData() {
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public boolean checkCollisions(PointF newHoleCenter) {

        Hole newHole = new Hole(newHoleCenter, Hole.Type.HOLE);

        for (Hole hole : level.getHoles()) {
            if (newHole.collides(hole)) {
                return true;
            }
        }
        if (level.getStartHole() != null && newHole.collides(level.getStartHole())){
            return true;
        }
        if (level.getEndHole() != null && newHole.collides(level.getEndHole())){
            return true;
        }

        return false;
    }
}
