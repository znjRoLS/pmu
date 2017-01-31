package rosko.bojan.rupko.newlevel;

import android.graphics.PointF;
import android.graphics.RectF;
import android.util.Log;

import rosko.bojan.rupko.Level;
import rosko.bojan.rupko.imageview.Hole;
import rosko.bojan.rupko.imageview.ImageData;
import rosko.bojan.rupko.imageview.MyPointF;
import rosko.bojan.rupko.imageview.MyRectF;

/**
 * Created by rols on 1/20/17.
 */

public class NewLevelImageData extends ImageData {

    //TODO: refactor
    private MyPointF dragPoints[];
    private MyPointF dragPointsEnd[];

    //TODO: small rectangles
    //private HashMap<Integer, MyPointF> dragPoints;

    public NewLevelImageData() {
        super();

        //TODO: hardcoded
        dragPoints = new MyPointF[20];
        dragPointsEnd = new MyPointF[20];
    }

    public Level getLevel() {

        if (!validLevel()) {
            return null;
        }

        Level level = new Level();

        MyPointF normalizedStartHole = new MyPointF(
                startHole.getCenter().x / screenWidth,
                startHole.getCenter().y / screenHeight
        );
        level.setStartHole(normalizedStartHole);

        MyPointF normalizedEndHole = new MyPointF(
                endHole.getCenter().x / screenWidth,
                endHole.getCenter().y / screenHeight
        );
        level.setEndHole(normalizedEndHole);

        for (Hole hole : holes) {
            MyPointF normalizedHole = new MyPointF(
                    hole.getCenter().x / screenWidth,
                    hole.getCenter().y / screenHeight
            );
            level.addHole(normalizedHole);
        }

        for (MyRectF wall : walls) {
            MyRectF normalizedWall = new MyRectF(
                    wall.left / screenWidth,
                    wall.top / screenHeight,
                    wall.right / screenWidth,
                    wall.bottom / screenHeight
            );
            Log.d("wall", "one wall " + wall.left + " " + wall.top);
            Log.d("wall", "two wall " + normalizedWall.left + " " + normalizedWall.top);
            level.addWall(normalizedWall);
        }

        return level;
    }

    public boolean validLevel() {
        return (startHole != null && endHole != null);
    }

    public void addDraggable(int index, float x, float y) {
        dragPoints[index] = new MyPointF(x,y);
        dragPointsEnd[index] = new MyPointF(x,y);
    }

    public boolean finishDraggable(int index) {
        MyPointF startPoint = dragPoints[index];
        MyPointF endPoint = dragPointsEnd[index];

        float left = startPoint.x < endPoint.x ? startPoint.x : endPoint.x;
        float right = startPoint.x < endPoint.x ? endPoint.x : startPoint.x;
        float top = startPoint.y < endPoint.y ? startPoint.y : endPoint.y;
        float bottom = startPoint.y < endPoint.y ? endPoint.y : startPoint.y;

        MyRectF wall = new MyRectF(left, top, right, bottom);

        dragPoints[index] = null;
        dragPointsEnd[index] = null;

        if (startHole != null){
            if (startHole.collides(wall)) return false;
        }
        if (endHole != null) {
            if (endHole.collides(wall)) return false;
        }
        for (Hole hole : holes) {
            if (hole.collides(wall)) return false;
        }
        for (MyRectF existingWall : walls) {
            if (wall.intersect(existingWall)) {
                return false;
            }
        }

        walls.add(wall);
        return true;
    }

    public void updateDraggable(int index, float x, float y) {
        dragPointsEnd[index].x = x;
        dragPointsEnd[index].y = y;
    }

    public MyPointF[] getDraggables() {
        return dragPoints;
    }

    public MyPointF[] getDraggablesEnd() {
        return dragPointsEnd;
    }
}
