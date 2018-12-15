package bot;

import java.util.concurrent.ConcurrentHashMap;

import structures.User;

public interface IBotFactory {
	public IBot getInstance(String username, ConcurrentHashMap<Long, User> users);
}
