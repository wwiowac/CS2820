import java.awt.*;

/**
 * @author Jacob Roschen
 *         Wraps up the robot into a nice class
 */
public class Robot implements EventConsumer {
    // Cost per move that the robot makes. Adjust this based on the size of the floor
    private static final double MOVE_COST = 0.25;

    private Master master;
    private Floor floor;

    private int id;
    private double chargeLevel;
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

    @Override
    public void handleTaskEvent(Task task, Event event) {
        switch (task.type) {
            case SpecificRobotToLocation:
                master.printTime();
                if (move(task.location)) {
                    System.out.println("Robot " + id + " moved to [" + task.location.x + "," + task.location.y + "]");
                } else {
                    System.out.println("Robot " + id + " could not move to [" + task.location.x + "," + task.location.y + "]");
                    // TODO: Need to reroute if it is stuck
                    event.addFirstTask(task, this);
                }
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
     *
     * @return if robot needs to be recharged
     */
    public boolean needsRecharge() {
        return chargeLevel < 70;
    }

    /**
     * Advance the robot to its next step
     *
     * @param newloc The location the robot should move to
     * @return true if the robot was able to move there
     */
    private boolean move(Point newloc) {
        chargeLevel -= Robot.MOVE_COST;

        if (!floor.moveRobot(this.location, newloc, this.isRaised)) return false; // Robot did not move

        this.location = newloc;
        if (this.shelf != null) {
            this.shelf.setLocation(this.location);
        }

        return true;
    }

    public Point getLocation() {
        return location;
    }
}
