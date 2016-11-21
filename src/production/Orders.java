package production;

import java.util.*;

/**
 * @author Wesley Weirather
 */
public class Orders implements EventConsumer {
    Master master;
    private ArrayList<String> ordersStatus;

    public Orders(Master master) {
        this.master = master;
        ordersStatus = new ArrayList<>();
    }

    /**
     * Schedule random orders
     * @param ordercount number of orders to schedule
     * @param latesttime max time (from current) at which an order might be scheduled
     */
    public void scheduleRandomOrders(int ordercount, int latesttime) {
        for (int i=0; i<ordercount; i++) {
            InventoryItem item = InventoryList.catalog[new Random().nextInt(InventoryList.catalog.length)];
            int time = new Random().nextInt(latesttime);
            scheduleOrder(item, time);
        }
    }

    /**
     * Order an item
     * @param item
     * @param time (from current)
     */
    public void scheduleOrder(InventoryItem item, int time) {
        int ordernum = ordersStatus.size();
        ordersStatus.add("Order pending");
        Event e = new Event(new Task(Task.TaskType.BeginItemRetrieval, item.getId()), master, ordernum);
        master.scheduleEvent(e, time);
    }

    /**
     * Handle order status updates
     * @param task
     * @param event
     */
    @Override
    public void handleTaskEvent(Task task, Event event) {
        switch (task.type) {
            case OrderStatus_Submitted:
                ordersStatus.set(event.ordernum, "Order submitted");
                master.scheduleEvent(event);
                break;
            case OrderStatus_Completed:
                ordersStatus.set(event.ordernum, "Order completed");
                master.scheduleEvent(event);
                break;
        }
    }

}
