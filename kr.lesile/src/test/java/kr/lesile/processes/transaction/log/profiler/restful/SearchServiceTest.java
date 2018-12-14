package kr.lesile.processes.transaction.log.profiler.restful;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Before;
import org.junit.Test;

import kr.lesile.processes.transaction.log.common.customer.Account;
import kr.lesile.processes.transaction.log.common.customer.Customer;
import kr.lesile.processes.transaction.log.profiler.manager.ProfilerManager;
import kr.lesile.processes.transaction.log.profiler.restful.SearchService;

public class SearchServiceTest 
{
	private SearchService searchService;
	
	
	
	@Before
	public void setup()
	{
		this.searchService = new SearchService();
		this.searchService.init();
		
		Customer customer = new Customer( "CUSTOMER.1", "LEE.1", "20181204" );
		Account account = new Account( "ACCOUNT.1", 1000, "20181204" );
		customer.getAccountMap().put( "ACCOUNT.1" , account);
		
		ProfilerManager.customerMap.put( "CUSTOMER.1" , customer);
	}


	
	
	@Test
	public void testProcessingCustomer() throws MalformedURLException, IOException 
	{
		HttpURLConnection con = (HttpURLConnection)
                new URL("http","localhost", 4567, "/customer/CUSTOMER.1").
                        openConnection();
		
		assertTrue( (con.getResponseCode() == 200) );
	}
	
	
	
	@Test
	public void testProcessingAccount() throws MalformedURLException, IOException 
	{
		HttpURLConnection con = (HttpURLConnection)
                new URL("http","localhost", 4567, "/customer/CUSTOMER.1/account/ACCOUNT.1").
                        openConnection();

		assertTrue( (con.getResponseCode() == 200) );
	}
}
