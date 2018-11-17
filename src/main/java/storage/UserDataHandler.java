package storage;

import java.util.ArrayList;
import java.util.List;

import structures.User;

public class UserDataHandler {
	private IDatabase database;
	private String userName;
	private String userFileID;

	public UserDataHandler(IDatabase database, String userName, String userFileID) {
		this.database = database;
		this.userName = userName;
		this.userFileID = userFileID;
	}
	
	public User getUser() throws Exception {
		List<String[]> extractedList = database.extractData();
		if (extractedList.size() == 0)
			return new User(userName, userFileID, new ArrayList<String>(), null, null);
		String[] lastRow = extractedList.get(extractedList.size() - 1);
		String fieldName = null;
		String fieldKey = null;
		if (lastRow.length == 2) {
			fieldName = lastRow[0];
			fieldKey = lastRow[1];
		}
		User user = new User(userName, userFileID, getUserIdList(extractedList), fieldName, fieldKey);
		return user;
	}
	
	private List<String> getUserIdList(List<String[]> extractedData) {
		List<String> idList = new ArrayList<String>();
		for (String[] row : extractedData) {
			if (row.length == 1)
				idList.add(row[0]);
		}
		return idList;
	}
	
	private List<String[]> getStringRows(List<String> IDList) {
		List<String[]> rowList = new ArrayList<String[]>() ;
		for (String id : IDList) {
			String[] row = { id };
			rowList.add(row);
		}
		return rowList;
	}

	public void saveUser(User user) throws Exception {				
		List<String[]> rowList = getStringRows(user.savedFilmsIDs);				
		if (user.currentField != null && user.currentOptions.get(user.currentField) != null) {
			String[] row = {user.currentField.name(), user.currentOptions.get(user.currentField)};
			rowList.add(row);
		}			
		database.saveData(rowList);
	}

}
