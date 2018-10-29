package storage;

import java.io.IOException;
import java.util.List;

public class TestDatabase implements Database {
	private List<String[]> data;

	public List<String[]> savedData;

	public TestDatabase(List<String[]> data) {
		this.data = data;
	}

	public List<String[]> extractData() throws IOException {
		return data;
	}

	public void saveData(List<String[]> rows) throws IOException {
		savedData = rows;
	}

}
