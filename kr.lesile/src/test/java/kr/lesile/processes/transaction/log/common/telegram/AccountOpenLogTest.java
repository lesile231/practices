package kr.lesile.processes.transaction.log.common.telegram;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import kr.lesile.processes.transaction.log.common.telegram.AccountOpenLog;

public class AccountOpenLogTest 
{
	private AccountOpenLog accountOpenLog;
	
	private String customerNo;
	private String accountNo;
	private String dateOfAccountOpen;
	
	
	
	
	
	@Before
	public void setup()
	{
		this.customerNo = "CUSTOMER.1";
		this.accountNo = "ACCOUNT.1";
		this.dateOfAccountOpen = "20181203";
		
		this.accountOpenLog = new AccountOpenLog( this.customerNo, this.accountNo, this.dateOfAccountOpen );
	}

	
	
	
	
	@Test
	public void testAccountOpenLog() 
	{
		AccountOpenLog newAccountOpenLog = new AccountOpenLog( this.customerNo, this.accountNo, this.dateOfAccountOpen );
		assertNotNull( newAccountOpenLog );
	}

	
	
	@Test
	public void testGetCustomerNo() 
	{
		assertThat( "CUSTOMER.1", is( this.accountOpenLog.getCustomerNo() ));
	}

	
	
	@Test
	public void testGetAccountNo() 
	{
		assertThat( "ACCOUNT.1", is( this.accountOpenLog.getAccountNo() ));
	}

	
	
	@Test
	public void testGetDateOfAccountOpen() 
	{
		assertThat( "20181203", is( this.accountOpenLog.getDateOfAccountOpen() ));
	}
}
