package fr.upmc.aar.evaluator8000.business;

import java.util.HashSet;
import java.util.Set;

public class User {

	private int id;
	private String login;
	private String password;
	private boolean active;
	private Set<Comment> comments = new HashSet<Comment>(0);
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public Set<Comment> getComments() {
		return comments;
	}
	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}
	
	@Override
	public String toString() {
		return "User [id=" + id + "login=" + login + ", isActive=" + active +"]";
	}
	
	@Override
	public boolean equals(Object other)
	{
		if(this.getClass() != other.getClass())
			return false;
		
		if(this == other)
			return true;
		
		return ( this.login.equals(((User)other).getLogin()) );
	}
	
	@Override
	public int hashCode()
	{
		int hash = 42 * this.login.hashCode();
		return hash;
	}
}
