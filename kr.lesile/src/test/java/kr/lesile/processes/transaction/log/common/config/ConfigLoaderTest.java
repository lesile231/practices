package kr.lesile.processes.transaction.log.common.config;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

public class ConfigLoaderTest 
{
	private Properties property;
	
	
	
	
	
	@Before
	public void setup() throws IOException, InterruptedException 
	{
		String propertyPath = "./transaction.log.producer.properties";
		
		property = new Properties();
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream( propertyPath );
		
		if ( inputStream != null ) 
		{
			this.property.load( inputStream );
		}
		else
		{
			throw new FileNotFoundException("property file '" + propertyPath + "' is not found");
		}
	}

	
	
	
	
	@Test
	public void testGetIntTag() 
	{
		String tag = "broker.acks";
		
		assertNotNull( this.property );
		
		assertNotNull( this.property.getProperty(tag) );
		
		assertThat( 1 , is(Integer.parseInt( this.property.getProperty(tag) )) );
	}

	
	
	@Test
	public void testGetStringTag() 
	{
		String tag = "broker.topic";

		assertNotNull( this.property );
		
		assertNotNull( this.property.getProperty(tag) );
		
		assertThat( "TRANSACTION_LOG" , is( this.property.getProperty(tag) ) );
	}
	
	
}
