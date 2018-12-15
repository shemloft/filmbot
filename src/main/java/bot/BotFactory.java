package bot;

import java.util.concurrent.ConcurrentHashMap;

import storage.IFilmDatabase;
import storage.IQuestionGenerator;
import structures.User;

public class BotFactory implements IBotFactory{
	
	private IFilmDatabase filmDatabase;
	private IQuestionGenerator questionGenerator;
	
	public BotFactory(IFilmDatabase filmDatabase, IQuestionGenerator questionGenerator) {
		this.filmDatabase = filmDatabase;
		this.questionGenerator = questionGenerator;
	}

	@Override
	public IBot getInstance(String username, ConcurrentHashMap<Long, User> users) {
		User user = new User(username);
		return new Bot(user, new IState[] {
				new DialogState(user, filmDatabase),
				new GameState(user, questionGenerator),
				new ResultTableState(users)});
	}

}
