import java.util.*;

public class Master implements EventConsumer {

    RobotScheduler robotscheduler;
    Integer currentTime;

    Master() {
        robotscheduler = new RobotScheduler(this);
        currentTime = 0;
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
                Event spawnedevent = new Event(new Task(Task.TaskType.DispatchAvailableRobotToLocation, task.location), robotscheduler);
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
        }
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
