package collision;

import geometry.Line;
import geometry.Point;
import geometry.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class GameEnvironment {

    private List<Collidable> collidables;

    public GameEnvironment() {
        this.collidables = new ArrayList<>();
    }

    public void addCollidable(Collidable c) {
        this.collidables.add(c);
    }

    public void removeCollidable(Collidable c) {
        this.collidables.remove(c);
    }

    /**
     * Assume an object moving from line.start() to line.end().
     * If this object will not collide with any of the collidables
     * in this collection, return null.
     * Else, return the information about the closest collision that is going to occur.
     */
    public CollisionInfo getClosestCollision(Line trajectory) {
        Collidable closestObject = null;
        Point closestPoint = null;
        double minDistance = Double.POSITIVE_INFINITY;

        // CRITICAL FIX: Create a copy of the list.
        // If a block is removed while we are checking for collisions
        // (rare but possible in complex frames), the original list might change.
        List<Collidable> collidablesCopy = new ArrayList<>(this.collidables);

        for (Collidable c : collidablesCopy) {
            Rectangle rect = c.getCollisionRectangle();
            Point p = trajectory.closestIntersectionToStartOfLine(rect);

            if (p != null) {
                double distance = trajectory.start().distance(p);
                if (distance < minDistance) {
                    minDistance = distance;
                    closestPoint = p;
                    closestObject = c;
                }
            }
        }

        if (closestObject == null) {
            return null;
        }

        return new CollisionInfo(closestPoint, closestObject);
    }
}