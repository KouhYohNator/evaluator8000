package evaluator.business;

import java.util.Date;

public abstract class Media {
	
	private int id;
	private String title;
	private String genre;
	private double score;
	private Date release;
	private boolean completed;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	public Date getRelease() {
		return release;
	}
	public void setRelease(Date release) {
		this.release = release;
	}
	public boolean isCompleted() {
		return completed;
	}
	public void setCompleted(boolean completed) {
		this.completed = completed;
	}
	@Override
	public String toString() {
		return "Media [title=" + title + ", genre=" + genre + 
				", score=" + score + ", release=" + release + "]";
	}
}
