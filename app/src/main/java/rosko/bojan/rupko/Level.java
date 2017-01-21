package rosko.bojan.rupko;

import android.graphics.PointF;
import android.graphics.RectF;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by rols on 1/19/17.
 */

//Note, everything is in percentage!
public class Level implements Serializable{
    PointF startHole;
    PointF endHole;
    ArrayList<PointF> holes;
    ArrayList<RectF> walls;

    public Level() {
        holes = new ArrayList<>();
        walls = new ArrayList<>();
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

    public void addHole(PointF hole) {
        this.holes.add(hole);
    }

    public void removeHole(PointF hole) {
        this.holes.remove(hole);
    }

    public ArrayList<RectF> getWalls() {
        return walls;
    }

    public void setWalls(ArrayList<RectF> walls) {
        this.walls = walls;
    }

    public void addWall(RectF wall) {
        this.walls.add(wall);
    }

    public void removeWall(RectF wall) {
        this.walls.remove(wall);
    }
}
