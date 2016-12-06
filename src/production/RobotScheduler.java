package production;

import java.awt.*;
import java.util.*;

/**
 * @author Jacob Roschen
 *
 * Class that controls the robots
 */
public class RobotScheduler implements EventConsumer {
    LinkedList<Robot> availableRobots = new LinkedList<>();
    ArrayList<Robot> chargingRobots = new ArrayList<>();
    ArrayList<Robot> workingRobots = new ArrayList<>();
    // Used for path finding
    PriorityQueue<Cell> openCells;
    boolean closedCells[][];

    Master master;
    Floor floor;
    InventoryManagement inventory;

    RobotScheduler(Master m, Floor f, InventoryManagement i) {
        master = m;
        floor = f;
        inventory = i;
        seedRobots(10);
    }

    /**
     * Initializes Robot objects in the warehouse
     * @author Jacob Roschen
     *
     * @param robotCount Number of robots to initialize
     */
    void seedRobots(int robotCount) {
        for (int i = 0; i < robotCount; i++) {
            Point position = new Point(20 + i, 0);
            Robot robot = new Robot(i, master, floor, position);
            availableRobots.add(robot);
        }
    }

    /**
     * Handles the events 'AvailableRobotRetrieveFromLocation', 'SpecificRobotPlotPath', and 'EndItemRetrieval'
     *
     * @author Jacob Roschen
     * @author Wes Weirather
     *
     * @param task
     * @param event
     */
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

                // First go get the item, return to the picker, return the shelf, then finally go back home (Reverse order)
                event.addFirstTask(new Task(Task.TaskType.EndItemRetrieval, robot, null), this);
                event.addFirstTask(new Task(Task.TaskType.SpecificRobotPlotPath, robot, robot.getLocation()), this);
                event.addFirstTask(new Task(Task.TaskType.LowerShelf), robot);
                event.addFirstTask(new Task(Task.TaskType.SpecificRobotPlotPath, robot, task.location), this); // Go back to the shelf area
                // Tell the picker the item has arrived
                event.addFirstTask(new Task(Task.TaskType.PickItemFromShelf, null, task.item), master.picker);
                event.addFirstTask(new Task(Task.TaskType.SpecificRobotPlotPath, robot, master.picker.getDropoffLocation()), this);
                event.addFirstTask(new Task(Task.TaskType.RaiseShelf), robot);
                // Move back to its home
                event.addFirstTask(new Task(Task.TaskType.SpecificRobotPlotPath, robot, task.location), this);
                master.scheduleEvent(event);
                break;
            case SpecificRobotPlotPath:
                ArrayList<Point> route = findPath(task.robot.getLocation(), task.location, task.robot.hasShelf());
                if (route == null) {
                    System.out.println("Robot is already at destination or a path cannot be determined");
                    master.scheduleEvent(event, 1);
                    return;
                }
                // Add events to head of event ticket in reverse order (They end up in the same order)
                for (int i=route.size()-1; i>=0; i--) {
                    event.addFirstTask(new Task(Task.TaskType.SpecificRobotToLocation, route.get(i)), task.robot);
                }
                master.scheduleEvent(event, 1);
                break;
            case EndItemRetrieval:
                // After the robot has been returned home, let it do other work including start charging
                chargingRobots.add(task.robot);
                workingRobots.remove(task.robot);
                event.addFirstTask(new Task(Task.TaskType.RobotCharge, task.robot), this);
                master.scheduleEvent(event, 1);
                break;
            case RobotCharge:
                task.robot.charge();
                System.out.println("Charging robot "+ task.robot + " " + task.robot.chargeLevel() +"%");
                if(task.robot.needsRecharge()) {
                    // keep charging
                    event.addFirstTask(new Task(Task.TaskType.RobotCharge, task.robot), this);
                } else {
                    // Done charging
                    System.out.println("Robot "+ task.robot +" done charging");
                    availableRobots.add(task.robot);
                    chargingRobots.remove(task.robot);
                }

                master.scheduleEvent(event, 1);
                break;
        }
    }

    /**
     * Checks to see if the robot can move to the specified location. Is a helper method for checkAndUpdateCost()
     * @author Jacob Roschen
     *
     * @param c The cell to check if one can move to
     * @param hasShelf Where you can move is determined by if the robot has a shelf
     * @return
     */
    boolean canMove(Cell c, boolean hasShelf) {
        if (c.type == Cell.Type.ROBOT ||
                c.type == Cell.Type.EMPTY ||
                c.type == Cell.Type.ROBOTLOWEREDSHELF ||
                c.type == Cell.Type.ROBOTRAISEDSHELF ||
                c.type == Cell.Type.HOME ||
                (c.type == Cell.Type.SHELF && !hasShelf)) {
            return true;
        }

        return false;
    }

    /**
     * Calculates the cost of the move from the current cell to the next one. Is a helper method for findPath()
     * @author Jacob Roschen
     *
     * @param current The current cell
     * @param next The cell you want to move to
     * @param hasShelf Does the robot have a shelf?
     */
    void checkAndUpdateCost(Cell current, Cell next, boolean hasShelf) {
        if (!canMove(next, hasShelf) || closedCells[next.x][next.y]) return;
        int nextFinalCost = next.heuristicCost + current.finalCost + 1;

        boolean inOpen = openCells.contains(next);
        if (!inOpen || nextFinalCost < next.finalCost) {
            next.finalCost = nextFinalCost;
            next.parent = current;
            if (!inOpen) openCells.add(next);
        }
    }

    /**
     * Finds a path from the start Point to the end Point using the A* algorithm
     * @author Jacob Roschen
     *
     * @param start Starting Point
     * @param end Ending Point
     * @param hasShelf Does the robot currently have a shelf?
     * @return An ArrayList of the coordinates that the robot needs to traverse
     */
    ArrayList<Point> findPath(Point start, Point end, boolean hasShelf) {
        ArrayList<Point> path = new ArrayList<>();

        Cell[][] grid = floor.getGrid();
        closedCells = new boolean[grid.length][grid[0].length];
        openCells = new PriorityQueue<>((Object o1, Object o2) -> {
            Cell c1 = (Cell) o1;
            Cell c2 = (Cell) o2;

            return c1.finalCost < c2.finalCost ? -1 :
                    c1.finalCost > c2.finalCost ? 1 : 0;
        });

        for (int i = 0; i < grid.length; ++i) {
            for (int j = 0; j < grid[0].length; ++j) {
                grid[i][j].finalCost = 0;
                grid[i][j].parent = null;
                grid[i][j].heuristicCost = Math.abs(i - end.x) + Math.abs(j - end.y);
            }
        }

        openCells.add(grid[start.x][start.y]);

        Cell curLoc;
        while ((curLoc = openCells.poll()) != null) {
            closedCells[curLoc.x][curLoc.y] = true;

            if (curLoc.equals(grid[end.x][end.y])) {
                break;
            }

            Cell t;
            Point[] neighbors = {
                    new Point(curLoc.x - 1, curLoc.y),
                    new Point(curLoc.x, curLoc.y - 1),
                    new Point(curLoc.x, curLoc.y + 1),
                    new Point(curLoc.x + 1, curLoc.y)
            };

            for (Point neighbor : neighbors) {
                if (neighbor.x < 0 || neighbor.y < 0 || neighbor.y >= grid[0].length || neighbor.x >= grid.length)
                    continue;

                t = grid[neighbor.x][neighbor.y];
                checkAndUpdateCost(curLoc, t, hasShelf);
            }
        }


        if (closedCells[end.x][end.y]) {
            //Trace back the path
            Cell current = grid[end.x][end.y];
            path.add(current);
            while (current.parent != null) {
                current = current.parent;
                path.add(0, current);
            }
            // The current location is at the front of the list, remove it
            path.remove(0);
        } else {
            return null;
        }

        return path;
    }
}
