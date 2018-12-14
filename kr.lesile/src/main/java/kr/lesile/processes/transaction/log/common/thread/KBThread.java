package kr.lesile.processes.transaction.log.common.thread;

public class KBThread extends Thread
{
		
	public KBThread() {}
	
	
	
	
	
	public void doThreadWait( long expectedElapsedTime )
	{
		boolean isToWait = true;
		
		long time = System.currentTimeMillis();
		
		while ( isToWait )
		{
			if ( elapsedTime(time) >= expectedElapsedTime )
			{
				isToWait = false;
			}
		}
	}
	
	
	
	
	
	public long elapsedTime( long time ) 
	{ 
	    long diff = System.currentTimeMillis() - time; 
	    return diff; 
	} 
	
	
}
