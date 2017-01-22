package rosko.bojan.rupko.imageview;

import android.graphics.PointF;
import android.graphics.RectF;
import android.util.Log;
import android.util.Pair;

import java.io.Serializable;
import java.util.ArrayList;

import rosko.bojan.rupko.preferences.GameConfiguration;
import rosko.bojan.rupko.Level;

/**
 * Created by rols on 1/15/17.
 */

public class ImageData implements Serializable{

//    private Level level;

    protected Hole startHole;
    protected Hole endHole;
    protected ArrayList<Hole> holes;
    protected ArrayList<RectF> walls;

    protected int screenWidth;
    protected int screenHeight;
    protected float currentRadius;

    public ImageData() {
        holes = new ArrayList<>();
        walls = new ArrayList<>();
        currentRadius = 0f;

    }


    public boolean checkCollisions(MyPointF newHoleCenter) {

        Hole newHole = new Hole(newHoleCenter, Hole.Type.HOLE);
        newHole.setRadius(currentRadius);

        for (Hole hole : holes) {

            if (newHole.collides(hole)) {
                return true;
            }
        }
        if (startHole != null && newHole.collides(startHole)){
            return true;
        }
        if (endHole != null && newHole.collides(endHole)){
            return true;
        }

        for (RectF wall : walls) {
            if (newHole.collides(wall)) {
                return true;
            }
        }

        return false;
    }

    public void setScreenSize(Pair<Integer, Integer> size) {

        Log.d("screensize", "yep im herhe " + size.first + " " + size.second );

        screenHeight = size.first;
        screenWidth = size.second;

        int smaller = screenHeight>screenWidth?screenWidth:screenHeight;

        currentRadius = GameConfiguration.currentConfiguration.HOLE_RADIUS_PERCENTAGE * smaller;

        Log.d("screensize", currentRadius + "");

        if (startHole != null)
            startHole.setRadius(currentRadius);
        if (endHole != null)
            endHole.setRadius(currentRadius);
        for (Hole hole : holes)
            hole.setRadius(currentRadius);
    }


//    public void loadLevel(Level level) {
//
//    }
//


    public Hole getStartHole() {
        return startHole;
    }

    public void setStartHole(Hole startHole) {
        startHole.setRadius(currentRadius);
        this.startHole = startHole;
    }

    public Hole getEndHole() {
        return endHole;
    }

    public void setEndHole(Hole endHole) {
        endHole.setRadius(currentRadius);
        this.endHole = endHole;
    }

    public ArrayList<Hole> getHoles() {
        return holes;
    }

    public void setHoles(ArrayList<Hole> holes) {
        this.holes = holes;
    }

    public void addHole(Hole hole) {
        hole.setRadius(currentRadius);
        this.holes.add(hole);
    }

    public void removeHole(Hole hole) {
        this.holes.remove(hole);
    }

    public void addWall(RectF wall) {
        this.walls.add(wall);
    }

    public void removeWall(RectF wall) {
        this.walls.remove(wall);
    }

    public ArrayList<RectF> getWalls() {
        return walls;
    }

    public void setWalls(ArrayList<RectF> walls) {
        this.walls = walls;
    }
}
