package kr.lesile.kakaobank.codingtest.transaction.log.common.telegram;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class LogTelegramTest 
{
	@Test
	public void testGetJsonData() 
	{
		Gson gson = new GsonBuilder().create();
		
		assertNotNull( gson );
		
		String jsonData = gson.toJson( this );

		assertNotNull( jsonData );
	}
}
