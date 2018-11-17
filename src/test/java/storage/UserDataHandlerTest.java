//package storage;
//
//import junit.framework.TestCase;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.junit.Test;
//
//import structures.Field;
//import structures.User;
//
//public class UserDataHandlerTest extends TestCase {
//
//	private IDatabase database;
//	private String userName;
//	private String userFileID;
//	private UserDataHandler userDataHandler;
//
//	private void initializeUserDataHandler(List<String[]> data) {
//		database = new TestDatabase(data);
//		userName = "name";
//		userFileID = "fileID";
//		userDataHandler = new UserDataHandler(database, userName, userFileID);
//	}
//
//	@Test
//	public void testCreateUserEmptyDatabase() throws Exception {
//		List<String[]> data = new ArrayList<String[]>();
//		initializeUserDataHandler(data);
//
//		User user = userDataHandler.getUser();
//		assertEquals(new ArrayList<String>(), user.savedFilmsIDs);
//		assertEquals(userName, user.name);
//		assertEquals(userFileID, user.ID);
//	}
//
//	@Test
//	public void testCreateUserNoCurrentOption() throws Exception {
//		List<String[]> data = new ArrayList<String[]>();
//		String[] idRow = { "6" };
//		data.add(idRow);
//		initializeUserDataHandler(data);
//
//		User user = userDataHandler.getUser();
//		assertEquals(1, user.savedFilmsIDs.size());
//		assertEquals("6", user.savedFilmsIDs.get(0));
//		assertEquals(null, user.currentField);
//	}
//
//	@Test
//	public void testCreateUserWithCurrentOption() throws Exception {
//		List<String[]> data = new ArrayList<String[]>();
//		String[] idRow = { "6" };
//		String[] optionRow = { "COUNTRY", "США" };
//		data.add(idRow);
//		data.add(optionRow);
//		initializeUserDataHandler(data);
//
//		User user = userDataHandler.getUser();
//		assertEquals(1, user.savedFilmsIDs.size());
//		assertEquals("6", user.savedFilmsIDs.get(0));
//		assertEquals(Field.COUNTRY, user.currentField);
//		assertEquals("США", user.getCurrentKey());
//	}
//
//	@Test
//	public void testSaveUserWithCurrentOption() throws Exception {
//		List<String> IDList = new ArrayList<String>();
//		IDList.add("8");
//		User user = new User("Даша", "Даша", IDList, "COUNTRY", "США");
//		List<String[]> data = new ArrayList<String[]>();
//		initializeUserDataHandler(data);
//
//		String[] expectedIDRow = { "8" };
//		String[] expectedOptionRow = { "COUNTRY", "США" };
//
//		userDataHandler.saveUser(user);
//		TestDatabase testDatabase = (TestDatabase) database;
//		List<String[]> savedData = testDatabase.savedData;
//
//		assertEquals(2, savedData.size());
//		assertEquals(expectedIDRow[0], savedData.get(0)[0]);
//		assertEquals(expectedOptionRow[0], savedData.get(1)[0]);
//		assertEquals(expectedOptionRow[1], savedData.get(1)[1]);
//
//	}
//
//	@Test
//	public void testSaveUserNoCurrentOption() throws Exception {
//		List<String> IDList = new ArrayList<String>();
//		IDList.add("8");
//		User user = new User("Даша", "Даша", IDList, null, null);
//		List<String[]> data = new ArrayList<String[]>();
//		initializeUserDataHandler(data);
//
//		String[] expectedIDRow = { "8" };
//
//		userDataHandler.saveUser(user);
//		TestDatabase testDatabase = (TestDatabase) database;
//		List<String[]> savedData = testDatabase.savedData;
//
//		assertEquals(1, savedData.size());
//		assertEquals(expectedIDRow[0], savedData.get(0)[0]);
//	}
//
//}
