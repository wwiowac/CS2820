import java.awt.*;
import java.io.IOException;
import java.util.*;


/**
 * Master class. Contains main method for initializing the simulation, and is
 * responsible for running the simulation.
 */
public class Master implements EventConsumer {

    Floor floor;
    RobotScheduler robotscheduler;
    InventoryManagement inventory;
    Integer currentTime;

    private PriorityQueue<ScheduledEvent> EventQueue;

    // Dummy item
    static InventoryItem dummyitem = new InventoryItem("4456", "toilet paper", 0.12);

    /**
     * Setup the simulation. This should only load thing that will be used in every simulation.
     * Simulation specific setup should be handled in the method initializing Master.
     */
    Master() {
        EventQueue = new PriorityQueue<>(new scheduleOrdering());
        inventory = new InventoryManagement(this);
        floor = new Floor(this, inventory);
        robotscheduler = new RobotScheduler(this, floor, inventory);
        currentTime = 0;

        /// Temporary inventory setup TODO: Remove this
        // Setup inventory
        inventory.addShelf(new Point(17, 24));
        InventoryItem[] items = {}; // = seed items
        for (InventoryItem item : items) {
            inventory.addItem(item);
        }

        inventory.addItem(dummyitem);
    }

    /**
     * Orders events so they are executed in the correct order in the priority queue.
     */
    private class scheduleOrdering implements Comparator<ScheduledEvent> {
        /**
         * Determines the ordering of two ScheduledEvents
         * @param o1
         * @param o2
         * @return 1 | -1 | 0
         */
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

    /**
     * Handles a Task and its parent event.
     * Implementing this method makes Master an EventConsumer.
     * makes
     * @param task
     * @param event
     */
    @Override
    public void handleTaskEvent(Task task, Event event) {
        switch (task.type) {
            case BeginItemRetrieval:
                System.out.println("Time: " + this.currentTime.toString());
                System.out.println("Beginning item retrieval");
                Event spawnedevent = new Event(new Task(Task.TaskType.AvailableRobotRetrieveFromLocation,
                        inventory.getItemShelf(inventory.getItembySku(task.itemsku)).getLocation()),
                        robotscheduler);
                spawnedevent.addLastTask(new Task(Task.TaskType.EventFinished), null);
                scheduleEvent(spawnedevent);
                break;
        }
    }

    /**
     * Schedule an event to occur "immediately"
     * @param event
     */
    public void scheduleEvent(Event event) {
        scheduleEvent(event, 0);
    }

    /**
     * Schedule an event to occur offset time from now.
     * This method is unique to Master.
     * @param event
     * @param offset
     */
    public void scheduleEvent(Event event, Integer offset) {
        ScheduledEvent todo = new ScheduledEvent(event, currentTime+offset);
        EventQueue.add(todo);
    }

    /**
     * The main simulation loop for master.
     * Executes scheduled events in the priority queue.
     */
    public void simulate() {
        System.out.println("Time: " + currentTime.toString());
        System.out.println("Simulation beginning...");
        // While there are things to do
        while (!EventQueue.isEmpty()) {
            ScheduledEvent se = EventQueue.poll();
            // Jump to time of next event
            currentTime = se.time;
            Event e = se.event;
            // Execute the next Task in the event
            e.doNextTask();
            /*try {
                System.in.read();
            } catch (IOException e1) {
                e1.printStackTrace();
            }*/
        }
    }

    /**
     * Helper method that prints the current simulation time;
     */
    public void printTime() {
        System.out.println("Time: " + currentTime.toString());
    }

    /**
     * main method for initializing master.
     * Also added initial events to queue.
     * @param args
     */
    public static void main(String[] args) {
        Master master = new Master();

        // Seed Event queue
        Event e1 = new Event(new Task(Task.TaskType.BeginItemRetrieval, dummyitem.getId()), master);
        master.scheduleEvent(e1);
        master.simulate();
    }

}

/**
 * Helper class for event in priority queue.
 */
class ScheduledEvent {
    Event event;
    Integer time;

    public ScheduledEvent(Event e, Integer t) {
        event = e;
        time = t;
    }
}
