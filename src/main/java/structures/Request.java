package structures;

import bot.Duel;

public class Request {
	
	public User user;
	public Duel duel;
	public boolean isAccepted;
	
	public Request(User user, Duel duel) {
		this.user = user;
		this.duel = duel;
		this.isAccepted = false;
	}
	
	public void accept() {
		isAccepted = true;
	}
}
