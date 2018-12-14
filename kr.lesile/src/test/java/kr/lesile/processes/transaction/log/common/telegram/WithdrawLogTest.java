package kr.lesile.processes.transaction.log.common.telegram;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import kr.lesile.processes.transaction.log.common.telegram.WithdrawLog;

public class WithdrawLogTest 
{
	private WithdrawLog withdraw;
	
	private String customerNo;
	private String withdrawAccountNo;
	private Integer withdrawAmount;
	private String dateOfWithdraw;
	
	
	
	
	
	@Before
	public void setup()
	{
		this.customerNo = "CUSTOMER.1";
		this.withdrawAccountNo = "ACCOUNT.1";
		this.withdrawAmount = 1000;
		this.dateOfWithdraw = "20181203";
		
		this.withdraw = new WithdrawLog( this.customerNo, this.withdrawAccountNo, 
										this.withdrawAmount, this.dateOfWithdraw );
	}
	
	
	
	
	
	@Test
	public void testWithdrawLog() 
	{
		WithdrawLog newWithdraw = new WithdrawLog( this.customerNo, this.withdrawAccountNo, 
												  this.withdrawAmount, this.dateOfWithdraw );
		
		assertNotNull( newWithdraw );
	}

	
	
	@Test
	public void testGetCustomerNo() 
	{
		assertThat( "CUSTOMER.1", is( this.withdraw.getCustomerNo() ));
	}

	
	
	@Test
	public void testGetWithdrawAccountNo() 
	{
		assertThat( "ACCOUNT.1", is( this.withdraw.getWithdrawAccountNo() ));
	}

	
	
	@Test
	public void testGetWithdrawAmount() 
	{
		assertThat( 1000, is( this.withdraw.getWithdrawAmount() ));
	}
	
	
	
	@Test
	public void testDateOfWithdraw()
	{
		assertThat( "20181203", is( this.withdraw.getDateOfWithdraw() ));
	}

}
