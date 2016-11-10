import java.util.ArrayList;
import java.util.HashMap;

public class Floor {
    private Master master;

    private HashMap<Robot, Integer[]> robot_locs;
    private ArrayList<Robot> robots;

    Floor(Master m) {
        master = m;
        robot_locs = new HashMap<>();
        robots = new ArrayList<>();
        seedRobots(5);
    }

    public void seedRobots(int robotcount) {
        for (int i=0; i<robotcount; i++) {
            Robot robot = new Robot(i, master, this);
            Integer[] postion = {10 + i, 0};
            robot_locs.put(robot, postion);
            robots.add(robot);
        }
    }

    public Integer[] getRobotPosition(Robot robot) {
        return robot_locs.get(robot);
    }

    public void setRobotPosition(Robot robot, Integer[] position) {
        robot_locs.put(robot, position);
    }

    public ArrayList<Robot> getAllRobots() {
        return robots;
    }

}
