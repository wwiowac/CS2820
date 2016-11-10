public class Robot implements EventConsumer {
    // Cost per move that the robot makes. Adjust this based on the size of the floor
    private static final double MOVE_COST = 1.0;

    private Master master;
    private Floor floor;

    private int id;
    private double chargeLevel;
    private Direction direction;

    public Robot(int id, Master m, Floor f) {
        this.id = id;
        this.chargeLevel = 100;
        master = m;
        floor = f;
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
                System.out.println("Robot " + Integer.toString(id) + " moving to [" + task.location[0].toString() + "," + task.location[1].toString() + "]");
                move(task.location);
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
    private void move(Integer[] newloc) {
        chargeLevel -= Robot.MOVE_COST;
        floor.setRobotPosition(this, newloc);
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
