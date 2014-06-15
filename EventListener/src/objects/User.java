package objects;




public class User {
	private int ID;
	private String name;
	private String lastName;
	private String userName;
	private String password;
	private String mail;
	private String image;
	private String mobileNumber;
	
	public User(int ID, String name, String lastName, String userName, String password, String mail,
			String image,  String mobileNumber){
		this.ID = ID;
		this.name = name;
		this.lastName = lastName;
		this.userName = userName;
		this.password = password;
		this.mail = mail;
		this.image = image;
		this.mobileNumber = mobileNumber;
	}
	
	
	public int getID(){
		return ID;
	}
	
	public String getName(){
		return name;
	}
	
	
	
	public String getLastName(){
		return lastName;
	}
	
	public String getUserName(){
		return userName;
	}
	
	public String getMobileNumber(){
		return mobileNumber;
	}
	
	public String getPassword(){
		return password;
	}
	
	public String getMail(){
		return mail;
	}
	
	public String getImage(){
		return image;
	}
	
	
	
}
