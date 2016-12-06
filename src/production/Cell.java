package production;

import java.awt.*;

/**
 * A fairly basic class that extends point with a type which is used in the visualizer.
 * It also contains a few properties for the robot scheduler algorithm
 *
 * @author Jacob Roschen
 */
public class Cell extends Point {
    Type type;

    // Used for the robot scheduler algorithm
    int finalCost;
    Cell parent;
    int heuristicCost;

    Cell(int x, int y) {
        super(x, y);
        type = Type.EMPTY;
    }

    /**
     * All of the types of a cell that can be represented on the floor
     *
     * @author Jacob Roschen
     */
    public enum Type {
        EMPTY,
        SHELF,
        ROBOT,
        PICKER,
        PACKER,
        BELT,
        BINONBELT,
        ROBOTRAISEDSHELF,
        ROBOTLOWEREDSHELF,
        ROBOTHOME,
        HOME
    }
}