package evaluator.business;

public class Movie extends Media {

	private String rating;
	private String runtime;
	private String director;
	private String cast;
	private String image;
	
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	public String getRuntime() {
		return runtime;
	}
	public void setRuntime(String runtime) {
		this.runtime = runtime;
	}
	public String getDirector() {
		return director;
	}
	public void setDirector(String director) {
		this.director = director;
	}
	public String getCast() {
		return cast;
	}
	public void setCast(String cast) {
		this.cast = cast;
	}
	
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	@Override
	public String toString() {
		return super.toString() + " \n\t Type: " 
				+ "Movie [director=" + director + ", cast=" + cast
				+ ", runtime=" + runtime + ", rating=" + rating + "]";
	}
	
}
