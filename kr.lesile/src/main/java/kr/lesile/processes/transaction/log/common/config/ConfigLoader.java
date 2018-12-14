package kr.lesile.processes.transaction.log.common.config;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader 
{
	private Properties property = null;
	private InputStream inputStream = null;
	
	
	
	
	public ConfigLoader( String propertyPath ) throws IOException
	{
		this.property = new Properties();
		this.inputStream = getClass().getClassLoader().getResourceAsStream( propertyPath );
		
		if ( this.inputStream != null ) 
		{
			this.property.load( this.inputStream );
		}
		else
		{
			throw new FileNotFoundException("property file '" + propertyPath + "' is not found");
		}
	}
	
	
	
	
	
	public int getIntTag( String tag ) 
	{
		int result = Integer.parseInt( this.property.getProperty(tag) );
		return result;
	}
	
	
	
	public String getStringTag( String tag ) 
	{
		String result = this.property.getProperty(tag);
		return result;
	}
	
	
}
