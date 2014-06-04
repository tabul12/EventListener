package Objects;

public class Band {
	private int ID;
	private int UserID;
	private String Name;
	private String About;
	private String Mail;
	/**
	 * Constructor
	 * @param BandUserID
	 * @param name
	 * @param BandAbout
	 * @param BandMail
	 */
	public Band(int ID,int UserID,String Name,String About,String Mail)
	{
		this.ID = ID;
		this.UserID = UserID;
		this.Name = Name;
		this.About = About;
		this.Mail = Mail;
	}
	/**
	 * 
	 * @returns id
	 */
	public int getID()
	{
		return ID;
	}
	public int getUserID()
	{
		return UserID;
	}
	/**
	 * 
	 * @returns name
	 */
	public String getName(){
		return Name;
	}
	/**
	 * 
	 * @returns information about band
	 */
	public String getAbout()
	{
		return About;
	}
	/**
	 * 
	 * @returns mail
	 */
	public String getMail(){
		return Mail;
	}
}
