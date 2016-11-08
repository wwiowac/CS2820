public interface EventConsumer {
    void handleTaskEvent(Task task, Event event);
}