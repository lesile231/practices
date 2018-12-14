package kr.lesile.processes.transaction.log.profiler.manager;

import org.junit.Before;
import org.junit.Test;

import kr.lesile.processes.transaction.log.profiler.manager.ProfilerManager;

public class ProfilerManagerTest 
{
	private ProfilerManager profilerManager;
	
	
	
	@Before
	public void setup()
	{
		this.profilerManager = new ProfilerManager();
	}
	
	
	
	@Test
	public void testRun() 
	{
		this.profilerManager.start();
	}
}
