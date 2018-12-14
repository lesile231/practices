package kr.lesile.processes.transaction.log.common.messagequeue;

import java.util.LinkedList;

public class MessageQueue<T> 
{
	private LinkedList<T> queue = null;
	
	
	
	
	
	public MessageQueue()
	{
		this.queue = new LinkedList<T>();
	}
	
	
	
	
	
	public int size()
	{
		synchronized ( this.queue )
		{
			return this.queue.size();
		}
	}
	
	
	
	public boolean put( T data )
	{
		boolean result = false;
		
		synchronized ( this.queue )
		{
			this.queue.add( data );
		}
		
		return result;
	}
	
	
	
	public T poll()
	{
		synchronized ( this.queue )
		{
			return this.queue.poll();
		}
	}
}
