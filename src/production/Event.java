package production;

import java.util.*;

public class Event {

    private LinkedList<KeyValuePair<Task, EventConsumer>> EventTicket;

    public Event(Task task, EventConsumer handler) {
        EventTicket = new LinkedList<>();
        KeyValuePair<Task, EventConsumer> eventhandler = new KeyValuePair<>(task, handler);
        EventTicket.add(eventhandler);
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

    public void addLastTask(Task t, EventConsumer handler) {
        KeyValuePair<Task, EventConsumer> eventhandler = new KeyValuePair<>(t, handler);
        EventTicket.add(eventhandler);
    }

}
