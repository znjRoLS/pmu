package rosko.bojan.rupko;

import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;

import java.util.ArrayList;

/**
 * Created by rols on 1/19/17.
 */

public class Level {
    PointF startHole;
    PointF endHole;
    ArrayList<PointF> holes;
    ArrayList<RectF> walls;

    public Level() {
    }

    public PointF getStartHole() {
        return startHole;
    }

    public void setStartHole(PointF startHole) {
        this.startHole = startHole;
    }

    public PointF getEndHole() {
        return endHole;
    }

    public void setEndHole(PointF endHole) {
        this.endHole = endHole;
    }

    public ArrayList<PointF> getHoles() {
        return holes;
    }

    public void setHoles(ArrayList<PointF> holes) {
        this.holes = holes;
    }

    public ArrayList<RectF> getWalls() {
        return walls;
    }

    public void setWalls(ArrayList<RectF> walls) {
        this.walls = walls;
    }
}
