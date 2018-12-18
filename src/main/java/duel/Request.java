package duel;

import structures.User;

public class Request {
	
	private User user;
	private Duel duel;
	private boolean isAccepted;
	
	public Request(User user, Duel duel) {
		this.user = user;
		this.duel = duel;
		this.isAccepted = false;
	}
	
	public void accept() {
		isAccepted = true;
	}
	
	public User getSender() {
		return user;
	}
	
	public Duel getDuel() {
		return duel;
	}
	
	public boolean isAccepted() {
		return isAccepted;
	}
}
