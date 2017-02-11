package rosko.bojan.rupko.imageview;

import android.util.Log;
import android.util.Pair;

import java.io.Serializable;
import java.util.ArrayList;

import rosko.bojan.rupko.preferences.GameConfiguration;

/**
 * Created by rols on 1/15/17.
 */

public class ImageData implements Serializable{

//    private Level level;

    protected Hole startHole;
    protected Hole endHole;
    protected ArrayList<Hole> holes;
    protected ArrayList<MyRectF> walls;

    public int screenWidth;
    public int screenHeight;
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

        for (MyRectF wall : walls) {
            if (newHole.collides(wall)) {
                return true;
            }
        }

        return false;
    }

    public void setScreenSize(Pair<Integer, Integer> size) {

        Log.e("runnable post?", "setting scren size");
        Log.d("screensize", "yep im herhe " + size.first + " " + size.second );

        screenHeight = size.first;
        screenWidth = size.second;


    }

    public void updateRadius(){
        int smaller = screenHeight>screenWidth?screenWidth:screenHeight;
        currentRadius = GameConfiguration.currentConfiguration.HOLE_RADIUS_RATIO * smaller;

        Log.d("screensize", currentRadius + "");

        if (startHole != null)
            startHole.setRadius(currentRadius);
        if (endHole != null)
            endHole.setRadius(currentRadius);
        for (Hole hole : holes)
            hole.setRadius(currentRadius);
    }

    public void loadLevel(Level level) {

        Log.e("runnable post?", "running level");

        MyPointF normalizedStartHole = level.getStartHole();

        startHole = new Hole(
                new MyPointF(
                    normalizedStartHole.x * screenWidth,
                    normalizedStartHole.y * screenHeight
                ),
                Hole.Type.START
        );

        MyPointF normalizedEndHole = level.getEndHole();

        endHole = new Hole(
                new MyPointF(
                        normalizedEndHole.x * screenWidth,
                        normalizedEndHole.y * screenHeight
                ),
                Hole.Type.END
        );

        for (MyPointF levelHole : level.getHoles()) {

            Hole hole = new Hole(
                    new MyPointF(
                            levelHole.x * screenWidth,
                            levelHole.y * screenHeight
                    ),
                    Hole.Type.HOLE
            );

            holes.add(hole);
        }

        for (MyRectF levelWall : level.getWalls()) {
            MyRectF wall = new MyRectF(
                    levelWall.left * screenWidth,
                    levelWall.top * screenHeight,
                    levelWall.right * screenWidth,
                    levelWall.bottom * screenHeight
            );

            Log.d("wall", "one wall " + wall.left + " " + wall.top);
            Log.d("wall", "two wall " + levelWall.left + " " + levelWall.top);

            walls.add(wall);
        }

        updateRadius();
    }

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

    public void addWall(MyRectF wall) {
        this.walls.add(wall);
    }

    public void removeWall(MyRectF wall) {
        this.walls.remove(wall);
    }

    public ArrayList<MyRectF> getWalls() {
        return walls;
    }

    public void setWalls(ArrayList<MyRectF> walls) {
        this.walls = walls;
    }
}
