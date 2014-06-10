package objects;

public class Event {
	private int ID;
	private int userID;
	private int placeID;
	private String time;
	private String about;
	private String price;
	private String image;
	
	public Event(int ID,int userID,int placeID,String time,String about,String price,
			String image){
		
		this.ID = ID;
		this.userID = userID;
		this.placeID = placeID;
		this.time = time;
		this.about = about;
		this.price = price;
		this.image = image;		
	}
	
	public int getID(){
		return this.ID;
	}
	
	public int getPlaceId(){
		return this.placeID;
	}
	
	public int getUserID(){
		return this.userID;
	}
	
	public String getTime(){
		return this.time;
	}
	
	public String getAbout(){
		return this.about;
	}
	
	public String getPrice(){
		return this.price;
	}
	public String getImage(){
		return this.image;
	}
}
