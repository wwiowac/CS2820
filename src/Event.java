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
        tecpair.Value.handleTaskEvent(tecpair.Key, this);
    }

    public void addTask(Task t, EventConsumer handler) {
        KeyValuePair<Task, EventConsumer> eventhandler = new KeyValuePair<>(t, handler);
        EventTicket.add(eventhandler);
    }

}
