
public class Task {

    public enum TaskType {
        BeginItemRetrieval,
        DispatchAvailableRobotToLocation,
        RaiseShelf,
        SpecificRobotToLocation,
        RobotCharged
    }

    public TaskType type;
    public Integer[] location;

    Task(TaskType type) {
        this.type = type;
    }

    Task(TaskType type, Integer[] location) {
        this.type = type;
        this.location = location;
    }
}