package fr.upmc.aar.evaluator8000.business;

public class Comment {

	private int id;
	private User user;
	private Media media;
	private String content;
	private double score;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Media getMedia() {
		return media;
	}
	public void setMedia(Media media) {
		this.media = media;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	
	@Override
	public String toString() {
		return "Comment [id=" + id + "user=" + user.getLogin() + ", media=" + media.getTitle() + 
				", score=" + score + ", content=" + content + "]";
	}
	
	@Override
	public boolean equals(Object other)
	{
		if(this.getClass() != other.getClass())
			return false;
		
		if(this == other)
			return true;
		
		return (
				this.media.equals(((Comment)other).getMedia()) &&
				this.user.equals(((Comment)other).getUser())
				);
	}
	
	@Override
	public int hashCode()
	{
		int hash = 42 * this.media.hashCode();
		hash += 42 * this.user.hashCode();
		return hash;
	}
}
