package objects;

public class Place {
	private int ID;
	private int userID;
	private String name;
	private String adress;
	private String about;
	private String image;
	
	public Place(int ID,int userID,String name,String adress,String about,String image){
		this.ID = ID;
		this.userID = userID;
		this.name = name;
		this.adress = adress;
		this.about = about;
		this.image = image;		
	}
	
	/*
	 * this method returns place ID
	 */
	public int getID(){
		return this.ID;
	}

	/*
	 * this method returns user ID who 
	 * added this place
	 */
	public int getUserID(){
		return this.userID;
	}
	
	/*
	 * this method returns name of this place
	 */
	public String getName(){
		return this.name;
	}
	
	/*
	 * this method returns adress of this place
	 */
	public String getAdress(){
		return this.adress;
	}
	
	/*
	 * this method returns about for this place
	 */
	public String getAbout(){
		return this.about;
	}
	
	/*
	 * this method returns profile image for this place
	 */
	public String getProfileImage(){
		return this.image;
	}
}
