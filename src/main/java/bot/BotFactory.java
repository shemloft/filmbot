package bot;

import storage.IFilmDatabase;
import storage.IQuestionGenerator;

public class BotFactory implements IBotFactory{
	
	private IFilmDatabase database;
	private IQuestionGenerator generator;
	
	public BotFactory(IFilmDatabase database, IQuestionGenerator generator) {
		this.database = database;
		this.generator = generator;
	}

	@Override
	public IBot getInstance(String username) {
		return new Bot(username, new IState[] {new UserState(username, database), new GameState(generator)});
	}

}
