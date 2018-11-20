package storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import structures.Field;
import structures.User;

public class UserDataHandler {

	private IFilmDatabaseFileHandler fileHandler;
	private String userName;
	private String userFileID;

	public UserDataHandler(IFilmDatabaseFileHandler fileHandler, String userName, String userFileID) {
		this.fileHandler = fileHandler;
		this.userName = userName;
		this.userFileID = userFileID;
	}

	public User getUser() throws Exception {
		List<String[]> extractedList = fileHandler.extractData();
		
		Map<Field, List<String>> currentData = new HashMap<Field, List<String>>();
		if (extractedList.size() == 0)
			return new User(userName, userFileID, new ArrayList<String>(), null);
		String[] lastRow = extractedList.get(extractedList.size() - 1);
		
		if (lastRow.length >= 2) {
			for (int i = 0; i < lastRow.length; i += 2) {
				String fieldString = lastRow[i];
				String option = lastRow[i+1];
				Field field;
				try {
					field = Field.valueOf(fieldString);
				} catch (IllegalArgumentException e) {
					continue;
				}
				
				if (!currentData.containsKey(field))
					currentData.put(field, new ArrayList<String>());
				currentData.get(field).add(option);
			}
		}
		
		if (currentData.size() == 0)
			currentData = null;
		
		User user = new User(userName, userFileID, getUserIdList(extractedList), currentData);
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
		List<String[]> rowList = new ArrayList<String[]>();
		for (String id : IDList) {
			String[] row = { id };
			rowList.add(row);
		}
		return rowList;
	}

	public void saveUser(User user) throws Exception {
		List<String[]> rowList = getStringRows(user.savedFilmsIDs);
		List<String> row = new ArrayList<String>();
		if (user.currentOptions != null) {
			for (Field field : user.currentOptions.keySet())
				for (String option : user.currentOptions.get(field)) {
					row.add(String.valueOf(field));
					row.add(option);
				}
			rowList.add(row.toArray(new String[row.size()]));
		}
		fileHandler.saveData(rowList);
	}

}
