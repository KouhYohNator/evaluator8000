package evaluator.business;


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
	
	@Override
	public String toString() {
		return super.toString() + " \n\t Type: " 
				+ "Game [developer=" + developer + ", publisher=" + publisher
				+ ", image=" + image + ", platform=" + platform + "]";
	}
	
}
