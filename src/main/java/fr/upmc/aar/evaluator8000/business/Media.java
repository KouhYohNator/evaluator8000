package fr.upmc.aar.evaluator8000.business;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public abstract class Media {
	
	protected int id;
	protected String title;
	protected String genre;
	protected double score;
	protected Date release;
	protected boolean completed;
	protected Set<Comment> comments = new HashSet<Comment>(0);
	
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
	public Set<Comment> getComments() {
		return comments;
	}
	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}
	@Override
	public String toString() {
		return "Media [id= " + id + ", title=" + title + ", genre=" + genre + 
				", score=" + score + ", release=" + release + ", comments" + comments + "]";
	}
}
