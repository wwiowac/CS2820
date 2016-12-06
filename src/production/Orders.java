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
            Order order = new Order();
            int time = new Random().nextInt(latesttime);
            scheduleOrder(order, time);
        }
    }

    /**
     * @author - Kyle (changes made to implement multiple items in an order)
     *
     * Order an item
     * @param order an order contains the customer(which contains name and address) and orderList of InventoryItems
     * @param time (from current)
     */
    public void scheduleOrder(Order order, int time) {
        int ordernum = ordersStatus.size();
        ordersStatus.add("Order pending");
        for (InventoryItem i :order.getOrdersItems()) {
            Event e = new Event(new Task(Task.TaskType.BeginItemRetrieval, i.getId()), master, ordernum);
            master.scheduleEvent(e, time);
        }
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
