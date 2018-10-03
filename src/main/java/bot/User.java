package bot;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


import au.com.bytecode.opencsv.CSVWriter;

public class User 
{
	public String Name;
	public ArrayList<Film> savedFilms;

	public User(String name) 
	{
		Name = name;
		savedFilms = new ArrayList<Film>();
	}

	public void createUserInfo() throws IOException {
		CSVWriter writer = new CSVWriter(new FileWriter(Name + ".csv", true));
		writer.close();
	}

	public void addInfo() throws IOException {

		CSVWriter writer = new CSVWriter(new FileWriter(Name + ".csv", true));
		for (Film film : savedFilms) {
			String[] record = { film.getTitle(), film.getCountry(), film.getYear() };
			writer.writeNext(record);
		}
		writer.close();
	}

	public void ReadInfo() throws Exception 
	{
		ParserCSV parser = new ParserCSV(Name + ".csv");
		savedFilms = parser.filmList;
	}

	public void addFilm(Film film) 
	{
		savedFilms.add(film);
	}

}