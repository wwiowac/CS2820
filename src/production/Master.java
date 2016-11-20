package production;

import java.util.*;

import static java.lang.Thread.sleep;


/**
 * Master class. Contains main method for initializing the simulation, and is
 * responsible for running the simulation.
 * @author wesley
 */
public class Master implements EventConsumer {

    Floor floor;
    Belt belt;
    RobotScheduler robotscheduler;
    InventoryManagement inventory;
    Integer currentTime;
    Visualizer visualizer;

    private PriorityQueue<ScheduledEvent> EventQueue;

    // Dummy item
    static ArrayList<InventoryItem> inventoryItems = new ArrayList<>();

    /**
     * Setup the simulation. This should only load thing that will be used in every simulation.
     * Simulation specific setup should be handled in the method initializing Master.
     */
    Master() {
        EventQueue = new PriorityQueue<>(new scheduleOrdering());
        floor = new Floor(this);
        inventory = new InventoryManagement(floor);
        robotscheduler = new RobotScheduler(this, floor, inventory);
        belt = new Belt(this, floor);
        visualizer = new Visualizer(floor);
        currentTime = 0;

        /// Temporary inventory setup TODO: Remove this
        // Setup inventory
        for (int i = 0; i < 25; i++) {
            InventoryItem item = new InventoryItem(""+ i, i +" toilet paper", i);
            inventory.addItem(item);
            inventoryItems.add(item);
        }
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

                Shelf s = inventory.getItemShelf(inventory.getItembySku(task.itemsku));
                if(!s.isAvailable()) {
                    System.out.println("Item could not be retrieved: Shelf in use.");
                    event.addFirstTask(task, this);
                    scheduleEvent(event, 1);
                } else {
                    s.setAvailable(false);
                    Event spawnedevent = new Event(new Task(Task.TaskType.AvailableRobotRetrieveFromLocation, s.getLocation()), robotscheduler);
                    spawnedevent.addLastTask(new Task(Task.TaskType.EventFinished), null);
                    scheduleEvent(spawnedevent);
                }
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
        ScheduledEvent todo = new ScheduledEvent(event, currentTime + offset);
        EventQueue.add(todo);
    }

    /**
     * The main simulation loop for master.
     * Executes scheduled events in the priority queue.
     *
     * @param speedMultiplier How fast the simulation should run. Ex. 2 is 2x's as fast as normal speed
     */
    public void simulate(int speedMultiplier) {
        System.out.println("Time: " + currentTime);
        System.out.println("Simulation beginning...");
        long startTime = System.currentTimeMillis();
        // While there are things to do
        while (!EventQueue.isEmpty()) {
            ScheduledEvent se = EventQueue.poll();

            // Controls the speed at which the game runs
            if(!Objects.equals(currentTime, se.time)) {
                visualizer.repaint(se.time);
                try {
                    long timeToSleepFor = (1000/speedMultiplier) - (System.currentTimeMillis() - startTime);
                    if(timeToSleepFor > 0) sleep(timeToSleepFor);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                startTime = System.currentTimeMillis();
            }

            // Jump to time of next event
            currentTime = se.time;
            Event e = se.event;
            // Execute the next Task in the event
            e.doNextTask();

        }
        System.out.println("Done!");
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
        for (InventoryItem item : inventoryItems) {
            Event e = new Event(new Task(Task.TaskType.BeginItemRetrieval, item.getId()), master);
            master.scheduleEvent(e);
        }

        master.simulate(10);
    }

}

/**
 * Helper class for event in priority queue.
 * @author wesley
 */
class ScheduledEvent {
    Event event;
    Integer time;

    public ScheduledEvent(Event e, Integer t) {
        event = e;
        time = t;
    }
}
