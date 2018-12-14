package kr.lesile.processes.transaction.log.common.datamap;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.util.HashMap;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class DataMapTest 
{
	private HashMap<String, String> dataMap = null;
	
	
	
	
	
	@Before
	public void setup()
	{
		this.dataMap = new HashMap<String, String>();
	}
	
	
	
	
	
	@Test
	public void testDataMap() 
	{
		HashMap<String, String> newDataMap = new HashMap<String, String>();
		assertNotNull( newDataMap );
	}

	
	
	@Test
	public void testPut() 
	{
		this.dataMap.put( "Key1" , "Value1");
		assertThat( "Value1", is( this.dataMap.get("Key1") ));
	}

	
	
	@Test
	public void testGet() 
	{
		this.dataMap.put( "Key1" , "Value1");
		assertThat( "Value1", is( this.dataMap.get("Key1") ));
	}

	
	
	@Test
	public void testKeySet() 
	{
		Set<String> keySet = this.dataMap.keySet();
		assertNotNull( keySet );
	}

	
}
