package kr.lesile.processes.transaction.log.common.telegram;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import kr.lesile.processes.transaction.log.common.telegram.JoinLog;

public class JoinLogTest 
{
	private JoinLog joinLog;
	
	private String customerNo;
	private String customerName;
	private String dateOfJoin;
	
	
	
	
	
	@Before
	public void setup()
	{
		this.customerNo = "CUSTOMER.1";
		this.customerName = "LEE.1";
		this.dateOfJoin = "20181203";
		
		this.joinLog = new JoinLog( this.customerNo, this.customerName, this.dateOfJoin );
	}
	
	
	
	
	
	@Test
	public void testJoinLog() 
	{
		JoinLog newJoinLog = new JoinLog( this.customerNo, this.customerName, this.dateOfJoin );
		assertNotNull( newJoinLog );
	}

	
	
	@Test
	public void testGetCustomerNo() 
	{
		assertThat( "CUSTOMER.1", is( this.joinLog.getCustomerNo() ));
	}

	
	
	@Test
	public void testGetCustomerName() 
	{
		assertThat( "LEE.1", is( this.joinLog.getCustomerName() ));
	}

	
	
	@Test
	public void testGetDateOfJoin() 
	{
		assertThat( "20181203", is( this.joinLog.getDateOfJoin() ));
	}

	
}
