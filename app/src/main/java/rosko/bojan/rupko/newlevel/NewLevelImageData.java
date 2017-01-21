package rosko.bojan.rupko.newlevel;

import android.graphics.PointF;
import android.graphics.RectF;

import rosko.bojan.rupko.Level;
import rosko.bojan.rupko.imageview.Hole;
import rosko.bojan.rupko.imageview.ImageData;

/**
 * Created by rols on 1/20/17.
 */

public class NewLevelImageData extends ImageData {

    //TODO: refactor
    private PointF dragPoints[];
    private PointF dragPointsEnd[];

    //TODO: small rectangles
    //private HashMap<Integer, PointF> dragPoints;

    public NewLevelImageData() {
        super();

        //TODO: hardcoded
        dragPoints = new PointF[20];
        dragPointsEnd = new PointF[20];
    }

    public Level getLevel() {

        if (!validLevel()) {
            return null;
        }

        Level level = new Level();

        PointF normalizedStartHole = new PointF(
                startHole.getCenter().x / screenWidth,
                startHole.getCenter().y / screenHeight
        );
        level.setStartHole(normalizedStartHole);

        PointF normalizedEndHole = new PointF(
                endHole.getCenter().x / screenWidth,
                endHole.getCenter().y / screenHeight
        );
        level.setEndHole(normalizedEndHole);

        for (Hole hole : holes) {
            PointF normalizedHole = new PointF(
                    hole.getCenter().x / screenWidth,
                    hole.getCenter().y / screenHeight
            );
            level.addHole(normalizedHole);
        }
        level.setWalls(walls);

        return level;
    }

    public boolean validLevel() {
        return (startHole != null && endHole != null);
    }

    public void addDraggable(int index, float x, float y) {
        dragPoints[index] = new PointF(x,y);
        dragPointsEnd[index] = new PointF(x,y);
    }

    public boolean finishDraggable(int index) {
        PointF startPoint = dragPoints[index];
        PointF endPoint = dragPointsEnd[index];

        float left = startPoint.x < endPoint.x ? startPoint.x : endPoint.x;
        float right = startPoint.x < endPoint.x ? endPoint.x : startPoint.x;
        float top = startPoint.y < endPoint.y ? startPoint.y : endPoint.y;
        float bottom = startPoint.y < endPoint.y ? endPoint.y : startPoint.y;

        RectF wall = new RectF(left, top, right, bottom);

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
        for (RectF existingWall : walls) {
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

    public PointF[] getDraggables() {
        return dragPoints;
    }

    public PointF[] getDraggablesEnd() {
        return dragPointsEnd;
    }
}
