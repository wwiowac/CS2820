/*
*@author Jacob Guth
*
*A station is an area on the Floor defined by its Location (upper left corner),
*width and height.
*/
public class Station{
	
	private Location location;
	
	private final int WIDTH;
	private final int HEIGHT;
	private final int MAX_CAPACITY;
	
	private String name;
	
	public Station(Location location, int width, int height){
		this.location = location;
		this.WIDTH = width;
		this.HEIGHT = height;
	}
	
	public boolean contains(Location other){
		return(location.getX() <= other.getX() <= location.getX() + WIDTH
				&& location.getY() <= other.getY() <= location.getY() + HEIGHT);
	}
	
	public int getHeight(){
		return this.HEIGHT;
	}
	
	//send out a notification(most likely to Belt) that this station is currently full
	public void notifyFull(){}
	public Location getLocation(){
		return location;
	}
	
}