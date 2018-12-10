package bot;

import storage.IFilmDatabase;
import storage.QuestionDatabase;
import storage.RandomQuestionGenerator;
import structures.User;

public class BotFactory implements IBotFactory{
	
	private IFilmDatabase database;
	private QuestionDatabase qDatabase;
	
	public BotFactory(IFilmDatabase database, QuestionDatabase qDatabase) {
		this.database = database;
		this.qDatabase = qDatabase;
	}

	@Override
	public IBot getInstance(String username) {
		User user = new User(username);
		return new Bot(user, new IState[] {
				new UserState(user, database),
				new GameState(user, new RandomQuestionGenerator(qDatabase))});
	}

}
