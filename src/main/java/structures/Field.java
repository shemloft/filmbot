package structures;

public enum Field {
	COUNTRY {
		public String noFilmsLeft() {
			return "Все фильмы этой страны, имеющиеся в базе, были предоставлены";
		};

		public String noFilmsAtAll() {
			return "В базе нет фильмов, снятых в этой стране :с";
		}
		
		public String shortCut() {
			return "/c";
		}
	},
	YEAR {
		public String noFilmsLeft() {
			return "Все фильмы этого года, имеющиеся в базе, были предоставлены";			
		};

		public String noFilmsAtAll() {			
			return "В базе нет фильмов, снятых в этот год :с";
		}
		
		public String shortCut() {
			return "/y";
		}
	};
	
	public abstract String noFilmsLeft();
	public abstract String noFilmsAtAll();
	public abstract String shortCut();

}
