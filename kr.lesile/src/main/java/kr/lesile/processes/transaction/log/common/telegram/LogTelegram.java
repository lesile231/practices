package kr.lesile.processes.transaction.log.common.telegram;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * 기본 로그 클래스
 */
public class LogTelegram 
{
	public String getJsonData()
	{
		Gson gson = null;
		String jsonData = null;
		
		gson = new GsonBuilder().create(); 
		jsonData = gson.toJson( this );
		
		return jsonData;
	}
}
