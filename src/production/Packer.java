package production;

import java.awt.*;
import java.util.ArrayList;

/**
 * @author Jacob Guth
 */
public class Packer implements EventConsumer {

    private Master master;
    private Floor floor;

    private ArrayList<Point> locations;
    /**
     * @constructor
     * @param master
     */
    public Packer(Master master, Floor floor) {
        this.master = master;
        this.floor = floor;
        //update cell types for visualizer
        locations = new ArrayList<>();
        for (int x=1; x<3; x++){
            for (int y=11; y<13; y++) {
                locations.add(new Point(x,y));
            }
        }
        showIdle();
    }

    /**
     * @description: Print information about the bin/order that has been packaged
     * @param task
     * @param event
     */
    @Override
    public void handleTaskEvent(Task task, Event event) {
        switch(task.type)
        {
            case PackOrder: {
                Order order = task.order;
                Customer customer = order.getCustomer();
                String name = customer.getName();
                System.out.println(name + "'s" + " order has is being packaged and labeled");
                Event spawnedevent = new Event(new Task(Task.TaskType.OrderPacked, task.order), this);
                master.scheduleEvent(spawnedevent, 1);
                showBusy();
                break;
            }
            case OrderPacked: {
                Order order = task.order;
                Customer customer = order.getCustomer();
                String name = customer.getName();
                System.out.println(name + "'s" + " order has been packaged and labeled");
                showIdle();
                break;
            }
        }
    }

    private void showIdle() {
        for (Point p : locations) {
            floor.updateItemAt(p, Cell.Type.IDLEPACKER);
        }
    }

    private void showBusy() {
        for (Point p : locations) {
            floor.updateItemAt(p, Cell.Type.PACKINGPACKER);
        }
    }
}
