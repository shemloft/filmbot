package bot;

import java.util.concurrent.ConcurrentHashMap;

import structures.BotMessage;
import structures.Messages;
import structures.User;

public class TestBotFactory implements IBotFactory{

	@Override
	public IBot getInstance(String username, ConcurrentHashMap<Long, User> users) {
		return new TestBot(new Messages(new BotMessage(username, new String[0])));
	}

}
