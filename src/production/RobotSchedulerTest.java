package production;

import org.junit.Assert;
import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;

public class RobotSchedulerTest {
    private Master master = new Master();
    private Floor floor = new Floor(master);
    private InventoryManagement im = new InventoryManagement(floor);
    private RobotScheduler robotScheduler = new RobotScheduler(master, floor);

    /**
     * Makes sure that the valid move cases are valid
     * @author Jacob Roschen
     */
    @Test
    public void testCanMove() {
        Cell.Type[] freeAreas = new Cell.Type[]{
                Cell.Type.EMPTY,
                Cell.Type.ROBOT,
                Cell.Type.ROBOTLOWEREDSHELF,
                Cell.Type.ROBOTRAISEDSHELF,
                Cell.Type.HOME
        };

        for (Cell.Type t : freeAreas) {
            Cell c = new Cell(0, 0);
            floor.updateItemAt(c, t);
            Assert.assertEquals(true, floor.canMove(new Point(0, 0), true));
        }

        // Can also move under shelves if the robot does not have a shelf
        Cell c = new Cell(0, 0);
        floor.updateItemAt(c, Cell.Type.SHELF);
        Assert.assertEquals(true, floor.canMove(new Point(0, 0), false));
    }

    /**
     * Tests to make sure the invalid moves are invalid
     * @author Jacob Roschen
     */
    @Test
    public void testCannotMove() {
        Cell.Type[] nonFreeAreas = new Cell.Type[]{
                Cell.Type.SHELF,
                Cell.Type.PICKER,
                Cell.Type.BELT,
                Cell.Type.BINONBELT,
                Cell.Type.ROBOTHOME,
                Cell.Type.IDLEPACKER,
                Cell.Type.PACKINGPACKER
        };

        for (Cell.Type t : nonFreeAreas) {
            Cell c = new Cell(0, 0);
            floor.updateItemAt(c, t);
            Assert.assertEquals(false, floor.canMove(new Point(0, 0), true));
        }
    }

    /**
     * Makes sure that seeding the robots works properly
     * @author Jacob Roschen
     */
    @Test
    public void testSeedRobots() {
        // RobotScheduler defaults to seeding 10 robots automatically
        robotScheduler.seedRobots(10);
        Assert.assertEquals(0, robotScheduler.workingRobots.size());
        Assert.assertEquals(0, robotScheduler.chargingRobots.size());
    }

    /**
     * Tests to make sure that a simple route from [0,0] to [0,5] works
     * @author Jacob Roschen
     */
    @Test
    public void testSimpleRoute() {
        Cell startingPoint = new Cell(0, 0);
        Cell endingPoint = new Cell(0, 5);

        ArrayList<Point> route = robotScheduler.findPath(startingPoint, endingPoint, false);

        // Should take 5 steps
        Assert.assertEquals(5, route.size());
        for (Point p : route) {
            // All x coordinates should be 0
            Assert.assertEquals(0, p.x);
        }
        for (int i = 0; i < 5; i++) {
            // Y coordinates should be 1, 2, 3, 4, 5
            Assert.assertEquals(i + 1, route.get(i).y);
        }
    }

    /**
     * Tests to make sure the robot will navigate under a shelf it doesn't have a shelf
     * @author Jacob Roschen
     */
    @Test
    public void testRouteUnderShelf() {
        Cell startingPoint = new Cell(0, 0);
        Cell endingPoint = new Cell(0, 5);

        floor.updateItemAt(new Point(0, 2), Cell.Type.SHELF);

        ArrayList<Point> route = robotScheduler.findPath(startingPoint, endingPoint, false);


        // Should take 5 steps
        Assert.assertEquals(5, route.size());
        for (Point p : route) {
            // All x coordinates should be 0
            Assert.assertEquals(0, p.x);
        }
        for (int i = 0; i < 5; i++) {
            // Y coordinates should be 1, 2, 3, 4, 5
            Assert.assertEquals(i + 1, route.get(i).y);
        }
    }

    /**
     * Tests to make sure the robot will navigate around a shelf if it has a shelf
     * @author Jacob Roschen
     */
    @Test
    public void testAroundShelf() {
        Cell startingPoint = new Cell(0, 0);
        Cell endingPoint = new Cell(0, 5);

        floor.updateItemAt(new Point(0, 2), Cell.Type.SHELF);

        ArrayList<Point> route = robotScheduler.findPath(startingPoint, endingPoint, true);

        // Should take 6 steps
        Assert.assertEquals(7, route.size());

        Assert.assertEquals(new Point(0, 1), route.get(0));
        Assert.assertEquals(new Point(1, 1), route.get(1));
        Assert.assertEquals(new Point(1, 2), route.get(2));
        Assert.assertEquals(new Point(1, 3), route.get(3));
        Assert.assertEquals(new Point(0, 3), route.get(4));
        Assert.assertEquals(new Point(0, 4), route.get(5));
        Assert.assertEquals(new Point(0, 5), route.get(6));
    }

    /**
     * Tests to make sure that routing to itself will return an empty list of steps
     * @author Jacob Roschen
     */
    @Test
    public void testRouteToSameLocation() {
        Cell startingPoint = new Cell(0, 0);
        Cell endingPoint = new Cell(0, 0);

        ArrayList<Point> route = robotScheduler.findPath(startingPoint, endingPoint, true);

        // The route should be null if there are no directions
        Assert.assertEquals(0, route.size());
    }

    /**
     * Tests to make sure that getNextRobot works, and when there are no available robots,
     * it returns null
     * @author Jacob Roschen
     */
    @Test
    public void testGetNextRobot() {
        // Use all of the current robots
        int numRobots = robotScheduler.availableRobots.size();
        for(int i = 0; i < numRobots; i++) {
            Robot r = robotScheduler.getNextRobot();
            // Make sure that the returned robot is the first one
            Assert.assertEquals(Integer.toString(i), r.toString());
        }

        Robot emptyRobot = robotScheduler.getNextRobot();
        Assert.assertNull(emptyRobot);
    }

    /**
     * Tests to make sure that RobotScheduler handles EndItemRetrieval correctly
     * @author Jacob Roschen
     */
    @Test
    public void testEndItemRetrieval() {
        Robot robot = robotScheduler.getNextRobot();
        Assert.assertEquals(1, robotScheduler.workingRobots.size());

        // End the item retrieval
        Task t = new Task(Task.TaskType.EndItemRetrieval, robot, null);
        robotScheduler.handleTaskEvent(t, new Event(t, robotScheduler));

        Assert.assertEquals(0, robotScheduler.workingRobots.size());
        Assert.assertEquals(1, robotScheduler.chargingRobots.size());
    }

    /**
     * Tests to make sure that RobotScheduler handles RobotCharge correctly
     * @author Jacob Roschen
     */
    @Test
    public void testRobotCharge() {
        Robot robot = robotScheduler.getNextRobot();
        robotScheduler.workingRobots.remove(robot);
        robotScheduler.chargingRobots.add(robot);
        // Used later to make sure the robot is available once its done charging
        int numAvailableRobots = robotScheduler.availableRobots.size();

        // Move the robot so the charge decreases
        for(int i = 0; i < 5; i++) {
            Point newPoint = (Point) robot.getLocation().clone();
            newPoint.translate(0, 1);

            Task taskMove = new Task(Task.TaskType.SpecificRobotToLocation, newPoint);
            robot.handleTaskEvent(taskMove, new Event(taskMove, robot));
        }
        Assert.assertEquals(98.75, robot.chargeLevel(), 0);

        // Start the recharging

        Task rechargeTask = new Task(Task.TaskType.RobotCharge, robot);
        robotScheduler.handleTaskEvent(rechargeTask, new Event(rechargeTask, robotScheduler));

        Assert.assertEquals(99.75, robot.chargeLevel(), 0);

        // Recharge again
        Task rechargeTask2 = new Task(Task.TaskType.RobotCharge, robot);
        robotScheduler.handleTaskEvent(rechargeTask2, new Event(rechargeTask2, robotScheduler));

        Assert.assertEquals(100, robot.chargeLevel(), 0);
        Assert.assertEquals(0, robotScheduler.chargingRobots.size());
        Assert.assertEquals(numAvailableRobots + 1, robotScheduler.availableRobots.size());
        Assert.assertEquals(0, robotScheduler.chargingRobots.size());
    }
}
