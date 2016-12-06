package production;

import java.awt.*;
import java.util.*;

public class Event {

    private LinkedList<KeyValuePair<Task, EventConsumer>> EventTicket;
    public Order order;

    public Event(Task task, EventConsumer handler) {
        this(task, handler, null);
    }

    public Event(Task task, EventConsumer handler, Order order) {
        EventTicket = new LinkedList<>();
        KeyValuePair<Task, EventConsumer> eventhandler = new KeyValuePair<>(task, handler);
        EventTicket.add(eventhandler);
        this.order = order;
    }

    public void doNextTask() {
        KeyValuePair<Task, EventConsumer> tecpair = EventTicket.removeFirst();
        if (tecpair.Value != null) {
            tecpair.Value.handleTaskEvent(tecpair.Key, this);
        }
    }

    public void addFirstTask(Task t, EventConsumer handler) {
        KeyValuePair<Task, EventConsumer> eventhandler = new KeyValuePair<>(t, handler);
        EventTicket.addFirst(eventhandler);
    }

    public void addFirstTasks(Task t, EventConsumer handler) {
        KeyValuePair<Task, EventConsumer> eventhandler = new KeyValuePair<>(t, handler);
        EventTicket.addFirst(eventhandler);
    }

    public void addLastTask(Task t, EventConsumer handler) {
        KeyValuePair<Task, EventConsumer> eventhandler = new KeyValuePair<>(t, handler);
        EventTicket.add(eventhandler);
    }

    /**
     * Removes the directions at the beginning of the event queue and returns where the
     * robot was headed to
     *
     * @author Jacob Roschen
     *
     * @return Where the robot was going before the directions were removed
     */
    public Point removeCurrentDirections() {
        Point robotDestination = null;
        while(this.EventTicket.getFirst().Key.type == Task.TaskType.SpecificRobotToLocation) {
            robotDestination = this.EventTicket.getFirst().Key.location;
            this.EventTicket.removeFirst();
        }
        
        return robotDestination;
    }

}
