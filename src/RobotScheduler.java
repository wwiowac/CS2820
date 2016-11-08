
public class RobotScheduler implements EventConsumer {

    Master master;

    RobotScheduler(Master m) {
        master = m;
    }

    @Override
    public void handleTaskEvent(Task task, Event event) {
        switch (task.type) {
            case DispatchAvailableRobotToLocation:
                System.out.println("Time: " + master.currentTime.toString());
                System.out.println("Sending a robot to [" + task.location[0].toString() + "," + task.location[1].toString() + "]");
        }
    }
}
