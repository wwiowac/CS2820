package code;

public class Orders {
public int ordernumber;
public InventoryItem item;
public int amount;
/**
 * @author Ryan;
 */
//to keep track if we are working on it 
// or can do the string thing 
public String process;
public boolean isActive=false;
/*
 * public InventoryItem[] itemstoget
 * public int[] amount;
 * 
 * 
 */
 
int i=0;

//public orders(InventoryItem[], int[]  amount, ordernumber)
public Orders(InventoryItem item,int number,int ordernumber)
{
amount=number;
	this.ordernumber=ordernumber;
	this.item=item;
	process="not working on";
	//this.things=new InventoryItem[number];
	//things[i]=item;
	//amout=new int[number];
			//numberlist[i]=number;
	//i++;
	
	
	
}
/*
public void add(InventorItem item,int number)
{
	things[i]=item;
   numberlist[i]=number;
   i++;
}
*/
/**
 * 
 * @Rkrutwig
 */
public int getNumber()
{
	return ordernumber;
	
}
//if want others can change here 
public String getItems(int ordernumber)
{if(ordernumber==this.ordernumber)
	return(item.getName());
return("not in the order");
}
//for moving order along can assign it here
public void changeProcess(String newprocess)
{
	
	this.process=newprocess;
}
//p

public String getProces()
{
return process;
}
//end 
}