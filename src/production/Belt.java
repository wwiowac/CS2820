package production;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by jacobguth on 12/5/16.
 */
public class Belt implements EventConsumer{

    private Master master;
    private Floor floor;

    private ArrayList<Bin> cells;

    private boolean canMove;
    private int NUM_CELLS;

    public Belt(Master master, Floor floor){

        this.master = master;
        this.floor = floor;

        cells = new ArrayList<>();

        NUM_CELLS = 30;
        for(int i=0; i<NUM_CELLS; i++){
            floor.updateItemAt(new Point(0,i), Cell.Type.BELT);
        }
        canMove = true;
    }

    private void move(){

        for(int i=1; i<cells.size(); i++){
            cells.set(i-1, cells.get(i));
            floor.updateItemAt(new Point(0, i-1), floor.getCell(new Point(0, i)).type);
        }
        cells.set(cells.size()-1, null);
        floor.updateItemAt(new Point (0, cells.size()-1), Cell.Type.BELT);
    }

    @Override
    public void handleTaskEvent(Task task, Event event) {
        switch (task.type){
            case MoveBelt:
                move();
                Event spawnedEvent = new Event(new Task(Task.TaskType.MoveBelt), this);
                master.scheduleEvent(spawnedEvent,1);
                break;
            case AddBinToBelt:
                addBin(task.bin, task.location.y);
                break;
        }
    }
    /**
     * @description: starts belt movement
     */
    public void resume(){
        canMove = true;
    }
    /**
     * @description: stops belt movement
     */
    public void stop(){
        canMove = false;
    }

    public void addBin(Bin bin, int index){
        cells.set(index, bin);
        floor.updateItemAt(new Point(0,index), Cell.Type.BINONBELT);
    }

}
