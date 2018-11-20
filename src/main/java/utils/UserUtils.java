package utils;

import dialog.Phrases;
import storage.CSVHandler;
import storage.UserDataHandler;
import structures.User;

public class UserUtils {

	public static User getUser(String name, String userFileID) {
		CSVHandler helperCSV = new CSVHandler(userFileID);
		UserDataHandler userData = new UserDataHandler(helperCSV, name, userFileID);
		User user;
		try {
			user = userData.getUser();
		} catch (Exception e) {
//			e.printStackTrace();
			user = new User(name, userFileID, null, null);
		}
		return user;
	}

	public static void saveUser(User user) throws Exception {
		try {
			CSVHandler helperCSV = new CSVHandler(user.ID);
			UserDataHandler userData = new UserDataHandler(helperCSV, user.name, user.ID);
			userData.saveUser(user);
		} catch (Exception e) {
			throw new Exception(Phrases.SAVE_USER_ERROR);
		}
	}

}
