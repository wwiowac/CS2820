import Inventory.MockShelf;

import java.util.*;
import static java.lang.Math.abs;

public class RobotScheduler implements EventConsumer {
    private LinkedList<Robot> availableRobots;
    private ArrayList<Robot> chargingRobots;
    private ArrayList<Robot> workingRobots;


    Master master;
    Floor floor;

    RobotScheduler(Master m, Floor f) {
        master = m;
        floor = f;
        availableRobots = new LinkedList<>(floor.getAllRobots());
    }

    @Override
    public void handleTaskEvent(Task task, Event event) {
        switch (task.type) {
            case DispatchAvailableRobotToLocation:
                master.printTime();
                System.out.println("Sending a robot to [" + task.location[0].toString() + "," + task.location[1].toString() + "]");
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
                ArrayList<Integer[]> route = mapRoute(floor.getRobotPosition(robot), task.location);
                if (route == null) {
                    System.out.println("Robot is already at destination");
                    master.scheduleEvent(event, 1);
                    return;
                }
                // Add events to head of event ticket in reverse order (They end up in the same order)
                for (int i=route.size()-1; i>=0; i--) {
                    event.addFirstTask(new Task(Task.TaskType.SpecificRobotToLocation, route.get(i)), robot);
                }
                master.scheduleEvent(event, 1);
        }
    }

    private ArrayList<Integer[]> mapRoute(Integer[] currentpos, Integer[] destination) {
        ArrayList<Integer[]> route = new ArrayList<>();
        Integer[] mappedpos = Arrays.copyOf(currentpos, currentpos.length);
        while (nextLocation(mappedpos, destination) != null) {
            mappedpos = nextLocation(mappedpos, destination);
            route.add(mappedpos);
        }
        if (route.size() == 0) {
            return null;
        }
        return route;
    }

    /**
     * Quick and dirty helper method for mapRoute
     * @param currentpos
     * @param destination
     * @return
     */
    private Integer[] nextLocation(Integer[] currentpos, Integer[] destination) {
        int xdelta = destination[0] - currentpos[0];
        int ydelta = destination[1] - currentpos[1];
        if (xdelta == 0 && ydelta == 0) {
            return null;
        }
        Integer[] nextloc = Arrays.copyOf(currentpos, currentpos.length);
        if (abs(xdelta) > abs(ydelta)) {
            nextloc[0] += (xdelta > 0) ? 1 : -1;
        } else {
            nextloc[1] += (ydelta > 0) ? 1 : -1;
        }
        return nextloc;
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
