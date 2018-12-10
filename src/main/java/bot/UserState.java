package bot;

import java.util.Arrays;

import dialog.Phrases;
import storage.IFilmDatabase;
import structures.BotMessage;
import structures.Field;
import structures.Film;
import structures.Options;
import structures.User;

public class UserState implements IState{
	
	private User user;
	
	private IFilmDatabase database;
	
	private String[] expectedAnswers;
	
	private DialogState currentState;	
	private Field currentField;
	private Options options;
	
	private boolean moreOptions;
	
	public UserState(User user, IFilmDatabase database) {
		this.user = user;
		this.database = database;
		currentState = DialogState.BASIC;
		expectedAnswers = getExpectedAnswers(currentState);
		options = new Options();
	}
	
	public void updateName(String newName) {
		user.updateName(newName);
	}
	
	public BotMessage[] getAnswer(String input) {
		if (!isExpected(input)) {
			currentField = null;
			options.reset();
			currentState = DialogState.BASIC;
			expectedAnswers = getExpectedAnswers(currentState);
			return new BotMessage[] { new BotMessage(processUnexpectedCommand(input), expectedAnswers) };			
		}
		
		DialogState oldState = currentState;
		currentState = nextState(oldState);
			
		String answer = Phrases.UNKNOWN_COMMAND;
		expectedAnswers = getExpectedAnswers(DialogState.BASIC);
		
		switch (oldState) {
		case BASIC:
			if ("NEXT".equals(input)) {				
				if (options.isEmpty()) 
					options.reset();
				answer = options.isEmpty() ? Phrases.NEXT_WITHOUT_OPT :
					getResponse(database.getFilm(user.savedFilmsIDs, options));	
				currentState = DialogState.BASIC;
			} else {
				currentField = Field.valueOf(input);
				answer = currentField.nowChoose();
				expectedAnswers = getExpectedAnswers(currentState);
				if (!moreOptions) 
					options.reset();				
			}
			break;			
		
		case CHOSING:
			options.add(currentField, input);
			answer = "Есть еще параметры?";
			expectedAnswers = getExpectedAnswers(currentState);
			break;
		
		case MORE_OPTIONS:
			moreOptions = "ЕЩЕ ОПЦИЯ".equals(input);
			answer = moreOptions ? "Выберите еще опцию" :
				getResponse(database.getFilm(user.savedFilmsIDs, options));
			expectedAnswers = getExpectedAnswers(currentState);
			break;
		}
		options.print();
		return new BotMessage[] { new BotMessage(answer, expectedAnswers) };
	
	}
	
	private String processUnexpectedCommand(String input) {
		switch (input) {
		case "/start":
			return user.isFirstTime() 
					? String.format("Добро пожаловать, %s.%s", user.getName(), Phrases.HELP) 
					: String.format("Давно не виделись, %s.", user.getName());
		case "/help":
			return Phrases.HELP;
		default:
			return Phrases.UNKNOWN_COMMAND;
		}
	}
	
	private DialogState nextState(DialogState state) {
		switch(state) {
		case BASIC:
			return DialogState.CHOSING;
		case CHOSING:
			return DialogState.MORE_OPTIONS;
		case MORE_OPTIONS:
			return DialogState.BASIC;
		default:
			return DialogState.BASIC;
		}
	}
	
	private boolean isExpected(String input) {
		return Arrays.asList(expectedAnswers).contains(input);
	}
	
	
	
 	private String[] getExpectedAnswers(DialogState state) {
		switch(state) {
		case BASIC:
			return Phrases.BASIC_ANSWERS;
		case CHOSING:
			return database.getFieldValuesArray(currentField);
		case MORE_OPTIONS:
			return Phrases.MORE_OPTIONS_ANSWERS;
		default:
			return Phrases.BASIC_ANSWERS;
			
		}
	}
 	
 	private String getResponse(Film film) {
		if (film != null && film.ID == 0)
			return Phrases.NO_SUCH_FILM;
		else if (film != null)
			user.addFilm(film);
		return film != null ? film.title : Phrases.NO_MORE_FILM;
	}

	@Override
	public String getName() {
		return "Получение фильмов";
	}

}
