public class Initializer {

    public static void main(String[] args) {
        Master master = new Master();
        // Seed Event queue
        Event e1 = new Event(new Task(Task.TaskType.BeginItemRetrieval, new Integer[]{12,3}), master);
        master.scheduleEvent(e1);
        master.simulate();
    }

}
