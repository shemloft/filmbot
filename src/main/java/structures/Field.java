package structures;

public enum Field {
	YEAR("Теперь выберите год"),
	GENRE("Теперь выберите жанр"),
	ACTOR("Теперь выберите актера")	;

	String nowChoose;

	Field(String nowChoose) {		
		this.nowChoose = nowChoose;
	}


	public String nowChoose() {
		return nowChoose;
	}

}