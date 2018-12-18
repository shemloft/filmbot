package structures;

import java.util.ArrayList;
import java.util.List;

import duel.Request;

public class User {

	private String name;
	public List<Integer> savedFilmsIDs;
	private boolean firstTime;
	private int points;
	public boolean inDuel;
	public boolean answeredInDuel;
	public boolean correctAnsweredInDuel;
	private User opponent;
	private Long ID;
	private int duelPoints;
	private Request request;
	

	public User(String name, Long ID) {
		this.savedFilmsIDs = new ArrayList<Integer>();
		firstTime = true;
		this.name = name;
		this.ID =ID;
	}
	
	public boolean isFirstTime() {
		if (!firstTime)
			return firstTime;
		firstTime = false;
		return true;			
	}
	
	public Long getID() {
		return ID;
	}
	
	
	public String getName() {
		return name;
	}
	
	public void updateName(String newName) {
		name = newName;
	}

	public void addFilm(Film film) {
		savedFilmsIDs.add(film.getID());
	}
	
	public void addPoints(int earnedPoints) {
		points += earnedPoints;
	}
	
	public int getPoints() {
		return  points;
	}
	
	public void addDuelPoints(int earnedPoints) {
		duelPoints += earnedPoints;
	}
	
	public int getDuelPoints() {
		return  duelPoints;
	}
	
	public void setOpponent(User opponent) {
		this.opponent = opponent;
	}
	
	public User getOpponent() {
		return opponent;
	}
	
	public void resetOpponent() {
		opponent = null;
	}
	
	public void resetDuelPoints() {
		duelPoints = 0;
	}
	
	public void onDuelFinished() {
		resetDuelPoints();
		prepareForNewAnswerInDuel();
		inDuel = false;
		request = null;
		resetOpponent();
	}
	
	public void prepareForNewAnswerInDuel() {
		answeredInDuel = false;
		correctAnsweredInDuel = false;
	}
	
	public void sendRequest(Request request) {
		this.request = request;
	}
	
	public boolean hasRequest() {
		return request != null;
	}
	
	public Request getRequest() {
		return request;
	}
	
	public void resetRequest() {
		request = null;
	}
	
}