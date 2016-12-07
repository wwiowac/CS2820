package production;

import java.awt.*;

/**
 * @author Jacob Guth
 * @author Jacob Roschen
 * @author Wesley
 */
public class Belt implements EventConsumer{

    private Master master;
    private Floor floor;
    private Bin[] cells = new Bin[30];

    private boolean celloccupied = false;

    /**
     * @constructor: initializes a belt with 30 cells
     * @param master
     * @param floor
     */
    public Belt(Master master, Floor floor){

        this.master = master;
        this.floor = floor;

        for(int i=0; i<cells.length; i++){
            floor.updateItemAt(new Point(0,i), Cell.Type.BELT);
        }
    }

    /**
     * @description: increments the contents of the cells on the belt, changing the cell type accordingly
     */
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
        //If a bin has reached the packer, schedule an OrderPacked event
        if(cells[11] != null){
            Event event = new Event(new Task(Task.TaskType.PackOrder, cells[11].getOrder()), master.packer);
            master.scheduleEvent(event);
        }
    }

    /**
     * @description: task handler for moving the belt and adding a bin to the belt
     * @param task
     * @param event
     */
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
                System.out.println("New bin created for picker");
                break;
        }
    }

    /**
     *
     * @param bin: bin to be added to the belt
     * @param index: index of the cell the bin is being added to
     */
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
