import java.io.IOException;
import java.util.*;

public class Master implements EventConsumer {

    Floor floor;
    RobotScheduler robotscheduler;
    InventoryManagement inventory;
    Integer currentTime;

    // Dummy item
    static InventoryItem dummyitem = new InventoryItem("4456", "toilet paper", 0.12);

    Master() {
        floor = new Floor(this);
        robotscheduler = new RobotScheduler(this, floor);
        inventory = new InventoryManagement();
        currentTime = 0;

        // Setup inventory
        inventory.addShelf(new Integer[] { 17, 24 });
        InventoryItem[] items = {}; // = seed items
        for (InventoryItem item : items) {
            inventory.addItem(item);
        }

        inventory.addItem(dummyitem);
    }

    private class scheduleOrdering implements Comparator<ScheduledEvent> {
        @Override
        public int compare(ScheduledEvent o1, ScheduledEvent o2) {
            if (o1.time < o2.time) {
                return -1;
            }
            if (o1.time > o2.time) {
                return 1;
            }
            return 0;
        }
    }

    private PriorityQueue<ScheduledEvent> EventQueue = new PriorityQueue<>(new scheduleOrdering());

    @Override
    public void handleTaskEvent(Task task, Event event) {
        switch (task.type) {
            case BeginItemRetrieval:
                System.out.println("Time: " + this.currentTime.toString());
                System.out.println("Beginning item retrieval");
                Event spawnedevent = new Event(new Task(Task.TaskType.DispatchAvailableRobotToLocation,
                        inventory.getItemShelf(inventory.getItembySku(task.itemsku)).getLocation()),
                        robotscheduler);
                spawnedevent.addLastTask(new Task(Task.TaskType.EventFinished), null);
                scheduleEvent(spawnedevent);
        }
    }

    public void scheduleEvent(Event event) {
        scheduleEvent(event, 0);
    }

    public void scheduleEvent(Event event, Integer offset) {
        ScheduledEvent todo = new ScheduledEvent(event, currentTime+offset);
        EventQueue.add(todo);
    }

    public void simulate() {
        System.out.println("Time: " + currentTime.toString());
        System.out.println("Simulation beginning...");
        while (!EventQueue.isEmpty()) {
            ScheduledEvent se = EventQueue.poll();
            currentTime = se.time;
            Event e = se.event;
            e.doNextTask();
            /*try {
                System.in.read();
            } catch (IOException e1) {
                e1.printStackTrace();
            }*/
        }
    }

    public void printTime() {
        System.out.println("Time: " + currentTime.toString());
    }

    public static void main(String[] args) {
        Master master = new Master();

        // Seed Event queue
        Event e1 = new Event(new Task(Task.TaskType.BeginItemRetrieval, dummyitem.getId()), master);
        master.scheduleEvent(e1);
        master.simulate();
    }

}

class ScheduledEvent {
    Event event;
    Integer time;

    public ScheduledEvent(Event e, Integer t) {
        event = e;
        time = t;
    }
}
