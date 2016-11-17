import java.awt.*;

/**
 * @author Jacob Roschen
 *
 * Wraps up the robot into a nice class
 */
public class Robot implements EventConsumer {
    // Cost per move that the robot makes. Adjust this based on the size of the floor
    private static final double MOVE_COST = 1.0;

    private Master master;
    private Floor floor;
    private InventoryManagement inventory;

    private int id;
    private double chargeLevel;
    private Direction direction;
    private Point location;
    private boolean isRaised;
    private Shelf shelf;

    public Robot(int id, Master m, Floor f, Point location) {
        this.id = id;
        this.chargeLevel = 100;
        master = m;
        floor = f;
        this.location = location;
    }

    public enum Direction {
        North,
        East,
        South,
        West
    }

    @Override
    public void handleTaskEvent(Task task, Event event) {
        switch (task.type) {
            case SpecificRobotToLocation:
                master.printTime();
                move(task.location);
                System.out.println("Robot " + id + " moved to [" + task.location.x + "," + task.location.y + "]");
                master.scheduleEvent(event, 1);
                break;
            case RaiseShelf:
                System.out.println("Robot " + id + " raising shelf");
                raise();
                master.scheduleEvent(event, 1);
                break;
        }
    }

    /**
     * Picks the shelf up
     */
    private void raise() {
        isRaised = true;
        chargeLevel -= Robot.MOVE_COST;
        this.shelf = floor.raiseShelf(this.location);
    }

    /**
     * Sets the shelf down
     */
    private void lower() {
        this.chargeLevel -= Robot.MOVE_COST;
    }

    /**
     * Returns true if the robot has a charge less than 70%
     * @return if robot needs to be recharged
     */
    public boolean needsRecharge() {
        return chargeLevel < 70;
    }

    /**
     * Advance the robot to its next step
     */
    private void move(Point newloc) {
        chargeLevel -= Robot.MOVE_COST;
        floor.moveRobot(this.location, newloc, this.isRaised);
        this.location = newloc;
        if (this.shelf != null) {
            this.shelf.setLocation(this.location);
        }
    }

    /**
     * Simple way to change the "direction" of the robot. May need to be more sophisticated
     * @param d Direction that the robot should face
     */
    public void turn(Direction d) {
        chargeLevel -= Robot.MOVE_COST;
        direction = d;
    }

    public Point getLocation() {
        return location;
    }
}
