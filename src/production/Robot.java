package production;

import java.awt.*;
import java.util.Random;

/**
 * Wraps up the robot into a nice class
 *
 * @author Jacob Roschen
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
    private int blockedCount;

    public Robot(int id, Master m, Floor f, Point location) {
        this.id = id;
        this.chargeLevel = 100;
        this.master = m;
        this.floor = f;
        this.location = location;
        this.floor.updateItemAt(location, Cell.Type.ROBOTHOME);
        this.blockedCount = 0;
    }

    @Override
    public void handleTaskEvent(Task task, Event event) {
        switch (task.type) {
            case SpecificRobotToLocation:
                master.printTime();
                if (move(task.location)) {
                    System.out.println("production.Robot " + id + " moved to [" + task.location.x + "," + task.location.y + "]");
                    blockedCount = 0;
                } else {
                    System.out.println("production.Robot " + id + " could not move to [" + task.location.x + "," + task.location.y + "]");
                    // TODO: Need to reroute if it is stuck
                    event.addFirstTask(task, this);
                    blockedCount++;

                    if(blockedCount > 5) {
                        // Try to move in a random direction since we have been stuck
                        int result = new Random().nextInt(2);
                        Point newPoint = ((Point) location.clone());
                        if(new Random().nextInt(2) == 1) {
                            newPoint.translate(result, 0);
                        } else {
                            newPoint.translate(0, result);
                        }

                        if(master.floor.isEmptyLocation(newPoint)) {
                            event.addFirstTask(new Task(Task.TaskType.SpecificRobotToLocation, this.location), this);
                            event.addFirstTask(new Task(Task.TaskType.SpecificRobotToLocation, newPoint), this);
                        }
                    }
                }
                master.scheduleEvent(event, 1);
                break;
            case RaiseShelf:
                System.out.println("production.Robot " + id + " raising shelf");
                raise();
                master.scheduleEvent(event, 1);
                break;
            case LowerShelf:
                System.out.println("production.Robot " + id + " lowering shelf");
                lower();
                if(this.needsRecharge()) {
                    // TODO: Tell the robot to go recharge
                }
                master.scheduleEvent(event, 1);
                break;
        }
    }

    /**
     * Picks the shelf up
     *
     * @author Jacob Roschen
     * @author Wes Weirather
     */
    private void raise() {
        isRaised = true;
        chargeLevel -= Robot.MOVE_COST;
        this.shelf = floor.raiseShelf(this.location);
    }

    /**
     * Sets the shelf down
     *
     * @author Jacob Roschen
     *
     */
    private void lower() {
        this.chargeLevel -= Robot.MOVE_COST;
        this.isRaised = false;
        this.shelf = null;
        floor.lowerShelf(this.location);
    }

    /**
     * Returns true if the robot has a charge less than 70%
     *
     * @author Jacob Roschen
     *
     * @return if robot needs to be recharged
     */
    public boolean needsRecharge() {
        return chargeLevel < 70;
    }

    /**
     * Tries to advance the robot to its next step
     *
     * @author Wes Weirather
     * @author Jacob Roschen
     *
     * @param newloc The location the robot should move to
     * @return true if the robot was able to move there
     */
    private boolean move(Point newloc) {
        if (!floor.moveRobot(this.location, newloc, this.isRaised)) return false; // production.Robot did not move

        chargeLevel -= Robot.MOVE_COST;
        this.location = newloc;
        if (this.shelf != null) {
            this.shelf.setLocation(this.location);
        }

        return true;
    }

    /**
     * Returns the current robot location
     *
     * @author Jacob Roschen
     * @return The current robot location
     */
    public Point getLocation() {
        return location;
    }

    /**
     * Returns true if the robot has a shelf
     * @author Jacob Roschen
     * @return
     */
    public boolean hasShelf() {
        return shelf != null;
    }
}
