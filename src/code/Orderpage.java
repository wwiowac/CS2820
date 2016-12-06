package code;
import java.util.HashMap;
import java.util.Random;
//import java.util.HashMap<K,V>;
public class Orderpage {
	//private InventoryList list;
	/**
	 * @author Ryan
	 */
	public Random rand=new Random();
	public HashMap<Integer,Orders> orders;
	/*
	public Orderpage(InventoryList temp){
		this.list=temp;
		orders=new HashMap<Integer,Orders>();
		
	}
	*/
public String veiwprocess(int Ordernumber)
{
	
	String temp="does not exist";
	
	/**
	 * @author Ryan
	 * so making a little hash map with ordes and numebrs 
	 */
	if(orders.containsKey(Ordernumber))
	 temp=orders.get(Ordernumber).getProces();	
	
	
// looks in the que for the that thing and then returns the process of that order
	/*forEach(Order Item:Orderlist)
	if(ordernumber==Item.name())
	temp=Item.getStage();
	
	// will set temp to the spot the order is on the step or could do ints
	 
	 */
	return temp;
	
}
/**
 * @author Ryan
 * @param item
 * @param howmany
 * @param ordernumber
 */
// 2 mehtods one for new orders and one for exicting orders.
public void addorder(InventoryItem item,int howmany,int ordernumber)
{
	//orders.get(ordernumber).addItem(item,howmany);
	// here is where you want to enques
	//enqueue(temp2)
}
/**
 * 
 * @param item
 * @param howmany
 * @author Ryan
 */
public void addorder(InventoryItem item,int howmany)
{
	// adds this order to the warhouse que
	int temp=rand.nextInt()*123;
	while(orders.containsKey(temp)==true)
	{
		temp=rand.nextInt()*123;
		
	}
	Orders temp2=new Orders(item,howmany,temp);
	
	orders.put(temp,temp2);
	// here is where you want to enques
	//enqueue(temp2)
}
/**
 * 
 * @param number
 * @author Ryan
 */
public void cancelOrder(int number)
{
	if(orders.containsKey(number))
	{
		orders.remove(number);
		//dequeue(orders.get(number));
	}
	//dequeue(number);
// deletes it from the robot que 
	// we need to replace the items?
}
}
