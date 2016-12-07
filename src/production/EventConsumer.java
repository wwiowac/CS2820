package production;

/**
 * Interface allowing a class to be used as an event "handler" in the event loop
 * @author Wesley
 * @implNote Implementing classes should handle at least one Task.TaskType
 */
public interface EventConsumer {
    void handleTaskEvent(Task task, Event event);
}