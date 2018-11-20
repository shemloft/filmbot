//package utils;
//
//import static org.junit.Assert.assertEquals;
//
//import java.io.File;
//import java.util.ArrayList;
//import java.util.List;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//
//import structures.User;
//import utils.UserUtils;
//
//public class UserUtilsTest {
//	
//	
//	private List<String> idList;	
//	private User user; 
//
//	
//	private void createFilmList() {
//		idList = new ArrayList<String>();
//		idList.add("12");
//		idList.add("13");
//	}
//
//	@Before
//	public void createUser() {
//		createFilmList();
//		user = new User("a", "a",  idList, null);
//	}
//	
//	@Before
//	public void testSaveUser() throws Exception {
//		UserUtils.saveUser(user);
//	}	
//	
//
//	@Test
//	public void testGetUser() {
//		assertEquals(UserUtils.getUser(user.name, user.ID).savedFilmsIDs, user.savedFilmsIDs);
//	}
//
//	@After
//	public void deleteFile() {
//		File file = new File(user.ID + ".csv");
//		file.delete();
//	}
//}
