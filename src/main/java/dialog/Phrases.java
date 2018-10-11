package dialog;

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
}
