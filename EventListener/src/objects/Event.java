package objects;

public class Event {
	private int ID;
	private int userID;
	private int placeID;
	private String name;
	private String time;
	private String about;
	private String price;
	private String image;
	
	
	public Event(int ID,int userID,int placeID,String name,String time,String about,String price,
			String image){
		
		this.ID = ID;
		this.userID = userID;
		this.placeID = placeID;
		this.time = time;
		this.about = about;
		this.price = price;
		this.image = image;		
		this.name = name;
	}
	
	/*
	 * this method returns event ID
	 */
	public int getID(){
		return this.ID;
	}
	
	/*
	 * this method returns place ID
	 * where this event is held
	 */
	public int getPlaceID(){
		return this.placeID;
	}
	
	
	/*
	 * this method returns user ID who added this event
	 */
	public int getUserID(){
		return this.userID;
	}
	
	/*
	 * This method returns string name
	 */
	public String getName(){
		return this.name;
	}
	
	/*
	 * this method returns time of this event
	 */
	public String getTime(){
		return this.time;
	}
	
	/*
	 * this method returns about for this event
	 */
	public String getAbout(){
		return this.about;
	}
	
	/*
	 * this method returns price, for this event
	 */
	public String getPrice(){
		return this.price;
	}
	/*
	 * this method returns image for this event
	 */
	public String getImage(){
		return this.image;
	}
}
