import java.awt.*;

/**
 * A simple description of the action that will take place, together
 * with the EventConsumer that will handle it. This is essentially
 * a struct
 */
public class Task {

    public enum TaskType {
        BeginItemRetrieval,
        AvailableRobotRetrieveFromLocation,
        RaiseShelf,
        ShelfPickedUp,
        SpecificRobotPlotPath,
        SpecificRobotToLocation,
        RobotCharged,
        EventFinished,
        UpdateFloorPosition,
        MoveBelt
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

    Task(TaskType type, Robot robot, Point location) {
        this.type = type;
        this.robot = robot;
        this.location = location;
    }
}