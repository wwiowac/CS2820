package production;

/**
 * Created by Wesley on 12/5/2016.
 */
public class Order {
    public InventoryItem item;
    public OrderStatus status;

    public enum OrderStatus {
        OrderSubmitted,
        OrderCompleted
    }
}
