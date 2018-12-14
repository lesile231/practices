package kr.lesile.processes.transaction.log.common.telegram;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import kr.lesile.processes.transaction.log.common.telegram.DepositLog;

public class DepositLogTest 
{
	private DepositLog depositLog;
	
	private String customerNo;
	private String depositAccountNo;
	private int depositAmount;
	private String dateOfDeposit;
	
	
	
	
	
	@Before
	public void setup()
	{
		this.customerNo = "CUSTOMER.1";
		this.depositAccountNo = "ACCOUNT.1";
		this.depositAmount = 1000;
		this.dateOfDeposit = "20181203";
		
		this.depositLog = new DepositLog( this.customerNo, this.depositAccountNo, this.depositAmount, this.dateOfDeposit );
	}
	
	
	
	@Test
	public void testDepositLog() 
	{
		DepositLog newDepositLog = new DepositLog( this.customerNo, this.depositAccountNo, this.depositAmount, this.dateOfDeposit );
		assertNotNull( newDepositLog );
	}

	
	
	@Test
	public void testGetCustomerNo() 
	{
		assertThat( "CUSTOMER.1", is( this.depositLog.getCustomerNo() ));
	}

	
	
	@Test
	public void testGetDepositAccountNo() 
	{
		assertThat( "ACCOUNT.1", is( this.depositLog.getDepositAccountNo() ));
	}

	
	
	@Test
	public void testGetDepositAccountAmount() 
	{
		assertThat( 1000, is( this.depositLog.getDepositAccountAmount() ));
	}
	
	
	
	@Test
	public void testGetDateOfDeposit()
	{
		assertThat( "20181203", is( this.depositLog.getDateOfDeposit() ));
	}

	
}
