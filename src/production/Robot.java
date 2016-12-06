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

    /**
     * Creates a new robot with the specified id, and location
     * @author Jacob Roschen
     *
     * @param id Unique ID of the robot
     * @param m Master Object
     * @param f Floor object
     * @param location Where the robot should be initially located
     */
    public Robot(int id, Master m, Floor f, Point location) {
        this.id = id;
        this.chargeLevel = 100;
        this.master = m;
        this.floor = f;
        this.location = location;
        this.floor.updateItemAt(location, Cell.Type.ROBOTHOME);
        this.blockedCount = 0;
    }

    /**
     * Handles 'SpecificRobotToLocation', 'RaiseShelf', and 'LowerShelf'
     * If the robot cannot move to the specified location, it will reroute
     * @author Jacob Roschen
     *
     * @param task Task to complete
     * @param event Event to do
     */
    @Override
    public void handleTaskEvent(Task task, Event event) {
        switch (task.type) {
            case SpecificRobotToLocation:
                master.printTime();
                if (move(task.location)) {
                    System.out.println("Robot " + id + " moved to [" + task.location.x + "," + task.location.y + "]");
                    blockedCount = 0;
                } else {
                    System.out.println("Robot " + id + " could not move to [" + task.location.x + "," + task.location.y + "]");
                    event.addFirstTask(task, this);
                    blockedCount++;

                    // Wait until the robot hasn't been able to move for 3 turns
                    if(blockedCount > 2) {
                        // Try to move in a random direction and then reroute
                        int result = new Random().nextInt(4);
                        Point newPoint = ((Point) location.clone());
                        switch (result) {
                            case 0:
                                newPoint.translate(1, 0);
                                break;
                            case 1:
                                newPoint.translate(-1, 0);
                                break;
                            case 2:
                                newPoint.translate(0, 1);
                                break;
                            case 3:
                                newPoint.translate(0, -1);
                                break;
                        }

                        if(master.floor.isEmptyLocation(newPoint)) {
                            // Remove all old directions while retaining the end destination
                            Point robotDestination = event.removeCurrentDirections();

                            // Reroute the robot
                            event.addFirstTask(new Task(Task.TaskType.SpecificRobotPlotPath, this, robotDestination), this.master.robotscheduler);
                            // Move in the random direction to try and free itself from collisions
                            event.addFirstTask(new Task(Task.TaskType.SpecificRobotToLocation, newPoint), this);
                        }
                    }
                }
                master.scheduleEvent(event, 1);
                break;
            case RaiseShelf:
                System.out.println("Robot " + id + " raising shelf");
                raise();
                master.scheduleEvent(event, 1);
                break;
            case LowerShelf:
                System.out.println("Robot " + id + " lowering shelf");
                lower();
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
     * @author Jacob Roschen
     *
     * @return if robot needs to be recharged
     */
    public boolean needsRecharge() {
        return chargeLevel < 100;
    }

    /**
     * Charge the robot 4x's faster than what it can move, and make sure that it can only be charged to 100%
     * @author Jacob Roschen
     */
    public void charge() {
        chargeLevel += MOVE_COST*4;

        if(chargeLevel > 100) chargeLevel = 100;
    }

    /**
     * Returns the charge level of the robot
     * @author Jacob Roschen
     *
     * @return The current charge level
     */
    public double chargeLevel() {
        return chargeLevel;
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
        if (!floor.moveRobot(this.location, newloc, this.isRaised)) return false; // Robot did not move

        chargeLevel -= Robot.MOVE_COST;
        this.location = newloc;
        if (this.shelf != null) {
            this.shelf.setLocation(this.location);
        }

        return true;
    }

    /**
     * Returns the current robot location
     * @author Jacob Roschen
     *
     * @return The current robot location
     */
    public Point getLocation() {
        return location;
    }

    /**
     * Returns true if the robot has a shelf
     * @author Jacob Roschen
     *
     * @return True if the robot currently has a shelf, false otherwise
     */
    public boolean hasShelf() {
        return shelf != null;
    }

    /**
     * Used for printing the current robot
     * @author Jacob Roschen
     *
     * @return The ID of the robot as a string
     */
    @Override
    public String toString() {
        return Integer.toString(id);
    }
}
