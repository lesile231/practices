package kr.lesile.kakaobank.codingtest.transaction.log.common.messagequeue;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

public class MessageQueueTest 
{
	private LinkedList<String> queue;

	
	
	
	
	@Before
	public void setup()
	{
		this.queue = new LinkedList<String>();
	}
	
	
	
	
	
	@Test
	public void testMessageQueue() 
	{
		LinkedList<String> newQueue = new LinkedList<String>();
		assertNotNull( newQueue );
	}

	
	
	@Test
	public void testSize()
	{
		assertNotNull( this.queue );
		this.queue.add( "Test1" );
		assertThat( 1, is( this.queue.size() ));
	}

	
	
	@Test
	public void testPut() 
	{
		this.queue.push( "Test1" );
		
		assertThat( 1, is( this.queue.size() ));
		assertThat( "Test1", is( this.queue.poll() ));
	}

	
	
	@Test
	public void testPoll() 
	{
		this.queue.push( "Test1" );
		assertThat( "Test1", is( this.queue.poll() ));
	}

	
}
