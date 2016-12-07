package production;


import org.junit.Assert;
import org.junit.Test;

import java.awt.*;

public class RobotTest {
    private Master m = new Master();

    /**
     * Test the default Robot constructor
     */
    @Test
    public void testConstructor() {
        Robot r = new Robot(0, m, m.floor, new Point(3, 3));

        Assert.assertEquals(new Point(3, 3), r.getLocation());
        Assert.assertEquals(Cell.Type.ROBOTHOME, m.floor.getCell(new Point(3, 3)).type);
        Assert.assertEquals(100.0, r.chargeLevel(), 0);
    }

    /**
     * Tests to make sure that raising and lowering the robot works
     * @author Jacob Roschen
     */
    @Test
    public void testRaiseAndLower() {
        Robot r = new Robot(0, m, m.floor, new Point(3, 3));
        // Add shelf
        m.inventory.addShelf(new Point(3, 4));
        Assert.assertEquals(Cell.Type.SHELF, m.floor.getCell(new Point(3, 4)).type);

        // Move robot under shelf
        Task taskMove2 = new Task(Task.TaskType.SpecificRobotToLocation, new Point(3, 4));
        r.handleTaskEvent(taskMove2, new Event(taskMove2, r));
        Assert.assertEquals(0, r.getBlockedCount());

        Assert.assertEquals( Cell.Type.HOME, m.floor.getCell(new Point(3, 3)).type);
        Assert.assertEquals( Cell.Type.ROBOTLOWEREDSHELF, m.floor.getCell(new Point(3, 4)).type);

        Task taskRaise = new Task(Task.TaskType.RaiseShelf, r);
        r.handleTaskEvent(taskRaise, new Event(taskRaise, r));

        Assert.assertEquals(Cell.Type.ROBOTRAISEDSHELF, m.floor.getCell(new Point(3, 4)).type);
        Assert.assertTrue(r.isRaised());
        Assert.assertTrue(r.hasShelf());
        Assert.assertEquals(new Point(3, 4), r.getLocation());

        Task taskLower = new Task(Task.TaskType.LowerShelf, r);
        r.handleTaskEvent(taskLower, new Event(taskLower, r));

        Assert.assertEquals(Cell.Type.ROBOTLOWEREDSHELF, m.floor.getCell(new Point(3, 4)).type);
        Assert.assertFalse(r.isRaised());
        Assert.assertFalse(r.hasShelf());
        Assert.assertEquals(new Point(3, 4), r.getLocation());
    }

    /**
     * Test to make sure the shelf moves with the robot
     * @author Jacob Roschen
     */
    @Test
    public void testMoveWithShelf() {
        Robot r = new Robot(0, m, m.floor, new Point(3,3));
        // Add shelf
        m.inventory.addShelf(new Point(3, 4));
        // Move robot
        Task taskMove = new Task(Task.TaskType.SpecificRobotToLocation, new Point(3, 4));
        r.handleTaskEvent(taskMove, new Event(taskMove, r));
        Assert.assertEquals(0, r.getBlockedCount());

        Task taskRaise = new Task(Task.TaskType.RaiseShelf, r);
        r.handleTaskEvent(taskRaise, new Event(taskRaise, r));

        // Move robot
        Task taskMove2 = new Task(Task.TaskType.SpecificRobotToLocation, new Point(3, 5));
        r.handleTaskEvent(taskMove2, new Event(taskMove2, r));
        Assert.assertEquals(0, r.getBlockedCount());

        Assert.assertEquals(new Point(3, 5), r.getLocation());
        Assert.assertEquals(Cell.Type.EMPTY, m.floor.getCell(new Point(3, 4)).type);
        Assert.assertEquals(Cell.Type.ROBOTRAISEDSHELF, m.floor.getCell(new Point(3, 5)).type);
    }

    @Test
    public void testMoveBlockedWithReroute() {
        Robot r = new Robot(0, m, m.floor, new Point(3,3));
        // Add shelf
        m.inventory.addShelf(new Point(3, 4));
        m.inventory.addShelf(new Point(3, 5));

        // Move robot
        Task taskMove = new Task(Task.TaskType.SpecificRobotToLocation, new Point(3, 4));
        r.handleTaskEvent(taskMove, new Event(taskMove, r));
        Assert.assertEquals(0, r.getBlockedCount());

        // Pick up shelf
        Task taskRaise = new Task(Task.TaskType.RaiseShelf, r);
        r.handleTaskEvent(taskRaise, new Event(taskRaise, r));

        // Move robot failed 1
        Task taskMove1 = new Task(Task.TaskType.SpecificRobotToLocation, new Point(3, 5));
        r.handleTaskEvent(taskMove1, new Event(taskMove1, r));
        Assert.assertEquals(1, r.getBlockedCount());

        // Move robot failed 2
        Task taskMove2 = new Task(Task.TaskType.SpecificRobotToLocation, new Point(3, 5));
        r.handleTaskEvent(taskMove2, new Event(taskMove2, r));
        Assert.assertEquals(2, r.getBlockedCount());

        // Move robot failed 3
        Task taskMove3 = new Task(Task.TaskType.SpecificRobotToLocation, new Point(3, 5));
        r.handleTaskEvent(taskMove3, new Event(taskMove3, r));
        Assert.assertEquals(3, r.getBlockedCount());

        // Move robot failed 4
        Task taskMove4 = new Task(Task.TaskType.SpecificRobotToLocation, new Point(4, 4));
        r.handleTaskEvent(taskMove4, new Event(taskMove4, r));
        Assert.assertEquals(0, r.getBlockedCount());
    }

    /**
     * Test to make sure the shelf moves with the robot
     * @author Jacob Roschen
     */
    @Test
    public void testCannotMove() {
        Robot r = new Robot(0, m, m.floor, new Point(3,3));
        // Add shelf
        m.inventory.addShelf(new Point(3, 4));
        m.inventory.addShelf(new Point(3, 5));
        // Move robot
        Task taskMove = new Task(Task.TaskType.SpecificRobotToLocation, new Point(3, 4));
        r.handleTaskEvent(taskMove, new Event(taskMove, r));
        Assert.assertEquals(0, r.getBlockedCount());

        // Raise shelf
        Task taskRaise = new Task(Task.TaskType.RaiseShelf, r);
        r.handleTaskEvent(taskRaise, new Event(taskRaise, r));

        // Try to move, but it cannot if there is already a shelf there and we currently have a shelf
        Task taskMove2 = new Task(Task.TaskType.SpecificRobotToLocation, new Point(3, 5));
        r.handleTaskEvent(taskMove2, new Event(taskMove2, r));

        Assert.assertEquals(1, r.getBlockedCount());
        Assert.assertEquals(new Point(3, 4), r.getLocation());
        Assert.assertEquals(Cell.Type.ROBOTRAISEDSHELF, m.floor.getCell(new Point(3, 4)).type);
        Assert.assertEquals(Cell.Type.SHELF, m.floor.getCell(new Point(3, 5)).type);
    }

    /**
     * Make sure that the robot loses charge when moving, and can recharge up to 100%
     * @author Jacob Roschen
     */
    @Test
    public void testCharging() {
        Robot r = new Robot(0, m, m.floor, new Point(3, 3));
        // Move robot
        Task taskMove2 = new Task(Task.TaskType.SpecificRobotToLocation, new Point(1, 0));
        r.handleTaskEvent(taskMove2, new Event(taskMove2, r));
        Assert.assertEquals(0, r.getBlockedCount());

        Assert.assertTrue(r.needsRecharge());
        Assert.assertEquals(99.75, r.chargeLevel(), 0);

        // Charge the robot
        r.charge();

        // Make sure the robot only charges back up to 100%
        Assert.assertEquals(100.0, r.chargeLevel(), 0);
    }

    /**
     * Make sure that the putstring puts out the correct value
     * @author Jacob Roschen
     */
    @Test
    public void testToString() {
        Robot r = new Robot(0, m, m.floor, new Point(3, 3));
        Assert.assertEquals("0", r.toString());
    }
}
