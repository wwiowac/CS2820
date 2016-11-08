public class Robot {
    // Cost per move that the robot makes. Adjust this based on the size of the floor
    private static final double MOVE_COST = 1.0;

    private int id;
    private double chargeLevel;
    private Direction direction;

    public Robot(int id) {
        this.id = id;
        this.chargeLevel = 100;
    }

    public enum Direction {
        North,
        East,
        South,
        West
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
    public void move() {
        chargeLevel -= Robot.MOVE_COST;

        // Robot will need to "move"
    }

    /**
     * Simple way to change the "direction" of the robot. May need to be more sophisticated
     * @param d Direction that the robot should face
     */
    public void turn(Direction d) {
        chargeLevel -= Robot.MOVE_COST;
        direction = d;
    }
}
