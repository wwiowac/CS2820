import java.awt.*;

public class Task {

    public enum TaskType {
        BeginItemRetrieval,
        DispatchAvailableRobotToLocation,
        RaiseShelf,
        SpecificRobotToLocation,
        RobotCharged,
        EventFinished,
        UpdateFloorPosition
    }

    public TaskType type;
    public Point location;
    public String itemsku;

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
}