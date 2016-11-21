package production;

import java.util.*;

/**
 * @author Wesley Weirather
 */
public class Orders {
    Master master;

    public Orders(Master master) {
        this.master = master;
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
        Event e = new Event(new Task(Task.TaskType.BeginItemRetrieval, item.getId()), master);
        master.scheduleEvent(e, time);
    }


}
