import java.awt.*;
import java.util.*;
import static java.lang.Math.abs;

/**
 * @author Jacob Roschen
 *
 * Class that controls the robots
 */
public class RobotScheduler implements EventConsumer {
    private LinkedList<Robot> availableRobots = new LinkedList<>();
    private ArrayList<Robot> chargingRobots = new ArrayList<>();
    private ArrayList<Robot> workingRobots = new ArrayList<>();

    Master master;
    Floor floor;

    RobotScheduler(Master m, Floor f) {
        master = m;
        floor = f;
        seedRobots(5);
    }

    /**
     * Initializes Robot objects in the warehouse
     * @param robotCount Number of robots to initialize
     */
    private void seedRobots(int robotCount) {
        for (int i = 1; i <= robotCount; i++) {
            Point position = new Point(9 + i, 0);
            Robot robot = new Robot(i, master, position);
            availableRobots.add(robot);
        }
    }

    @Override
    public void handleTaskEvent(Task task, Event event) {
        switch (task.type) {
            case AvailableRobotRetrieveFromLocation:
                master.printTime();
                System.out.println("Sending a robot to [" + task.location.x + "," + task.location.y + "]");
                Robot robot;
                try {
                    robot = availableRobots.removeFirst();
                } catch (NoSuchElementException ex) {
                    // No available robots to fetch the order
                    System.out.println("No robots available: deferring");
                    event.addFirstTask(task, this);
                    master.scheduleEvent(event, 1);
                    return;
                }
                // First go get the item, then return to the picker (Reverse order)
                event.addFirstTask(new Task(Task.TaskType.SpecificRobotPlotPath, robot, floor.pick), this);
                event.addFirstTask(new Task(Task.TaskType.RaiseShelf), robot);
                event.addFirstTask(new Task(Task.TaskType.SpecificRobotPlotPath, robot, task.location), this);
                master.scheduleEvent(event);
                break;
            case SpecificRobotPlotPath:
                ArrayList<Point> route = mapRoute(task.robot.getLocation(), task.location);
                if (route == null) {
                    System.out.println("Robot is already at destination");
                    master.scheduleEvent(event, 1);
                    return;
                }
                // Add events to head of event ticket in reverse order (They end up in the same order)
                for (int i=route.size()-1; i>=0; i--) {
                    event.addFirstTask(new Task(Task.TaskType.SpecificRobotToLocation, route.get(i)), task.robot);
                }
                master.scheduleEvent(event, 1);
                break;
        }
    }

    private ArrayList<Point> mapRoute(Point location, Point destination) {
        ArrayList<Point> route = new ArrayList<>();
        Point mappedPosition = (Point) location.clone();

        while (nextLocation(mappedPosition, destination) != null) {
            mappedPosition = nextLocation(mappedPosition, destination);
            route.add(mappedPosition);
        }

        if (route.size() == 0) {
            return null;
        }

        return route;
    }

    /**
     * Quick and dirty helper method for mapRoute
     * @param location Current location
     * @param destination Destination location
     * @return
     */
    private Point nextLocation(Point location, Point destination) {
        int deltaX = destination.x - location.x;
        int deltaY = destination.y - location.y;
        if (deltaX == 0 && deltaY == 0) {
            return null;
        }
        Point nextLocation = (Point) location.clone();
        if (abs(deltaX) > abs(deltaY)) {
            nextLocation.x += (deltaX > 0) ? 1 : -1;
        } else {
            nextLocation.y += (deltaY > 0) ? 1 : -1;
        }

        return nextLocation;
    }


    /**
     * Takes an order, and assigns it to the robot that is first in line
     *
     * @param s The shelf to fetch
     * @return Returns whether or not the order was accepted. It will be rejected if there are no available robots
     */
    public boolean fetch(MockShelf s) {
        Robot r;
        try {
            r = availableRobots.removeFirst();
        } catch (NoSuchElementException ex) {
            // No available robots to fetch the order
            return false;
        }

        workingRobots.add(r);

        // Give the robot directions
        // r.addDirections();

        return true;
    }

    // Need a way to detect if a robot has finished charging

    // Need a way to detect if a robot has reached its destination

}
