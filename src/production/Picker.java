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
    }

    public boolean compareContents(){
        return true;
    }


}
