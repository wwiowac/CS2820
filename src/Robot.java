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

    private int id;
    private double chargeLevel;
    private Direction direction;
    private Point location;

    public Robot(int id, Master m, Point location) {
        this.id = id;
        this.chargeLevel = 100;
        master = m;
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
        }
    }

    /**
     * Picks the shelf up
     */
    private void raise() {
        chargeLevel -= Robot.MOVE_COST;
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
        // Notify flood of location
//        floor.setRobotPosition(this, newloc);
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
