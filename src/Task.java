public class Task {

    public enum TaskType {
        BeginItemRetrieval,
        DispatchAvailableRobotToLocation,
        RaiseShelf,
        SpecificRobotToLocation,
        RobotCharged,
        EventFinished
    }

    public TaskType type;
    public Integer[] location;
    public String itemsku;

    Task(TaskType type) {
        this.type = type;
    }

    Task(TaskType type, Integer[] location) {
        this.type = type;
        this.location = location;
    }

    Task(TaskType type, String itemsku) {
        this.type = type;
        this.itemsku = itemsku;
    }
}