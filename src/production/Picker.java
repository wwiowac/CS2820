package production;

/**
 * Created by kyle on 12/5/16.
 */
public class Picker {
    private Order order;
    private Package pack;
    public Picker(Order order, Package pack){
        this.order = order;
        this.pack = pack;
        for(int x=1; x<3; x++){
            for(int y= 27; y<29; y++){
                master.floor.updateItemAt(new Point(x,y), Cell.Type.PICKER);
            }
        }
    }

    public boolean compareContents(){
        return true;
    }


}
