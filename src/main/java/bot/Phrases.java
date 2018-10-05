package bot;

import java.util.HashMap;
import java.util.Map;

public class Phrases {
	public static final String HELLO = "Назовите себя, пожалуйста";

	public static final String HELP = "Этот бот кидает кино по вашим запросам.\n" + "Формат ввода:\n" + "/y год\n"
			+ "/c страна\n" + "\n" + "Может быть выбрана только одна опция\n" + "/next для следующего фильма\n"
			+ "Вызов справки: /help\n" + "Корректный выход из бота с сохранением: /exit\n";

	public static final String UNKNOWN_COMMAND = "Неизвестная команда, загляни, пожалуйста, в справку";

	public static final String SHORT_COMMAND = "Слишком короткая команда, не могу понять :с";

	public static final String YEAR_NAN = "Ну как так, год должен быть числом";

	public static final String NEXT_WITHOUT_OPT = "Дружок, сначала выбери опцию, а потом проси фильм";

	public static final String DATABASE_ERROR = "Ошибочка с базой данных, перепроверьте её";

	public Map<String, String> yearPhrases;
	public Map<String, String> countryPhrases;

	public Phrases() {
		yearPhrases = new HashMap<String, String>();
		yearPhrases.put("no_films", "В базе нет фильмов, снятых в этот год :с");
		yearPhrases.put("all_films", "Все фильмы этого года, имеющиеся в базе, были предоставлены");
		countryPhrases = new HashMap<String, String>();
		countryPhrases.put("no_films", "В базе нет фильмов, снятых в этой стране :с");
		countryPhrases.put("all_films", "Все фильмы этой страны, имеющиеся в базе, были предоставлены");
	}

	public Map<String, String> getDictByKey(String keyName) {
		if (keyName.equals("year"))
			return yearPhrases;
		if (keyName.equals("country"))
			return countryPhrases;
		return null;
	}

}
