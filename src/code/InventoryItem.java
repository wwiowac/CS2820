package code;
/**
 * 
 * @author Ryan
 *
 */
public class InventoryItem {
	public String name;
	public int id;
	public int x;
	public int y;
	public String process;
	//will need a shelf
	public InventoryItem (String Name, int id,int x,int y)
	{
		process="has not been started";
	this.x=x;
	this.y=y;
	this.id=id;
	this.name=Name;
	}

	public String getName()
	{return name;
	}
	public int getId()
	{
		return id;
	}
	public int getX()
	{
		return x;
	}
	public int getY()
	{
		return y;
	}
	public String getProces()
	{
	return process;
	}
	public void changeProcess(String newprocess)
	{
		
		this.process=newprocess;
	}
	//public shelf getShelf()
	//return shelf;
	}

//end 

