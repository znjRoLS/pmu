package rosko.bojan.rupko;

import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;

import java.util.ArrayList;

import rosko.bojan.rupko.newlevel.Hole;

/**
 * Created by rols on 1/19/17.
 */

public class Level {
    Hole startHole;
    Hole endHole;
    ArrayList<Hole> holes;
    ArrayList<RectF> walls;

    public Level() {
        holes = new ArrayList<>();
        walls = new ArrayList<>();
    }

    public Hole getStartHole() {
        return startHole;
    }

    public void setStartHole(Hole startHole) {
        this.startHole = startHole;
    }

    public Hole getEndHole() {
        return endHole;
    }

    public void setEndHole(Hole endHole) {
        this.endHole = endHole;
    }

    public ArrayList<Hole> getHoles() {
        return holes;
    }

    public void setHoles(ArrayList<Hole> holes) {
        this.holes = holes;
    }

    public void addHole(Hole hole) {
        this.holes.add(hole);
    }

    public void removeHole(Hole hole) {
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
