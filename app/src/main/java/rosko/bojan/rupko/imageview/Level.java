package rosko.bojan.rupko.imageview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PointF;
import android.graphics.RectF;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;

import rosko.bojan.rupko.Logger;
import rosko.bojan.rupko.imageview.MyPointF;
import rosko.bojan.rupko.imageview.MyRectF;
import rosko.bojan.rupko.preferences.GameConfiguration;

/**
 * Created by rols on 1/19/17.
 */

//Note, everything is in percentage!
public class Level implements Serializable{

    private static final long serialVersionUID = 5555555L;

    MyPointF startHole;
    MyPointF endHole;
    ArrayList<MyPointF> holes;
    ArrayList<MyRectF> walls;

    String name;

    public Level() {
        holes = new ArrayList<>();
        walls = new ArrayList<>();
    }

    public String getThumbnailName() {
        return name + GameConfiguration.currentConfiguration.BITMAP_SUFIX;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MyPointF getStartHole() {
        return startHole;
    }

    public void setStartHole(MyPointF startHole) {
        this.startHole = startHole;
    }

    public MyPointF getEndHole() {
        return endHole;
    }

    public void setEndHole(MyPointF endHole) {
        this.endHole = endHole;
    }

    public ArrayList<MyPointF> getHoles() {
        return holes;
    }

    public void setHoles(ArrayList<MyPointF> holes) {
        this.holes = holes;
    }

    public void addHole(MyPointF hole) {
        this.holes.add(hole);
    }

    public void removeHole(MyPointF hole) {
        this.holes.remove(hole);
    }

    public ArrayList<MyRectF> getWalls() {
        return walls;
    }

    public void setWalls(ArrayList<MyRectF> walls) {
        this.walls = walls;
    }

    public void addWall(MyRectF wall) {
        this.walls.add(wall);
    }

    public void removeWall(MyRectF wall) {
        this.walls.remove(wall);
    }

    public static Level loadLevel(Context context, String levelName) {
        Level level = null;

        String levelSuffix = GameConfiguration.currentConfiguration.LEVEL_SUFIX;
        File file = new File(context.getFilesDir(), levelName + levelSuffix);
        boolean fileExisted = file.exists();

        if (!fileExisted) {
            Logger.throwError(context, "Level doesn't exist! Level: " + levelName + levelSuffix, true);
            return null;
        }

        try {
            FileInputStream inputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            level = (Level) objectInputStream.readObject();
            objectInputStream.close();
            inputStream.close();
        } catch (IOException e) {
            Logger.throwError(context, "Problem with output stream!", false);
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            Logger.throwError(context, "Problem with class not found!", false);
            e.printStackTrace();
        }

        Logger.throwError(context, "Successfully loaded level!", false);

        return level;
    }
}
