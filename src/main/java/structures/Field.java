package structures;

import dialog.Phrases;

public enum Field {
	COUNTRY("c", "Все фильмы этой страны, имеющиеся в базе, были предоставлены",
			"В базе нет фильмов, снятых в этой стране :с", Phrases.COUNTRIES),
	YEAR("y", "Все фильмы этого года, имеющиеся в базе, были предоставлены", "В базе нет фильмов, снятых в этот год :с",
			Phrases.YEARS),
	GENRE("g", "Все фильмы этого жанра, имеющиеся в базе, были предоставлены",
			"В базе нет фильмов, снятых в этом жанре :с", Phrases.GENRES);

	String shortCut;
	String noFilmsLeft;
	String noFilmsAtAll;
	String[] avaliableFields;

	Field(String shortCut, String noFilmsLeft, String noFilmsAtAll, String[] avaliableFields) {
		this.shortCut = shortCut;
		this.noFilmsLeft = noFilmsLeft;
		this.noFilmsAtAll = noFilmsAtAll;
		this.avaliableFields = avaliableFields;
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

	public String[] avaliableFields() {
		return avaliableFields;
	}

}