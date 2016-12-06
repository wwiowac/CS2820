package production;

import java.awt.*;

/**
 * A simple description of the action that will take place, together
 * with the EventConsumer that will handle it. This is essentially
 * a struct
 */
public class Task {

    public enum TaskType {
        BeginItemRetrieval,
        EndItemRetrieval,
        AvailableRobotRetrieveFromLocation,
        RaiseShelf,
        LowerShelf,
        ShelfPickedUp,
        SpecificRobotPlotPath,
        SpecificRobotToLocation,
        RobotCharge,
        EventFinished,
        UpdateFloorPosition,
        MoveBelt,
        OrderStatus_Submitted,
        OrderStatus_Completed,
        ItemToBelt
    }

    public TaskType type;
    public Point location;
    public String itemsku;
    public Robot robot;

    Task(TaskType type) {
        this.type = type;
    }

    Task(TaskType type, Point location) {
        this.type = type;
        this.location = location;
    }

    Task(TaskType type, String itemsku) {
        this.type = type;
        this.itemsku = itemsku;
    }

    Task(TaskType type, Robot robot) {
        this.type = type;
        this.robot = robot;
    }

    Task(TaskType type, Robot robot, Point location) {
        this.type = type;
        this.robot = robot;
        this.location = location;
    }
}