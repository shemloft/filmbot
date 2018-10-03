package bot;

import java.security.KeyException;
import java.util.ArrayList;

public class Film 
{
	private String m_sTitle;
	private String m_sYear;
	private String m_sCountry;
	
	public Film(String sTitle, String sYear, String sCountry)
	{
		m_sTitle = sTitle;
		m_sYear = sYear;
		m_sCountry = sCountry;
	}
	
	public static ArrayList<String> getPossibleFields()
	{
		ArrayList<String> fields = new ArrayList<String>();
		fields.add("country");
		fields.add("year");
		fields.add("title");
		return fields;
		
	}
	
	public String getField(String fieldName) throws KeyException
	{
		if (fieldName.equals("country"))
			return getCountry();
		if (fieldName.equals("year"))
			return getYear();
		if (fieldName.equals("title"))
			return getTitle();
		throw new KeyException("Неизвестный ключ");
		
	}
	
	public String getYear() 
	{
		return m_sYear;
	}
	
	public String getCountry()
	{
		return m_sCountry;
	}
	
	public String getTitle()
	{
		return m_sTitle;
	}
	

}
