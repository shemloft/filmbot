package storage;

import java.io.IOException;
import java.util.List;

public class TestFilmDatabaseFileHandler implements IFilmDatabaseFileHandler {
	private List<String[]> data;

	public List<String[]> savedData;

	public TestFilmDatabaseFileHandler(List<String[]> data) {
		this.data = data;
	}

	public List<String[]> extractData() throws IOException {
		return data;
	}

	public void saveData(List<String[]> rows) throws IOException {
		savedData = rows;
	}

	public void addData(String[] record) throws IOException {
		data.add(record);
	}

	public void deleteData(String[] record) throws IOException {
		data.remove(record);
	}

}
