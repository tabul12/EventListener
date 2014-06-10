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
	
	public int getID(){
		return this.ID;
	}

	public int getUserID(){
		return this.userID;
	}
	
	public String getName(){
		return this.name;
	}
	
	public String getAdress(){
		return this.adress;
	}
	
	public String getAbout(){
		return this.about;
	}
	public String getImage(){
		return this.image;
	}
}
