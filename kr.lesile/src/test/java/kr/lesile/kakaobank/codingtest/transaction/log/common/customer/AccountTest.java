package kr.lesile.kakaobank.codingtest.transaction.log.common.customer;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import kr.lesile.processes.transaction.log.common.customer.Account;

public class AccountTest 
{
	private Account account;
	private String accountNo;
	private Integer balance;
	private String dateOfAccountOpen;
	
	
	
	
	
	@Before
	public void setup()
	{
		this.accountNo = "ACCOUNT.1";
		this.dateOfAccountOpen = "20181203";
		this.balance = 1000;
		
		this.account = new Account( accountNo, balance, dateOfAccountOpen);
	}
	
	
	
	
	
	@Test
	public void testGetAccountNo() 
	{
		assertThat("ACCOUNT.1", is( this.account.getAccountNo() ));
	}

	
	
	@Test
	public void testGetBalance() 
	{
		assertThat(1000, is( this.account.getBalance() ));
	}

	
	
	@Test
	public void testSetBalance() 
	{
		this.account.setBalance(2000);
		assertThat(2000, is( this.account.getBalance() ));
	}

	
	
	@Test
	public void testGetDateOfAccountOpen() 
	{
		assertThat("20181203", is( this.account.getDateOfAccountOpen() ));
	}

	
}
