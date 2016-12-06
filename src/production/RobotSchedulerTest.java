package production;

import org.junit.Assert;
import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;

public class RobotSchedulerTest {
    private Master master = new Master();
    private Floor floor = new Floor(master);
    private InventoryManagement im = new InventoryManagement(floor);
    private RobotScheduler robotScheduler = new RobotScheduler(master, floor, im);

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
            Assert.assertEquals(true, robotScheduler.canMove(floor.getGrid()[0][0], true));
        }

        // Can also move under shelves if the robot does not have a shelf
        Cell c = new Cell(0, 0);
        floor.updateItemAt(c, Cell.Type.SHELF);
        Assert.assertEquals(true, robotScheduler.canMove(floor.getGrid()[0][0], false));
    }

    @Test
    public void testCannotMove() {
        Cell.Type[] nonFreeAreas = new Cell.Type[]{
                Cell.Type.SHELF,
                Cell.Type.PICKER,
                Cell.Type.BELT,
                Cell.Type.BINONBELT,
                Cell.Type.CHARGER,
                Cell.Type.ROBOTHOME
        };

        for (Cell.Type t : nonFreeAreas) {
            Cell c = new Cell(0, 0);
            floor.updateItemAt(c, t);
            Assert.assertEquals(false, robotScheduler.canMove(floor.getGrid()[0][0], true));
        }
    }

    @Test
    public void testSeedRobots() {
        // RobotScheduler defaults to seeding 10 robots automatically
        robotScheduler.seedRobots(10);
        Assert.assertEquals(0, robotScheduler.workingRobots.size());
        Assert.assertEquals(0, robotScheduler.chargingRobots.size());
    }

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
}
