package bot;

public class Film 
{
	private String m_sTitle;
	private int m_iYear;
	private String m_sCountry;
	
	public Film(String sTitle, int iYear, String sCountry)
	{
		m_sTitle = sTitle;
		m_iYear = iYear;
		m_sCountry = sCountry;
	}
	
	public String getFilmInfo()
	{
		return String.format("title: %s, country: %s, year: %d", m_sTitle, m_sCountry, m_iYear);
	}
	
	public int getYear() 
	{
		return m_iYear;
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
