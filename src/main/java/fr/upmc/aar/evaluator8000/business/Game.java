package fr.upmc.aar.evaluator8000.business;


public class Game extends Media{

	private String image;
	private String developer;
	private String publisher;
	private int platform;
	
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getDeveloper() {
		return developer;
	}
	public void setDeveloper(String developer) {
		this.developer = developer;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	public int getPlatform() {
		return platform;
	}
	public void setPlatform(int platform) {
		this.platform = platform;
	}
	
	public void update(Game fromGame)
	{
		this.genre = fromGame.genre;
		this.developer = fromGame.developer;
		this.image = fromGame.image;
	}
	
	@Override
	public String toString() {
		return super.toString() + " \n\t Type: " 
				+ "Game [developer=" + developer + ", publisher=" + publisher
				+ ", image=" + image + ", platform=" + platform + "]";
	}
	
	@Override
	public boolean equals(Object other)
	{
		if(this.getClass() != other.getClass())
			return false;
		
		if(this == other)
			return true;
		
		return ( this.title.equals(((Game)other).getTitle()) &&
				 this.platform == ((Game)other).getPlatform() &&
				 this.release.equals(((Game)other).getRelease())
				);
	}

	@Override
	public int hashCode()
	{
		int hash = 42 * this.title.hashCode();
		hash += 42 * this.platform;
		hash += 42 * this.release.hashCode();
		return hash;
	}
	
}
