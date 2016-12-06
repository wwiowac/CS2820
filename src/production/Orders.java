package production;

import java.util.*;

/**
 * @author Wesley Weirather
 */
public class Orders implements EventConsumer {
    Master master;
    private ArrayList<Order> orders;
    private int ordersInProgress = 0;

    public Orders(Master master) {
        this.master = master;
        orders = new ArrayList<>();
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
        ordersInProgress++;
        Order newOrder = new Order();
        newOrder.status = Order.OrderStatus.OrderSubmitted;
        Event e = new Event(new Task(Task.TaskType.BeginItemRetrieval, item.getId()), master, newOrder);
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
                event.order.status = Order.OrderStatus.OrderSubmitted;
                master.scheduleEvent(event);
                break;
            case OrderStatus_Completed:
                event.order.status = Order.OrderStatus.OrderCompleted;
                master.scheduleEvent(event);
                break;
        }
    }

}
