package structures;

public enum Field {
	COUNTRY("/c", "Все фильмы этой страны, имеющиеся в базе, были предоставлены",
			"В базе нет фильмов, снятых в этой стране :с"),
	YEAR("/y", "Все фильмы этого года, имеющиеся в базе, были предоставлены",
			"В базе нет фильмов, снятых в этот год :с"),
	GENRE("/g", "Все фильмы этого жанра, имеющиеся в базе, были предоставлены",
			"В базе нет фильмов, снятых в этом жанре :с");

	String shortCut;
	String noFilmsLeft;
	String noFilmsAtAll;

	Field(String shortCut, String noFilmsLeft, String noFilmsAtAll) {
		this.shortCut = shortCut;
		this.noFilmsLeft = noFilmsLeft;
		this.noFilmsAtAll = noFilmsAtAll;
	}

	public String noFilmsLeft() {
		return noFilmsLeft;
	}

	public String noFilmsAtAll() {
		return noFilmsAtAll;
	}

	public String shortCut() {
		return shortCut;
	}

}