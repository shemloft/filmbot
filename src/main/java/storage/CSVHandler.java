package storage;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

public class CSVHandler implements IFilmDatabaseFileHandler {

	public String fileName;

	public CSVHandler(String fileName) {
		this.fileName = fileName;
	}

	public String getFileName() {
		return fileName;
	}

	public void saveData(List<String[]> rows) throws IOException {
		CSVWriter writer = new CSVWriter(new FileWriter(fileName + ".csv"), ';', '"');
		for (String[] record : rows)
			writer.writeNext(record);
		writer.close();
	}

	public void addData(String[] record) throws IOException {
		CSVWriter writer = new CSVWriter(new FileWriter(fileName + ".csv", true), ';', '"');
		writer.writeNext(record);
		writer.close();
	}

	public List<String[]> extractData() throws IOException {
		List<String[]> allRows = new ArrayList<String[]>();
		try {
			CSVReader reader = new CSVReader(new FileReader(fileName + ".csv"), ';', '"');
			allRows = reader.readAll();
			reader.close();
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException(dialog.Phrases.DATABASE_ERROR);
		}
		return allRows;
	}
}