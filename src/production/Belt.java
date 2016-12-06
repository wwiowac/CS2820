package production;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by jacobguth on 12/5/16.
 */
public class Belt implements EventConsumer{

    private Master master;
    private Floor floor;

    private Bin[] cells = new Bin[30];

    private boolean celloccupied = false;

    private boolean canMove;
    private int NUM_CELLS;

    public Belt(Master master, Floor floor){

        this.master = master;
        this.floor = floor;

        canMove = true;

        for(int i=0; i<cells.length; i++){
            floor.updateItemAt(new Point(0,i), Cell.Type.BELT);
        }
    }

    private void move(){
        celloccupied = false;
        for(int i=1; i<cells.length; i++){
            cells[i-1] = cells[i];
            Cell.Type nextcell = floor.getCell(new Point(0, i)).type;
            if (nextcell == Cell.Type.BINONBELT) {
                celloccupied = true;
            }
            floor.updateItemAt(new Point(0, i-1), nextcell);
        }
        cells[cells.length-1] = null;
        floor.updateItemAt(new Point (0, cells.length-1), Cell.Type.BELT);
    }

    @Override
    public void handleTaskEvent(Task task, Event event) {
        switch (task.type){
            case MoveBelt:
                if (celloccupied) {
                    move();
                    Event spawnedEvent = new Event(new Task(Task.TaskType.MoveBelt), this);
                    master.scheduleEvent(spawnedEvent, 1);
                }
                break;
            case AddBinToBelt:
                addBin(task.bin, task.location.y);
                master.scheduleEvent(event,1);
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
        if (!celloccupied) {
            Event spawnedEvent = new Event(new Task(Task.TaskType.MoveBelt), this);
            master.scheduleEvent(spawnedEvent, 1);
            celloccupied = true;
        }
        cells[index] = bin;
        floor.updateItemAt(new Point(0,index), Cell.Type.BINONBELT);
    }

}
