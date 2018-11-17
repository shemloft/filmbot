package storage;

import java.io.IOException;
import java.util.List;

public interface IDatabase {	
	public void addData(String[] record) throws IOException;
	
	public List<String[]> extractData() throws IOException;

	public void saveData(List<String[]> rows) throws IOException;
}
