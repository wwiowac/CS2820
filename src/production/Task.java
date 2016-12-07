package production;

import java.awt.*;

/**
 * A simple description of the action that will take place, together
 * with the EventConsumer that will handle it. This is essentially
 * a struct. Also may contain any information essential to the
 * execution of the task.
 * @author Wesley Weirather
 */
public class Task {

    public enum TaskType {
        BeginItemRetrieval,
        EndItemRetrieval,
        AvailableRobotRetrieveFromLocation,
        RaiseShelf,
        LowerShelf,
        SpecificRobotPlotPath,
        SpecificRobotToLocation,
        RobotCharge,
        EventFinished,
        MoveBelt,
        OrderStatus_Submitted,
        OrderStatus_Completed,
        PickItemFromShelf,
        AddBinToBelt,
        CreateBinForOrder,
        PackOrder,
        OrderPacked
    }

    public TaskType type;
    public Point location;
    public String itemsku;
    public Robot robot;
    public Bin bin;
    public InventoryItem item;
    public Order order;

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

    Task(TaskType type, Point location, InventoryItem item){
        this.type = type;
        this.location = location;
        this.item = item;
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

    Task(TaskType type, Point location, Bin bin){
        this.type = type;
        this.location = location;
        this.bin = bin;
    }

    Task(TaskType type, Order order){
        this.order = order;
        this.type = type;
    }
}