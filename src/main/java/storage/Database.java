package storage;

import java.io.IOException;
import java.util.List;

public interface Database {
	public List<String[]> extractData () throws IOException;
	public void saveData(List<String[]> rows) throws IOException;
}
