package collision;

import geometry.Point;
import geometry.Rectangle;
import geometry.Velocity;

import geometry.Point;
import geometry.Rectangle;
import geometry.Velocity;
import sprites.Ball; // כשנעבור למטלה 3 נשנה חתימה, אבל בינתיים לפי מה שיש לך


public interface Collidable {
    // Return the "collision shape" of the object.
    Rectangle getCollisionRectangle();

    // Notify the object that we collided with it at collisionPoint with
    // a given velocity.
    // The return is the new velocity expected after the hit (based on
    // the force the object inflicted on us).
    Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity);
}